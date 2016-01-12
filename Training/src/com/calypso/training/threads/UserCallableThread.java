package com.calypso.training.threads;

import java.util.concurrent.Callable;

public class UserCallableThread implements Callable<Long> {
	
	private long num;
	public UserCallableThread (Long number){
		this.num = number;
	}
	
	@Override
	public Long call() throws Exception{
		long sum = 0;
		for (long i = 0; i < num; i ++){
			sum += i;
		}
		System.out.println("Total is ::"+ sum + "for ::"+Thread.currentThread());
		return sum;
	}
}
