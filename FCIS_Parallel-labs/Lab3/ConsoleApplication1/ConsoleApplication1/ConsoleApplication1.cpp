#include <iostream>
#include <omp.h>

using namespace std;

int main()
{
    int N_total = 1000000000;
    int N_inner = 0;
    double x, y;
    double pi;
    srand(time(NULL));
#pragma omp parallel for default(shared) private(x,y) reduction(+:N_inner)
    
    for (int i = 0; i < N_total; i++)
    {
        
        x = double(rand() % 100 / 100.0);
        y = double(rand() % 100/ 100.0);
        
        if ((x - 0.5) * (x - 0.5) + (y - 0.5) * (y - 0.5) <= 0.25) {
            N_inner++;
            //cout << N_inner << endl;
        }
        
    }
    pi = 4.0 * (double)N_inner / (double)N_total;
    cout << "Value of pi is : " << pi << endl;
    system("pause");
    return 0;
}
