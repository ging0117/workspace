package com.verizon.zoetool.et;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.SoapService;
import com.verizon.zoetool.utils.XmlHandler;

public class ExactTargetService {
	private Map<String, String> mapParams;
	private String sURL, sPluckUser, sPluckPwd, sUser;
	private String sApiName = "ETGetSystemStatus";
	
	public ExactTargetService(String user, AppUtils.Env env)
	{
		sURL = AppUtils.getEtUrl(env);
		sPluckUser = AppUtils.getEtUser(env);
		sPluckPwd = AppUtils.getEtPwd(env);
		
		sUser = user;
	}
	
	private void initParamsMap()
	{
		mapParams = new HashMap<String, String>();
		mapParams.put("###To###", sURL);
		mapParams.put("###Username###", sPluckUser);
		mapParams.put("###Password###", sPluckPwd);
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	public String checkSystemAvailability()
	{
		return GetSystemStatus();
	}
	private String GetSystemStatus()
	{
		initParamsMap();		
		SoapService ss = new SoapService(sUser);
		String sResponse = ss.callETServer(sApiName, sURL, mapParams);
		clearParamsMap();
		
		XmlHandler xh = new XmlHandler(sUser);
		return xh.parseXmlResponseToHtml(sResponse, sApiName);
	}
}
