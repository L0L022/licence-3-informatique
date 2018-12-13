
/************************************************************
 Utilisation du SGF
 ************************************************************/

#include <stdio.h>
#include <stdlib.h>

#include "sgf-header.h"

void read_all_file(OFILE* file) {
    int c;
    while ((c = sgf_getc(file)) > 0) {
        putchar(c);
    }
}

void read_all(const char * filename) {
    printf("\nCONTENU DE ");
    printf(filename);
    printf("\n\n");

    OFILE* file = sgf_open_read("essai.txt");
    read_all_file(file);
    sgf_close(file);
}

void write(const char * filename, const char * data) {
    OFILE* file = sgf_open_write(filename);
    sgf_puts(file, data);
    sgf_close(file);
}

void fat_state() {
    static char * fat_state_names[] = {"BLOCK", "FAT_FREE", "FAT_RESERVED", "FAT_INODE", "FAT_EOF"};

    unsigned int fat_state_count[5] = {0, 0, 0, 0, 0};

    for (int i = 0; i < get_disk_size(); ++i) {
        if (get_fat(i) > 0) {
            ++fat_state_count[0];
        } else {
            ++fat_state_count[abs(get_fat(i))];
        }
    }

    for (int i = 0; i < 5; ++i) {
        if (i > 0) {
            printf(", ");
        }
        printf("%s: %d", fat_state_names[i], fat_state_count[i]);
    }
    printf("\n");
}

const char * text1 =
        "Ceci est un petit texte qui occupe\n"
        "quelques blocs sur ce disque fictif.\n"
        "Le bloc faisant 128 octets, il faut\n"
        "que je remplisse pour utiliser\n"
        "plusieurs blocs.\n";

const char * text2 =
        "NOUVEAU TEXTE\n"
        "Ceci est un petit texte qui occupe\n"
        "quelques blocs sur ce disque fictif.\n"
        "Le bloc faisant 128 octets, il faut\n"
        "que je remplisse pour utiliser\n"
        "plusieurs blocs.\n"
        "NOUVEAU TEXTE\n";

void test_file_methods() {
    sgf_remove_file("essai.txt");

    printf("\nLISTE DES FICHIERS\n\n");
    list_directory();

    fat_state();

    printf("\nessai.txt : vide\n\n");
    write("essai.txt", "");
    read_all("essai.txt");
    fat_state();

    printf("\nLISTE DES FICHIERS\n\n");
    list_directory();

    printf("\nessai.txt : text1\n\n");
    write("essai.txt", text1);
    read_all("essai.txt");
    fat_state();

    printf("\nLISTE DES FICHIERS\n\n");
    list_directory();

    printf("\nessai.txt : text2\n\n");
    write("essai.txt", text2);
    read_all("essai.txt");
    fat_state();

    printf("\nLISTE DES FICHIERS\n\n");
    list_directory();

    printf("\nessai.txt : supprime\n\n");
    sgf_remove_file("essai.txt");
    fat_state();

    printf("\nLISTE DES FICHIERS\n\n");
    list_directory();
}

void test_seek_plus_one() {
    // 240 chars
    write("essai.txt", "abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababab");

    OFILE* file = sgf_open_read("essai.txt");
    int c;
    while ((c = sgf_getc(file)) > 0) {
        putchar(c);
        sgf_seek(file, file->ptr += 1); // doit afficher 120 a
    }

    sgf_close(file);
    printf("\n");   ²
}

void test_seek_plus_eight() {
    // 160 chars
    write("essai.txt", "abbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbb");

    OFILE* file = sgf_open_read("essai.txt");
    int c;
    while ((c = sgf_getc(file)) > 0) {
        putchar(c);
        sgf_seek(file, file->ptr += 7); // doit afficher 20 a
    }

    sgf_close(file);
    printf("\n");
}

void test_seek() {
    test_seek_plus_one();
    test_seek_plus_eight();
}

void test_append_empty_file() {
    sgf_remove_file("essai.txt");

    write("essai.txt", "");

    list_directory();
    read_all("essai.txt");

    for (int i = 0; i < 600; ++i) {
        OFILE* file = sgf_open_append("essai.txt");
        sgf_putc(file, 'a' + (i % 26));
        sgf_close(file);
    }

    list_directory();
    read_all("essai.txt");
    printf("\n");
}

void test_append_filled_file() {
    sgf_remove_file("essai.txt");

    write("essai.txt", ":");

    list_directory();
    read_all("essai.txt");

    for (int i = 0; i < 600; ++i) {
        OFILE* file = sgf_open_append("essai.txt");
        sgf_putc(file, 'a' + (i % 26));
        sgf_close(file);
    }

    list_directory();
    read_all("essai.txt");
    printf("\n");
}

void test_append_new_file() {
    sgf_remove_file("essai.txt");

    list_directory();

    for (int i = 0; i < 600; ++i) {
        OFILE* file = sgf_open_append("essai.txt");
        sgf_putc(file, 'a' + (i % 26));
        sgf_close(file);
    }

    list_directory();

    read_all("essai.txt");
    printf("\n");
}

void test_append() {
    test_append_empty_file();
    test_append_filled_file();
    test_append_new_file();
}

int main() {
    init_sgf();

    test_seek();
    test_append();

    return (EXIT_SUCCESS);
}
