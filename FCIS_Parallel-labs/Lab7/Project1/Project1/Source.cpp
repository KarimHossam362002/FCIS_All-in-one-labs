#include <iostream>
#include <mpi.h>

using namespace std;

int main(int argc, char* argv[]) {
	MPI_Init(&argc, &argv);
	int size;
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	int rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	int n = 100;
	int N = n*size;
	double local_sum = 0.0;
	double* pi_sum = new double[size];
	double pi = 0.0;
	MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);
	for (int i = rank * n; i < (rank*n)+n; i++)
	{
		local_sum += 4/(1+(((i-0.5)/N)*((i - 0.5) / N)));
	}
	local_sum /= N;

	MPI_Gather(&local_sum, 1, MPI_DOUBLE, pi_sum, 1, MPI_DOUBLE, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		for (int i = 0; i < size; i++) {
			pi += pi_sum[i];
		}
		cout << "Pi =  " << pi << endl;
	}
	MPI_Finalize();
	return 0;
}