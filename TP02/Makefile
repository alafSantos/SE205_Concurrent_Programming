EXE = hello_thread
CFLAGS = -lpthread
CC = gcc

EXE:$(EXE)
	$(CC) $^.c $(CFLAGS) -o $@

run1:$(EXE)
	./$^

clean:
	rm -f $(EXE)