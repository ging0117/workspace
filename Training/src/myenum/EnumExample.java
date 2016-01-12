package myenum;

public class EnumExample {
	
	public static void main(String[] args)
	{
		
		
		
		System.out.println("CA ID:"+AgeLimitEnum.CA_AGE_LIMIT.getSid());
		System.out.println("CA ID:"+AgeLimitEnum.TX_AGE_LIMIT.getSid());
		System.out.println("CA ID:"+AgeLimitEnum.CA_AGE_LIMIT.getPercentage());
		System.out.println("tx Bugger:"+AgeLimitEnum.TX_AGE_LIMIT.getSid());
	
		for(AgeLimitEnum enumkey:AgeLimitEnum.values())
		{
			
			System.out.println("VAL:: "+ enumkey.getSid());
		}
		
		
	}
	
	
	

}
