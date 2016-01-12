package com.verizon.zoetool.service;

import com.verizon.zoetool.orch.account.GetCustomer;
import com.verizon.zoetool.orch.account.GetCustomerReference;
import com.verizon.zoetool.orch.account.GetDevices_CSR;
import com.verizon.zoetool.orch.account.GetRefundHistory_CSR;
import com.verizon.zoetool.orch.account.GetSubscriptionHistory_CSR;
import com.verizon.zoetool.orch.account.GetTransactionHistory_CSR;
import com.verizon.zoetool.orch.account.GetWatchHistory_CSR;
import com.verizon.zoetool.orch.oauth.OAuthUtils;
import com.verizon.zoetool.redbox.GetRBAccessToken;
import com.verizon.zoetool.redbox.RBGetCustomerPreferences;
import com.verizon.zoetool.redbox.RBGetCustomerProfile;
import com.verizon.zoetool.scm.SCMUsage;
import com.verizon.zoetool.scm.SCMViewPurchases;
import com.verizon.zoetool.scm.SCMGetSubscriberProfile;
import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.vms.VMSAccountService;

public class AccountManager {	
	private String sUser;
	public String sHtmlSCMViewPurchasesRental, sHtmlSCMViewPurchasesOnetime, sHtmlSCMUsage, sHtmlSCMViewPurchasesSubscription, sHtmlSCMUsageSubscription, sHtmlSCMGetSubscriberProfile;
	public String sHtmlRBPreferences, sHtmlRBProfile;
	public String sHtmlTransactionHistory, sHtmlSubscriptionHistory, sHtmlRefundHistory;
	public String sHtmlWatchHistory, sHtmlDeviceList, sHtmlGetCustomer;
	public String sHtmlVMSAccount, sHtmlVMSEntitlements;
	
	public AccountManager(String user)
	{
		sUser = user;
	}
	public void getCustomerProfile(AppUtils.Env env, String pcn, boolean bOL, boolean bRB, boolean bSCM, boolean bVMS)
	{
		if (bSCM)
		{
			SCMViewPurchases scmv = new SCMViewPurchases(sUser, env);
			sHtmlSCMViewPurchasesRental = scmv.getRentalHistory(pcn);
			sHtmlSCMViewPurchasesOnetime = scmv.getPurchaseHistory(pcn);
			sHtmlSCMViewPurchasesSubscription = scmv.getSubscriptionHistory(pcn);
			
			AppUtils au = new AppUtils();
			String sStartDate = au.getSCMDate(-1, 0, 1);
			String sEndDate = au.getSCMDate(0, 0, 1);
			
			SCMUsage scmu = new SCMUsage(sUser, env);
			sHtmlSCMUsage = scmu.getUsages(pcn, sStartDate, sEndDate); 
			sHtmlSCMUsageSubscription = scmu.getSubscriptionHistory(pcn, sStartDate, sEndDate);	
			
			SCMGetSubscriberProfile scmg = new SCMGetSubscriberProfile(sUser,env);
			sHtmlSCMGetSubscriberProfile = scmg.getSCMGetSubscriberProfile(pcn);
			
		}
		if (bOL)
		{
			OAuthUtils oauth = new OAuthUtils(sUser, env);
			String sOAuthToken = oauth.getOAuthToken(env);
			String sOAuthToken_CSR = oauth.getOAuthToken_CSR(env);
			
			GetCustomer getCustomer = new GetCustomer(sUser, env, sOAuthToken);
			sHtmlGetCustomer = getCustomer.doGetCustomer(pcn);
			
			AppUtils au = new AppUtils();
			String sStartDate = au.getDate(-1, 0, 1);
			String sEndDate = au.getDate(0, 0, 1);
			
			
			GetTransactionHistory_CSR gt= new GetTransactionHistory_CSR(sUser, env, sOAuthToken_CSR);
			sHtmlTransactionHistory = gt.doGetTransactionHistory_CSR(pcn, sStartDate, sEndDate);
			
			GetSubscriptionHistory_CSR gs = new GetSubscriptionHistory_CSR(sUser, env, sOAuthToken_CSR);
			sHtmlSubscriptionHistory = gs.doGetSubscriptionHistory_CSR(pcn, sStartDate, sEndDate);			
			
			GetRefundHistory_CSR gr = new GetRefundHistory_CSR(sUser, env, sOAuthToken_CSR);
			sHtmlRefundHistory = gr.doGetRefundHistory_CSR(pcn, sStartDate, sEndDate);
			
			GetWatchHistory_CSR gw = new GetWatchHistory_CSR(sUser, env, sOAuthToken_CSR);
			sHtmlWatchHistory = gw.doGetWatchHistory_CSR(pcn, sStartDate, sEndDate);
			
			GetDevices_CSR gd = new GetDevices_CSR(sUser, env, sOAuthToken_CSR);
			sHtmlDeviceList = gd.doGetDevices_CSR(pcn);
		}
		if (bRB)
		{
			GetRBAccessToken g = new GetRBAccessToken(sUser, env);
			String sRbToken= g.getToken();
			RBGetCustomerProfile rb = new RBGetCustomerProfile(sUser, env);
			sHtmlRBProfile = rb.doGetCustomerProfile(sRbToken, pcn);
			
			RBGetCustomerPreferences rb2 = new RBGetCustomerPreferences(sUser, env);
			sHtmlRBPreferences = rb2.doGetCustomerPreferences(sRbToken, pcn);
		}
		if(bVMS)
		{
			VMSAccountService vms = new VMSAccountService(sUser, env);
			sHtmlVMSAccount = vms.findAccountByPCN(pcn);
			
		}
		
	}
	public boolean match(AppUtils.Env env, String pcn, String email)
	{
		OAuthUtils oauth = new OAuthUtils(sUser, env);
		String sOAuthToken = oauth.getOAuthToken(env);
		
		GetCustomerReference ol = new GetCustomerReference(sUser, env, sOAuthToken);
		String sPCN = ol.doGetCustomerReference(email);
		if (sPCN.equalsIgnoreCase(pcn))
			return true;
		else
			return false;
	}
}
