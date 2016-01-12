package com.calypso.training.threads;

public class ThreadLocalExample {

	public static class myClass implements Runnable {

		private ThreadLocal<Integer> data = new ThreadLocal<Integer>();

		@Override
		public void run() {
			data.set((int)( Math.random() * 2000));
			try {

				Thread.sleep(3000);

			} catch (InterruptedException e) {
				e.printStackTrace();

			}
			System.out.println(Thread.currentThread() + "Data is ::" + data.get());
		}

	}

	public static void main(String[] args) throws InterruptedException {

		// TODO Auto-generated method stub
		
		
		
		myClass obj = new myClass();
		myClass obj2 = new myClass();
//downcasting from parent--myclass to thread(child)
		
		Thread t = new Thread(obj);
		Thread t2 = new Thread(obj2);
	
	
		t.start();
		t2.start();
	
		//until both of them finish the work and join together
		
		
		t.join();
		t2.join();

	}

}
