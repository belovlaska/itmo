import timeit


def lex_string(string):
    json_string = ''

    if string[0] == '"':
        string = string[1:]
    else:
        return None, string

    for c in string:
        if c == '"':
            return json_string, string[len(json_string) + 1:]
        else:
            json_string += c


def lex_number(string):
    json_number = ''

    number_characters = [str(d) for d in range(0, 10)] + ['-', 'e', '.']

    for c in string:
        if c in number_characters:
            json_number += c
        else:
            break

    rest = string[len(json_number):]

    if not len(json_number):
        return None, string

    if '.' in json_number:
        return float(json_number), rest

    return int(json_number), rest


def lex(string):
    tokens = []

    while len(string):
        json_string, string = lex_string(string)
        if json_string is not None:
            tokens.append(json_string)
            continue

        json_number, string = lex_number(string)
        if json_number is not None:
            tokens.append(json_number)
            continue

        if string[0] in [' ', '\t', '\b', '\n', '\r']:
            string = string[1:]
        elif string[0] in [',', ':', '[', ']', '{', '}']:
            tokens.append(string[0])
            string = string[1:]

    return tokens


def parse_array(tokens):
    json_array = []

    t = tokens[0]
    if t == ']':
        return json_array, tokens[1:]

    while True:
        json, tokens = parse(tokens)
        json_array.append(json)

        t = tokens[0]
        if t == ']':
            return json_array, tokens[1:]
        else:
            tokens = tokens[1:]


def parse_object(tokens):
    json_object = {}
    t = tokens[0]

    if t == '}':
        return json_object, tokens[1:]

    while True:
        json_key = tokens[0]

        if type(json_key) is str:
            tokens = tokens[1:]

        if tokens[0] == ':':
            tokens = tokens[1:]

        json_value, tokens = parse(tokens)
        json_object[json_key] = json_value

        t = tokens[0]
        if t == '}':
            return json_object, tokens[1:]
        tokens = tokens[1:]


def parse(tokens):
    t = tokens[0]
    if t == '[':
        return parse_array(tokens[1:])
    elif t == '{':
        return parse_object(tokens[1:])
    else:
        return t, tokens[1:]


def loads(string):
    return parse(lex(string))[0]


class Yaml:
    def dump(obj):
        return Yaml.create(obj).to_string()

    def __init__(self, data):
        self._data = data

    def create(obj):
        data = obj
        if isinstance(obj, list):
            data = [Yaml.create(val) for val in obj]
        elif isinstance(obj, dict):
            data = dict((key, Yaml.create(val))
                        for (key, val) in obj.items())
        return Yaml(data)

    def to_string(self, tabs=0, prefix=''):
        def pre(tabs):
            return "  " * tabs

        if (isinstance(self._data, list) or isinstance(self._data, dict)) and len(self._data) == 0:
            if isinstance(self._data, list):
                return "[]"
            else:
                return "{}"
        elif isinstance(self._data, list):
            if tabs == 0:
                return ''
            else:
                return '\n' + '\n'.join(["{}- {}".format(pre(tabs - 1), val.to_string(tabs)) for val in self._data]) + '\n'
        elif isinstance(self._data, dict):
            return '\n{}'.format(pre(tabs)).join(
                ["{}:\n{}{}".format(key, pre(tabs + 1), val.to_string(tabs + 1, prefix=' ')) if (
                    isinstance(val._data, dict)) else "{}:{}".format(key, val.to_string(tabs + 1, prefix=' '))
                 for (key, val) in self._data.items()])
        elif isinstance(self._data, str):
            return prefix + self._data
        else:
            return prefix + str(self._data)

start_time = timeit.default_timer()
inp = open("in.json", "r", encoding="utf8").read()
for i in range (100):
    t = Yaml.dump(loads((inp)))
print("time: ", timeit.default_timer() - start_time)
open("out.yaml", "w").write(t)
print("Если программа работает, лучше ее не трогать!")

