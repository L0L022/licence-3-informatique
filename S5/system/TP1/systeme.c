
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "cpu.h"
#include "asm.h"
#include "systeme.h"

enum { SYSC_EXIT, SYSC_PUTI, SYSC_NEW_THREAD };

#define MAX_PROCESS (20) /* nb maximum de processus */
#define EMPTY       (0)  /* processus non prêt      */
#define READY       (1)  /* processus prêt          */

struct {
    PSW cpu;             /* mot d'état du processeur */
    int state;           /* état du processus     */
} process[MAX_PROCESS];  /* table des processus   */

int current_process = -1; /* nu du processus courant */

int prog_begin_addr = 0;   /* l'adresse du programme en cours d'écriture */
int prog_extra_memory = 0; /* la mémoire à alouer en plus au programme en cours d'écriture */

/**********************************************************
** Début du programme
***********************************************************/

void begin_prog() {
    begin(prog_begin_addr);
    prog_extra_memory = 0;
}

/**********************************************************
** Début du programme avec de la mémoire libre au début
***********************************************************/

void begin_prog_with_extra_memory(int bytes) {
    begin(prog_begin_addr);
    prog_extra_memory = bytes;
    
    for (int i = 0; i < bytes; ++i) {
        nop();
    }
}

/**********************************************************
** Fin du programme
***********************************************************/

PSW end_prog() {
    PSW cpu;
    
    int instructions_count = end();
    
    /*** valeur initiale du PSW ***/
    memset (&cpu, 0, sizeof(cpu));
    cpu.PC = prog_extra_memory;
    cpu.SB = prog_begin_addr;
    cpu.SS = instructions_count;
    
    prog_begin_addr += instructions_count;
    
    return cpu;
}

PSW make_process_loop() {
    begin_prog();
            set(R2, 1000);
        label(10);
            add(R1, R2, 1000);
            nop();
            jump(10);
    return end_prog();
}

PSW make_process_segv() {
    begin_prog();
        set(R0, 0);
        set(R1, 0);
        set(R2, 1);
        label(10);
            sysc(R1, 0, SYSC_PUTI);
            load(R0, R1, 0);
            add(R1, R2, 0);
            jump(10);
    return end_prog();
}

PSW make_process_inst() {
    begin_prog();
        set(R0, INST_SYSC + 1);
        set(R1, 3);
        store(R0, R1, 0);
        nop();
        halt();
    return end_prog();
}

PSW make_process_ex2() {
    begin_prog();
        set(R1, 0);      /* R1 = 0              */
        set(R2, 1000);   /* R2 = 1000           */
        set(R3, 5);      /* R3 = 5              */
    label(10);           /* set label 10        */
        sysc(R1, 0, SYSC_PUTI);
        cmp(R1, R2);     /* AC = (R1 - R2)      */
        if_gt(20);       /* if (AC > 0) go 20   */
        nop();           /* no operation        */
        nop();           /* no operation        */
        add(R1, R3, 0);  /* R1 += (R3 + 0)      */
        jump(10);        /* go 10               */
    label(20);           /* set label 20        */
        sysc(0, 0, SYSC_EXIT);
        //halt();          /* poweroff            */
    return end_prog();
}

PSW make_process_sysc_exit() {
    begin_prog();
        nop();
        sysc(0, 0, SYSC_EXIT);
        nop();
    return end_prog();
}

PSW make_process_thread() {
    begin_prog();
        /*** créer un thread ***/
        sysc(R1, R1, SYSC_NEW_THREAD); /* créer un thread */
        if_gt(10);                     /* le père va en 10 */
        
        /*** code du fils ***/
        set(R3, 1000);               /* R3 = 1000  */
        sysc(R3, 0, SYSC_PUTI);      /* afficher R3 */
        nop();
        nop();
        
        /*** code du père ***/
        label(10);                  /* set label 10 */
        set(R3, 2000);              /* R3 = 1000    */
        sysc(R3, 0, SYSC_PUTI);     /* afficher R3  */
        sysc(0, 0, SYSC_EXIT);      /* fin du thread */
    return end_prog();
}

PSW make_process_thread2() {
    begin_prog_with_extra_memory(2);
        
        set(R0, 0); /* adresse compteur partagé */
        set(R1, 1); /* adresse stopeur partagé */
        
        /*** met compteur partagé à -1 ***/
        set(R3, -1);
        store(R3, R0, 0);
        
        /*** met stopeur partagé à 1 ***/
        set(R3, 1);
        store(R3, R1, 0);
        
        /*** créer un thread ***/
        sysc(R2, R2, SYSC_NEW_THREAD); /* créer un thread */
        if_gt(10);                     /* le père va en 10 */
        
        /*** code du fils ***/
        set(R2, 0); /* compteur */
        set(R3, 1); /* valeur à incrémenter */
        set(R4, 5); /* nombre d'itérations à faire */
        
        label(20);
            add(R2, R3, 0); /* incrémente le compteur */
            store(R2, R0, 0); /* met à jour le compteur partagé */
            
        /*** boucle de 0 à nb d'itérations ***/
        cmp(R4, R2);
        if_gt(20);
        
        /*** met compteur partagé à zéro pour terminer le thread père ***/
        set(R2, 0);
        store(R2, R1, 0);
        
        sysc(0, 0, SYSC_EXIT);
        
        /*** code du père ***/
        label(10);
        set(R3, 0);
        label(15);
            load(R2, R0, 0); /* récupère le compteur partagé */
            sysc(R2, 0, SYSC_PUTI); /* affiche le compteur partagé */
            
        /*** boucle tant que le stopeur est sup à zéro ***/
        load(R2, R1, 0);
        cmp(R2, R3);
        if_gt(15);
        
        sysc(0, 0, SYSC_EXIT);
    return end_prog();
}

/**********************************************************
** Ajoute un processus
***********************************************************/

int add_process(PSW m) {
    int i = 0;
    
    while (process[i].state != EMPTY) {
        ++i;
    }
    
    process[i].cpu = m;
    process[i].state = READY;
    
    return i;
}

/**********************************************************
** Démarrage du système (création d'un programme)
***********************************************************/

PSW system_init(void) {
    printf("Booting\n");
    
    for (int i = 0; i < MAX_PROCESS; ++i) {
        process[i].state = EMPTY;
    }
    
    current_process = add_process(make_process_thread2());
//     add_process(make_process_thread());
//     add_process(make_process_inst());
//     add_process(make_process_sysc_exit());
//     add_process(make_process_ex2());
//     add_process(make_process_thread());
//     add_process(make_process_thread2());
//     add_process(make_process_segv());
    
    return process[current_process].cpu;
}


void print_interruption(WORD interruption) {
    static char* interrupts[] = {"NONE","SEGV","INST","TRACE","SYSC"};
    printf("Interruption : %s\n", interrupts[interruption]);
}

void sysc_exit() {
    process[current_process].state = EMPTY;
    printf("process %d exit\n", current_process);
    
    for (int i = 0; i < MAX_PROCESS; ++i) {
        if (process[i].state != EMPTY) {
            return;
        }
    }
    
    printf("System poweroff\n");
    exit(EXIT_SUCCESS);
}

void sysc_puti(PSW m) {
    printf("R%d = %d\n", m.RI.i, m.DR[m.RI.i]);
}

PSW scheduler(PSW m) {
    process[current_process].cpu = m;
    
    do {
        current_process = (current_process + 1) % MAX_PROCESS;
    } while (process[current_process].state != READY);
    
    return process[current_process].cpu;
}

int new_thread(PSW cpu) {
	int child = 0;
	
	while (process[child].state != EMPTY) {
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

PSW process_sysc(PSW m) {
    switch (m.RI.ARG) {
        case SYSC_EXIT:
            sysc_exit();
            m = scheduler(m);
            break;
        case SYSC_PUTI:
            sysc_puti(m);
            break;
        case SYSC_NEW_THREAD:
            m = sysc_new_thread(m);
            break;
        default:
            printf("Unknown system call : %d\n", m.RI.ARG);
            exit(EXIT_FAILURE);
    }
    return m;
}



/**********************************************************
** Traitement des interruptions par le système (mode système)
***********************************************************/

PSW process_interrupt(PSW m) {
    printf("\n______ P%d ______\n", current_process);
    
    //dump_cpu(m);
    //print_interruption(m.IN);
    
    switch (m.IN) {
        case INT_SEGV:
            printf("SEGV\n");
            sysc_exit();
            m = scheduler(m);
            break;
        case INT_TRACE:
            //dump_cpu(m);
            m = scheduler(m);
            break;
        case INT_INST:
            printf("INST\n");
            sysc_exit();
            m = scheduler(m);
            break;
        case INT_SYSC:
            m = process_sysc(m);
            break;
        case INT_KEYBOARD:
            break;
        default:
            break;
    }
    return m;
}
