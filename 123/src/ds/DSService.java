package com.verizon.zoetool.ds;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;

public class DSService {
	private static final Logger logger = Logger.getLogger(DSService.class);
	private String sURL, sUser;
	private AppUtils.Env ENV;
	private String sApiName = "DSService";
	
	public DSService(String user, AppUtils.Env env)
	{
		ENV = env;
		sUser = user;
		sURL = AppUtils.getDSUrl(env);
	}
	
	public String callDS()
	{
		if (ENV.equals(AppUtils.Env.SIT))
		{
			return genHtml("no", "", "DS service availability check doesn't apply to SIT environment.");
		}
		else
		{
			HttpService httpService = new HttpService(sUser);
			String sResponse = httpService.doGetCall(sApiName, sURL);
			return parseJsonResponse(sResponse);
		}
	}
	
	private String parseJsonResponse(String json)
	{
		String sHtml = "";
		try {
			JSONObject o = new JSONObject(json);
			JSONArray array = o.getJSONArray("hits");
			for(int i = 0; i < array.length(); i++)
			{
				String sID = array.getJSONObject(i).getString("id");
				if (sID.isEmpty())
				{
					sHtml = genHtml("no", "", "id is empty");
					break;
				}
				if (i == array.length() - 1)
					sHtml = genHtml("yes", "", "");
			}
			
			
		} catch (JSONException e) {
			logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
			
			if (json.startsWith("<httperror>"))
			{
				XmlHandler xml = new XmlHandler(sUser);
			
				sHtml = genHtml("no", xml.extractInnerText(json, "code"), xml.extractInnerText(json, "message"));
			}
			else
				sHtml = genHtml("no", "", "invalid json");
		}
		return sHtml;
	}
	
	private String genHtml(String isAvailable, String code, String message)
	{
		StringBuilder sBuilder = new StringBuilder();
		
		if (isAvailable.equalsIgnoreCase("yes"))
		{
			sBuilder.append("<tr><th>DS</th><td>ds</td>");
			sBuilder.append("<td>YES</td>");
			sBuilder.append("<td>" + code + "</td>");
			sBuilder.append("<td>" + message + "</td></tr>");
		}
		else
		{
			sBuilder.append("<tr><th>DS</th><td>ds</td>");
			sBuilder.append("<td class=\"error\">NO</td>");
			sBuilder.append("<td class=\"error\">" + code + "</td>");
			sBuilder.append("<td class=\"error\">" + message + "</td></tr>");
		}
		
		return sBuilder.toString();
	}

}
