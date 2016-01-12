package com.verizon.zoetool.scm;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;

public class SCMUsageDetail {
	private String sApiName = "SCMUsageDetail";
	private Map<String, String> mapParams;
	private String sURL, sUser;
	private AppUtils.Env eEnv;
	
	public SCMUsageDetail(String user, AppUtils.Env env)
	{
		eEnv = env;
		sURL = AppUtils.getScmUsageUrl(env);
		sUser = user;
	}

	private void initParamsMap(String userid, String eventSeq)
	{
		mapParams = new HashMap<String, String>();
		mapParams.put("###UserID###", userid);
		mapParams.put("###EventSeq###", eventSeq);
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}

	public String getUsageDetail(String userid, String eventSeq)
	{
		initParamsMap(userid, eventSeq);
		HttpService http = new HttpService(sUser);
		String sResponse = http.callSCMServer(eEnv, sApiName, sURL, mapParams);
		clearParamsMap();
		
		XmlHandler xh = new XmlHandler(sUser);
		return xh.parseXmlResponseToHtml(sResponse, sApiName);			
	}
	
}
