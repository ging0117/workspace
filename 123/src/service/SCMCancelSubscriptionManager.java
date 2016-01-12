package com.verizon.zoetool.service;

import com.verizon.zoetool.scm.SCMCancelPurchase;
import com.verizon.zoetool.scm.SCMViewPurchases;
import com.verizon.zoetool.utils.AppUtils;

public class SCMCancelSubscriptionManager {
	public String sHtmlSCMViewPurchasesSubscription, sHtmlSCMCancelPurchase, sFulfillmentID;
	private String sUser;
	
	public SCMCancelSubscriptionManager(String user)
	{
		sUser = user;
	}
	
	public void getSCMViewPurchaseSubscriptionHistory(AppUtils.Env env, String pcn)
	{
		SCMViewPurchases scmv = new SCMViewPurchases(sUser, env);
		// hide table
		sHtmlSCMViewPurchasesSubscription = scmv.getActiveSubscriptionHistory(pcn).replaceFirst("display:block;", "display:none;");
		
		sFulfillmentID = scmv.getFulfillmentIdOfLatestActiveSubscription(pcn);
	}
	public void cancelSubscription(AppUtils.Env env, String pcn, String fulfillmentid)
	{
		if (fulfillmentid != null && fulfillmentid.length() > 0)
		{
			SCMCancelPurchase scm = new SCMCancelPurchase(sUser, env);
			sHtmlSCMCancelPurchase = scm.doCancelPurchase(pcn, fulfillmentid);
		}
		else
		{
			sHtmlSCMCancelPurchase = "<table id=\"tbSCMCancelPurchase\" class=\"customers\" style=\"display:block;\">"
					+ "<tr class=\"alt\"><th colspan=\"2\">SCM Cancel Subscription</th></tr>"
					+ "<tr><td>FulfillmentID</td><td></td></tr>"
					+ "<tr><td>Error Code</td><td></td></tr><tr><td>Error Message</td><td>No Fulfillment ID</td></tr></table>";
		}
	}
}
