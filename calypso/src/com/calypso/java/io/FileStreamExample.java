package com.calypso.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStreamExample {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// createFile("Input.txt");
		// writeFile("Input.txt");
		readFile("Input.txt");

	}

	private static void createFile(String filename) throws IOException {
		File f = new File(filename);
		if (f.exists()) {
			System.out.println("File already exist");
		} else {

			f.createNewFile();
		}

	}

	// if it is not a c lass start with a lowercase word
	private static void writeFile(String filename) throws IOException {
		FileOutputStream fout = new FileOutputStream(filename);
		String data = "I hire in Fremont";
		fout.write(data.getBytes());
		fout.flush();
		fout.close();

	}

	private static void readFile(String filename) throws IOException {
		FileInputStream fin = new FileInputStream("input.txt");

		int data = 0;
		// end of file function
		while ((data = fin.read()) != -1) {
			{

				System.out.print((char) data);
			}

		}
		fin.close();

	}

}
