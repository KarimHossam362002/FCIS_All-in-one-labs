#define F_CPU 16000000UL
#include "FreeRTOS.h"
#include "task.h"
#include "queue.h"
#include "USART.h"
#include <avr/io.h>
#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#define MAX_NUMBERS       10
#define UART_TERMINATE        ((int)0x7FFF) /*tell the receiver “no more data.”*/
#define QUEUE_LENGTH      (MAX_NUMBERS + 1)

static void vSenderTask(void *pvParameters);
static void vReceiverTask(void *pvParameters);

static QueueHandle_t xQueue = NULL;
//////////////// GPT /////////////////////////////////
static inline void uart_puts(const char *s) { // print string
	USART_sendstr((unsigned char*)s);
}
static inline void uart_putc(char c) { // print characters on UART
	USART_send((unsigned char)c);
}
static void uart_putl(long v) { // print integers on UART
	char b[22]; sprintf(b, "%ld", v); uart_puts(b);
}
// HELPER FUNCTION for convert int to string or buffer of characters
static int usart_try_read_int(long *out)
{
	static char buf[16];
	static uint8_t idx = 0;
	/* If character waiting in UART */
	if (UCSR0A & (1<<RXC0)) {
		unsigned char c = UDR0;
		USART_send(c);
		if (c=='\r' || c=='\n' || isspace(c)) {
			if (idx == 0) return 0;
			buf[idx] = '\0';
			idx = 0;
			char *endp = NULL;
			long v = strtol(buf, &endp, 10);
			if (endp != buf) { *out = v; return 1; }
			return 0;
			} else if (idx < sizeof(buf)-1) {
			if (idx==0 && (c=='+' || c=='-')) { buf[idx++] = c; }
			else if (isdigit(c)) { buf[idx++] = c; }
			/* else ignore non-digit */
		}
	}
	return 0;
}
//////////////// END GPT /////////////////////////////////

int main(void)
{
	USART_init();
	// TO DO 1
	// create the Task with queue length 10+1 and size of int
	
	if (xQueue == NULL) {
		uart_puts("Queue create failed\r\n");
		for (;;) {}
	}
	// TO DO 2
	// Create Two Tasks sender and receiver
	
	vTaskStartScheduler();
	for (;;) {}
}

static void vSenderTask(void *pvParameters)
{
	(void)pvParameters;
	for (;;) {
		
		uint8_t count = 0; // for calculating avg later
		for (;;) {
			long value;
			if (usart_try_read_int(&value)) {
				if (value == 0 || count >= MAX_NUMBERS) {
					int end = UART_TERMINATE;
					xQueueSendToBack(xQueue, &end, 0);
					
					break;
					} else {
					// TO DO 3
					// initialize lValueToSend to equal value
					
					// TO DO 4
					// Insert Back
					
					count++;
					
				}
			}
			vTaskDelay(pdMS_TO_TICKS(5));
		}
		vTaskDelay(pdMS_TO_TICKS(50));
	}
}

static void vReceiverTask(void *pvParameters)
{
	(void)pvParameters;
	for (;;) {
		// TO DO 5
		// init sum = 0;
		
		// TO DO 6
		// init count = 0
		
		for (;;) {
			int lValueToReceive;
			if (xQueueReceive(xQueue, &lValueToReceive, portMAX_DELAY) == pdPASS) {
				if (lValueToReceive == UART_TERMINATE) break;
				
				// TO DO 7
				// else calculcate sum = sum + lValueToReceive
				
				// TO DO 8
				// increment count by 1
				
			}
		}
		if (count == 0) {
			// TO DO 9 (Optional)
			//using uart_puts(""); print a message that no numbers entered
			} else {
			// TO DO 10
			// calulcate Avg
			
		}
	}
}