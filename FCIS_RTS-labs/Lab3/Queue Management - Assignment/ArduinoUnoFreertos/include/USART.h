/*
 * USART.h
 *
 * Created: 10/01/2017 05:51:14 م
 *  Author: D.Randa
 */ 


#ifndef USART_H_
#define USART_H_
#include <stddef.h>


void USART_init(void);
void USART_sendstr( unsigned char* str);
void USART_send( unsigned char data);
unsigned char USART_receive(void);
int USART_readline();

#endif /* USART_H_ */