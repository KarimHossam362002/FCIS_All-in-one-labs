
/* FreeRTOS*/
#include "FreeRTOS.h"
#include "task.h"
#include "queue.h"
#include "semphr.h"
#include "timers.h"
#include "USART.h"
#define F_CPU 16000000UL
TimerHandle_t Timer_Function1;
void MyTimer( TimerHandle_t Timer_Function1 )
{
	/*toggle the led on portD pin 5*/
	PORTD ^= (1<<5);
}
void Task1(void* para)
{
	char in;
	uint16_t period = 100; //initial timer period in ticks
	while(1)
	{
		
	    /*
		1. receive a char from the user,then display it on terminal window. 
		
		2. implement the mentioned conditions.
		
		*/
		USART_sendstr("Type character d , i , s , r \r\n");
		char c = USART_receive();
		USART_sendstr("you Typed : \n");
		USART_send(c);
		
		if (c == 'd'){
			xTimerChangePeriod(Timer_Function1,period*2,100);
		}
		else if (c == 'i'){
			xTimerChangePeriod(Timer_Function1,period/2,100);
		}
		else if (c == 'r')
		{
			xTimerReset(Timer_Function1,100);
		}
		else if (c == 's')
		{
			xTimerStop(Timer_Function1,100);
		}
		else{
			USART_sendstr("Wrong Character , Exiting... \n");
		}

	}
}
int main(void) {
	USART_init();
	DDRD = 0xFF;
	
	/*
	1. create a auto-reload timer with periodic ticks = 100.
	
	2. start the timer.
	
	3. create a task which controls the frequency of the timer.
	
	4. start the scheduler.
	
	*/
	Timer_Function1 = xTimerCreate("Timer1",100,pdTRUE,NULL,MyTimer);
	xTimerStart(Timer_Function1,100);
	
	xTaskCreate(Task1,"Task1",256,NULL,1,NULL);
	
	vTaskStartScheduler();
	
	return 0;
}
