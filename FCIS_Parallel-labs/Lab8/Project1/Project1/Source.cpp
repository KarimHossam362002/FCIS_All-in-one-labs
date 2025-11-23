#include <iostream>
#include <mpi.h>

using namespace std;

int main() {

	MPI_Init(NULL,NULL);

	int rank,size;
	int N = 100;
	int i;
	double sum =0.0;
	double pi = 0.0;
	double pi_total = 0.0;
	double step = 1.0/N;
	
	
	MPI_Comm_rank(MPI_COMM_WORLD,&rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	int chunk_size = N/size; //100/4 = 25
	int start = rank * chunk_size;//
	int end =start+ chunk_size ; // 0
	double x = 0.0;
#pragma omp parallel
{
	#pragma omp for private(i) reduction(+:sum)
		for ( i = start; i < end; i++)
		{
			x = (i+0.5)*step;
			sum += 4.0/(1.0+(x*x));
		}
		#pragma omp master
		{
			pi = step*sum;
		}
}
MPI_Reduce(&pi,&pi_total,1,MPI_DOUBLE,MPI_SUM,0,MPI_COMM_WORLD);

if (rank == 0){
	cout << pi_total << endl;
}

	
	

	MPI_Finalize();
	return 0;

}