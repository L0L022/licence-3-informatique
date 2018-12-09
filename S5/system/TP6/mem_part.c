#include <stdlib.h> 
#include <stdio.h> 
#include <string.h>

#include <sys/types.h> 
#include <sys/ipc.h> 
#include <sys/shm.h>

#define KEY                  0x00012347

typedef struct {
    int value;
	char * tab;
} COUNTER;

int main(void) {
    COUNTER *c;
    int memid;
	char * cc;

    /* création ou lien avec la zone partagée */
    memid = shmget(KEY, sizeof(COUNTER) + 1000, 0700 | IPC_CREAT); 
    if (memid == -1) { perror("shmget"); return (EXIT_FAILURE); }
    
    /* montage en mémoire */
    c = shmat(memid, NULL, 0);

	cc = malloc(c->value += 1);
	if (c->value - 1 > 0) {
		printf("accès prem case\n");
		printf("prem case = %c\n", c->tab[0]);
		memcpy(cc, c->tab, c->value - 1);
	}
	if (c->value > 1) {
		cc[c->value - 2] = 'a' + c->value - 1;
	}
	cc[c->value - 1] = '\0';

	c->tab = cc;
    printf("compteur = %d '%s'\n", c->value, c->tab);
	printf("prem case = '%c'\n", c->tab[0]);

    return (EXIT_SUCCESS); 
}
