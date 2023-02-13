CFLAGS = -fopt-info-vec-optimized -std=c99 -fno-inline -msse -O3 -g -o

EXE1 = vector_reduction
EXE2 = vector_addition
EXE3 = matrix_vector_multiply

EXE = $(EXE1) $(EXE2) $(EXE3)

first:$(EXE1)
	gcc $(CFLAGS) $^ $^.c

run1: first
	./$(EXE1)

second:$(EXE2)
	gcc $(CFLAGS) $^ $^.c

run2: second
	./$(EXE2)

third:$(EXE3)
	gcc $(CFLAGS) $^ $^.c

run3: third
	./$(EXE3)

clean:
	rm -f $(EXE)