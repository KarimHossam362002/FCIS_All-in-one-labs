#define F_CPU 16000000UL

#include "FreeRTOS.h"
#include "task.h"
#include "semphr.h"
#include "USART.h"
#include <avr/io.h>
#include <util/delay.h>
#include <stdio.h>

SemaphoreHandle_t xMutex;

void TaskA(void *pvParameters);
void TaskB(void *pvParameters);

int main(void)
{
	USART_init();

	// Create MUTEX semaphore
	xMutex = xSemaphoreCreateMutex(); 

	if(xMutex != NULL)
	{
		 // create taska
		 xTaskCreate(TaskA,"Task A",256,NULL,1,NULL);
		 //create taskb 
		xTaskCreate(TaskB,"Task B",256,NULL,1,NULL);

		vTaskStartScheduler();
	}

	while(1);
}

void TaskA(void *pvParameters)
{
	char buffer[30];

	while(1)
	{
		// take the semaphore
	if(xSemaphoreTake(xMutex , portMAX_DELAY))
	{
	sprintf(buffer, "TaskA says hello12334567891011121314151617181920!\r\n");
			USART_sendstr(buffer);

	//give the semaphore
	xSemaphoreGive(xMutex);	
	}
     // delay for 700
	 vTaskDelay(700);
		
	}
}


void TaskB(void *pvParameters)
{
	char buffer[30];

	while(1)
	{
		//take the semaphore inside the if
		if(xSemaphoreTake(xMutex , portMAX_DELAY))
		{
			sprintf(buffer, "TaskB reporting!\r\n");
			USART_sendstr(buffer);

			// give the sempahore
			xSemaphoreGive(xMutex);	
		}

	//delaay for 500
	vTaskDelay(500);
	}
}
