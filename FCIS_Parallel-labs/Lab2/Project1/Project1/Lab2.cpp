#include <omp.h>
#include <stdio.h>
#include <stdlib.h>
#define chunksize 10
#define n 100

int main(int argc, char* argv[])
{
	int numthread, tid, i, chunk;
	int arr[n];
	for (int i = 0; i < n; i++)
	{
		arr[i] = i * 2;
	}
	chunk = chunksize;
#pragma omp parallel shared (arr,numthread,chunk) private (i,tid)
	{
		tid = omp_get_thread_num();
		if (tid == 0)
		{
			numthread = omp_get_num_threads();
			printf("Num of threads = %d\n", numthread);
		}

#pragma omp for schedule (dynamic,chunk)
		for (i = 0; i < n; i++) {
			if (arr[i] == 50) {
				printf("Thread %d found Number %d",tid,arr[i]);
			}
		}


	}
}
