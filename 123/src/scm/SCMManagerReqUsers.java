package com.verizon.zoetool.scm;
import java.util.HashMap;
import java.util.Map;

import com.verizon.zoetool.utils.HttpService;
import com.verizon.zoetool.utils.XmlHandler;
import com.verizon.zoetool.utils.AppUtils;





public class SCMManagerReqUsers {
	
	private String sApiName = "SCMManageReqUsers";
	private Map<String, String> mapParams;
	private String sURL, sUser;
	private AppUtils.Env eEnv;
	private HashMap<String, String> mapXsl;
	
public SCMManagerReqUsers(String userid, String user, AppUtils.Env env)
	{
		eEnv = env;
		sURL = AppUtils.getScmCareUrl(env);
		sApiName = "SCMManageReqUsers";
		
	}


	private void initParamsMap(String userid)
	{
		mapParams = new HashMap<String, String>();
		AppUtils au = new AppUtils();
		mapParams.put("###UserID###", userid);

		
		
	}
	private void clearParamsMap()
	{
		mapParams.clear();
		
	}
	
	private void clearXslParamsMap()
	{
		mapXsl.clear();
	}

   private void initXslParamsMap(String userid)
	{
		mapXsl = new HashMap<String, String>();
		mapXsl.put("env", eEnv.toString());
		mapXsl.put("###UserID###", userid);
	}

	//public String getSCMManageReqUsers(String channelid, String callerVendorid, String id, String actionid, String enterpriseid, String userid)
	
	public String getSCMManagerReqUsers(String userid)
	{
		
		System.out.println("inside getscmmanage here is right!!");
		initParamsMap(userid);
		HttpService http = new HttpService(sUser);
		String sResponse = http.callSCMServer(eEnv, sApiName, sURL, mapParams);
		System.out.println(eEnv+"-"+sApiName+"-"+sURL);
		clearParamsMap();
		System.out.println(sResponse.toString());
	//	XmlHandler xh = new XmlHandler(sUser);
	//	String responseText= xh.parseXmlResponseToHtml(sResponse, sApiName, mapParams);
	//	System.out.println(responseText);
	 //   return responseText;  
	    initXslParamsMap(userid);
		XmlHandler xh = new XmlHandler(sUser);
		String sHtml = xh.parseXmlResponseToHtml(sResponse, sApiName, mapXsl);	
		clearXslParamsMap();
		//System.out.println(sResponse.toString());
		System.out.println(sHtml.toString());
		return sHtml;

	}
	
	
	


	

	
	
	
	/*@Test
	
	public void doTest() throws IOException
	
	
	{
		getSCMManageReqUsers("12345678901234");
	}
	
	*/
}

