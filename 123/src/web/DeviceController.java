package com.verizon.zoetool.web;

/*import java.security.Principal;
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

import com.verizon.zoetool.domain.Device;
import com.verizon.zoetool.service.DeviceManager;*/

//@Controller
//@SessionAttributes
public class DeviceController {
	/*private static final Logger logger = Logger.getLogger(DeviceController.class);
	
	
	@RequestMapping(value = "/device2.htm", method = RequestMethod.POST)
	public String removeDevice(
			@Valid @ModelAttribute("device") Device device, 
			BindingResult result,
			ModelMap model,
			Principal principal)
	{
		String user = principal.getName();
		logger.info("[user=" + user + "]\t[view=device2]\t[env=" + device.getEnv() + "]\t[deviceid=" + device.getDeviceid() + "]");
		if (result.hasErrors())
		{
			return "device";
		}
		DeviceManager dm = new DeviceManager(user);
		String sXml = dm.removeDevice(device.getEnv(), device.getDeviceid());
		
		model.addAttribute("device", device);
		model.addAttribute("response", sXml);
		
		return "device2";
	}
	
	
	@RequestMapping(value = "/device.htm", method = RequestMethod.GET)
	public String displayDevice(ModelMap model, Principal principal)
	{
		String user = principal.getName();
		logger.info("[user=" + user + "]\t[view=device]");		
		return "device";
	}
	
	@ModelAttribute("device")
	public Device getDeviceObject()
	{
		return new Device();
	}
	
	@ModelAttribute("envList")
	public static Map<String, String> populateEnvList()
	{
		Map<String,String> env = new LinkedHashMap<String,String>();
		env.put("dyn", "dyn");
		env.put("sit", "sit");
		env.put("stg", "stg");
		//env.put("prd", "prd");
		return env;
	}*/
}
