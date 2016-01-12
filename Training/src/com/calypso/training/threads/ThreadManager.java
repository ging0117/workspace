package com.calypso.training.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadManager {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		List threadList = new ArrayList();
		for (int i = 0; i < 500; i ++){
			UserThread obj = new UserThread(10000000l + i);
			Thread threadObj = new Thread(obj);
			threadObj.setName("Calypso" + i); //set name or other things you want to add before start
			
			threadList.add(threadObj);
			if (i > 50 && i < 100){
				threadObj.setPriority(Thread.MAX_PRIORITY);
			}
			threadObj.start();
		}
		int runningThreads = 0;
		do{
			runningThreads = 0;
			for (Object threadObject : threadList){
				Thread s = (Thread) threadObject;
				if (s.isAlive()){
					runningThreads ++;
				}
			}
		}while(runningThreads != 0);
		
		Thread.currentThread().sleep(5000);
		
		System.out.println("All thread finished" + Thread.currentThread());//waiting for the threads, using isAlive to make it happen
		executorImplement();
		callableImplement();
		
	}
	public static void executorImplement(){
		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 500; i ++){
			UserThread obj = new UserThread(10000000l + i);
			service.execute(obj);
		}
		
		service.shutdown();
		do{
			
		}while (! (service.isTerminated()));
		System.out.println("All thread finished" + Thread.currentThread());
		
	}
	public static void callableImplement() throws InterruptedException, ExecutionException{
		ExecutorService service = Executors.newFixedThreadPool(30);
		List<Future<Long>> datalist = new ArrayList<>();
		
		for (int i = 0; i < 500; i ++){
			UserCallableThread obj = new UserCallableThread(10000000l + i);
			Future<Long> futureData = service.submit(obj);
			datalist.add(futureData);
		}
		
		for(Future<Long> data : datalist){
			//fetching data from future obj
			System.out.println("Sum is :: "+data.get());
		}
		//executor service will not accept any more threads
		service.shutdown();
		do{
			
		}while (! (service.isTerminated()));
		System.out.println("All thread finished" + Thread.currentThread());
		
	}
	

}
