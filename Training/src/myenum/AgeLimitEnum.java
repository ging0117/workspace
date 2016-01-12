
package myenum;

/**
 * 
 * 
 * @author ging0117
 *
 */


public enum AgeLimitEnum {
	
	CA_AGE_LIMIT(1,"223",94555),
	TX_AGE_LIMIT(2,"333",94456);
	
	private AgeLimitEnum(int id, String percent,int zip)
	{
		
	this.sid=id;
	this.percent= percent;
	this.sid=zip;
	}
	
	private int  sid;
	private String percent;
	private int zip;
	
	public int getSid() {
		
		return sid;
	}
	public String getPercentage() {
		return percent;
	}
	public int getZip() {
		return zip;
	}
	
	
	public int addBufferId(){
		
	return this.sid+20;	
		
	}
}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

