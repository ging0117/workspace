package com.calypso.java.caeum;

public class EnumManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(CaliforniaEnum.SUMMER.getI());
		System.out.println(CaliforniaEnum.SUMMER.getZ());
		System.out.println(CaliforniaEnum.SUMMER.getP());
for( CaliforniaEnum e: CaliforniaEnum.values())
{
	System.out.println(e.getI());
	System.out.println(e.getZ());
	System.out.println(e.getP());
	
}
		
	}

}
