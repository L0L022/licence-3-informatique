#include <stdio.h> 
#include <stdlib.h> 
#include <pthread.h> 
#include <stdbool.h>
#include <unistd.h> 

volatile int theChar; 
volatile enum {READ,WRITE} job = READ; 


/*************************************************************
** Producteur: Lire l'entrée standard et, pour chaque
** caractère, donner le tour au consommateur.
**************************************************************/
void* read_stdin (void* argument) { 
    do { 
        while (job != READ) {     /* attendre mon tour */
            usleep(100); // noting to do
        }
        theChar = getchar(); 
        job = WRITE;              /* donner le tour */ 
    } while (theChar != EOF);     /* Ctrl-D sur une ligne vide */
    return NULL;
}


/*************************************************************
** Consommateur: Attendre son tour et, pour chaque caractère,
** l'afficher et donner le tour au producteur.
**************************************************************/
void* write_to_stdout (void* name) { 
    unsigned long cpt = 0; 
    while (true) {
        while (job != WRITE) {         /* attendre */
            cpt ++;
            usleep(100);
        }
        if (theChar == EOF) break;
        printf("cpt=%lu, car=%c\n", cpt, theChar); 
        job = READ;                    /* donner le tour */ 
    }
    return NULL;
} 


/*************************************************************
** Créer les threads et attendre leurs terminaisons.
**************************************************************/
int main (void) { 
    pthread_t read_thread, write_thread; 

    if (pthread_create(&read_thread, NULL, write_to_stdout, NULL)) { 
        perror("pthread_create"); 
        exit(EXIT_FAILURE); 
    } 
    if (pthread_create(&write_thread, NULL, read_stdin, NULL)) { 
        perror("pthread_create"); 
        exit(EXIT_FAILURE); 
    } 

    if (pthread_join(read_thread, NULL)) {
        perror("pthread_join");
        exit(EXIT_FAILURE); 
    }

    if (pthread_join(write_thread, NULL)) {
        perror("pthread_join"); 
        exit(EXIT_FAILURE); 
    }

    printf("Fin du pere\n") ;
    return (EXIT_SUCCESS);
}
