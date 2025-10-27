#include <iostream>
#include <omp.h>
#define chunkSize 5
using namespace std;

int main() {
    int a[20], b[20], i;
    int result = 0;

  
#pragma omp parallel for shared(a, b) private(i)
    for (i = 0; i < 20; i++) {
        a[i] = i;
        b[i] = i;
    }

    cout << "array filled successfully" << endl;

  
#pragma omp parallel for private(i) shared(a, b) schedule(static, chunkSize) reduction(+:result)
    for (i = 0; i < 20; i++) {
        result += a[i] * b[i];
    }

    cout << "Sum: " << result << endl;

    system("pause");
    return 0;
}
