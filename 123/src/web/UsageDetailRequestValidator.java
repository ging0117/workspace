package com.verizon.zoetool.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.verizon.zoetool.domain.SCMUsageDetailRequestInfo;
import com.verizon.zoetool.utils.AppUtils;

public class UsageDetailRequestValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return SCMUsageDetailRequestInfo.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "env", "required.env");
		
		SCMUsageDetailRequestInfo info = (SCMUsageDetailRequestInfo) target;
		
		AppUtils au = new AppUtils();
		if (!au.isValidEnv(info.getEnv()))
			errors.rejectValue("env", "invalid.env");
		if (!au.isValidPCN(info.getUserid()))
			errors.rejectValue("userid", "invalid.userid");
		
		if (info.getEventseq() != null && info.getEventseq().length() > 0)
	    {
	    	String sPattern = "^[0-9]*$";
			Pattern pattern = Pattern.compile(sPattern);
			Matcher matcher = pattern.matcher(info.getEventseq());
			if (!matcher.find())
				errors.rejectValue("eventseq", "invalid.eventseq");
	    }
	}

}

