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

import com.verizon.zoetool.domain.MovieName;
import com.verizon.zoetool.domain.SCMCheckPriceRequestInfo;
import com.verizon.zoetool.service.SCMCheckPriceManager;
import com.verizon.zoetool.utils.AppUtils;

@Controller
public class SCMCheckPriceController{
	private static final Logger logger = Logger.getLogger(SCMCheckPriceController.class);
	
	@RequestMapping(value = "/scmcheckprice.htm", method = RequestMethod.GET)
	public String showForm(ModelMap model, Principal principal)
	{
		String user = principal.getName();
		logger.info("[user=" + user + "]\t[view=scmcheckprice]");
		return "scmcheckprice";
	}
	
	@RequestMapping(value = "/scmcheckprice2.htm", method = RequestMethod.POST)
	public String productSearchMovieName(
			@Valid @ModelAttribute("moviename") MovieName moviename, 
			BindingResult result,
			ModelMap model,
			Principal principal)
	{
		String user = principal.getName();
		
		if (result.hasErrors())
		{
			return "scmcheckprice";
		}
		
		logger.info("[user=" + user + "]\t[view=scmcheckprice2]");
		
		AppUtils.Env env = AppUtils.getEnv(moviename.getEnv());
		String sQuery = moviename.getMoviename();
		
		SCMCheckPriceManager am = new SCMCheckPriceManager(user);
		am.getProductDetails(env, sQuery);
		
		model.addAttribute("moviename", moviename); 
		model.addAttribute("productdetails", am.sHtmlProductDetails);
		
		return "scmcheckprice2";
	}	

	@RequestMapping(value = "/scmcheckprice3.htm", method = RequestMethod.GET)
	public String retrieveProductSearchData(
			@Valid @ModelAttribute("scmcheckpricerequest") SCMCheckPriceRequestInfo requestinfo,
			ModelMap model,
			BindingResult result,
			Principal principal)
	{
		SCMCheckPriceValidator validator = new SCMCheckPriceValidator();
		validator.validate(requestinfo, result);
		
		if (result.hasErrors())
		{
			return "error";
		}
		
		String user = principal.getName();
		logger.info("[user=" + user + "]\t[view=scmcheckprice3]");
	
		AppUtils.Env env = AppUtils.getEnv(requestinfo.getEnv());
		SCMCheckPriceManager am = new SCMCheckPriceManager(user);
		am.checkPrice(env, requestinfo.getPurchaseoptionid());
		
		model.addAttribute("env", env);
		model.addAttribute("title", requestinfo.getTitle());
		model.addAttribute("scmcheckprice", am.sHtmlSCMCheckPrice);
        
        return "scmcheckprice3";
	}
	
	@ModelAttribute("moviename")
	public MovieName getMovieNameObject()
	{
		return new MovieName();
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
