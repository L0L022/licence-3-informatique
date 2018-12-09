cmake_minimum_required(VERSION 2.6.0)

project(TP7 C)

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

add_executable(tp7 ${SRCS} ${HEADERS} ${OBJS})

configure_file(disk0 disk0 COPYONLY)
configure_file(sgf-impl.o.keep sgf-impl.o COPYONLY)

add_custom_target(format ${CMAKE_CURRENT_SOURCE_DIR}/format)
