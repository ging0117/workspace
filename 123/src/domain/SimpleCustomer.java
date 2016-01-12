package com.verizon.zoetool.domain;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class SimpleCustomer {
	@NotEmpty (message = "Please select a environment!")
	String env;
	@NotEmpty (message = "PCN can not be empty!")
	@Pattern (regexp = "^[0-9]*$", message = "Invalid PCN")
	@Size (min = 14, max = 14, message = "invalid PCN")
	String pcn;	
	
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
}
