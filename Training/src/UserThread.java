

public class UserThread implements Runnable{
private long num;
public UserThread(long number){
	this.num = number;
	
}
	
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long sum=0;
		for(long i=0;i<num;i++)
		{
			
			sum+=i;
			
		}
		
		System.out.println("Total is" + sum +"from run():"+Thread.currentThread());
		
		
	}

}
