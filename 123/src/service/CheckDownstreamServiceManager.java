package com.verizon.zoetool.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.verizon.zoetool.ds.DSService;
import com.verizon.zoetool.et.ExactTargetService;
import com.verizon.zoetool.orch.account.GetProductDetailByProductID;
import com.verizon.zoetool.orch.account.ProductSearch;
import com.verizon.zoetool.orch.oauth.OAuthUtils;
import com.verizon.zoetool.pluck.PluckService;
import com.verizon.zoetool.redbox.GetRBAccessToken;
import com.verizon.zoetool.redbox.RBGetCustomerProfile;
import com.verizon.zoetool.scm.SCMViewPurchases;
import com.verizon.zoetool.utils.AppUtils;

public class CheckDownstreamServiceManager {
	private static final Logger logger = Logger.getLogger(CheckDownstreamServiceManager.class);
	private String sUser;
	public String sRB, sSCM, sET, sPluck = "", sCSP = "", sDS;
	
	public CheckDownstreamServiceManager(String user)
	{
		sUser = user;
	}
	public void check(AppUtils.Env env, String pcn)
	{
		//RB
		GetRBAccessToken g = new GetRBAccessToken(sUser, env);
		String sRbToken= g.getToken();
		RBGetCustomerProfile rb = new RBGetCustomerProfile(sUser, env);
		sRB = rb.doGetCustomerProfile_checkSystemAvailability(sRbToken, pcn);		
		//SCM
		SCMViewPurchases scm = new SCMViewPurchases(sUser, env);
		sSCM = scm.checkSystemAvailability(pcn);
		//ET
		ExactTargetService et = new ExactTargetService(sUser, env);
		sET = et.checkSystemAvailability();
		// Pluck
		sPluck = checkPluck(env, pcn);
		// DS
		DSService ds = new DSService(sUser, env);
		sDS = ds.callDS();
		
	}
	private String checkPluck(AppUtils.Env env, String pcn)
	{
		String sServiceName = "Pluck";
		PluckService pluck = new PluckService(sUser, env);
		
		// Pluck - step 1 and 2: get OAUth token
		OAuthUtils oauth = new OAuthUtils(sUser, env);
		String sOAuthToken = oauth.getOAuthToken_CheckAvailability(env, pluck);
		if (sOAuthToken.length() == 0)
		{
			return pluck.printStatusList(sServiceName);
		}
		
		// Pluck - step 3: call ProductSearch to get a list of product ids
		ProductSearch ps = new ProductSearch(sUser, env, sOAuthToken);
		int iPageSize = 20;
		List<String> listProductIds = ps.doProductSearch_CheckAvailability(1, iPageSize, pluck);
		if (listProductIds.size() == 0)
		{
			return pluck.printStatusList(sServiceName);
		}
		
		// Pluck - step 4: Get RB access Token
		GetRBAccessToken rb = new GetRBAccessToken(sUser, env);
		String sRbToken= rb.getToken_CheckAvailability(pluck);
		if (sRbToken == null || sRbToken.length() == 0)
		{
			return pluck.printStatusList(sServiceName);
		}
		
		// Pluck - step 5: Get Rb Customer Profile
		RBGetCustomerProfile rb2 = new RBGetCustomerProfile(sUser, env);
		boolean success = rb2.doGetCustomerProfile_checkSystemAvailability(sRbToken, pcn, pluck);
		if (!success)
			return pluck.printStatusList(sServiceName);
		Map<String, String> mapCustInfo = rb2.getCustomerInfo();
				
		// Pluck - step 6:loop through the product id list		
		GetProductDetailByProductID getDetail = new GetProductDetailByProductID(sUser, env, sOAuthToken);
		for(int i = 0; i < listProductIds.size(); i++)
		{
			String sProductId = listProductIds.get(i);
			String sAltCode = getDetail.doGetProductDetailByProductID_CheckAvailability(sProductId, pluck);
			
			logger.info("[user=" + sUser + "]\t[api=PluckService]\t[input=productid:" + sProductId + ";altcode:" + sAltCode + "]");
			if (sAltCode.length() > 0)
			{
				success = pluck.checkSystemAvailability(sRbToken, mapCustInfo, sAltCode);
				if (success)
					return pluck.printStatusList(sServiceName);
			}
		}
		
		return pluck.printStatusList(sServiceName);
		
	}
}
