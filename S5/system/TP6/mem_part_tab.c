#include <stdlib.h> 
#include <stdio.h> 
#include <string.h>

#include <sys/types.h> 
#include <sys/ipc.h> 
#include <sys/shm.h>

#define KEY_INFO                  0x00012347
#define KEY_DATA                  0x00012348

typedef struct {
    int size;
} TAB;

int main(void) {
    TAB *info;
    int memid_info;
	int memid_data;
	void * data;
	int data_existed = 1;

    /* création ou lien avec la zone partagée */
    memid_info = shmget(KEY_INFO, sizeof(TAB), 0700 | IPC_CREAT); 
    if (memid_info == -1) { perror("shmget"); return (EXIT_FAILURE); }
    
    /* montage en mémoire */
    info = shmat(memid_info, NULL, 0);
	
	if (info->size == 0) {
		info->size = 1;
		data_existed = 0;
	}
	printf("taille %d\n", info->size);

	/* création ou lien avec la zone partagée */
	memid_data = shmget(KEY_DATA, info->size, 0700 | IPC_CREAT);

    if (memid_data == -1) { perror("shmget"); return (EXIT_FAILURE); }

	/* montage en mémoire */
	data = shmat(memid_data, NULL, 0);

	/* affiche tableau */
	if (info->size > 0) {
		printf("data : %s\n", (char *)data);
	}

	/* augmente taille data */
	if (data_existed == 0) {
		((char *)data)[0] = '\0';
		return EXIT_SUCCESS;
	}

	int new_size = info->size + 1;

	void * data_copy = malloc(info->size);
	memcpy(data_copy, data, info->size);

	shmdt(data);

	shmctl(memid_data, IPC_RMID, NULL);

	/* création ou lien avec la zone partagée */
	memid_data = shmget(KEY_DATA, new_size, 0700 | IPC_CREAT); 

    if (memid_data == -1) { perror("shmget"); return (EXIT_FAILURE); }

	/* montage en mémoire */
	data = shmat(memid_data, NULL, 0);

	memcpy(data, data_copy, info->size);
	info->size = new_size;

	if (info->size > 1) {
		((char *)data)[info->size - 2] = 'a' + info->size - 2;
	}
	((char *)data)[info->size - 1] = '\0';

	printf("data : %s\n", (char *)data);
	/*
	printf("'");
	for (int i = 0; i < info->size; ++i) {
		printf("%d", ((char *)data)[i]);
	}
	printf("'\n");
	*/

    return (EXIT_SUCCESS); 
}
