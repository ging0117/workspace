package com.verizon.zoetool.orch.account;

import java.util.HashMap;
import java.util.Map;



import com.verizon.zoetool.pluck.PluckService;
import com.verizon.zoetool.utils.AbstractServiceStatusObject;
import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;

public class GetProductDetailByProductID {
	private String sApiName = "GetProductDetailByProductID";
	private String sApiName_scm ="GetProductDetaiByProductID_scm";
	private String sURL, sUser;
	private Map<String, String> mapParams;
	private String sErrorResponse;
	private AppUtils.Env eEnv;
	private HashMap<String, String> mapXsl;
	
	public GetProductDetailByProductID(String user, AppUtils.Env env, String token)
	{
		sURL = AppUtils.getOlUrlWeb(env) + sApiName + "?access_token=" + token;
		sUser = user;
		eEnv = env;
	}
	
	private void initParamsMap(String productid)
	{
		mapParams = new HashMap<String, String>();
		AppUtils au = new AppUtils();
		mapParams.put("###TransactionID###", au.genTransactionID());
		mapParams.put("###TransactionTime###", au.genOLTransactionTime());
		mapParams.put("###ProductID###", productid);
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	private void initXslParamsMap(AppUtils.Env env)
	{
		mapXsl = new HashMap<String, String>();
		mapXsl.put("env", env.toString());
	}
	private void clearXslParamsMap()
	{
		mapXsl.clear();
	}
	public String doGetProductDetailByProductID(String productid)
	{
		initParamsMap(productid);		
		HttpService http = new HttpService(sUser);
		String sResponse = http.callOLServer(sApiName, sURL, mapParams);		
		clearParamsMap();	
		
		initXslParamsMap(eEnv);
		XmlHandler xh = new XmlHandler(sUser);
		String sHtml = xh.parseXmlResponseToHtml(sResponse, sApiName_scm, mapXsl);
		clearXslParamsMap();
		
		return sHtml;			
	}
	public String doGetProductDetailByProductID_CheckAvailability(String productid, AbstractServiceStatusObject service)
	{
		initParamsMap(productid);

		HttpService http = new HttpService(sUser);
		String sResponse = http.callOLServer(sApiName, sURL, mapParams);
		
		sErrorResponse = sResponse;
		clearParamsMap();
		String sAltCode = extractAltCode(sResponse);
		if (sAltCode.length() > 0)
		{
			if (service instanceof PluckService)
				((PluckService)service).updateStatusList(PluckService.Steps.OL_GetProductDetailByProductID, true, "", "");
		}
		else
		{
			XmlHandler xh = new XmlHandler(sUser);
			String sCode = xh.extractInnerText(sResponse, "ResultCode");
			String sMessage = xh.extractInnerText(sResponse, "ResultMessage");
			if (sCode.length() > 0 || sMessage.length() > 0)
			{
				if (service instanceof PluckService)
					((PluckService)service).updateStatusList(PluckService.Steps.OL_GetProductDetailByProductID, false, sCode, sMessage);
			}
			else
			{
				sCode = xh.extractInnerText(sResponse, "code");
				sMessage = xh.extractInnerText(sResponse, "message");
				if (sCode.length() > 0 || sMessage.length() > 0)
				{
					if (service instanceof PluckService)
						((PluckService)service).updateStatusList(PluckService.Steps.OL_GetProductDetailByProductID, false, sCode, sMessage);
				}
				else
				{
					if (service instanceof PluckService)
						((PluckService)service).updateStatusList(PluckService.Steps.OL_GetProductDetailByProductID, false, "", sResponse);
				}
			}
		}
		return sAltCode;
	}
	
	private String extractAltCode(String xml)
	{
		XmlHandler xh = new XmlHandler(sUser);
		return xh.extractInnerText(xml, "AltCode");
	}
	public String getErrorResponse()
	{
		return sErrorResponse;
	}
}
