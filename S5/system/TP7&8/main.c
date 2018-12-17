
/************************************************************
 Utilisation du SGF
 ************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>

#include "sgf-header.h"

static char * text1 =
        "Ceci est un petit texte qui occupe\n"
        "quelques blocs sur ce disque fictif.\n"
        "Le bloc faisant 128 octets, il faut\n"
        "que je remplisse pour utiliser\n"
        "plusieurs blocs.\n";

static char * text2 =
        "NOUVEAU TEXTE\n"
        "Ceci est un petit texte qui occupe\n"
        "quelques blocs sur ce disque fictif.\n"
        "Le bloc faisant 128 octets, il faut\n"
        "que je remplisse pour utiliser\n"
        "plusieurs blocs.\n"
        "NOUVEAU TEXTE\n";

static char * text3 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi cursus fermentum nulla id iaculis. Aenean sodales porttitor ipsum quis porta. Praesent at metus laoreet, ornare risus tincidunt, viverra augue. Nam porta eget arcu ac finibus. Curabitur ipsum orci, placerat vitae tellus id, tristique cursus nulla. Phasellus sed nisl leo. Morbi ornare consequat suscipit. Duis tempor sed.";

char * read(char * filename) {
    OFILE* file = sgf_open_read(filename);

    assert(file != NULL);
    assert(file->inode.size >= 0);

    char * data = malloc((size_t)file->inode.size + 1);
    assert(data != NULL);

    int i = 0, c;
    while ((c = sgf_getc(file)) > 0) {
        data[i++] = (char)c;
    }
    data[i++] = '\0';

    sgf_close(file);

    return data;
}

char * read_plus_seek(char * filename, int step) {
    OFILE* file = sgf_open_read(filename);

    assert(file != NULL);
    assert(file->inode.size > 0);

    char * data = malloc((size_t)file->inode.size + 1);
    assert(data != NULL);

    int i = 0, c;
    while ((c = sgf_getc(file)) > 0) {
        data[i++] = (char)c;
        sgf_seek(file, file->ptr += step);
    }
    data[i++] = '\0';

    sgf_close(file);

    return data;
}

void write(char * filename, char * data) {
    OFILE* file = sgf_open_write(filename);
    assert(file != NULL);
    sgf_puts(file, data);
    sgf_close(file);
}

void fat_state(unsigned int fat_state_count[5]) {
    for (int i = 0; i < 5; ++i) {
        fat_state_count[i] = 0;
    }

    for (int i = 0; i < get_disk_size(); ++i) {
        if (get_fat(i) > 0) {
            ++fat_state_count[0];
        } else {
            ++fat_state_count[abs(get_fat(i))];
        }
    }
}

void print_fat_state() {
    unsigned int fat_state_count[5];
    fat_state(fat_state_count);

    static char * fat_state_names[] = {"BLOCK", "FAT_FREE", "FAT_RESERVED", "FAT_INODE", "FAT_EOF"};

    for (int i = 0; i < 5; ++i) {
        if (i > 0) {
            printf(", ");
        }
        printf("%s: %d", fat_state_names[i], fat_state_count[i]);
    }
    printf("\n");
}

void test_read_and_write(char * original) {
    write("essai.txt", original);
    char * copy = read("essai.txt");

    assert(strcmp(original, copy) == 0);

    free(copy);
}

void test_seek_plus_one() {
    char * original = "abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababab";
    write("essai.txt", original);
    char * copy = read_plus_seek("essai.txt", 1);

    assert(strcmp("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", copy) == 0);

    free(copy);
}

void test_seek_plus_seven() {
    char * original = "abbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbbabbbbbbb";
    write("essai.txt", original);
    char * copy = read_plus_seek("essai.txt", 7);

    assert(strcmp("aaaaaaaaaaaaaaaaaaaa", copy) == 0);

    free(copy);
}

void test_remove_file() {
    static char * files_name[] = {"1", "2", "3"};

    for (int i = 0; i < 3; ++i) {
        assert(sgf_open_read(files_name[i]) == NULL);
    }

    unsigned int original_fat_state_count[5];
    fat_state(original_fat_state_count);

    for (int i = 0; i < 3; ++i) {
        write(files_name[i], text2);
    }

    for (int i = 0; i < 3; ++i) {
        OFILE * f = sgf_open_read(files_name[i]);
        assert(f != NULL);
        free(f);
    }

    for (int i = 0; i < 3; ++i) {
        sgf_remove_file(files_name[i]);
    }

    for (int i = 0; i < 3; ++i) {
        assert(sgf_open_read(files_name[i]) == NULL);
    }

    unsigned int new_fat_state_count[5];
    fat_state(new_fat_state_count);

    assert(memcmp(original_fat_state_count, new_fat_state_count, sizeof(unsigned int) * 5) == 0);
}

void test_append_empty_file() {
    char * original = text3;
    size_t original_length = strlen(original);
    write("essai.txt", "");

    for (size_t i = 0; i < original_length; ++i) {
        OFILE* file = sgf_open_append("essai.txt");
        sgf_putc(file, original[i]);
        sgf_close(file);
    }

    char * copy = read("essai.txt");

    assert(strcmp(original, copy) == 0);

    free(copy);
}

void test_append_filled_file() {
    char * original = malloc(strlen(text1) + strlen(text3));
    strcat(original, text1);
    strcat(original, text3);
    size_t original_length = strlen(original);
    write("essai.txt", text1);

    for (size_t i = strlen(text1); i < original_length; ++i) {
        OFILE* file = sgf_open_append("essai.txt");
        sgf_putc(file, original[i]);
        sgf_close(file);
    }

    char * copy = read("essai.txt");

    assert(strcmp(original, copy) == 0);

    free(copy);
    free(original);
}

void test_append_new_file() {
    char * original = text3;
    size_t original_length = strlen(original);

    sgf_remove_file("essai.txt");

    for (size_t i = 0; i < original_length; ++i) {
        OFILE* file = sgf_open_append("essai.txt");
        sgf_putc(file, original[i]);
        sgf_close(file);
    }

    char * copy = read("essai.txt");

    assert(strcmp(original, copy) == 0);

    free(copy);
}

int main() {
    init_sgf();

    test_read_and_write("");
    test_read_and_write(text1);
    test_read_and_write(text2);
    test_read_and_write(text3);

    test_seek_plus_one();
    test_seek_plus_seven();

    test_remove_file();

    test_append_empty_file();
    test_append_filled_file();
    test_append_new_file();

    return (EXIT_SUCCESS);
}
