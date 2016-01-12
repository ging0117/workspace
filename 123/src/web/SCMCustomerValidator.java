package com.verizon.zoetool.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.verizon.zoetool.domain.SCMCustomer;
import com.verizon.zoetool.utils.AppUtils;

public class SCMCustomerValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return SCMCustomer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "env", "required.env");
		
		SCMCustomer cust = (SCMCustomer) target;
		// empty pcn and email
		if (cust.getPcn() == null || cust.getPcn().length() == 0)// Empty PCN
		{
			if (cust.getEmail() == null || cust.getEmail().length() == 0)// EMpty Email
			{
				errors.rejectValue("pcn", "required.pcnoremail");
				errors.rejectValue("email", "required.pcnoremail");
			}
		}
		else // Non-empty PCN
		{
			if (cust.getEmail() == null || cust.getEmail().length() == 0) // Empty Email
			{	
				AppUtils au = new AppUtils();
				if (!au.isValidPCN(cust.getPcn()))
					errors.rejectValue("pcn", "invalid.pcn");
			}
		}
		
		if (cust.getFulfillmentid() != null || cust.getFulfillmentid().length() > 0) // Non-Empty FulfillmentId
		{
			if (!isValidFulfillmentID(cust.getFulfillmentid()))
			{
				errors.rejectValue("fulfillmentid", "invalid.fulfillmentid");
			}
		}
	}
	
    private boolean isValidFulfillmentID(String id)
    {
    	if (id != null && id.length() == 0)
    		return true;
    	if (id != null && id.length() == 10)
    	{
    		String sPattern = "^[0-9]*$";
			Pattern pattern = Pattern.compile(sPattern);
			Matcher matcher = pattern.matcher(id);
			if (matcher.find())
				return true;
    	}
    	return false;
    }
}
