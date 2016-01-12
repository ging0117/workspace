package com.calypso.java.collection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FileCollectionExample {
	private static Map<Integer, String> userMap = new HashMap<>();

	private static void populateReader() {
		FileWriter writer = null;
		BufferedWriter bf = null;
		try {
			writer = new FileWriter("UserFilter.txt");
			bf = new BufferedWriter(writer, 50);
			Set<Entry<Integer, String>> e = userMap.entrySet();
			Iterator<Entry<Integer, String>> i = e.iterator();
			while (i.hasNext()) {
				Entry<Integer, String> d = i.next();
				String data = d.getKey() + "," + d.getValue();
				bf.write(data);
				bf.write("\n");

			}
			bf.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (writer != null) {

				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	public static void main(String[] args) {
		createFile();
		filterUsers();
		populateReader();
	}

	private static void filterUsers() {

		FileReader reader = null;
		BufferedReader bf = null;
		try {
			reader = new FileReader("User.txt");
			bf = new BufferedReader(reader, 50);
			String data;

			while ((data = bf.readLine()) != null) {
				String[] dAm = data.split(",");
				int age = Integer.parseInt(dAm[2]);
				if (age < 30) {
					System.out.println(dAm[0]);
					int id = Integer.parseInt(dAm[0]);

					userMap.put(id, dAm[1]);

				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void createFile() {

		// TODO Auto-generated method stub
		FileWriter writer = null;
		BufferedWriter buf = null;

		try {
			writer = new FileWriter("User.txt");
			buf = new BufferedWriter(writer);
			buf.write("11,John,23\n");
			buf.write("12,Mary,54\n");
			buf.write("13,Leo,25");
			// tell jvm ...try to finish the project
			buf.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (writer != null) {

				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}
}
