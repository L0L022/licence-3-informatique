
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "cpu.h"
#include "asm.h"
#include "systeme.h"




/**********************************************************
** Démarrage du système (création d'un programme)
***********************************************************/

PSW system_init(void) {
    PSW cpu;

    printf("Booting\n");
    /*** création d'un programme ***/
    
    /*begin(20);
            set(R2, 1000);
        label(10);
            add(R1, R2, 1000);
			//write_mem(20, 60);
            nop();
            jump(10);
    end();*/

	begin(20);
		    set(R1, 0);      /* R1 = 0              */
		    set(R2, 1000);   /* R2 = 1000           */
		    set(R3, 5);      /* R3 = 5              */
			//sysc(0, 0, SYSC_EXIT);
		label(10);           /* set label 10        */
			sysc(R1, 0, SYSC_PUTI);
		    cmp(R1, R2);     /* AC = (R1 - R2)      */
		    if_gt(20);       /* if (AC > 0) go 20   */
		    nop();           /* no operation        */
		    nop();           /* no operation        */
		    add(R1, R3, 0);  /* R1 += (R3 + 0)      */
		    jump(10);        /* go 10               */
		label(20);           /* set label 20        */
		    halt();          /* poweroff            */
	end();
    
    /*** valeur initiale du PSW ***/
    memset (&cpu, 0, sizeof(cpu));
    cpu.PC = 0;
    cpu.SB = 20;
    cpu.SS = 20;

    return cpu;
}

void sysc_exit() {
	exit(EXIT_SUCCESS);
}

void sysc_puti(PSW m) {
	printf("registre : %d\n", m.DR[m.RI.i]);
}

void process_sysc(PSW m) {
	switch (m.RI.ARG) {
		case SYSC_EXIT:
			sysc_exit();
			break;
		case SYSC_PUTI:
			sysc_puti(m);
			break;
	}
}

/**********************************************************
** Traitement des interruptions par le système (mode système)
***********************************************************/

PSW process_interrupt(PSW m) {

	printf("interuption: %d\n", m.IN);
    
    switch (m.IN) {
        case INT_SEGV:
			exit(EXIT_FAILURE);
            break;
        case INT_TRACE:
			//dump_cpu(m);
            break;
        case INT_INST:
			exit(EXIT_FAILURE);
            break;
        case INT_SYSC:
			process_sysc(m);
            break;
        case INT_KEYBOARD:
            break;
        default:
            break;
    }
    return m;
}
