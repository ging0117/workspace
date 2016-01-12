package com.calypso.training.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class SaveRandonExample {
	
	public static void main(String[] args) throws IOException{
	
	Scanner sc = new Scanner(new BufferedReader(new FileReader("Input.txt"),200));
	
	sc.useDelimiter(",\\$'");
	while(sc.hasNext()){
		
		System.out.println(sc.next());
	
	}
	
	sc.close();
	//write file using files and paths of N10 package
	
	Path p=Paths.get("Input.txt");
	Charset c=Charset.forName("US-ASCII");
	try {
		BufferedWriter b=Files.newBufferedWriter(p,c);
		String data= "11,f,s\n22,t";
		data="hul23392";
		data="jwiqdq2";
		b.write(data);
		b.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//Apache common - io library
	//Asynchromous ID using Channels and Buffered in NID package

	RandomAccessFile rd= new RandomAccessFile("Input2.txt","rw");
	FileChannel fc= rd.getChannel();
	//Allocate buffer size
	
	ByteBuffer bf= ByteBuffer.allocate(52);
	//read bytes to buffer
	
	int bytesRead =fc.read(bf);
	while(bytesRead!=-1)
	{
		System.out.println("Bytes Read"+bytesRead);
		//flip buffer
		bf.flip();
		while(bf.hasRemaining()){
			
			System.out.println((char)bf.get());
		}
		bf.clear();
		bytesRead=fc.read(bf);
		
	}
	
	rd.close();
	}
}
