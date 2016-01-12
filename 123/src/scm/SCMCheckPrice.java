package com.verizon.zoetool.scm;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;
import com.verizon.zoetool.utils.AppUtils;


public class SCMCheckPrice {
	private String sApiName = "SCMCheckPrice";
	private Map<String, String> mapParams;
	private String sURL, sUser;
	private AppUtils.Env eEnv;
	private HashMap<String, String> mapXsl;
	
	public SCMCheckPrice(String user, AppUtils.Env env)
	{
		eEnv = env;
		sURL = AppUtils.getScmCareUrl(env);
		sUser = user;
	}

	private void initParamsMap(String userid, String purchaseoptionid)
	{
		mapParams = new HashMap<String, String>();
		AppUtils au = new AppUtils();
		mapParams.put("###TransactionID###", au.genTransactionID());
		mapParams.put("###SKUID###", purchaseoptionid);
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}

	private void initXslParamsMap(AppUtils.Env env)
	{
		mapXsl = new HashMap<String, String>();
		mapXsl.put("env", env.toString());
	}
	private void clearXslParamsMap()
	{
		mapXsl.clear();
	}
	public String getSCMChekPrice(String userid, String purchaseoptionid)
	{
		initParamsMap(userid, purchaseoptionid);
		initXslParamsMap(eEnv);
		HttpService http = new HttpService(sUser);
		String sResponse = http.callSCMServer(eEnv, sApiName, sURL, mapParams);
		
		clearParamsMap();
		
		XmlHandler xh = new XmlHandler(sUser);
		String sHtml = xh.parseXmlResponseToHtml(sResponse, sApiName, mapXsl);
		clearXslParamsMap();
		return sHtml;
		
	}

}