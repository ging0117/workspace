package com.verizon.zoetool.orch.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.verizon.zoetool.pluck.PluckService;
import com.verizon.zoetool.utils.AbstractServiceStatusObject;
import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;

public class ProductSearch {
	private String sApiName = "ProductSearch";
	private String sURL, sUser;
	private Map<String, String> mapParams;
	
	public ProductSearch(String user, AppUtils.Env env, String token)
	{
		sURL = AppUtils.getOlUrlWeb(env) + sApiName + "?access_token=" + token;
		sUser = user;
	}
	
	private void initParamsMap(int pagesize, int pagenumber, String query)
	{
		mapParams = new HashMap<String, String>();
		AppUtils au = new AppUtils();
		mapParams.put("###TransactionID###", au.genTransactionID());
		mapParams.put("###TransactionTime###", au.genOLTransactionTime());
		mapParams.put("###PageSize###", Integer.toString(pagesize));
		mapParams.put("###PageNumber###", Integer.toString(pagenumber));
		mapParams.put("###MovieName###", query);
		
		}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	public List<String> doProductSearch_CheckAvailability(int pagesize, int pagenumber, AbstractServiceStatusObject service)
	{
		initParamsMap(pagesize, pagenumber, "car");

		HttpService http = new HttpService(sUser);
		String sResponse = http.callOLServer(sApiName, sURL, mapParams);
		
		clearParamsMap();
		
		List<String> list = extractProductIDs(sResponse);

		if (list.size() == 0) //fail
		{
			XmlHandler xh = new XmlHandler(sUser);
			String sCode = xh.extractInnerText(sResponse, "ResultCode");
			String sMessage = xh.extractInnerText(sResponse, "ResultMessage");
			if (sCode.length() > 0 || sMessage.length() > 0)
			{
				if (service instanceof PluckService)
					((PluckService)service).updateStatusList(PluckService.Steps.OL_ProductSearch, false, sCode, sMessage);
			}
			else
			{
				sCode = xh.extractInnerText(sResponse, "code");
				sMessage = xh.extractInnerText(sResponse, "message");
				if (sCode.length() > 0 || sMessage.length() > 0)
				{
					if (service instanceof PluckService)
						((PluckService)service).updateStatusList(PluckService.Steps.OL_ProductSearch, false, sCode, sMessage);
				}
				else
				{
					if (service instanceof PluckService)
						((PluckService)service).updateStatusList(PluckService.Steps.OL_ProductSearch, false, "", sResponse.substring(0, 300));
				}
			}
		}
		else // success
		{
			if (service instanceof PluckService)
				((PluckService)service).updateStatusList(PluckService.Steps.OL_ProductSearch, true, "", "");
		}
		return list;
	}
	
	public List<String> doProductSearch(int pagesize, int pagenumber, String query)
	{
		initParamsMap(pagesize, pagenumber, query);

		HttpService http = new HttpService(sUser);
		String sResponse = http.callOLServer(sApiName, sURL, mapParams);
		
		clearParamsMap();
		
		List<String> list = extractProductIDs(sResponse);
		return list;
	}
	
	private List<String> extractProductIDs(String xml)
	{
		List<String> list = new ArrayList<String>();
		
		if (xml.length() == 0)
			return list;
		
		String[] s = xml.split("<ProductInfo>");
		
		XmlHandler xh = new XmlHandler(sUser);
		
		for(int i = 0; i < s.length; i++)
		{
			String sProductID = xh.extractInnerText(s[i], "ProductID");
			if (sProductID.length() > 0)
				list.add(sProductID);
		}	
	
		return list;
	}
}
