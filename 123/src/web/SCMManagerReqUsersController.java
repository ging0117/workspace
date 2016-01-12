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

import com.verizon.zoetool.domain.SCMCreateCustomer;
import com.verizon.zoetool.service.SCMManagerReqUsersManager;
import com.verizon.zoetool.utils.AppUtils;

@Controller
public class SCMManagerReqUsersController{
       private static final Logger logger = Logger.getLogger(SCMManagerReqUsersController.class);
       
       @RequestMapping(value = "/scmcreatecustomer.htm", method = RequestMethod.GET)
       public String showForm(ModelMap model, Principal principal)
       {
              String user = principal.getName();
              logger.info("[user=" + user + "]\t[view=scmcreatecustomer]");
              return "scmcreatecustomer";
       }
       
       

       
       @RequestMapping(value = "/scmcreatecustomer2.htm", method = RequestMethod.POST)
       public String scmcreatecustomer(
                     @Valid @ModelAttribute("scmcreatecustomer") SCMCreateCustomer scmcreatecustomer, 
                     BindingResult result,
                     ModelMap model,
                     Principal principal)
       {
    	      String pnc = scmcreatecustomer.getPcn();
    	      String user = principal.getName();
              
              if (result.hasErrors())
              {
                     return "scmcreatecustomer";
              }
              
              logger.info("[user=" + user + "]\t[view=scmcreatecustomer2]");
              
              AppUtils.Env env = AppUtils.getEnv(scmcreatecustomer.getEnv());
      //  String sQuery = scmcreatecustomer.getSCMCreatecustomer();

              SCMManagerReqUsersManager scm = new SCMManagerReqUsersManager( pnc, user,  env);
              scm.getSCMManagerReqUsers(pnc, user,  env);
        
              
              model.addAttribute("scmcreatecustomer", scmcreatecustomer); 
              model.addAttribute("getSCMManagerReqUsers", scm.sHtmlSCMManagerReqUsers);
              return "scmcreatecustomer2";
       }
       

       @ModelAttribute("scmcreatecustomer")
       public SCMCreateCustomer getSCMCreateCustomer()
       {
              return new SCMCreateCustomer();
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
