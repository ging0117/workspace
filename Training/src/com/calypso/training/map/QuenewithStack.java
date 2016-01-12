package com.calypso.training.map;

import java.util.Stack;

public class QuenewithStack<T> {

	Stack<T> s1= new Stack<T>();
	Stack<T> s2= new Stack<T>();
	
	public void enquene(T data){
		
		s1.push(data);
		
	}
	
public T dequene(){
	if(s2.isEmpty()){
		
		while(!s1.isEmpty()){
			
			s2.push(s1.pop());
		}
	}
	
	return s2.pop();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//get first in first out
		QuenewithStack<Integer> data = new QuenewithStack<>();
		data.enquene(24);
		data.enquene(12);
		data.enquene(36);
		data.enquene(28);
		System.out.println("data1 "+data.dequene());
		System.out.println("data2 "+data.dequene());
		

	}

}
