#include "FreeRTOS.h"
#include "task.h"
#include <avr/io.h>


int main(void)
{


	// Start scheduler
	vTaskStartScheduler();

	for (;;);
	return 0;
}



