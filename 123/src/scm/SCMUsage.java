package com.verizon.zoetool.scm;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;

public class SCMUsage {
	private String sApiName = "SCMUsage";
	private Map<String, String> mapParams;
	private String sScmResponse = ""; // Used by subscription history
	private HashMap<String, String> mapXsl;
	private String sURL, sUser;
	private AppUtils.Env eEnv;
	
	public SCMUsage(String user, AppUtils.Env env)
	{
		eEnv = env;
		sURL = AppUtils.getScmUsageUrl(env);
		sUser = user;
	}

	private void initParamsMap(String userid, String startdate, String enddate)
	{
		mapParams = new HashMap<String, String>();
		mapParams.put("###UserID###", userid);
		mapParams.put("###StartDate###", startdate);
		mapParams.put("###EndDate###", enddate);
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	private void initXslParamsMap(AppUtils.Env env, String userid)
	{
		mapXsl = new HashMap<String, String>();
		mapXsl.put("userid", userid);
		mapXsl.put("env", env.toString());
	}
	private void clearXslParamsMap()
	{
		mapXsl.clear();
	}

	public String getUsages(String userid, String startdate, String enddate)
	{
		sScmResponse = callSCM(userid, startdate, enddate);
		
		initXslParamsMap(eEnv, userid);
		XmlHandler xh = new XmlHandler(sUser);
		String sHtml= xh.parseXmlResponseToHtml(sScmResponse, sApiName, mapXsl);			
		clearXslParamsMap();
				
		return sHtml;
	}
	private String callSCM(String userid, String startdate, String enddate)
	{
		initParamsMap(userid, startdate, enddate);
		HttpService http = new HttpService(sUser);
		String sResponse = http.callSCMServer(eEnv, sApiName, sURL, mapParams);
		clearParamsMap();
		return sResponse;
	}
	
	public String getSubscriptionHistory(String userid, String startdate, String enddate)
	{
		if (sScmResponse.length() == 0)
		{
			sScmResponse = callSCM(userid, startdate, enddate);
		}
		
		initXslParamsMap(eEnv, userid);
		XmlHandler xh = new XmlHandler(sUser);		
		String sHtml = xh.parseXmlResponseToHtml(sScmResponse, sApiName + "_SubscriptionHistory", mapXsl);
		clearXslParamsMap();
		return sHtml;
	}
}
