
CFLAGS=-Wall -g
HEADERS=$(wildcard *.h)

all : tp2.exe
all : testing

tp2.exe: cpu.o systeme.o asm.o simul.o
	$(CC) $(CFLAGS) -o $@ $^

testing:
	@test -e make-private && ./make-private || true

%.o: %.c $(HEADERS)
	$(CC) $(CFLAGS) -o $@ -c $<

clean:
	rm -vf *.o tp2.exe
