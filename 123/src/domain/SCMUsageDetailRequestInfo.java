package com.verizon.zoetool.domain;

public class SCMUsageDetailRequestInfo {
	private String env;
	private String userid;
	private String eventseq;
	
	public String getEnv()
	{
		return env;
	}
	public void setEnv(String value)
	{
		if (value.equalsIgnoreCase("none"))
			value = "";
		this.env = value;
	}
	public String getUserid()
	{
		return userid;
	}
	public void setUserid(String value)
	{
		this.userid = value.trim();
	}
	public String getEventseq()
	{
		return eventseq;
	}
	public void setEventseq(String value)
	{
		this.eventseq = value.trim();
	}
}
