cmake_minimum_required(VERSION 3.1)

project(TP7&8 C)

set(SRCS
    main.c
    sgf-dir.c
    sgf-disk.c
    sgf-fat.c
    sgf-io.c
)

set(HEADERS
    sgf-header.h
    sgf-impl.h
)

set(OBJS
    sgf-impl.o
)

add_executable(tp7_and_8 ${SRCS} ${HEADERS} ${OBJS})

set_property(TARGET tp7_and_8 PROPERTY C_STANDARD 11)

configure_file(disk0 disk0 COPYONLY)
configure_file(sgf-impl.o sgf-impl.o COPYONLY)

add_custom_target(format ${CMAKE_CURRENT_SOURCE_DIR}/format)
