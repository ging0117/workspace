package com.verizon.zoetool.scm;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;

public class SCMViewPurchases {
	private String sApiName = "SCMViewPurchases";
	private Map<String, String> mapParams;
	private String sURL, sUser;
	private String sScmResponse = ""; // Used by subscription history
	private AppUtils.Env eEnv;
	
	public enum PricePlanType
	{
		Subscription,
		One_Time,
		Rental;
		
		@Override public String toString() 
    	{
			return super.toString().replace('_', ' ');
    	}
	}
	
	public SCMViewPurchases(String user, AppUtils.Env env)
	{
		eEnv = env;
		sURL = AppUtils.getScmCareUrl(env);
		sUser = user;
	}
	
	private void initParamsMap(String userid, PricePlanType pppType, boolean activeStatusOnly)
	{
		mapParams = new HashMap<String, String>();
		mapParams.put("###UserID###", userid);
		mapParams.put("###PPPType###", pppType.toString());
		if (activeStatusOnly)
		{
			mapParams.put("<!--", "");
			mapParams.put("-->", "");
		}
			
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	
	public String getRentalHistory(String userid)
	{
		String sResponse = callSCM(userid, PricePlanType.Rental, false);
		
		XmlHandler xh = new XmlHandler(sUser);
		String sHtml = xh.parseXmlResponseToHtml(sResponse, sApiName);
		return sHtml.replaceFirst("tbSCMViewPurchases", "tbSCMViewPurchasesRental").replaceFirst("SCM View Purchases", "SCM View Purchases - VOD");
	}
	public String getPurchaseHistory(String userid)
	{
		String sResponse = callSCM(userid, PricePlanType.One_Time, false);
		
		XmlHandler xh = new XmlHandler(sUser);
		String sHtml = xh.parseXmlResponseToHtml(sResponse, sApiName);
		return sHtml.replaceFirst("tbSCMViewPurchases", "tbSCMViewPurchasesOnetime").replaceFirst("SCM View Purchases", "SCM View Purchases - EST");
	}
	
	public String checkSystemAvailability(String userid)
	{
		sScmResponse = callSCM(userid, PricePlanType.Rental, false);
		XmlHandler xh = new XmlHandler(sUser);
		String sHtml = xh.parseXmlResponseToHtml(sScmResponse, sApiName + "_checkservice");
		if (sHtml.length() == 0)
			sHtml = "<tr><th>SCM</th><td class=\"error\">ViewPurchases</td><td class=\"error\">NO</td><td class=\"error\"></td><td class=\"error\"></td></tr>";
		return sHtml;
	}
	
	
	private String callSCM(String userid, PricePlanType pppType, boolean activeStatusOnly)
	{
		initParamsMap(userid, pppType, activeStatusOnly);
		HttpService http = new HttpService(sUser);
		String sResponse = http.callSCMServer(eEnv, sApiName, sURL, mapParams);
		clearParamsMap();
		return sResponse;
	}
	public String getSubscriptionHistory(String userid)
	{
		sScmResponse = callSCM(userid, PricePlanType.Subscription, false);
		
		XmlHandler xh = new XmlHandler(sUser);
		return xh.parseXmlResponseToHtml(sScmResponse, sApiName + "_SubscriptionHistory");
	}
	public String getActiveSubscriptionHistory(String userid)
	{
		sScmResponse = callSCM(userid, PricePlanType.Subscription, true);
		
		XmlHandler xh = new XmlHandler(sUser);
		return xh.parseXmlResponseToHtml(sScmResponse, sApiName + "_SubscriptionHistory");
	}
	
	public String getFulfillmentIdOfLatestActiveSubscription(String userid)
	{
		if (sScmResponse.length() == 0)
		{
			sScmResponse = callSCM(userid, PricePlanType.Subscription, true);
		}
		
		XmlHandler xh = new XmlHandler(sUser);
		return xh.extractInnerText(sScmResponse, "PurchaseLineItemID");
	}
	
}
