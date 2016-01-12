package com.verizon.zoetool.scm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;

public class SCMCancelPurchase {
	private String sApiName = "SCMCancelPurchase";
	private Map<String, String> mapParams;
	private Map<String, String> mapXsl;
	private String sURL, sUser;
	private AppUtils.Env eEnv;
	
	public SCMCancelPurchase(String user, AppUtils.Env env)
	{
		eEnv = env;
		sURL = AppUtils.getScmCancelUrl(env);
		sUser = user;
	}

	private void initParamsMap(String userid, String fulfillmentid)
	{
		AppUtils au = new AppUtils();
		mapParams = new HashMap<String, String>();
		mapParams.put("###UserID###", userid);
		mapParams.put("###FulfillmentID###", fulfillmentid);
		mapParams.put("###ExtEventID###", au.genTransactionID());
		mapParams.put("###EventDate###", genEventDate());
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	private void initXslParamsMap(String fulfillmentid)
	{
		mapXsl = new HashMap<String, String>();
		mapXsl.put("fulfillmentid", fulfillmentid);
	}
	private void clearXslParamsMap()
	{
		mapXsl.clear();
	}

	public String doCancelPurchase(String userid, String fulfillmentid)
	{
		initParamsMap(userid, fulfillmentid);
		HttpService http = new HttpService(sUser);
		String sResponse = http.callSCMServer(eEnv, sApiName, sURL, mapParams);
		clearParamsMap();
		
		initXslParamsMap(fulfillmentid);
		XmlHandler xh = new XmlHandler(sUser);
		String sHtml = xh.parseXmlResponseToHtml(sResponse, sApiName, mapXsl);	
		clearXslParamsMap();
		return sHtml;
	}
	
	private String genEventDate()
	{
		TimeZone tz = TimeZone.getTimeZone("America/New_York");
		Calendar cal = Calendar.getInstance(tz); //returns a calendar set to the local time 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSS");
		format.setCalendar(cal);  //explicitly set the calendar into the date formatter
		
		String sTime = format.format(cal.getTime());
		return sTime;
	}

}

