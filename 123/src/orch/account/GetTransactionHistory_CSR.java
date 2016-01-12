package com.verizon.zoetool.orch.account;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;

public class GetTransactionHistory_CSR {
	private String sApiName = "GetTransactionHistory_CSR";
	private String sURL, sUser;	
	private Map<String, String> mapParams;
	
	public GetTransactionHistory_CSR(String user, AppUtils.Env env, String token)
	{
		sURL = AppUtils.getOlUrlCsr(env) + sApiName + "?access_token=" + token;	
		sUser = user;
	}
	private void initParamsMap(String pcn, String startdate, String enddate)
	{
		mapParams = new HashMap<String, String>();
		AppUtils au = new AppUtils();
		mapParams.put("###TransactionID###", au.genTransactionID());
		mapParams.put("###TransactionTime###", au.genOLTransactionTime());
		mapParams.put("###CustomerNumber###", pcn);
		mapParams.put("###StartDate###", startdate);
		mapParams.put("###EndDate###", enddate);
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	
	public String doGetTransactionHistory_CSR(String pcn, String startdate, String enddate)
	{
		initParamsMap(pcn, startdate, enddate);
		HttpService http = new HttpService(sUser);
		String sResponse = http.callOLServer(sApiName, sURL, mapParams);
		clearParamsMap();
		
		XmlHandler xh = new XmlHandler(sUser);		
		return xh.parseXmlResponseToHtml(sResponse, sApiName);
	}
	
}
