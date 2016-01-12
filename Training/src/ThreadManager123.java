

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager123 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//ArrayList is a class
		//needs to initialize types of lists
		List threadlist = new ArrayList();
	
		for (int i = 0; i < 500; i++) {

			UserThread obj = new UserThread(1000000l + i);
			// Thread is parent..obj is child
			Thread threadObj = new Thread(obj);

			threadObj.setName("Caplyso" + i);

			threadlist.add(threadObj);

			if (i > 50 && i < 200) {
				threadObj.setPriority(Thread.MAX_PRIORITY);

			}
			threadObj.start();

		}

		int runningThreads = 0;

		do {
			runningThreads = 0;
			for (Object threadObject : threadlist) {
				// Object is parent(threadObject) and need to downcasting to child(thread) .....downcasting
				Thread s = (Thread) threadObject;
				if (s.isAlive()) {

					runningThreads++;
				}
			}

		} while (runningThreads != 0);
		
		Thread.currentThread().sleep(5000);
			System.out.println("All thread finished" + Thread.currentThread());
	executorsImplement();
	
	}
	
	
	public static void callabbleImpelment(){
		ExecutorService service = Executors.newFixedThreadPool(10);
	ListeFuture<loop>
	
	
}
	
	
	public static void executorsImplement() {
		
		ExecutorService service = Executors.newScheduledThreadPool(10);
		
		 for(int i=0;i<500;i++)
		 {
			 
			 
			 UserThread obj= new UserThread(1000000l+i);
			 service.execute(obj);
			 
		 }
		 
		 service.shutdown();
		 do{
		 }while(!(service.isTerminated()));
		
		 System.out.println("All thread finished"+Thread.currentThread());
			 
			 
		 
	}
	
	
	
	
	
	
	
	
	
	
	
	}
	
