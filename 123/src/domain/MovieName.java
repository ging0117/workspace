package com.verizon.zoetool.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class MovieName {
	@NotEmpty (message = "Please select a environment!")
	String env;
	@NotEmpty (message = "Movie Name can not be empty!")
	String moviename;	

	public String getMoviename()
	{
		return moviename;
	}
	public void setMoviename(String value)
	{
		this.moviename = value.trim();
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
