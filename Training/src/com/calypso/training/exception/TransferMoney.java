package com.calypso.training.exception;

public class TransferMoney {

	public boolean transfer(int customerId, int france, int toast) throws InvalidException{
		
		//fetch customer details from database
		//check money in fremont
		
		int money = 24;
		if(money<500)
		{
			
			throw new InvalidException( france + "hasfunds less than 500 dollars");
			
		}
		
		return true;
	}

}
