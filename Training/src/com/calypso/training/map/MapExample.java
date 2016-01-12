package com.calypso.training.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MapExample {

	static Map<Integer, String> dataMap = new ConcurrentHashMap<>();

	// static is using class
	private static void populateMap() {
		dataMap.put(23, "Fremont");
		//dataMap.put(null, null);
		dataMap.put(4, "NewWork");
		dataMap.put(23, "fremont");

	}

	public static void fetchByKeys() {

		Set<Integer> keySet = dataMap.keySet();
		for (int data : keySet) {

			System.out.println("Key" + data);
			System.out.println("Value" + dataMap.get(data));

		}

	}

	public static void fetchByEntries(){
		
		Set<Entry<Integer, String>> entrySet= dataMap.entrySet();
		Iterator<Entry<Integer,String>> itr=entrySet.iterator();
		while(itr.hasNext()){
			dataMap.put(27, "SJC");
			Entry<Integer,String> entry=itr.next();
		
			System.out.println("Key"+entry.getKey());
			System.out.println("Value"+entry.getValue());
		
	//	dataMap.remove(23);
		
		
		}
	}
	
	
	public static void main(String[] args) {

		populateMap();
		//fetchByKeys();
		fetchByEntries();
	}
}
