package com.verizon.zoetool.orch.account;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;


public class GetCustomer {
	private String sApiName = "GetCustomer";
	private String sURL, sUser;
	private Map<String, String> mapParams;
	
	public GetCustomer(String user, AppUtils.Env env, String token)
	{
		sURL = AppUtils.getOlUrlWeb(env) + sApiName + "?access_token=" + token;
		sUser = user;
	}
	
	private void initParamsMap(String pcn)
	{
		mapParams = new HashMap<String, String>();
		AppUtils au = new AppUtils();
		mapParams.put("###TransactionID###", au.genTransactionID());
		mapParams.put("###TransactionTime###", au.genOLTransactionTime());
		mapParams.put("###CustomerNumber###", pcn);
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	
	public String doGetCustomer(String pcn)
	{
		initParamsMap(pcn);

		HttpService http = new HttpService(sUser);
		String sResponse = http.callOLServer(sApiName, sURL, mapParams);
		
		clearParamsMap();
		
		XmlHandler xh = new XmlHandler(sUser);
		return xh.parseXmlResponseToHtml(sResponse, sApiName);
	}
	
}
