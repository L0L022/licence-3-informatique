
#ifndef __SYSTEM_H
#define __SYSTEM_H


#define MAX_PROCESS  (20)   /* nb maximum de processus  */

#define EMPTY         (0)   /* processus non-prêt       */
#define READY         (1)   /* processus prêt           */

/**********************************************************
** initialisation du système
***********************************************************/

PSW system_init(void);

/**********************************************************
** appel du système (traitement des interruptions)
***********************************************************/

PSW process_interrupt(PSW m);


enum { SYSC_EXIT, SYSC_PUTI, SYSC_NEW_THREAD };

#endif

