#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

/**********************************************************************
** Outils de synchronisation.
**********************************************************************/
pthread_mutex_t mutex     = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t  condition = PTHREAD_COND_INITIALIZER;

/**********************************************************************
** Définition des couleurs et du nombre de peintres
**********************************************************************/
typedef enum {LIBRE, PRISE} OCCUPATION;
char* occupation_name[] = {"LIBRE", "PRISE"};

#define NB_PHILO  (3)
OCCUPATION fourchettes[NB_PHILO] = {LIBRE, LIBRE};
int nbPhilo = 0;

/**********************************************************************
** Chaque peintre va récupérer un numéro unique et une couleur
** et peindre trois fois en alternant les couleurs.
**********************************************************************/
void* painter (void* _unused) {
	int i;
    int my_number;

    /*On pose les fourchettes */
    pthread_mutex_lock(&mutex);
    my_number = (nbPhilo++);
    pthread_mutex_unlock(&mutex);

   	int f_gauche = my_number;
	int f_droite = (my_number + 1) % NB_PHILO;
	
	printf("f%d = %d %d\n", my_number, f_gauche, f_droite);
	
	sleep(1);

    /* Chaque philosophe mange 3 bouchées */
    for(i=0; (i < 3); i++) {
    

		for (int j = 0; j < NB_PHILO; ++j) {
			printf("f%d = %s | ", j, occupation_name[fourchettes[j]]);
		}

		printf("philo %d se met à penser\n", my_number);

        /* il faut attendre pour avoir ses fourchettes */
        pthread_mutex_lock(&mutex);
        while (fourchettes[f_gauche] != LIBRE || fourchettes[f_droite] != LIBRE) {
					printf("philo %d cherche la fourchette !\n", my_number);
            /* je m'endors car la condition est fausse (une des fourchettes est prise)*/
            pthread_cond_wait(&condition, &mutex);
        }

		fourchettes[f_gauche] = PRISE;
		fourchettes[f_droite] = PRISE;

        printf("philo %d prend les fourchettes\n", my_number);
		pthread_cond_broadcast(&condition);
		pthread_mutex_unlock(&mutex);

        printf("philo %d mange\n", my_number);
        sleep(1);

		pthread_mutex_lock(&mutex);
		fourchettes[f_gauche] = LIBRE;
		fourchettes[f_droite] = LIBRE;
		printf("philo %d rend les fourchettes\n", my_number);
        pthread_cond_broadcast(&condition);
        pthread_mutex_unlock(&mutex);

		sleep(1);
    }
 
    /* pas de résultat */
    return NULL;
}

/**********************************************************************
** Initialiser les peintres et attendre la fin du travail.
**********************************************************************/



int main (void) {
    pthread_t philo[NB_PHILO];
    int i;
    
    for(i=0; (i < NB_PHILO); i++) {
        if (pthread_create(&philo[i], NULL, painter, NULL)) {
            perror("thread");
            return (EXIT_FAILURE);
        }
    }

    for(i=0; (i < NB_PHILO); i++) {
        if (pthread_join(philo[i], NULL)) {
            perror("pthread_join");
            return (EXIT_FAILURE);
        }
    }

    printf("Fin du pere\n") ;
    return (EXIT_SUCCESS);
}
