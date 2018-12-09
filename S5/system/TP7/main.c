
/************************************************************
 Utilisation du SGF
 ************************************************************/

#include <stdio.h>
#include <stdlib.h>

#include "sgf-header.h"

void simple_read() {
    OFILE* file;
    int c;

    printf("\nCONTENU DE essai.txt\n\n");
    file = sgf_open_read("essai.txt");
    while ((c = sgf_getc(file)) > 0) {
        putchar(c);
    }
    sgf_close(file);
}

void simple_write1() {
    OFILE* file = sgf_open_write("essai.txt");
    sgf_puts(file, "Ceci est un petit texte qui occupe\n");
    sgf_puts(file, "quelques blocs sur ce disque fictif.\n");
    sgf_puts(file, "Le bloc faisant 128 octets, il faut\n");
    sgf_puts(file, "que je remplisse pour utiliser\n");
    sgf_puts(file, "plusieurs blocs.\n");
    sgf_close(file);
}

void simple_write2() {
    OFILE* file = sgf_open_write("essai.txt");
    sgf_puts(file, "NOUVEAU TEXTE\n");
    sgf_puts(file, "Ceci est un petit texte qui occupe\n");
    sgf_puts(file, "quelques blocs sur ce disque fictif.\n");
    sgf_puts(file, "Le bloc faisant 128 octets, il faut\n");
    sgf_puts(file, "que je remplisse pour utiliser\n");
    sgf_puts(file, "plusieurs blocs.\n");
    sgf_puts(file, "NOUVEAU TEXTE\n");
    sgf_close(file);
}

void fat_state() {
    for (int i = 0; i < get_disk_size(); ++i) {
        printf("%d, ", get_fat(i));
    }
    printf("\n");
}

int main() {
    init_sgf();
    
    printf("\nLISTE DES FICHIERS\n\n");
    list_directory();
    
    simple_write1();
    simple_read();
    simple_write2();
    simple_read();
    
    //fat_state();

    return (EXIT_SUCCESS);
}

