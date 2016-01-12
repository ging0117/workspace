package com.verizon.zoetool.domain;

public class SCMCheckPriceRequestInfo {
	private String env;
	private String purchaseoptionid;
	private String title;
	
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
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String value)
	{
		this.title = value.trim();
	}
	public String getPurchaseoptionid()
	{
		return purchaseoptionid;
	}
	public void setPurchaseoptionid(String value)
	{
		this.purchaseoptionid = value.trim();
	}
}
