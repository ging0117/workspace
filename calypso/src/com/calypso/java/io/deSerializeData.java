package com.calypso.java.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class deSerializeData {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		deSerializeData();
		

	}
	public static void deSerializeData() throws IOException, ClassNotFoundException {
		FileInputStream foo = new FileInputStream("Sen.txt");
		ObjectInputStream os = new ObjectInputStream(foo);
		Object obj = os.readObject();
		//downcasting
		UserSerializable u= (UserSerializable)obj;

		System.out.println(u.ssn);
		System.out.println(u.name);
		os.close();
		foo.close();

	
	
	
	}
	
	
}
