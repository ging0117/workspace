package com.verizon.zoetool.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class Device {
	@NotEmpty (message = "Please select a environment!")
	private String env;
	@NotEmpty (message = "Please enter a device id!")
	private String deviceid;
	
	public String getDeviceid()
	{
		return deviceid;
	}
	public void setDeviceid(String id)
	{
		this.deviceid = id.trim();
	}
	public String getEnv()
	{
		return env;
	}
	public void setEnv(String environment)
	{
		if (environment.equalsIgnoreCase("none"))
			environment = "";
		this.env = environment;
	}
}
