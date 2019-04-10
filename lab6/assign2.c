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
pthread_t readerThread[50],writerThread[50];

void init()
{
	sem_init(&mutex,0,1);
	sem_init(&wrt,0,1);
    readcnt=0;
	
}

void *writer(void *param)
{
    int id = (int )param;
    int wrtCnt=1;
	do {
    // writer requests for critical section
    printf("Caretaker %d queues\n",id);
    sem_wait(&wrt);  
   
    // performs the write
    printf("Caretaker %d enters\n",id);
    wrtCnt++;
    sleep(2);
    // leaves the critical section
    sem_post(&wrt);
    printf("Caretaker %d exits\n",id);
    sleep(5);

    } while(wrtCnt>=0);
}

void *reader(void *param)
{
    int id = (int )param;
    int cnt=1;
	do {
    
   // Reader wants to enter the critical section
   printf("Animal %d queues\n",id);
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
   printf("Animal %d enters (%d)\n",id,readcnt);
   sleep(2);
   cnt++;
   sem_wait(&mutex);   // a reader wants to leave

   readcnt--;

   // that is, no reader is left in the critical section,
   if (readcnt == 0) 
       sem_post(&wrt);         // writers can enter

   sem_post(&mutex); // reader leaves
   printf("Animals %d exits\n",id);
   sleep(5);

} while(cnt>=0);
}

int main()
{
	init();
	int no_of_writers=5;
    int no_of_readers=5;
	int i;
	for(i=0;i<no_of_writers;i++)
	{
		pthread_create(&writerThread[i],NULL,writer,(void *)i );
        printf("Caretaker %d created\n",i);
	}
	for(i=0;i<no_of_readers;i++)
	{
		pthread_create(&readerThread[i],NULL,reader,(void *)i);
        printf("Animal %d created\n",i);
	}
	for(i=0;i<no_of_writers;i++)
	{
		pthread_join(writerThread[i],NULL);
	}
	for(i=0;i<no_of_readers;i++)
	{
		pthread_join(readerThread[i],NULL);
	}
    while(1);

}