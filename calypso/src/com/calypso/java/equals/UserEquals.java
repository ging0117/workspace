package com.calypso.java.equals;
//object is parent class (compares object values)
public class UserEquals {
	public String name;
	public int ssn;
	
	public boolean equals(Object obj)
	{
		if(obj==null)
		{
			return false;
			
		}if(!(obj instanceof UserEquals))
		{
			
			return false;
		}
		
		return true;
		
	}

}
