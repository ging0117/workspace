package com.verizon.zoetool.redbox;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;

public class RBGetCustomerPreferences {
	private String sApiName = "RBGetCustomerPreferences";
	private String sURL, sUser;
	
	public RBGetCustomerPreferences(String user, AppUtils.Env env)
	{
		sURL = AppUtils.getRbUrl(env) + "/v5/customers/VZ.###pcn###/partner/preferences?access_token=###token_rb###";
		sUser = user;
	}
	
	private String genURL(String token_rb, String pcn)
	{
		return sURL.replaceFirst("###pcn###", pcn).replaceFirst("###token_rb###", token_rb);
	}
	
	public String doGetCustomerPreferences(String token_rb, String pcn)
	{
		String sURL = genURL(token_rb, pcn);
		HttpService httpService = new HttpService(sUser);
		String sResponse = httpService.doGetCall(sApiName, sURL);		

		XmlHandler xh = new XmlHandler(sUser);
		return xh.parseXmlResponseToHtml(sResponse, sApiName);
	}

}

