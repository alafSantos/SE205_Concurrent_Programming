#ifndef CIRCULAR_BUFFER_H
#define CIRCULAR_BUFFER_H

#include <pthread.h>

typedef struct {
  int first, last, size, max_size;
  void ** buffer;
  pthread_mutex_t m;
  pthread_cond_t v_full;
  pthread_cond_t v_empty;
} circular_buffer_t;

// Allocate and initialize the circular buffer structure
circular_buffer_t * circular_buffer_init(int size);

// Remove an element from circular buffer. When empty, return NULL.
void * circular_buffer_get(circular_buffer_t * b);

// Append an element into circular buffer. When full, return 0.
int circular_buffer_put(circular_buffer_t * b, void * d);

int circular_buffer_size(circular_buffer_t * b);
#endif
