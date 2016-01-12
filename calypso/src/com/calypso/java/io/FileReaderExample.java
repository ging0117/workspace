package com.calypso.java.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderExample {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		writeContent("a.txt");
		readContent("a.txt");
	}

	private static void writeContent(String filename) throws IOException {
		FileWriter writer = new FileWriter(filename);

		BufferedWriter buf = new BufferedWriter(writer, 100);
		String data = "Doing Java class";
		buf.write(data);
		buf.flush();
		buf.close();
		writer.close();

	}

	private static void readContent(String filename) throws IOException {
		FileReader reader = new FileReader(filename);

		// use buffer to read files
		BufferedReader buf = new BufferedReader(reader, 100);
		String data = "";
		while ((data = buf.readLine()) != null) {
			System.out.println(data);

		}
		buf.close();
		reader.close();

	}

}
