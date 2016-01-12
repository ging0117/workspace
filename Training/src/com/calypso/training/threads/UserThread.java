package com.calypso.training.threads;

public class UserThread implements Runnable {
	
	private long num;
	public UserThread (Long number){
		this.num = number;
	}
	
	
	@Override
	public void run() {
		long sum = 0;
		for (long i = 0; i < num; i ++){
			sum += i;
		}
		System.out.println("Total is :: "+ sum + " for :: " + Thread.currentThread());

	}

}
