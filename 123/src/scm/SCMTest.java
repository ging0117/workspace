/*package com.verizon.zoetool.scm;

import java.util.HashMap;
import java.io.IOException;
import java.util.Map;
import java.io.*;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;
import com.verizon.zoetool.utils.AppUtils;

import org.junit.Test;

public class SCMTest {

private String sApiName = "SCMTest";
private Map<String, String> mapParams;
private String sURL, sUser;
private AppUtils.Env eEnv;
private HashMap<String, String> mapXsl;



	
	public SCMTest(String user, AppUtils.Env env)
	
	{
		eEnv = env;
		sURL = AppUtils.getScmCareUrl(env);
		sUser = user;
	}

	private void initParamsMap(String userid)
	{
		mapParams = new HashMap<String, String>();
		AppUtils au = new AppUtils();
		//pParams.put("###TransactionID###", au.genTransactionID());
		//pParams.put("###SKUID###", purchaseoptionid);
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
	public void test(String userid)
	{
		initParamsMap(userid);
		initXslParamsMap(eEnv);
		HttpService http = new HttpService(sUser);
		String sResponse = http.callSCMServer(eEnv, sApiName, sURL, mapParams);
		
		System.out.println(sResponse);
		clearParamsMap();
		
		//lHandler xh = new XmlHandler(sUser);
		//ring sHtml = xh.parseXmlResponseToHtml(sResponse, sApiName, mapXsl);
		clearXslParamsMap();
		//turn sHtml;
		
	}



	 
	    
	@Test
	public void doTest() throws IOException
	
	
	{
		test("");
	}
}*/