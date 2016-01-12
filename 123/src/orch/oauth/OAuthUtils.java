package com.verizon.zoetool.orch.oauth;

import com.verizon.zoetool.pluck.PluckService;
import com.verizon.zoetool.utils.AbstractServiceStatusObject;
import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.XmlHandler;

public class OAuthUtils {
	private static String sCustomerEmail;
	private static String sCustomerPassword;
	private static String sApiKey_PRD, sApiKey_CSR_PRD;
	private static String sPassword_PRD, sPassword_CSR_PRD;
	private String sToken = "", sUser;
	
	public OAuthUtils(String user, AppUtils.Env env)
	{
		if (env.equals(AppUtils.Env.PRD))
		{
			sApiKey_PRD = AppUtils.getRbOauthApikey_PRD();
			sPassword_PRD = AppUtils.getRbOauthPwd(env, "app");
			sApiKey_CSR_PRD = AppUtils.getRbOauthApikey_CSR_PRD();
			sPassword_CSR_PRD = AppUtils.getRbOauthPwd(env, "csr");
		}
		else
		{
			sCustomerEmail = AppUtils.getRbOauthClient(env);
			sCustomerPassword = AppUtils.getRbOauthPwd(env, "");
		}
		sUser = user;
	}
	
	public String getOAuthToken(AppUtils.Env env)
	{
		if (env.equals(AppUtils.Env.PRD))
		{
			GetOAuthToken getOauthTokan = new GetOAuthToken(sUser, env);
			getOauthTokan.doGetOAuthToken(sApiKey_PRD, sPassword_PRD);
			return getOauthTokan.getToken();
		}
		else
		{
			CreateOAuthKey createOauthKey = new CreateOAuthKey(sUser, env);
			createOauthKey.doCreateOAuthKey(sCustomerEmail, sCustomerPassword);
			
			GetOAuthToken getOauthTokan = new GetOAuthToken(sUser, env);
			getOauthTokan.doGetOAuthToken(createOauthKey.getApiKey_Web(), createOauthKey.getPassword_Web());
			return getOauthTokan.getToken();
		}
		
	}

	
	public String getOAuthToken_CSR(AppUtils.Env env)
	{
		if (env.equals(AppUtils.Env.PRD))
		{
			GetOAuthToken getOauthTokan = new GetOAuthToken(sUser, env);
			getOauthTokan.doGetOAuthToken_CSR(sApiKey_CSR_PRD, sPassword_CSR_PRD);
			return getOauthTokan.getToken_CSR();
		}
		else
		{
			CreateOAuthKey createOauthKey = new CreateOAuthKey(sUser, env);
			createOauthKey.doCreateOAuthKey_CSR(sCustomerEmail, sCustomerPassword);
			
			GetOAuthToken getOauthTokan = new GetOAuthToken(sUser, env);
			getOauthTokan.doGetOAuthToken_CSR(createOauthKey.getApiKey_CSR(), createOauthKey.getPassword_CSR());
			return getOauthTokan.getToken_CSR();
		}
	}
	public String getOAuthToken_CheckAvailability(AppUtils.Env env, AbstractServiceStatusObject service)
	{
		if (env.equals(AppUtils.Env.PRD))
		{
			return getOAuthToken_CheckAvailability_PRD(env, service);
		}
		
		CreateOAuthKey createOauthKey = new CreateOAuthKey(sUser, env);
		createOauthKey.doCreateOAuthKey(sCustomerEmail, sCustomerPassword);
		
		// fail
		if (createOauthKey.getApiKey_Web().length() == 0 || createOauthKey.getPassword_Web().length() == 0)
		{
			String sResponse = createOauthKey.getErrorResponse();
			XmlHandler xh = new XmlHandler(sUser);
			String sCode = xh.extractInnerText(sResponse, "ResultCode");
			String sMessage = xh.extractInnerText(sResponse, "ResultMessage");
			if (sCode.length() > 0 || sMessage.length() > 0)
			{
				if (service instanceof PluckService)
					((PluckService)service).updateStatusList(PluckService.Steps.OL_CreateOAuthKey, false, sCode, sMessage);
			}
			else
			{
				sCode = xh.extractInnerText(sResponse, "code");
				sMessage = xh.extractInnerText(sResponse, "message");
				
				if (sCode.length() > 0 || sMessage.length() > 0)
				{
					if (service instanceof PluckService)
						((PluckService)service).updateStatusList(PluckService.Steps.OL_CreateOAuthKey, false, sCode, sMessage);
				}
				else
				{
					if (service instanceof PluckService)
						((PluckService)service).updateStatusList(PluckService.Steps.OL_CreateOAuthKey, false, "", sMessage);
				}
			}			
		}
		else // success
		{
			if (service instanceof PluckService)
				((PluckService)service).updateStatusList(PluckService.Steps.OL_CreateOAuthKey, true, "", "");
			
			GetOAuthToken getOauthTokan = new GetOAuthToken(sUser, env);
			getOauthTokan.doGetOAuthToken(createOauthKey.getApiKey_Web(), createOauthKey.getPassword_Web());
			sToken = getOauthTokan.getToken();
			if (sToken.length() > 0)// success
			{
				if (service instanceof PluckService)
					((PluckService)service).updateStatusList(PluckService.Steps.OL_GetOAuthToken, true, "", "");
			}
			else // fail
			{
				String sResponse = getOauthTokan.getErrorResponse();
				XmlHandler xh = new XmlHandler(sUser);
				String sCode = xh.extractInnerText(sResponse, "ResultCode");
				String sMessage = xh.extractInnerText(sResponse, "ResultMessage");
				if (sCode.length() > 0 || sMessage.length() > 0)
				{
					if (service instanceof PluckService)
						((PluckService)service).updateStatusList(PluckService.Steps.OL_GetOAuthToken, false, sCode, sMessage);
				}
				else
				{
					sCode = xh.extractInnerText(sResponse, "code");
					sMessage = xh.extractInnerText(sResponse, "message");
					if (sCode.length() > 0 || sMessage.length() > 0)
					{
						if (service instanceof PluckService)
							((PluckService)service).updateStatusList(PluckService.Steps.OL_GetOAuthToken, false, sCode, sMessage);
					}
					else
					{
						if (service instanceof PluckService)
							((PluckService)service).updateStatusList(PluckService.Steps.OL_GetOAuthToken, false, "", sResponse);
					}
				}
			}
		}
		return sToken;
	}
	public String getOAuthToken_CheckAvailability_PRD(AppUtils.Env env, AbstractServiceStatusObject service)
	{
		
		//if (service instanceof RoviService)			
		//	((RoviService)service).updateStatusList(RoviService.Steps.OL_CreateOAuthKey, true, "", "Not Applicable");
		//else if (service instanceof PluckService)
		//	((PluckService)service).updateStatusList(PluckService.Steps.OL_CreateOAuthKey, true, "", "Not Applicable");
			
			GetOAuthToken getOauthTokan = new GetOAuthToken(sUser, env);
			getOauthTokan.doGetOAuthToken(sApiKey_PRD, sPassword_PRD);
			sToken = getOauthTokan.getToken();
			
			if (sToken.length() > 0)// success
			{
				if (service instanceof PluckService)
					((PluckService)service).updateStatusList(PluckService.Steps.OL_GetOAuthToken, true, "", "");
			}
			else // fail
			{
				String sResponse = getOauthTokan.getErrorResponse();
				XmlHandler xh = new XmlHandler(sUser);
				String sCode = xh.extractInnerText(sResponse, "ResultCode");
				String sMessage = xh.extractInnerText(sResponse, "ResultMessage");
				if (sCode.length() > 0 || sMessage.length() > 0)
				{
					if (service instanceof PluckService)
						((PluckService)service).updateStatusList(PluckService.Steps.OL_GetOAuthToken, false, sCode, sMessage);
				}
				else
				{
					sCode = xh.extractInnerText(sResponse, "code");
					sMessage = xh.extractInnerText(sResponse, "message");
					if (sCode.length() > 0 || sMessage.length() > 0)
					{
						if (service instanceof PluckService)
							((PluckService)service).updateStatusList(PluckService.Steps.OL_GetOAuthToken, false, sCode, sMessage);
					}
					else
					{
						if (service instanceof PluckService)
							((PluckService)service).updateStatusList(PluckService.Steps.OL_GetOAuthToken, false, "", sResponse);
					}
				}
			}
			return sToken;		
	}
}
