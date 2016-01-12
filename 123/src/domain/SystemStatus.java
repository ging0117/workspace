package com.verizon.zoetool.domain;

public class SystemStatus implements Comparable<SystemStatus> {
	int step;
	int success = 0;
	String name;
	String code = "";
	String message = "";

	public int getStep()
	{
	return step;
	}
	public void setStep(int value)
	{
	this.step = value;
	}
	public int getSuccess()
	{
	return success;
	}
	public void setSuccess(int value)
	{
	this.success = value;
	}
	public String getCode()
	{
	return code;
	}
	public void setCode(String value)
	{
	this.code = value;
	}
	public String getMessage()
	{
	return message;
	}
	public void setMessage(String value)
	{
	this.message = value;
	}
	public String getName()
	{
	return name;
	}
	public void setName(String value)
	{
	this.name = value;
	}
	
	@Override
	public int compareTo(SystemStatus o) {
		return (step - o.step);
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<td>Step " + this.step + ": " + this.name + "</td>");		
		
		switch(success)
		{
		case -1:
			sb.append("<td>NO</td>");
			break;
		case 0:
			sb.append("<td>Unknown</td>");
			break;
		case 1:
			sb.append("<td>YES</td>");
			break;
		}
		
		sb.append("<td>" + this.code + "</td>");
		sb.append("<td>" + this.message + "</td>");
		
		if (success > 0)
			return sb.toString();
		else if (success == 0)
			return sb.toString().replace("<td>", "<td class=\"unknown\">");
		else
			return sb.toString().replace("<td>", "<td class=\"error\">");
	}
}
