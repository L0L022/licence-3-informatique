
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "cpu.h"
#include "asm.h"
#include "systeme.h"


struct {
    PSW  cpu;               /* mot d'état du processeur */
    int  state;             /* état du processus        */
} process[MAX_PROCESS];     /* table des processus      */

int current_process = -1;   /* nu du processus courant  */

PSW make_process1() {
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

	// /!\ faire un programme par question /!\

	//begin(20);
//sysc(0, 0, SYSC_EXIT);
		    //set(R1, 0);      /* R1 = 0              */
		    //set(R2, 1000);   /* R2 = 1000           */
		    //set(R3, 5);      /* R3 = 5              */
			//set(R6, 0);
			//set(R7, 0);
			////sysc(0, 0, SYSC_EXIT);
		//label(10);           /* set label 10        */
			////load(R4, R1, -1); non il faut incrémenter l'adresse jusqu'à dépasser l'espace mémoire disponible
			//add(R6, R7, 1);
			//sysc(R6, 0, SYSC_PUTI);
		    //cmp(R1, R2);     /* AC = (R1 - R2)      */
		    //if_gt(20);       /* if (AC > 0) go 20   */
		    //nop();           /* no operation        */
		    //nop();           /* no operation        */
		    //add(R1, R3, 0);  /* R1 += (R3 + 0)      */
		    //jump(10);        /* go 10               */
		//label(20);           /* set label 20        */
		//sysc(0, 0, SYSC_EXIT);
		    ////halt();          /* poweroff            */
	//end();
    
    begin(20);
    /*** créer un thread ***/
    sysc(R1, R1, SYSC_NEW_THREAD);  /* créer un thread  */
    if_gt(10);                      /* le père va en 10 */

    /*** code du fils ***/
    set(R3, 1000);                  /* R3 = 1000    */
    sysc(R3, 0, SYSC_PUTI);         /* afficher R3  */
    nop();
    nop();

    /*** code du père ***/
    label(10);                     /* set label 10   */
    set(R3, 2000);                 /* R3 = 2000      */
    sysc(R3, 0, SYSC_PUTI);        /* afficher R3    */
    sysc(0, 0, SYSC_EXIT);         /* fin du thread  */
end();
    
    /*** valeur initiale du PSW ***/
    memset (&cpu, 0, sizeof(cpu));
    cpu.PC = 0;
    cpu.SB = 20;
    cpu.SS = 20;

    return cpu;
}

void new_process(PSW cpu) {
	++current_process;
	process[current_process].cpu = cpu;
	process[current_process].state = READY;
}

/**********************************************************
** Démarrage du système (création d'un programme)
***********************************************************/

PSW system_init(void) {
	new_process(make_process1());
	//new_process(make_process1());

	return process[current_process].cpu;
}

int new_thread(PSW cpu) {
	int child = 0;
	
	while (process[child].state != EMPTY && child < MAX_PROCESS) {
		++child;
	}
	
	cpu.AC = 0;
	process[child].cpu = cpu;
	process[child].state = READY;
	
	return child;
}

PSW sysc_new_thread(PSW cpu) {
	cpu.AC = cpu.DR[cpu.RI.i] = new_thread(cpu);
	return cpu;
}

void sysc_exit() {
	printf("exit\n");
	process[current_process].state = EMPTY;
	for (int i = 0; i < MAX_PROCESS; ++i) {
		if (process[i].state != EMPTY) {
			return;
		}
	}	
	
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
		case SYSC_NEW_THREAD:
			m = sysc_new_thread(m);
			break;
	}
}

PSW scheduler(PSW m) {
	/* sauvegarder le processus courant si il existe */
	process[current_process].cpu = m;
	//process[current_process].state = READY;

	do {
		current_process = (current_process + 1) % MAX_PROCESS;
	} while (process[current_process].state != READY);
	
	/* relancer ce processus */
	return process[current_process].cpu;
}

/**********************************************************
** Traitement des interruptions par le système (mode système)
***********************************************************/

PSW process_interrupt(PSW m) {

	printf("P%d : interuption: %d\n",current_process, m.IN);


    
    switch (m.IN) {
        case INT_SEGV:
			exit(EXIT_FAILURE);
            break;
        case INT_TRACE:
			m = scheduler(m);
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
