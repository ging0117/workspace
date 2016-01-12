package com.calypso.training.patern.singleton;

public class UserSingleTon {
	
	public String state;
	//private UserSingleTon userObj;
	//need to use static...sharing infos
	private static UserSingleTon userObj;
	private UserSingleTon(){
		
		
	}
	
	public static UserSingleTon getInstance(){
		
		
		if(userObj==null)
		{
			
			
			userObj= new UserSingleTon();
		}
		return userObj;
	}
	
	public static void main(String arg[])
	{
		
		
		UserSingleTon obj = UserSingleTon.getInstance();
		obj.state="CA";
		System.out.println("State first"+obj.state);
		
		UserSingleTon obj2 = UserSingleTon.getInstance();

		System.out.println("State second"+obj2.state);
		
	}

}
