package com.calypso.training.exception;

public class Customer {
	public void transferMoney() throws InvalidException{
		
		
		TransferMoney obj= new TransferMoney();
		obj.transfer(123456, 20012345, 2003945);
		
	}
	
	
public void transferMoneycatch() throws InvalidException, ServerInvalidException{
		
		
		TransferMoney obj= new TransferMoney();
		try{//do
		obj.transfer(123456, 20012345, 2003945);
		}catch(InvalidException e){
			
			//log the exception to log file
			
			e.printStackTrace();
			
			throw new ServerInvalidException(e.getMessage());
			
		}
	
		
		
		
	}


	//last in first out
	public static void main(String args[]) throws InvalidException, ServerInvalidException{
		Customer obj= new Customer();
		//obj.transferMoney();
		obj.transferMoneycatch();
		
		
	}
	

}
