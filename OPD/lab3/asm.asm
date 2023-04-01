ORG 0x221
arr_first: WORD 0x0235 ; адрес первого элемента
arr_cur: WORD 0xA000 ; адрес текущего элемента
arr_length: WORD 0x4000 ; Количество элементов массива
result: WORD 0xE000
START:
CLA
ST result
LD #5
ST arr_length
LD arr_first
ST arr_cur
cycle: LD (arr_cur)+
ROR ; C=1 if (arr_cur % 2 == 1)
CLA ; --------
NOT ; --------- LD result
AND result ; -
ROL ; result*2 + C
ST result
LOOP $arr_length
JUMP cycle
HLT

arr: WORD 0xADE4, 0x6589, 0x2350, 0xCCAD, 0x4441