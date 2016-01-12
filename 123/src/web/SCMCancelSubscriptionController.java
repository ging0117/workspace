package com.verizon.zoetool.web;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.verizon.zoetool.domain.SCMCustomer;
import com.verizon.zoetool.orch.account.GetCustomerReference;
import com.verizon.zoetool.orch.oauth.OAuthUtils;
import com.verizon.zoetool.service.SCMCancelSubscriptionManager;
import com.verizon.zoetool.utils.AppUtils;

@Controller
@SessionAttributes
public class SCMCancelSubscriptionController {
	private static final Logger logger = Logger.getLogger(SCMCancelSubscriptionController.class);
	
	@RequestMapping(value = "/cancelsubscription2.htm", method = RequestMethod.POST)
	public String cancelSubscription(
			@Valid @ModelAttribute("scmcustomer") SCMCustomer scmcustomer, 
			BindingResult result,
			ModelMap model,
			Principal principal)
	{
		String user = principal.getName();
		
		SCMCustomerValidator scmCustValidator = new SCMCustomerValidator();
		scmCustValidator.validate(scmcustomer, result);
		
		if (result.hasErrors())
		{
			return "cancelsubscription";
		}
		
		AppUtils.Env env = AppUtils.getEnv(scmcustomer.getEnv());
		
		// Given Email
		if (scmcustomer.getEmail().length() > 0)
		{
			// Step 1: find out PCN			
			OAuthUtils oauth = new OAuthUtils(user, env);
			String sOAuthToken = oauth.getOAuthToken(env);
			GetCustomerReference ol = new GetCustomerReference(user, env, sOAuthToken);
			String sPCN = ol.doGetCustomerReference(scmcustomer.getEmail());
			
			if (sPCN.length() == 0) // No PCN found due to invalid email
			{
				result.rejectValue("pcn", "invalid.pcnoremail");
				result.rejectValue("email", "invalid.pcnoremail");
			}
			else // Step 2: PCN found, check if it match the PCN provided by customer
			{
				// Empty PCN from Customer
				if(scmcustomer.getPcn().length() == 0)
				{
					scmcustomer.setPcn(sPCN);
				}
				// Non-empty PCN from customer, then check if these PCNs match
				else
				{
					if(!scmcustomer.getPcn().equalsIgnoreCase(sPCN)) // not match
					{
						result.rejectValue("pcn", "invalid.pcnoremail");
						result.rejectValue("email", "invalid.pcnoremail");
					}
				}
			}
		}
			
		if (result.hasErrors())
		{
			return "cancelsubscription";
		}
		
		logger.info("[user=" + user + "]\t[view=cancelsubscription2]\t[env=" + scmcustomer.getEnv() 
				+ "]\t[pcn=" + scmcustomer.getPcn() + "]\t[email=" + scmcustomer.getEmail() 
				+ "]\t[fulfillmentid=" + scmcustomer.getFulfillmentid() + "]");		
		
		SCMCancelSubscriptionManager scm = new SCMCancelSubscriptionManager(user);
		scm.getSCMViewPurchaseSubscriptionHistory(env, scmcustomer.getPcn());
		
		// Empty fulfillmentId from customer
		if (scmcustomer.getFulfillmentid() == null || scmcustomer.getFulfillmentid().length() == 0)
			scm.cancelSubscription(env, scmcustomer.getPcn(), scm.sFulfillmentID);		
		else	// Given fulfillmentId from customer
			scm.cancelSubscription(env, scmcustomer.getPcn(), scmcustomer.getFulfillmentid());
		
		model.addAttribute("customer", scmcustomer);
		model.addAttribute("scmviewpurchase", scm.sHtmlSCMViewPurchasesSubscription);
		model.addAttribute("fulfillmentid2", scm.sFulfillmentID);
		model.addAttribute("scmcancelpurchase", scm.sHtmlSCMCancelPurchase);
		
		return "cancelsubscription2";
	}
	
	@RequestMapping(value = "/cancelsubscription.htm", method = RequestMethod.GET)
	public String showForm(ModelMap model, Principal principal)
	{
		String user = principal.getName();
		logger.info("[user=" + user + "]\t[view=cancelsubscription]");
		
		return "cancelsubscription";
	}
	
	@ModelAttribute("scmcustomer")
	public SCMCustomer getSCMCustomerObject()
	{
		return new SCMCustomer();
	}
	
	@ModelAttribute("envList")
	public static Map<String, String> populateEnvList()
	{
		Map<String,String> env = new LinkedHashMap<String,String>();
		
		for(String e: AppUtils.getAllAvailableEnvs())
		{
			if(e.equalsIgnoreCase("dyn"))
				env.put("dyn", "dyn");
			if(e.equalsIgnoreCase("sit"))
				env.put("sit", "sit");
			if (e.equalsIgnoreCase("stg"))
				env.put("stg", "stg");
			if (e.equalsIgnoreCase("prd"))
				env.put("prd", "prd");
		}
		
		return env;
	}
}
