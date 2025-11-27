/* ===================== ArduinoUnoFreertos.c ===================== */
#define F_CPU 16000000UL
/* FreeRTOS includes */
#include "FreeRTOS.h"
#include "task.h"
#include "queue.h"
/* Lab3 USART driver */
#include "USART.h"
#include <avr/io.h>
#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
/* ---------------- Assignment 2 Requirements ----------------
   - Read integers from serial terminal at 9600 8N1.
   - Stop when user types 0 OR after 10 numbers.
   - Use at least two tasks: sender (producer) & receiver (consumer).
   - Use a FreeRTOS queue for passing numbers.
   - Compute & print the average to the terminal.
   ---------------------------------------------------------- */
#define MAX_NUMBERS       10
#define END_MARKER        ((int16_t)0x7FFF)      
#define QUEUE_LENGTH      (MAX_NUMBERS + 1)      
#define QUEUE_ITEM_SIZE   sizeof(int16_t)
/* Tasks */
static void vSenderTask(void *pv);
static void vReceiverTask(void *pv);
/* Queue handle */
static QueueHandle_t xQueue = NULL;
/* ---------- UART helpers (using Lab3 USART driver) ---------- */
static inline void uart_puts(const char *s) {
    USART_sendstr((unsigned char*)s);
}
static inline void uart_putc(char c) {
    USART_send((unsigned char)c);
}
static void uart_putl(long v) {
    char b[22]; sprintf(b, "%ld", v); uart_puts(b);
}
/* --------- Non-blocking integer tokenizer from USART ---------
   Polls RX register directly (OK for Lab3 as driver doesn’t use RX ISR).
   Accepts optional +/-; commits on whitespace/newline.
   Echoes input.
   Returns 1 if a number parsed into *out, else 0.
   ------------------------------------------------------------- */
///////////////
static int usart_try_read_int(long *out)
{
    static char buf[16];
    static uint8_t idx = 0;
    /* If a character is waiting in UART */
    if (UCSR0A & (1<<RXC0)) {
        unsigned char c = UDR0;   /* read char */
        USART_send(c);            /* echo */
        if (c=='\r' || c=='\n' || isspace(c)) {
            if (idx == 0) return 0;      /* empty token */
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
/* ------------------------- main ------------------------- */
int main(void)
{
    USART_init();  /* 9600 8N1 in Lab3 driver */
    xQueue = xQueueCreate(QUEUE_LENGTH, QUEUE_ITEM_SIZE);
    if (xQueue == NULL) {
        uart_puts("Queue create failed\r\n");
        for (;;) {}
    }
    /* Note: on AVR, FreeRTOS stack size is in WORDS (not bytes). */
    xTaskCreate(vSenderTask,   "SND", 256, NULL, 1, NULL);
    xTaskCreate(vReceiverTask, "RCV", 256, NULL, 2, NULL);
    vTaskStartScheduler();  /* never returns if OK */
    for (;;) {}  /* safety */
}
/* --------------------- Sender Task -----------------------
   Prompts, reads integers from terminal, enqueues them.
   Stops on 0 or after MAX_NUMBERS and sends END_MARKER.
   -------------------------------------------------------- */
static void vSenderTask(void *pv)
{
    (void)pv;
    for (;;) {
        uart_puts("\r\n=== Assignment 2 ===\r\n");
        uart_puts("Type up to "); uart_putl(MAX_NUMBERS);
        uart_puts(" integers (space/newline separated).\r\n");
        uart_puts("Type 0 at any time to finish.\r\n> ");
        uint8_t count = 0;
        for (;;) {
            long value;
            if (usart_try_read_int(&value)) {
                if (value == 0 || count >= MAX_NUMBERS) {
                    int16_t end = END_MARKER;
                    xQueueSendToBack(xQueue, &end, 0);
                    uart_puts("\r\n[Input finished]\r\n");
                    break;
                } else {
                    int16_t msg = (int16_t)value;
                    xQueueSendToBack(xQueue, &msg, portMAX_DELAY);
                    count++;
                    uart_puts("\r\nOK ("); uart_putl(count);
                    uart_puts("/"); uart_putl(MAX_NUMBERS); uart_puts(")\r\n> ");
                }
            }
            vTaskDelay(pdMS_TO_TICKS(5));  /* be nice to scheduler */
        }
        vTaskDelay(pdMS_TO_TICKS(50));     /* give receiver time to print */
    }
}
/* -------------------- Receiver Task ----------------------
   Consumes numbers until END_MARKER, then prints count/sum/
   average (average printed as fixed-point with 2 decimals).
   -------------------------------------------------------- */
static void vReceiverTask(void *pv)
{
    (void)pv;
    for (;;) {
        long sum = 0;
        uint8_t n = 0;
        for (;;) {
            int16_t item;
            if (xQueueReceive(xQueue, &item, portMAX_DELAY) == pdPASS) {
                if (item == END_MARKER) break;
                sum += item;
                n++;
            }
        }
        if (n == 0) {
            uart_puts("No numbers entered. Average is undefined.\r\n");
        } else {
            /* Fixed-point average with 2 decimals (x100), rounded */
            long avg100 = (sum * 100L + n/2) / n;   /* (sum/n)*100 */
            long ip = avg100 / 100;
            long fp = labs(avg100 % 100);
            uart_puts("Count = ");   uart_putl(n);
            uart_puts(", Sum = ");   uart_putl(sum);
            uart_puts(", Average = ");
            uart_putl(ip);
            uart_putc('.');
            if (fp < 10) uart_putc('0');
            uart_putl(fp);
            uart_puts("\r\n");
        }
    }
}