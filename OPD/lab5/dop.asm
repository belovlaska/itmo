ORG 0x00
WORD $SYMB0
WORD $SYMB1
WORD $SYMB2
WORD $SYMB3
WORD $SYMB4
WORD $SYMB5
WORD $SYMB6
WORD $SYMB7
WORD $SYMB8
WORD $SYMB9
WORD $SYMBmi
WORD $SYMBpl
WORD $SYMBd
WORD $SYMBu
WORD $SYMBdot
WORD $SYMBeq


ORG 0x100
SYMB0:
LD #0x00
OUT 0x10
LD #0x7E
OUT 0x10
LD #0x81
OUT 0x10
OUT 0x10
OUT 0x10
LD #0x7E
OUT 0x10
RET

SYMB1:
LD #0x00
OUT 0x10
LD #0x40
OUT 0x10
LD #0xFF
OUT 0x10
RET

SYMB2:
LD #0x00
OUT 0x10
LD #0x61
OUT 0x10
LD #0x83
OUT 0x10
LD #0x85
OUT 0x10
LD #0x79
OUT 0x10
RET

SYMB3:
LD #0x00
OUT 0x10
LD #0x81
OUT 0x10
OUT 0x10
LD #0x91
OUT 0x10
LD #0xAA
OUT 0x10
LD #0xCC
OUT 0x10
RET

SYMB4:
LD #0x00
OUT 0x10
LD #0xF0
OUT 0x10
LD #0x10
OUT 0x10
LD #0xFF
OUT 0x10
RET

SYMB5:
LD #0x00
OUT 0x10
LD #0xE1
OUT 0x10
LD #0xA1
OUT 0x10
OUT 0x10
LD #0xBF
OUT 0x10
RET

SYMB6:
LD #0x00
OUT 0x10
LD #0xFF
OUT 0x10
LD #0x91
OUT 0x10
OUT 0x10
LD #0x9F
OUT 0x10
RET

SYMB7:
LD #0x00
OUT 0x10
LD #0x81
OUT 0x10
LD #0x82
OUT 0x10
LD #0x84
OUT 0x10
LD #0xF8
OUT 0x10
RET

SYMB8:
LD #0x00
OUT 0x10
LD #0xFF
OUT 0x10
LD #0x91
OUT 0x10
OUT 0x10
LD #0xFF
OUT 0x10
RET

SYMB9:
LD #0x00
OUT 0x10
LD #0xF1
OUT 0x10
LD #0x91
OUT 0x10
OUT 0x10
LD #0xFF
OUT 0x10
RET

SYMBmi:
LD #0x00
OUT 0x10
LD #0x10
OUT 0x10
OUT 0x10
OUT 0x10
RET

SYMBpl:
LD #0x00
OUT 0x10
LD #0x10
OUT 0x10
LD #0x38
OUT 0x10
LD #0x10
OUT 0x10
RET

SYMBd:
LD #0x00
OUT 0x10
LD #0x10
OUT 0x10
LD #0x54
OUT 0x10
LD #0x10
OUT 0x10
RET

SYMBu:
LD #0x00
OUT 0x10
LD #0x10
OUT 0x10
RET


SYMBdot:
LD #0x00
OUT 0x10
LD #0x01
OUT 0x10
RET

SYMBeq:
LD #0x00
OUT 0x10
LD #0x28
OUT 0x10
OUT 0x10
OUT 0x10
RET

ORG 0x400
INPUT:
IN 0x1D
AND #0x40
BEQ INPUT
IN 0x1C
ST (SP+1)
RET



ORG 0x500
inp: WORD $INPUT
STOP: WORD 0x0E
ARRAY: WORD 0x600

START: CLA
inVU9: PUSH
CALL (inp)
LD (SP+0)
SWAB
ST (ARRAY)
POP
CMP STOP
BEQ outVU6


PUSH
CALL (inp)
LD (SP+0)
ADD (ARRAY)
ST (ARRAY)+
POP
CMP STOP
BEQ outVU6
JUMP inVU9

ARGS: WORD 0x600
outVU6: LD (ARGS)
SWAB
AND NULLF
ST NOWWORD
ST NOWSYMB
LD (NOWSYMB)
ST NOWSYMB
CALL (NOWSYMB)
LD NOWWORD
CMP STOP
BEQ EXIT

LD (ARGS)+
AND NULLF
ST NOWWORD
ST NOWSYMB
LD (NOWSYMB)
ST NOWSYMB
CALL (NOWSYMB)
LD NOWWORD
CMP STOP
BEQ EXIT
JUMP outVU6
EXIT: HLT

NOWWORD: WORD 0x00
NULLF: WORD 0xFF
NOWSYMB: WORD 0x00
