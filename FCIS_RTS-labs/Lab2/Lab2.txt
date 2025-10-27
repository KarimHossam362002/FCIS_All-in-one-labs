#include "FreeRTOS.h"
#include "task.h"
#include <avr/io.h>


void vLEDToggle(void *pvParameters);
void vFANToggle(void *pvParameters);

int main(void)
{
	DDRB = 0xFF; // PORTB as output

	xTaskCreate(vLEDToggle, "LED", 256, NULL, 2, NULL);
	xTaskCreate(vFANToggle, "FAN", 256, NULL, 1, NULL);

	vTaskStartScheduler();

	for (;;);
	return 0;
}

// Task 1
void vLEDToggle(void *pvParameters){
	TickType_t xLastWakeTime = xTaskGetTickCount();
	while(1){
		PORTB |= (1<<PORTB0);   // LED ON
		vTaskDelayUntil(&xLastWakeTime, pdMS_TO_TICKS(500));
		PORTB &= ~(1<<PORTB0);  // LED OFF
		vTaskDelayUntil(&xLastWakeTime, pdMS_TO_TICKS(500));
	}
}

// Task 2
void vFANToggle(void *pvParameters){
	TickType_t xLastWakeTime = xTaskGetTickCount();
	while(1){
		PORTB |= (1<<PORTB1);   // FAN ON
		vTaskDelayUntil(&xLastWakeTime, pdMS_TO_TICKS(1000));
		PORTB &= ~(1<<PORTB1);  // FAN OFF
		vTaskDelayUntil(&xLastWakeTime, pdMS_TO_TICKS(1000));
	}
}
