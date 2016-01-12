package com.verizon.zoetool.scm;

/*import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;*/

public class SCMSearchContentByGroup {// Rental and oneTime only, CR9869
	/*private static final Logger logger = Logger.getLogger(SCMSearchContentByGroup.class);
	private String sApiName = "SCMSearchContentByGroup";
	private Map<String, String> mapParams;
	private String sURL, sUser;
	private AppUtils.Env eEnv;
	
	public SCMSearchContentByGroup()
	{
		eEnv = AppUtils.Env.DIT;
		sURL = AppUtils.getScmCareUrl(eEnv);
		sUser = "hzhou";
	}
	
	public SCMSearchContentByGroup(String user, AppUtils.Env env)
	{
		eEnv = env;
		sURL = AppUtils.getScmCareUrl(env);
		sUser = user;
	}
	
	private void initParamsMap(String productid)
	{
		AppUtils au = new AppUtils();
		mapParams = new HashMap<String, String>();
		mapParams.put("###TransID###", au.genTransactionID());
		mapParams.put("###ContentId###", productid);			
	}
	private void clearParamsMap()
	{
		mapParams.clear();
	}
	
	public String checkPrice(String productid)
	{
		String sResponse = callSCM(productid);
		return sResponse;
		//XmlHandler xh = new XmlHandler(sUser);
		//String sHtml = xh.parseXmlResponseToHtml(sResponse, sApiName);
		//return sHtml.replaceFirst("tbSCMViewPurchases", "tbSCMViewPurchasesRental").replaceFirst("SCM View Purchases", "SCM View Purchases - VOD");
	}
	
	private String callSCM(String productid)
	{
		initParamsMap(productid);
		HttpService http = new HttpService(sUser);
		String sResponse = http.callSCMServer(eEnv, sApiName, sURL, mapParams);
		clearParamsMap();
		return sResponse;
	}
    @Test
	public void doTest()
	{
    	//logger.info(checkPrice("7e2b8edf-1950-4a35-854d-d1fd6c404ac6"));
    	//logger.info(checkPrice("67d4afd6-49f5-43b6-ba31-2ad869728aca"));
	}*/
}
