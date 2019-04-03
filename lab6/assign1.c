#include "stdio.h"
#include "string.h"
#include "pthread.h"
#include "semaphore.h"
#include "stdlib.h"
#include "unistd.h"
#define BUFFER_SIZE 16

int buffer[BUFFER_SIZE];
sem_t mutex,wrt;
int readcnt;
int n=0;
int m=0;
pthread_t readerThread[50],writerThread[50];

void init()
{
	sem_init(&mutex,0,1);
	sem_init(&wrt,0,1);
    readcnt=0;
	
}

void *writer(void *param)
{
    int wrtCnt=0;
	do {
    // writer requests for critical section
    sem_wait(&wrt);  
   
    // performs the write
    n++;
    m++;
    wrtCnt++;
    // leaves the critical section
    sem_post(&wrt);

    } while(wrtCnt<=50000000);
}

void *reader(void *param)
{
    int cnt=0;
	do {
    
   // Reader wants to enter the critical section
   sem_wait(&mutex);

   // The number of readers has now increased by 1
   readcnt++;                          

   // there is atleast one reader in the critical section
   // this ensure no writer can enter if there is even one reader
   // thus we give preference to readers here
   if (readcnt==1)     
      sem_wait(&wrt);                    

   // other readers can enter while this current reader is inside 
   // the critical section
   sem_post(&mutex);                   

   // current reader performs reading here
   if(cnt%1000000==0)
   {
       printf("n is %d\n"+n);
       printf("m is %d\n",m);
   }
   cnt++;
   sem_wait(&mutex);   // a reader wants to leave

   readcnt--;

   // that is, no reader is left in the critical section,
   if (readcnt == 0) 
       sem_post(&wrt);         // writers can enter

   sem_post(&mutex); // reader leaves

} while(cnt<=50000000);
}

int main()
{
	init();
	int no_of_writers=2;
    int no_of_readers=2;
	int i;
	for(i=0;i<no_of_writers;i++)
	{
		pthread_create(&writerThread[i],NULL,writer,&i);
	}
	for(i=0;i<no_of_readers;i++)
	{
		pthread_create(&readerThread[i],NULL,reader, &i);
	}
	for(i=0;i<no_of_writers;i++)
	{
		pthread_join(writerThread[i],NULL);
	}
	for(i=0;i<no_of_readers;i++)
	{
		pthread_join(readerThread[i],NULL);
	}

    printf("Final value of n is %d\n",n);
    printf("Final value of m is %d\n",m);
}