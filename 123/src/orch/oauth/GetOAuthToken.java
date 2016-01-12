package com.verizon.zoetool.orch.oauth;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;

public class GetOAuthToken {
	private String sApiName = "GetOAuthToken";
	private String sURL, sUser;
	private Map<String, String> mapParams;
	private String sToken = "", sToken_CSR = "", sErrorResponse = "";
	
	public GetOAuthToken(String user, AppUtils.Env env)
	{
		sURL = AppUtils.getOlUrlWeb(env) + sApiName;
		sUser = user;
	}
	private void initParamsMap(String oauthKey, String password, boolean isCSR)
	{
		mapParams = new HashMap<String, String>();
		AppUtils au = new AppUtils();
		mapParams.put("###TransactionID###", au.genTransactionID());
		mapParams.put("###TransactionTime###", au.genOLTransactionTime());
		mapParams.put("###TransactionAccessPoint###", "JVPWEB");
		mapParams.put("###TransactionDeviceID###", "JVP-WEB-FireFox-127.0.0.1");
		mapParams.put("###OAuthKey###", oauthKey);
		mapParams.put("###Password###", password);
		if (isCSR)
			mapParams.put("###OAuthScope###", "CSR");
		else
			mapParams.put("###OAuthScope###", "APP");
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	
	public String getToken()
	{
		return sToken;
	}
	public String getToken_CSR()
	{
		return sToken_CSR;
	}
	public String getErrorResponse()
	{
		return sErrorResponse;
	}
	
	private void doGetOAuthToken(String oauthKey, String password, boolean isCSR)
	{
		initParamsMap(oauthKey, password, isCSR);
		HttpService httpService = new HttpService(sUser);
		String sXmlResponse = httpService.callOLServer(sApiName, sURL, mapParams);
		
		if(isCSR)
		{
			parseXmlResponse(sXmlResponse, "CSR");
		}
		else
		{
			parseXmlResponse(sXmlResponse, "APP");
			if (sToken.length() == 0) // check system availability
				sErrorResponse = sXmlResponse;
		}
		clearParamsMap();
	}
	public void doGetOAuthToken(String oauthKey, String password)
	{
		doGetOAuthToken(oauthKey, password, false);
	}
	public void doGetOAuthToken_CSR(String oauthKey, String password)
	{
		doGetOAuthToken(oauthKey, password, true);
	}

	private void parseXmlResponse(String xml, String oAuthScope)
	{
		int a = xml.indexOf("<Token>");
		int b = xml.indexOf("</Token>");
		int c = "<Token>".length();
		if ( a != -1 && b != -1 && b > a + c)
		{
			if (oAuthScope.equalsIgnoreCase("app"))
			{
				sToken = xml.substring(a + c, b);
			}
			else if (oAuthScope.equalsIgnoreCase("csr"))
				sToken_CSR = xml.substring(a + c, b);
		}
	}
	
}