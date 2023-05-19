ORG 0x0
V0: WORD $default, 0X180
V1: WORD $default,    0X180
V2: WORD $int2,    0X180
V3: WORD $int3, 0x180
V4: WORD $default, 0X180
V5: WORD $default, 0X180
V6: WORD $default, 0X180
V7: WORD $default, 0X180

ORG 0x44
X: WORD ?

max: WORD 0x002B 		; 43, максимальное значение Х
min: WORD 0xFFD6 		; -42, минимальное значение Х

default: IRET

START: 
DI
CLA
OUT 0x1 	; Запрет прерываний для неиспользуемых ВУ
OUT 0x3
OUT 0xB
OUT 0xD
OUT 0x11
OUT 0x15
OUT 0x19
OUT 0x1D

LD #0xA  	; Загрузка в аккумулятор MR (1000|0010=1010)
OUT 5 		; Разрешение прерываний для 2 ВУ
LD #0xB 	; Загрузка в аккумулятор MR (1000|0011=1011)
OUT 7		; Разрешение прерываний для 1 ВУ

EI

PROG:
DI
LD X
INC
INC
CALL CHECK
ST X
EI	
JUMP PROG

int2: ; Обработка прерывания на ВУ-2
DI
;LD X
NOP
IN 4
NOP
OR X
ST X
NOP
EI 
IRET

int3: ; Обработка прерывания на ВУ-3
DI
; LD X
NOP
PUSH
ASL
ADD X
DEC
DEC
OUT 6
NOP
POP
NOP
EI
IRET

CHECK: ; Проверка принадлежности X к ОДЗ

CHECK_MIN: ; Если x > min переход на проверку верхней границы
CMP min
BPL CHECK_MAX
JUMP LD_MIN ; Иначе загрузка min в аккумулятор

CHECK_MAX: ; Проверка пересечения верхней границы X
CMP max ; Если x < max переход
BMI RETURN 
LD_MIN: LD min ; Загрузка минимального значения в X 
RETURN: RET ; Метка возврата из проверки на ОДЗ  