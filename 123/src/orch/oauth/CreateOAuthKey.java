package com.verizon.zoetool.orch.oauth;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;

public class CreateOAuthKey {
	private String sApiName = "CreateOAuthKey";
	private String sURL, sUser;
	private Map<String, String> mapParams;
	
	private String sAPIKey = "", sPassword = "", sErrorResponse = "";
	private String sAPIKey_CSR = "", sPassword_CSR = "";
	
	public CreateOAuthKey(String user, AppUtils.Env env)
	{
		sURL = AppUtils.getOlUrlWeb(env) + sApiName;
		sUser = user;
	}
	
	private void initParamsMap(String customerEmail, String customerPassword, boolean isCSR)
	{
		mapParams = new HashMap<String, String>();
		AppUtils au = new AppUtils();
		mapParams.put("###TransactionID###", au.genTransactionID());
		mapParams.put("###TransactionTime###", au.genOLTransactionTime());
		mapParams.put("###AuthEmail###", customerEmail);
		mapParams.put("###AuthPassword###", customerPassword);
		mapParams.put("###TransactionAccessPoint###", "JVPWEB");
		if (isCSR)
			mapParams.put("###OAuthScope###", "CSR");
		else
			mapParams.put("###OAuthScope###", "APP");
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	

	public String getApiKey_Web()
	{
		return sAPIKey;
	}
	public String getPassword_Web()
	{
		return sPassword;
	}
	public String getErrorResponse()
	{
		return sErrorResponse;
	}
	public String getApiKey_CSR()
	{
		return sAPIKey_CSR;
	}
	public String getPassword_CSR()
	{
		return sPassword_CSR;
	}
	
	private void doCreateOAuthKey(String customerEmail, String customerPassword, boolean isCSR)
	{
		initParamsMap(customerEmail, customerPassword, isCSR);
		
		HttpService httpService = new HttpService(sUser);
		String sXmlResponse = httpService.callOLServer(sApiName, sURL, mapParams);
		
		if (isCSR)
		{
			parseXmlResponse(sXmlResponse, "CSR");
		}
		else
		{
			parseXmlResponse(sXmlResponse, "APP");
			if (sAPIKey.length() == 0 || sPassword.length() == 0) // check system availability
				sErrorResponse = sXmlResponse;
		}
		clearParamsMap();
	}
	public void doCreateOAuthKey(String customerEmail, String customerPassword)
	{
		doCreateOAuthKey(customerEmail, customerPassword, false);
	}
	public void doCreateOAuthKey_CSR(String customerEmail, String customerPassword)
	{
		doCreateOAuthKey(customerEmail, customerPassword, true);
	}

	
	private void parseXmlResponse(String xml, String oAuthScope)
	{
		int a = xml.indexOf("<ApiKey>");
		int b = xml.indexOf("</ApiKey>");
		int c = "<ApiKey>".length();
		if ( a != -1 && b != -1 && b > a + c)
		{
			if (oAuthScope.equalsIgnoreCase("app"))
				sAPIKey = xml.substring(a + c, b);
			else if (oAuthScope.equalsIgnoreCase("csr"))
				sAPIKey_CSR = xml.substring(a + c, b);
		}
		
		a = xml.indexOf("<Password>");
		b = xml.indexOf("</Password>");
		c = "<Password>".length();
		if ( a != -1 && b != -1 && b > a + c)
		{
			if (oAuthScope.equalsIgnoreCase("app"))
				sPassword = xml.substring(a + c, b);
			else if (oAuthScope.equalsIgnoreCase("csr"))
				sPassword_CSR = xml.substring(a + c, b);
		}
		
	}
	
}
