package com.verizon.zoetool.domain;

import org.hibernate.validator.constraints.Email;

public class SCMCustomer {		
		String env;
		String pcn;
		
		@Email (message = "Invalid email address.")
		String email;
		
		String fulfillmentid;
		
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
			this.email = value.trim().toLowerCase();
		}
		public String getFulfillmentid()
		{
			return fulfillmentid;
		}
		public void setFulfillmentid(String value)
		{
			this.fulfillmentid = value.trim();
		}
}
