package com.verizon.zoetool.orch.account;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;

public class GetCustomerReference {
	
	private String sApiName = "GetCustomerReference";
	private String sURL, sUser;
	private Map<String, String> mapParams;
	
	public GetCustomerReference(String user, AppUtils.Env env, String token)
	{
		sURL = AppUtils.getOlUrlWeb(env) + sApiName + "?access_token=" + token;	
		sUser = user;
	}
	private void initParamsMap(String email)
	{
		mapParams = new HashMap<String, String>();
		AppUtils au = new AppUtils();
		mapParams.put("###TransactionID###", au.genTransactionID());
		mapParams.put("###TransactionTime###", au.genOLTransactionTime());
		mapParams.put("###LoginEmail###", email.toLowerCase());
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	
	public String doGetCustomerReference(String email)
	{
		initParamsMap(email);

		HttpService http = new HttpService(sUser);
		String sResponse = http.callOLServer(sApiName, sURL, mapParams);
		
		clearParamsMap();
		String sPCN = parseXml(email, sResponse);
		//logger.info("[user=" + sUser + "]\t[api=" + sApiName + "]\t[input=email:" + email + "]\t[output=pcn:" + sPCN + "]");
		return sPCN;
	}
	
	private String parseXml(String email, String xml)
	{
		String sPCN = "";
		if (xml.length() == 0)
			return sPCN;
		int a = xml.indexOf("<PartnerCustomerNumber>");
		int b = xml.indexOf("</PartnerCustomerNumber>");
		int c = "<PartnerCustomerNumber>".length();
		if (a != -1 && b != -1 && a + c < b)
			sPCN = xml.substring(a + c, b);		
		return sPCN;
	}
	
}
