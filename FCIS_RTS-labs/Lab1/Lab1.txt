#include "FreeRTOS.h"
#include "task.h"
#include <avr/io.h>


void vLED1Toggle(void *pvParameters);
void vLED2Toggle(void *pvParameters);

int main(void)
{
	DDRB = 0xFF; // PORTB as output

	xTaskCreate(vLED1Toggle, "LED1", 256, NULL, 1, NULL);
	xTaskCreate(vLED2Toggle, "LED2", 256, NULL, 2, NULL);

	vTaskStartScheduler();

	for (;;);
	return 0;
}

// Task 1
void vLED1Toggle(void *pvParameters){
	TickType_t xLastWakeTime = xTaskGetTickCount();
	while(1){
		PORTB |= (1<<PORTB3);   // LED1 ON
		vTaskDelayUntil(&xLastWakeTime, pdMS_TO_TICKS(100));
		PORTB &= ~(1<<PORTB3);  // LED1 OFF
		vTaskDelayUntil(&xLastWakeTime, pdMS_TO_TICKS(100));
	}
}

// Task 2
void vLED2Toggle(void *pvParameters){
	TickType_t xLastWakeTime = xTaskGetTickCount();
	while(1){
		PORTB |= (1<<PORTB5);   // LED2 ON
		vTaskDelayUntil(&xLastWakeTime, pdMS_TO_TICKS(100));
		PORTB &= ~(1<<PORTB5);  // LED2 OFF
		vTaskDelayUntil(&xLastWakeTime, pdMS_TO_TICKS(100));
	}
}