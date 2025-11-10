

#define F_CPU 16000000UL
#define TrackSize 5
#include "FreeRTOS.h"
#include "task.h"
#include "semphr.h"
#include "USART.h"
#include "wiring.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <avr/io.h>
#include <util/delay.h>




static void producer( void *pvParameters );
static void consumer( void *pvParameters );


#define LONG_TIME 0xffff
#define TICKS_TO_WAIT    10

//-----------------------------------------------------------
TaskHandle_t TaskHandle_1;
TaskHandle_t TaskHandle_2;
QueueHandle_t Queue;
SemaphoreHandle_t sem;

int main(void)
{ 
  

  USART_init();
   /*TODO 1
    Create a binary semaphore (sem)
  */
   Queue=xQueueCreate(10,sizeof(char*));
  sem = xSemaphoreCreateBinary();
   
  if(sem != NULL){
    DDRC = 0xff;
	xTaskCreate( producer, "Producer", 256, NULL, 1, &TaskHandle_1 );
	xTaskCreate( consumer, "Consumer", 256, NULL, 3, &TaskHandle_2 );
	USART_sendstr("TESTING");
	vTaskStartScheduler();
	 }
  else
	 {
		 
	 }
	return 0;
	
	
}

static void producer( void *pvParameters )
{	
	 TickType_t xLastWakeTime = xTaskGetTickCount();
	int item; // Generate a random number as an item 
	while(1){
		if( xSemaphoreTake(sem,TICKS_TO_WAIT) == pdTRUE /* TODO 2 call an API to get/take the semaphore*/)
        {
			item= rand() % 100;
       
	
			  //TO DO 3 Produce item "send the item to the back of the Queue"
			  xQueueSendToBack(Queue,&item,TICKS_TO_WAIT);
			  char msg[50];
			  sprintf(msg, "Produced item: %d\r\n", item);
			  USART_sendstr(msg);
			  
		}
	
	// TODO 4 release the semaphore
	xSemaphoreGiveFromISR(sem,false);
	 vTaskDelay(1000 / portTICK_PERIOD_MS);

	}
}


static void consumer( void *pvParameters )
{
	TickType_t xLastWakeTime = xTaskGetTickCount();
	int recvitem;
	while (1)
	{
		if (xSemaphoreTake(sem,TICKS_TO_WAIT) == pdPASS  /* TODO 5 call an API to get/take the semaphore*/)
		{
			
			
			
				
			
			
			//TO DO 6 receive from the queue
			xQueueReceive(Queue,&recvitem,500);
			// TODO 7 print ("Consumed: %d\n", recvitem);
			char msg[50];
			sprintf(msg, "Consumed item: %d\r\n", recvitem);
			USART_sendstr(msg);
			
			
			
		}
		
		
			
	// TODO 8 release the semaphore
		xSemaphoreGiveFromISR(sem,false);
		vTaskDelay(1000 / portTICK_PERIOD_MS);
	}
	
	  
	
}
