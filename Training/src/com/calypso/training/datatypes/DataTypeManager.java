package com.calypso.training.datatypes;
/**
 * class for datatype testing
 * @author ging0117
 *@since 11/25
 *@version 1.0
 */
public class DataTypeManager {
/**
 * main method
 * 
 * @param args
 */
	
	//@override
	//public String toString(){
	//return "Calypso";
	//}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    //primitive datatype comparison
		
		int num1 =24;
		int num2=25;
		System.out.println("Primitive comparison :: "+(num1==num2));
	// Wrapper datatype comparison
		String data = new String("Fremont");
		String data2= new String("Fremont");
		System.out.println("Wrapper comparison ::"+(data==data2));
		//it will go to toString() point object Reference---DataTypeManager
		System.out.println("data::"+data);
		System.out.println("data2::"+data2);
		
		System.out.println("Datatypemanager object ::"+new DataTypeManager());
	    //Object value comparison
		System.out.println("Wrapper Value Comparison::"+(data.equals(data2)));
	   
	    //Wrapper data conversion
	    String data4 ="36";
	    int num4= Integer.parseInt(data4);
	    int num3=num1+num2+num4;
	    System.out.println("Coverted values addition::"+num3);
	    boolean data5=true;
	    String data6= String.valueOf(data5);
	    
	
	}

}
