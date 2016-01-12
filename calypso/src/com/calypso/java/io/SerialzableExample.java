package com.calypso.java.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerialzableExample {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		serializeDtat();
	}

	public static void serializeDtat() throws IOException {
		FileOutputStream foo = new FileOutputStream("Sen.txt");
		ObjectOutputStream os = new ObjectOutputStream(foo);
		UserSerializable obj = new UserSerializable();
		obj.ssn = 2234;
		obj.name = "Robert";
		os.writeObject(obj);
		os.close();
		foo.close();

	}
	
	
	

}
