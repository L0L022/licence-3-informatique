#include <stdio.h> 
#include <stdlib.h> 
#include <pthread.h> 
#include <semaphore.h> 
#include <unistd.h> 


sem_t mutex;

// usleep(1) permet de rendre la main, sans lui le premier thread libère et reprend mutex avant que le 2eme ait eu l'occasion de se lancer (il n'y a plus d'alternance entre les lignes de 0 et les lignes de 1)

/*************************************************************
** Afficher (20 fois) une ligne composée de 40 chiffres.
**************************************************************/
void* affichage (void* ptr_num) {
    int num = *((int*) ptr_num);

    for(int i = 0; i<20; i++) {
        sem_wait(&mutex); /* prologue */
            for(int j=0; j<20; j++) printf("%d",num);
            usleep(400); /* pour être sur d'avoir des problèmes */
            for(int j=0; j<20; j++) printf("%d",num);
            printf("\n");
            fflush(stdout);
        sem_post(&mutex); /* épilogue */
        usleep(1);
    }

    /* préparer un résultat (un entier) */
    int* result = malloc(sizeof(int));
    *result = (num + 10);
    return result;
}


/*************************************************************
** Initialiser le sémaphore, créer les deux threads et
** attendre leurs terminaisons.
**************************************************************/
int main (void) { 
    pthread_t fils0, fils1;
    int num0 = 0, num1 = 1;
    void *result0, *result1;
    
    sem_init(&mutex, 0, 1);

    if (pthread_create(&fils0, NULL, affichage, &num0)) { 
        perror("pthread_create"); 
        exit(EXIT_FAILURE); 
    } 
    if (pthread_create(&fils1, NULL, affichage, &num1)) { 
        perror("pthread_create"); 
        exit(EXIT_FAILURE); 
    } 

    if (pthread_join(fils0, &result0)) {
        perror("pthread_join");
        exit(EXIT_FAILURE); 
    }

    if (pthread_join(fils1, &result1)) {
        perror("pthread_join");
        exit(EXIT_FAILURE); 
    }

    printf("result 0 = %d\n", *((int*) result0));
    printf("result 1 = %d\n", *((int*) result1));
    return (EXIT_SUCCESS);
}
