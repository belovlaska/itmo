
import yaml
import json
import timeit

start_time = timeit.default_timer()
inp = open("in.json", "r", encoding="utf8").read()
for i in range (100):
    t = yaml.dump(json.loads(inp), default_flow_style=False, allow_unicode=True, sort_keys=False)
print('time: ', timeit.default_timer() - start_time)
open("out_dop1.yaml", "w").write(t)
print("Зачем изобретать велосипед, если можно на нем кататься?")