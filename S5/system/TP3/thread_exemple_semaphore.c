#include <stdio.h> 
#include <stdlib.h> 
#include <pthread.h> 
#include <stdbool.h>
#include <unistd.h> 
#include <semaphore.h> 

volatile int theChar;
#define BUFFER_SIZE         (10)
char buffer[ BUFFER_SIZE ];
int ptr_input = 0;
int ptr_output = 0;

sem_t nPLein, nVide;

/*************************************************************
** Producteur: Lire l'entrée standard et, pour chaque
** caractère, donner le tour au consommateur.
**************************************************************/
void* read_stdin (void* argument) { 
    do { 
        sem_wait(&nVide);
        theChar = getchar();
        buffer[ptr_input]=theChar;
        ptr_input = (ptr_input + 1) % BUFFER_SIZE; 
        sem_post(&nPLein);
        if (ptr_input == 9)
			sleep(1);
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
        sem_wait(&nPLein);
        if (buffer[ptr_output] == EOF) break;
        printf("cpt=%lu, car=%c\n", cpt, buffer[ptr_output]);
        ptr_output = (ptr_output + 1) % BUFFER_SIZE;
        sem_post(&nVide);
        if (ptr_output == 9)
			sleep(1);
    }
    return NULL;
} 


/*************************************************************
** Créer les threads et attendre leurs terminaisons.
**************************************************************/
int main (void) { 
    pthread_t read_thread, write_thread;
    
    sem_init(&nPLein, 0, 0);
    sem_init(&nVide, 0, BUFFER_SIZE);

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

	sem_destroy(&nPLein);
	sem_destroy(&nVide);

    printf("Fin du pere\n") ;
    return (EXIT_SUCCESS);
}
