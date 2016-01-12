package com.verizon.zoetool.vms;

import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.SoapService;
import com.verizon.zoetool.utils.XmlHandler;

public class VMSAccountService {
	private Map<String, String> mapParams;
	
	private String sAccountPk = "", sURL, sUser;
	
	public VMSAccountService(String user, AppUtils.Env env)
	{
		sURL = AppUtils.getVmsAccountManagerUrl(env);
		sUser = user;
	}
	
	private void initParamsMap(String pcn)
	{
		String sAccountEmail = pcn + "@cisco.com";
		
		mapParams = new HashMap<String, String>();
		mapParams.put("###AccountEmail###", sAccountEmail);
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	
	public String findAccountByPCN(String pcn)
	{
		String sApiName = "VMSFindAccountByEmail";
		initParamsMap(pcn);		
		SoapService ss = new SoapService(sUser);
		String sResponse = ss.callVMSServer(sApiName, sURL, mapParams);
		clearParamsMap();
			
		sAccountPk = extractAccountPk(sResponse);
		XmlHandler xh = new XmlHandler(sUser);
		return xh.parseXmlResponseToHtml(sResponse, sApiName);
	}
	private String extractAccountPk(String xml)
	{
		String sAccountPk = "";
		if (xml.length() == 0)
			return sAccountPk;//<ns1:accountPk>56962</ns1:accountPk>
		int a = xml.indexOf("<ns1:accountPk>");
		int b = xml.indexOf("</ns1:accountPk>");
		int c = "<ns1:accountPk>".length();
		if (a != -1 && b != -1 && a + c < b)
			sAccountPk = xml.substring(a + c, b);
		return sAccountPk;
	}
	public String getAccountPk()
	{
		return sAccountPk;
	}
}
