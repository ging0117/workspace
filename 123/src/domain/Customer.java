package com.verizon.zoetool.domain;

import org.hibernate.validator.constraints.Email;

public class Customer {	
	String env;	
	String pcn;
	
	@Email (message = "Invalid email address.")
	String email;
	
	String[] zoeComponents;
	
	public String getPcn()
	{
		return pcn;
	}
	public void setPcn(String value)
	{
		this.pcn = value.trim();
	}
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
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String value)
	{
		this.email = value.trim();
	}
	public String[] getZoeComponents()
	{
		return zoeComponents;
	}
	public void setZoeComponents(String[] zoe_Components)
	{
		this.zoeComponents = zoe_Components;
	}
}
