ORG 0xE3
ARG1: WORD 0x0002
ARG2: WORD 0xFFFE
ARG3: WORD 0x0

ORG  0x1D0

RESULT: WORD 0x0

CHECK1: WORD 0x0
CHECK2: WORD 0x0
CHECK3: WORD 0x0

RES1: WORD 0x0001
RES2: WORD 0xFFFF
RES3: WORD 0x0


ORG 0x0231
START:  CALL TEST1
        CALL TEST2
        CALL TEST3
        LD #0x1
        AND CHECK1
        AND CHECK2
        AND CHECK3
        ST RESULT
STOP:   HLT 

TEST1:  
        WORD 0x90E3 ; ASR $E3
        ST CHECK1
        CMP RES1
        BEQ DONE1
		RET
DONE1: 
        LD #0x1
        ST CHECK1
        CLA 
        RET 

TEST2: 
        WORD 0x90E4 ; ASR $E4
        ST CHECK2
        CMP RES2
        BEQ DONE2
		RET
DONE2:   
        LD #0x1
        ST CHECK2
        CLA 
        RET 

TEST3:  WORD 0x90E5 ; ASR $E5
        ST CHECK3
        CMP RES3
        BEQ DONE3
        RET
DONE3:   
        LD #0x1
        ST CHECK3
        CLA 
        RET
