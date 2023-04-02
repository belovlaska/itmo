mas = []


def good(sost):
    water = 0
    for i in mas[0]:
        try:
            water = sost.index(i)
            break
        except:
            pass
    if (sost[water+2] in mas[3]) or \
            (sost[water + 2] in mas[3] and
             (sost[water+3] in mas[8] or mas[9]) and
             sost[x] in mas[21] for x in range(len(sost))):
#true if word at water+2 index in column E or
# word at water+2 index in column E and
#word at water+3 index in column J or K and
#any word in sost in column W
        return True
    else:
        return False