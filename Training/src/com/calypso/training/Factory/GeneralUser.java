package com.calypso.training.Factory;

public class GeneralUser implements UserFactoryI {
@Override
public int authenticate(int uid)
{
	

System.out.println("General user method invoked");

return 20;
}


}
