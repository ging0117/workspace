package com.verizon.zoetool.web;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.verizon.zoetool.domain.MovieName;
import com.verizon.zoetool.service.ProductDetailsManager;
import com.verizon.zoetool.utils.AppUtils;

@Controller
@SessionAttributes
public class ProductDetailsController {
	
	@RequestMapping(value = "/showsearchresult.htm", method = RequestMethod.POST)
	public String productSearchMovieName(
			@Valid @ModelAttribute("moviename") MovieName moviename, 
			BindingResult result,
			ModelMap model,
			Principal principal)
	{
		String user = principal.getName();
		
		if (result.hasErrors())
		{
			return "moviesearchbytitle";
		}
		// Movie Name is empty
		if(moviename.getMoviename().length() == 0 )
		{
			result.rejectValue("moviename", "error.moviename", "Movie Name is empty! Please enter a movie.");
		}
		AppUtils.Env env = AppUtils.getEnv(moviename.getEnv());
		String mName = moviename.getMoviename();

		ProductDetailsManager am = new ProductDetailsManager(user);
		model.addAttribute("moviename", moviename); 
		model.addAttribute("productdetails", am.getProductDetails(env,mName));
		return "showsearchresult";
	}
	
	@RequestMapping(value = "/moviesearchbytitle.htm", method = RequestMethod.GET)
	public String showForm(ModelMap model, Principal principal)
	{
		return "moviesearchbytitle";
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
