package com.calypso.training.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerialManager {

	public void serializeObject() throws IOException {
		UserSerialize obj = new UserSerialize();
		obj.ssn = 12345678;
		obj.name = "Peter";

		FileOutputStream fos = new FileOutputStream("Serialize.txt");
		ObjectOutputStream pos = new ObjectOutputStream(fos);
		System.out.println("Ssn:" + obj.ssn);
		System.out.println("mme" + obj.name);
		pos.writeObject(obj);
		pos.flush();
		
		fos.close();
		
		pos.close();
	
	}

	public void deserializeObject() throws IOException, ClassNotFoundException {

		FileInputStream fos = new FileInputStream("Serialize.txt");
		ObjectInputStream pos = new ObjectInputStream(fos);
		Object o=pos.readObject();
		UserSerialize u=(UserSerialize)o;
		UserSerialize obj = (UserSerialize)pos.readObject();
		System.out.println("Ssn:" + obj.ssn);
		System.out.println("mme" + obj.name);
		
        fos.close();
		
		pos.close();
	

	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		ObjectSerialManager s = new ObjectSerialManager();
		s.serializeObject();
		s.deserializeObject();

	}

}
