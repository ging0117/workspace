package com.verizon.zoetool.redbox;

import com.verizon.zoetool.pluck.PluckService;
import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class GetRBAccessToken {
	private static final Logger logger = Logger.getLogger(GetRBAccessToken.class);
	private String sRBAccessToken = "";
	private String sApiName = "GetRBAccessToken";
	private String sURL, sPostBody, sUser;
	
	public GetRBAccessToken(String user, AppUtils.Env env)
	{
		sURL = AppUtils.getRbTokenUrl(env);
		
		String sScope = "PROFILE";
		if (env.equals(AppUtils.Env.PRD) || env.equals(AppUtils.Env.STG))
			sScope = "RBISupportTool";
		
		sPostBody = "scope=" + sScope + "&client_id=" + AppUtils.getRbClient(env)
				+ "&grant_type=client_credentials&client_secret=" + AppUtils.getRbPwd(env);
		sUser = user;
	}
	public String getToken()
	{
		HttpService httpService = new HttpService(sUser);
		String sResponse = httpService.callRBServer(sApiName, sURL, sPostBody, "application/x-www-form-urlencoded", null);
		parseJsonResponse(sResponse);
		return sRBAccessToken;
	}
	public String getToken_CheckAvailability(PluckService pluck)
	{
		HttpService httpService = new HttpService(sUser);
		String sResponse = httpService.callRBServer(sApiName, sURL, sPostBody, "application/x-www-form-urlencoded", null);
		
		parseJsonResponse(sResponse);		
		
		if (sRBAccessToken == null || sRBAccessToken.length() == 0)
		{
			XmlHandler xh = new XmlHandler(sUser);
			String sCode = xh.extractInnerText(sResponse, "errorcode");
			String sMessage = xh.extractInnerText(sResponse, "faultstring");
			if (sCode.length() > 0 || sMessage.length() > 0)
				pluck.updateStatusList(PluckService.Steps.RB_GetAccessToken, false, sCode, sMessage);
			else
			{
				sCode = xh.extractInnerText(sResponse, "code");
				sMessage = xh.extractInnerText(sResponse, "message");
				if (sCode.length() > 0 || sMessage.length() > 0)
					pluck.updateStatusList(PluckService.Steps.RB_GetAccessToken, false, sCode, sMessage);
				else
					pluck.updateStatusList(PluckService.Steps.RB_GetAccessToken, false, "", sResponse);
			}
		}
		else
			pluck.updateStatusList(PluckService.Steps.RB_GetAccessToken, true, "", "");
		return sRBAccessToken;
	}
	private void parseJsonResponse(String json)
	{
		try {
			JSONObject o = new JSONObject(json);
			sRBAccessToken = o.getString("access_token");
			
		} catch (JSONException e) {
			logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
		}		
	}
	
}
