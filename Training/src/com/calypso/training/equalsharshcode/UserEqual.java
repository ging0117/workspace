package com.calypso.training.equalsharshcode;

public class UserEqual {
	
	public int id;
	public String name;
	
	
	//this one is overloading
	public boolean equals(Object obj)

	{
	
		if(obj==null||(obj instanceof UserEqual))
		{
		return false;
		
		}
		
		
			
			
		//downcasting
		UserEqual obj2=(UserEqual)obj;
		return (this.id==obj2.id&&this.name.equals(obj2.name));
	}

	@Override
	
	public int hashCode()
	{
		
		
		int hash =55;
		hash=hash +id;
		hash = hash+(name==null?0:name.hashCode());
		return id;
	}
		
		
	
	
	
}
