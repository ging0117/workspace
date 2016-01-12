package com.verizon.zoetool.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

import com.verizon.zoetool.domain.Customer;
import com.verizon.zoetool.orch.account.GetCustomerReference;
import com.verizon.zoetool.orch.oauth.OAuthUtils;
import com.verizon.zoetool.service.AccountManager;
import com.verizon.zoetool.utils.AppUtils;

@Controller
@SessionAttributes
public class AccountController {
	private static final Logger logger = Logger.getLogger(AccountController.class);
	
	@RequestMapping(value = "/account2.htm", method = RequestMethod.POST)
	public String cancelSubscription(
			@Valid @ModelAttribute("customer") Customer customer, 
			BindingResult result,
			ModelMap model,
			Principal principal)
	{
		String user = principal.getName();
		
		CustomerValidator custValidator = new CustomerValidator();
		custValidator.validate(customer, result);

		if (result.hasErrors())
		{
			return "account";
		}
		
		AppUtils.Env env = AppUtils.getEnv(customer.getEnv());
		
		// Given Email
		if (customer.getEmail().length() > 0)
		{
			// Step 1: find out PCN			
			OAuthUtils oauth = new OAuthUtils(user, env);
			String sOAuthToken = oauth.getOAuthToken(env);
			GetCustomerReference ol = new GetCustomerReference(user, env, sOAuthToken);
			String sPCN = ol.doGetCustomerReference(customer.getEmail());
			
			if (sPCN.length() == 0) // No PCN found due to invalid email
			{
				result.rejectValue("pcn", "invalid.pcnoremail");
				result.rejectValue("email", "invalid.pcnoremail");
			}
			else // Step 2: PCN found, check if it match the PCN provided by customer
			{
				// Empty PCN from Customer
				if(customer.getPcn().length() == 0)
				{
					customer.setPcn(sPCN);
				}
				// Non-empty PCN from customer, then check if these PCNs match
				else
				{
					if(!customer.getPcn().equalsIgnoreCase(sPCN)) // not match
					{
						result.rejectValue("pcn", "invalid.pcnoremail");
						result.rejectValue("email", "invalid.pcnoremail");	
					}
				}
			}
		}
			
		if (result.hasErrors())
		{
			return "account";
		}
		
		
		logger.info("[user=" + user + "]\t[view=account2]\t[env=" + customer.getEnv() 
				+ "]\t[pcn=" + customer.getPcn() + "]\t[email=" + customer.getEmail() + "]");
		
		String[] sComponents = customer.getZoeComponents();
		boolean bOL = false, bRB = false, bSCM = false, bVMS = false;
		for(int i = 0; i < sComponents.length; i++)
		{
			if (sComponents[i].equalsIgnoreCase("OL"))
			{
				bOL = true;
				continue;
			}
			if (sComponents[i].equalsIgnoreCase("SCM"))
			{
				bSCM = true;
				continue;
			}
			if (sComponents[i].equalsIgnoreCase("Red Box"))
			{
				bRB = true;
				continue;
			}
			if (sComponents[i].equalsIgnoreCase("VMS"))
			{
				bVMS = true;
				continue;
			}
		}
		
		AccountManager am = new AccountManager(user);
		am.getCustomerProfile(env, customer.getPcn(), bOL, bRB, bSCM, bVMS);
		
		model.addAttribute("customer", customer);
		model.addAttribute("ol", bOL);
		model.addAttribute("redbox", bRB);
		model.addAttribute("scm", bSCM);
		model.addAttribute("vms", bVMS);
        
		model.addAttribute("getcustomer", am.sHtmlGetCustomer);
		model.addAttribute("transactionhistory", am.sHtmlTransactionHistory);
		model.addAttribute("subscriptionhistory", am.sHtmlSubscriptionHistory);
		model.addAttribute("refundhistory", am.sHtmlRefundHistory);
		model.addAttribute("watchhistory", am.sHtmlWatchHistory);
		model.addAttribute("devicelist", am.sHtmlDeviceList);

		model.addAttribute("scmpurchases_rental", am.sHtmlSCMViewPurchasesRental);
		model.addAttribute("scmpurchases_onetime", am.sHtmlSCMViewPurchasesOnetime);
		model.addAttribute("scmusage", am.sHtmlSCMUsage);	
		model.addAttribute("scmpurchases_subscription", am.sHtmlSCMViewPurchasesSubscription);
		model.addAttribute("scmusage_subscription", am.sHtmlSCMUsageSubscription);
		model.addAttribute("scmgetsubscriberprofile", am.sHtmlSCMGetSubscriberProfile);
		
		
		model.addAttribute("rbpreferences", am.sHtmlRBPreferences);
		model.addAttribute("rbprofile", am.sHtmlRBProfile);
		
		model.addAttribute("vmsaccount", am.sHtmlVMSAccount);
		model.addAttribute("vmsentitlements", am.sHtmlVMSEntitlements);

				
		return "account2";
	}
	@RequestMapping(value = "/account.htm", method = RequestMethod.GET)
	public String showForm(ModelMap model, Principal principal)
	{
		String user = principal.getName();
		logger.info("[user=" + user + "]\t[view=account]");
		
		return "account";
	}

	@ModelAttribute("customer")
	public Customer getCustomerObject()
	{
		return new Customer();
	}
	
	@ModelAttribute("zoeComponentList")
	public static List<String> populateComponentList()
	{
		List<String> zoeComponentList = new ArrayList<String>();
		zoeComponentList.add("OL");
		zoeComponentList.add("Red Box");
		zoeComponentList.add("SCM");
		//zoeComponentList.add("VMS");		
		
		return zoeComponentList;
	}
	
	@ModelAttribute("envList")
	public static Map<String, String> populateEnvList()
	{
		Map<String,String> env = new LinkedHashMap<String,String>();
		
		for(String e: AppUtils.getAllAvailableEnvs())
		{
			if (e.equalsIgnoreCase("dyn"))
				env.put("dyn", "dyn");
			if (e.equalsIgnoreCase("sit"))
				env.put("sit", "sit");
			if (e.equalsIgnoreCase("stg"))
				env.put("stg", "stg");
			if (e.equalsIgnoreCase("prd"))
				env.put("prd", "prd");
		}
		
		return env;
	}

}
