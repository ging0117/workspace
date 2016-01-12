package com.verizon.zoetool.scm;

import java.util.HashMap;
import java.util.Map;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;
import com.verizon.zoetool.utils.AppUtils;

public class SCMGetSubscriberProfile {
	private String sApiName = "SCMGetSubscriberProfile";
	private Map<String, String> mapParams;
	private String sURL, sUser;
	private AppUtils.Env eEnv;
	
	public SCMGetSubscriberProfile(String user, AppUtils.Env env)
	{
		eEnv = env;
		sURL = AppUtils.getScmCareUrl(env);
		sUser = user;
	}

	private void initParamsMap(String userid)
	{
		mapParams = new HashMap<String, String>();
		AppUtils au = new AppUtils();
		mapParams.put("###TransactionID###", au.genTransactionID());
		mapParams.put("###UserID###", userid);
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}

	public String getSCMGetSubscriberProfile(String userid)
	{
		initParamsMap(userid);
		
		HttpService http = new HttpService(sUser);
		String sResponse = http.callSCMServer(eEnv, sApiName, sURL, mapParams);
		
		clearParamsMap();
		
		XmlHandler xh = new XmlHandler(sUser);
		return xh.parseXmlResponseToHtml(sResponse, sApiName, null);
	}
	
}