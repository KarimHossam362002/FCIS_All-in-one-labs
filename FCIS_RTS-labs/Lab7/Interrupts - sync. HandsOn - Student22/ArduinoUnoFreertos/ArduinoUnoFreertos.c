#define F_CPU 16000000UL
#include "FreeRTOS.h"
#include "task.h"
#include "semphr.h"
#include "USART.h"
#include <stdio.h>
#include <stdlib.h>
#include <avr/io.h>
#include <util/delay.h>

TaskHandle_t CarEnterHandle;
TaskHandle_t CarExitHandle;
SemaphoreHandle_t ParkingSem;
static void car_enter( void *pvParameters );
static void car_exit( void *pvParameters );
#define PARKING_SPOTS 5

int main(void)
{
	USART_init();

	// Create a counting semaphore with PARKING_SPOTS initial + max count
	//todo1
	ParkingSem=	xSemaphoreCreateCounting(PARKING_SPOTS,5);
	if (ParkingSem != NULL)
	{

		//todo 2
		//create 2 tasks one for car_enter and other for car_exit priority of entering is higher.
		xTaskCreate(car_enter,"Car enter",256,NULL,2,NULL);
		xTaskCreate(car_exit,"Car Exit",256,NULL,1,NULL);		
		vTaskStartScheduler();
	}

	while(1);
}


static void car_enter(void *pvParameters)
{
	while (1)
	{
		//todo 3
		// take the semaphore inside the if and put portMAX_DELAY in the second parameter
		 if (xSemaphoreTake(ParkingSem,portMAX_DELAY) )
		{
			USART_sendstr("?? Car ENTERED the parking.\r\n");
			USART_sendstr("Available spots: \r\n\n");
			char msg[50];
			//sprintf(msg, "Parking Spots Available: %d\r\n", uxSemaphoreGetCount(ParkingSem));
			USART_sendstr(msg);
			USART_sendstr("\n");
		}

		vTaskDelay(1000);  // Next car comes after 1 second
	}
}

static void car_exit(void *pvParameters)
{
	while (1)
	{
		//    todo 4 // Simulate a car leaving every 3 seconds (delay for 3000)
		vTaskDelay(3000);

		//todo5  // Release a spot  (give the semaphore)
		xSemaphoreGiveFromISR(ParkingSem,NULL);

		USART_sendstr("?? Car EXITED the parking.\r\n");
		USART_sendstr("Available spots: \r\n\n");
		char msg[50];
		
		//sprintf(msg, "Produced item: %d\r\n", uxSemaphoreGetCount(ParkingSem));
		USART_sendstr(msg);
		USART_sendstr("\n");
	}
}
