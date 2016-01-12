package com.verizon.zoetool.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.verizon.zoetool.domain.Customer;
import com.verizon.zoetool.utils.AppUtils;

public class CustomerValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "env", "required.env");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zoeComponents", "required.zoeComponents");
		
		Customer cust = (Customer) target;
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
	}

}
