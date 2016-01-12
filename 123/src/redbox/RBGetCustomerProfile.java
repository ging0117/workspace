package com.verizon.zoetool.redbox;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.pluck.PluckService;
import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;

public class RBGetCustomerProfile {
	private String sApiName = "RBGetCustomerProfile";
	private String sURL, sUser;
	private Map<String, String> mapCustInfo;
	

	public RBGetCustomerProfile(String user, AppUtils.Env env)
	{
		sURL = AppUtils.getRbUrl(env) + "/v5/customers/VZ.###pcn###/partner/profile?access_token=###token_rb###";
		sUser = user;
	}

	private String genURL(String token_rb, String pcn)
	{
		return sURL.replaceFirst("###pcn###", pcn).replaceFirst("###token_rb###", token_rb);
	}
	public String doGetCustomerProfile(String token_rb, String pcn)
	{
		String sURL = genURL(token_rb, pcn);
		HttpService httpService = new HttpService(sUser);
		String sResponse = httpService.doGetCall(sApiName, sURL);
		
		XmlHandler xh = new XmlHandler(sUser);
		return xh.parseXmlResponseToHtml(sResponse, sApiName);
	}
	public String doGetCustomerProfile_checkSystemAvailability(String token_rb, String pcn)
	{
		String sURL = genURL(token_rb, pcn);
		HttpService httpService = new HttpService(sUser);
		String sResponse = httpService.doGetCall(sApiName, sURL);
		
		XmlHandler xh = new XmlHandler(sUser);
		return xh.parseXmlResponseToHtml(sResponse, sApiName + "_checkservice");
	}
	public boolean doGetCustomerProfile_checkSystemAvailability(String token_rb, String pcn, PluckService pluck)
	{
		String sURL = genURL(token_rb, pcn);
		HttpService httpService = new HttpService(sUser);
		String sResponse = httpService.doGetCall(sApiName, sURL);
		
		//sResponse = "<httperror><code>503</code><message>12345</message></httperror>";
		mapCustInfo = extractCustomerInfo(pcn, sResponse);
		
		if (mapCustInfo.get("pcn").length() > 0 
				&& mapCustInfo.get("CustomerNumber").length() > 0 
				&& mapCustInfo.get("EmailAddress").length() > 0)
		{
			pluck.updateStatusList(PluckService.Steps.RB_GetCustomerProfile, true, "", "");
			return true;
		}
		else
		{
			XmlHandler xh = new XmlHandler(sUser);
			String sCode = xh.extractInnerText(sResponse, "code");
			String sMessage = xh.extractInnerText(sResponse, "message");
			if (sCode.length() > 0 || sMessage.length() > 0) // <httperror>
				pluck.updateStatusList(PluckService.Steps.RB_GetCustomerProfile, false, sCode, sMessage);
			else
				pluck.updateStatusList(PluckService.Steps.RB_GetCustomerProfile, false, "", sResponse);
			return false;
		}
	}
	private Map<String, String> extractCustomerInfo(String pcn, String xml)
	{
		Map<String, String> map = new HashMap<String, String>();
		XmlHandler xh = new XmlHandler(sUser);
		map.put("pcn", pcn);
		map.put("CustomerNumber", xh.extractInnerText(xml, "CustomerNumber"));
		map.put("DisplayName", xh.extractInnerText(xml, "DisplayName"));
		map.put("EmailAddress", xh.extractInnerText(xml, "EmailAddress"));
		
		return map;
	}
	
	public Map<String, String> getCustomerInfo()
	{
		return mapCustInfo;
	}
}
