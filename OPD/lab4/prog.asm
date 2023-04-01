

;	f(x)
; 	x < 0 -> return x*2 + b
;	x <= a -> return a
;	x > a -> return x*2 + b

ORG 0x081
START:
CLA
ST result
LD x
DEC
PUSH
CALL $function
POP
INC
ADD result
ST result
LD y
INC
PUSH
CALL $function
POP
ADD result
ST result
LD z
PUSH
CALL $function
POP
INC
ADD result
ST result
HLT
z: WORD 0x0
y: WORD 0x0
x: WORD 0x0
result: WORD 0x03A3


ORG 0x6A1
function:
LD &1
BMI go1
CMP q
BEQ go2
BLT go2
go1: ASL
ADD w
JUMP go3
go2: LD q
go3: ST &1
RET
q: WORD 0x018D
w: WORD 0x0089