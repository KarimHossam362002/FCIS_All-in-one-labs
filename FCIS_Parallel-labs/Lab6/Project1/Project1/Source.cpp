#include <iostream>
#include <mpi.h>

using namespace std;

int main(int argc, char* argv[]) {
	MPI_Init(&argc, &argv);
	int size;
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	int rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	
	int next = (rank + 1) % size; // from 0 to 1 we do plus 1 and % to wrap around 4 so next get +1 and get back to 0 again
    int prev = (rank - 1 + size) % size; // 3 to 2 we do minus 1 the result of prev is? -> (2+4) = 6%4 = 2 #
	
	string send_value , recv_value;
	send_value = "Message";
    
	// We need to avoid deadlocks here so make sure you send and recv and other side recv and send
	// why rank % 2 == 0?
	// the adjacent nodes are even and odd numbers so we escalate the sending and receiving for even and odd nodes
    if (rank % 2 == 0) {
        MPI_Send(&send_value, 1, MPI_INT, next, 0, MPI_COMM_WORLD);
        MPI_Recv(&recv_value, 1, MPI_INT, prev, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    } else {
        MPI_Recv(&recv_value, 1, MPI_INT, prev, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Send(&send_value, 1, MPI_INT, next, 0, MPI_COMM_WORLD);
    }

    printf("Process %d received %s from process %d\n",
           rank, recv_value, prev);


	MPI_Finalize();
	return 0;
}