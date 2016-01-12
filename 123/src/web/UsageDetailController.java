package com.verizon.zoetool.web;

import java.security.Principal;

import javax.validation.Valid;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.verizon.zoetool.domain.SCMUsageDetailRequestInfo;
import com.verizon.zoetool.service.UsageDetailManager;
import com.verizon.zoetool.utils.AppUtils;

@Controller
public class UsageDetailController{
	private static final Logger logger = Logger.getLogger(UsageDetailController.class);
	
	@RequestMapping(value = "/usagedetail.htm", method = RequestMethod.GET)
	public String retrieveCustomerData(
			@Valid @ModelAttribute("usagedetailrequest") SCMUsageDetailRequestInfo requestinfo,			
			ModelMap model,
			BindingResult result,
			Principal principal)
	{
		UsageDetailRequestValidator validator = new UsageDetailRequestValidator();
		validator.validate(requestinfo, result);
		
		if (result.hasErrors())
		{
			return "error";
		}
		
		String user = principal.getName();
		logger.info("[user=" + user + "]\t[view=usagedetail]\t[env=" + requestinfo.getEnv() + "]\t[userid=" 
		+ requestinfo.getUserid() + "]\t[eventseq=" + requestinfo.getEventseq() + "]");
		
		AppUtils.Env environment = AppUtils.getEnv(requestinfo.getEnv());
		
		UsageDetailManager udm = new UsageDetailManager(user);        
        udm.getUsageDetail(environment, requestinfo.getUserid(), requestinfo.getEventseq());

        model.addAttribute("env", environment);
        model.addAttribute("pcn", requestinfo.getUserid());
        model.addAttribute("eventseq", requestinfo.getEventseq());
        model.addAttribute("data", udm.getHtml_UsageDetail());
        
        return "usagedetail";
	}
	
	@RequestMapping(value="/error.htm", method = RequestMethod.GET)
	public String showError(ModelMap model) {
		return "error";
	}
}
