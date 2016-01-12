package com.verizon.zoetool.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.junit.Test;

public class AppUtils
{
    public AppUtils()
    {
    }
    public enum Env
    {
    	DIT,
    	SIT,
    	STG,
    	PRD;
    	
    	@Override public String toString() 
    	{
    		String s = super.toString();
    		return s.toLowerCase();
    	}
    }
    
    private static final Logger logger = Logger.getLogger(AppUtils.class);
    private static final String sPackage = "app";
    private static String[] ENV;
    private static String PROXY_SWITCH, PROXY_URL;
    private static int PROXY_PORT;
    
    private static String OL_URL_WEB_DIT, OL_URL_WEB_SIT, OL_URL_WEB_STG, OL_URL_WEB_PRD;
    private static String OL_URL_CSR_DIT, OL_URL_CSR_SIT, OL_URL_CSR_STG, OL_URL_CSR_PRD;
    private static String SCM_URL_DIT, SCM_URL_SIT, SCM_URL_STG, SCM_URL_PRD;
    private static String SCM_URL_USAGE_DIT, SCM_URL_USAGE_SIT, SCM_URL_USAGE_STG, SCM_URL_USAGE_PRD;
    private static String SCM_URL_CANCEL_DIT, SCM_URL_CANCEL_SIT, SCM_URL_CANCEL_STG, SCM_URL_CANCEL_PRD;
    private static String SCM_SSL_KEYSTORE_DIT, SCM_SSL_KEYSTORE_SIT, SCM_SSL_KEYSTORE_STG, SCM_SSL_KEYSTORE_PRD;
    private static String SCM_SSL_KEYSTORE_PASSWORD_DIT, SCM_SSL_KEYSTORE_PASSWORD_SIT, SCM_SSL_KEYSTORE_PASSWORD_STG, SCM_SSL_KEYSTORE_PASSWORD_PRD;
    private static String SCM_SSL_KEYSTORE_TYPE_DIT, SCM_SSL_KEYSTORE_TYPE_SIT, SCM_SSL_KEYSTORE_TYPE_STG, SCM_SSL_KEYSTORE_TYPE_PRD;
    //private static String VMS_AFFILIATE_ID_DIT, VMS_AFFILIATE_ID_SIT, VMS_AFFILIATE_ID_STG, VMS_AFFILIATE_ID_PRD;
    private static String VMS_URL_ACCOUNT_MANAGER_DIT, VMS_URL_ACCOUNT_MANAGER_SIT, VMS_URL_ACCOUNT_MANAGER_STG, VMS_URL_ACCOUNT_MANAGER_PRD;
    private static String VMS_URL_RIGHTS_LOCKER_MANAGER_DIT, VMS_URL_RIGHTS_LOCKER_MANAGER_SIT, VMS_URL_RIGHTS_LOCKER_MANAGER_STG, VMS_URL_RIGHTS_LOCKER_MANAGER_PRD;
    private static String VMS_URL_DEVICE_MANAGER_DIT, VMS_URL_DEVICE_MANAGER_SIT, VMS_URL_DEVICE_MANAGER_STG, VMS_URL_DEVICE_MANAGER_PRD;
    private static String RB_OAUTH_CLIENT_DIT, RB_OAUTH_CLIENT_SIT, RB_OAUTH_CLIENT_STG, RB_OAUTH_APIKEY_PRD, RB_OAUTH_APIKEY_CSR_PRD;
    private static String RB_OAUTH_PASSWORD_DIT, RB_OAUTH_PASSWORD_SIT, RB_OAUTH_PASSWORD_STG, RB_OAUTH_PASSWORD_PRD, RB_OAUTH_PASSWORD_CSR_PRD;
    private static String RB_URL_DIT, RB_URL_SIT, RB_URL_STG, RB_URL_PRD;
    private static String RB_URL_TOKEN_DIT, RB_URL_TOKEN_SIT, RB_URL_TOKEN_STG, RB_URL_TOKEN_PRD;
    private static String RB_CLIENT_DIT, RB_CLIENT_SIT, RB_CLIENT_STG, RB_CLIENT_PRD;
    private static String URL_REMOVEDEVICE, DIR_XML, DIR_XML_RESPONSE, DIR_XSL;
    private static String RB_PWD_DIT, RB_PWD_SIT, RB_PWD_STG, RB_PWD_PRD;    
    
    private static String ET_URL_DIT, ET_URL_SIT, ET_URL_STG, ET_URL_PRD;
    private static String ET_USER_DIT, ET_USER_SIT, ET_USER_STG, ET_USER_PRD;
    private static String ET_PWD_DIT, ET_PWD_SIT, ET_PWD_STG, ET_PWD_PRD;
    private static String PLUCK_URL_DIT, PLUCK_URL_SIT, PLUCK_URL_STG, PLUCK_URL_PRD;
    private static String PLUCK_KEY_DIT, PLUCK_KEY_SIT, PLUCK_KEY_STG, PLUCK_KEY_PRD;
    private static String DS_URL_DIT, DS_URL_SIT, DS_URL_STG, DS_URL_PRD;
    
    private static ResourceBundle bundle;
    
    
    static 
    {
        bundle = ResourceBundle.getBundle(sPackage);
        ENV = setEnv();
        
        // Proxy
        setProxy(); 
        
        // OL
        setOL(ENV);
        
        // VMS
        //setVMS(ENV);
        
        // Remove Device URL
        URL_REMOVEDEVICE = readLine("url.removedevice");
       
        // OAuth        
        setOAUTH(ENV);
        // RB
        setRB(ENV);
              
        // XML
        DIR_XML = readLine("dir.xml");
        DIR_XML_RESPONSE = readLine("dir.xml.response");
        DIR_XSL = readLine("dir.xsl");
        
        // SCM
        setSCM(ENV);
        
        // ET
        setET(ENV);  
        
        // Pluck
        setPluck(ENV);
        
        // DS
        setDS(ENV);
    }
    public static String[] getAllAvailableEnvs()
    {
    	return ENV;
    }
    
    private static String readLine(String key)
    {
    	String value = null;
    	try
    	{
    		value = bundle.getString(key);
    		if (value != null)
        		value = value.trim();
    	}
    	catch(Exception e)
    	{
    		logger.error("[api=readLine]\t[key=" + key + "]\t[message=" + e.getMessage() + "]");
    	}
    	return value;
    }
	private static String[] setEnv()
	{
		String s = readLine("env").replace(" ", "");
        String[] sEnv = s.split(",");
        return sEnv;
	}
	
	
	private static void setProxy()
	{
		PROXY_SWITCH = readLine("proxy.switch");
        PROXY_URL = readLine("proxy.url");
        PROXY_PORT = Integer.parseInt(readLine("proxy.port"));
	}
	
	private static void setPluck(String[] env)
	{
		for(String s: env)
		{
			if (s.isEmpty())
				continue;
			String url = readLine(s + ".pluck.url");
			String key = readLine(s + ".pluck.sharedsecret.key");
			if (s.equalsIgnoreCase("dyn"))
			{
				PLUCK_URL_DIT = url;
				PLUCK_KEY_DIT = key;
			}
			if (s.equalsIgnoreCase("sit"))
			{
				PLUCK_URL_SIT = url;
				PLUCK_KEY_SIT = key;
			}
			if (s.equalsIgnoreCase("stg"))
			{
				PLUCK_URL_STG = url;
				PLUCK_KEY_STG = key;
			}
			if (s.equalsIgnoreCase("prd"))
			{
				PLUCK_URL_PRD = url;
				PLUCK_KEY_PRD = key;
			}
		}      
	}
	
	private static void setDS(String[] env)
	{
		for(String s: env)
		{
			if (s.isEmpty())
				continue;
			String url = readLine(s + ".ds.url");
			if (s.equalsIgnoreCase("dyn"))
			{
				DS_URL_DIT = url;
			}
			if (s.equalsIgnoreCase("sit"))
			{
				DS_URL_SIT = url;
			}
			if (s.equalsIgnoreCase("stg"))
			{
				DS_URL_STG = url;
			}
			if (s.equalsIgnoreCase("prd"))
			{
				DS_URL_PRD = url;
			}
		}      
	}

	private static void setOL(String[] env)
	{
		for(String s: env)
		{
			if (s.isEmpty())
				continue;
			String web = readLine(s + ".ol.url.base") + readLine(s + ".ol.url.web");
			String csr = readLine(s + ".ol.url.base") + readLine(s + ".ol.url.csr");
			if (s.equalsIgnoreCase("dyn"))
			{
				OL_URL_WEB_DIT = web;
		        OL_URL_CSR_DIT = csr;
			}
			if (s.equalsIgnoreCase("sit"))
			{
				OL_URL_WEB_SIT = web;
		        OL_URL_CSR_SIT = csr;
			}
			if (s.equalsIgnoreCase("stg"))
			{
				OL_URL_WEB_STG = web;
		        OL_URL_CSR_STG = csr;
			}
			if (s.equalsIgnoreCase("prd"))
			{
				OL_URL_WEB_PRD = web;
		        OL_URL_CSR_PRD = csr;
			}
		}      
	}
	private static void setSCM(String[] env)
	{
		for(String s: env)
		{
			if (s.isEmpty())
				continue;
			String url = readLine(s + ".scm.url.other");
			String urlusage = readLine(s + ".scm.url.base") + readLine(s + ".scm.url.usage");
			String urlcancel = readLine(s + ".scm.url.base") + readLine(s + ".scm.url.cancel");
			String keystore = readLine(s + ".scm.ssl.keystore");
			String password = readLine(s + ".scm.ssl.keystore.password");
			String type = readLine(s + ".scm.ssl.keystore.type");
	        
			if (s.equalsIgnoreCase("dyn"))
			{
				SCM_URL_DIT = url;
		        SCM_URL_USAGE_DIT = urlusage;
		        SCM_URL_CANCEL_DIT = urlcancel;
		        
		        SCM_SSL_KEYSTORE_DIT = keystore;
		        SCM_SSL_KEYSTORE_PASSWORD_DIT = password;
		        SCM_SSL_KEYSTORE_TYPE_DIT = type;				
			}
			if (s.equalsIgnoreCase("sit"))
			{
				SCM_URL_SIT = url;
		        SCM_URL_USAGE_SIT = urlusage;
		        SCM_URL_CANCEL_SIT = urlcancel;
		        
		        SCM_SSL_KEYSTORE_SIT = keystore;
		        SCM_SSL_KEYSTORE_PASSWORD_SIT = password;
		        SCM_SSL_KEYSTORE_TYPE_SIT = type;
			}
			if (s.equalsIgnoreCase("stg"))
			{
				SCM_URL_STG = url;
		        SCM_URL_USAGE_STG = urlusage;
		        SCM_URL_CANCEL_STG = urlcancel;
		        
		        SCM_SSL_KEYSTORE_STG = keystore;
		        SCM_SSL_KEYSTORE_PASSWORD_STG = password;
		        SCM_SSL_KEYSTORE_TYPE_STG = type;
			}
			if (s.equalsIgnoreCase("prd"))
			{
				SCM_URL_PRD = url;
		        SCM_URL_USAGE_PRD = urlusage;
		        SCM_URL_CANCEL_PRD = urlcancel;
		        
		        SCM_SSL_KEYSTORE_PRD = keystore;
		        SCM_SSL_KEYSTORE_PASSWORD_PRD = password;
		        SCM_SSL_KEYSTORE_TYPE_PRD = type;
			}
		}    
	}
	/*private static void setVMS(String[] env)
	{
		for(String s: env)
		{
			if (s.isEmpty())
				continue;
			//String id = readLine(s + ".vms.affiliateid");
			String account = readLine(s + ".vms.url.base") + readLine(s + ".vms.url.accountmanager");
			String rightslocker = readLine(s + ".vms.url.base") + readLine(s + ".vms.url.rightslockermanager");
			String device = readLine(s + ".vms.url.base") + readLine(s + ".vms.url.devicemanager");       

			if (s.equalsIgnoreCase("dyn"))
			{
				//VMS_AFFILIATE_ID_DIT = id;
		        VMS_URL_ACCOUNT_MANAGER_DIT = account;
		        VMS_URL_RIGHTS_LOCKER_MANAGER_DIT = rightslocker;
		        VMS_URL_DEVICE_MANAGER_DIT = device;
			}
			if (s.equalsIgnoreCase("sit"))
			{
				//VMS_AFFILIATE_ID_SIT = id;
		        VMS_URL_ACCOUNT_MANAGER_SIT = account;
		        VMS_URL_RIGHTS_LOCKER_MANAGER_SIT = rightslocker;
		        VMS_URL_DEVICE_MANAGER_SIT = device;
			}
			if (s.equalsIgnoreCase("stg"))
			{
				//VMS_AFFILIATE_ID_STG = id;
		        VMS_URL_ACCOUNT_MANAGER_STG = account;
		        VMS_URL_RIGHTS_LOCKER_MANAGER_STG = rightslocker;
		        VMS_URL_DEVICE_MANAGER_STG = device;
			}
			if (s.equalsIgnoreCase("prd"))
			{
				//VMS_AFFILIATE_ID_PRD = id;
		        VMS_URL_ACCOUNT_MANAGER_PRD = account;
		        VMS_URL_RIGHTS_LOCKER_MANAGER_PRD = rightslocker;
		        VMS_URL_DEVICE_MANAGER_PRD = device;
			}
		}      
	}*/
	private static void setOAUTH(String[] env)
	{
		for(String s: env)
		{
			if (s.isEmpty())
				continue;
			
			if (s.equalsIgnoreCase("prd"))
			{
				RB_OAUTH_APIKEY_PRD = readLine(s + ".rb.oauth.apikey");
		        RB_OAUTH_PASSWORD_PRD = readLine(s + ".rb.oauth.password"); 
		        RB_OAUTH_APIKEY_CSR_PRD = readLine(s + ".rb.oauth.apikey.csr");
		        RB_OAUTH_PASSWORD_CSR_PRD = readLine(s + ".rb.oauth.password.csr");
		        continue;
			}
			String client = readLine(s + ".rb.oauth.client");
			String password = readLine(s + ".rb.oauth.password");
			if (s.equalsIgnoreCase("dyn"))
			{
				RB_OAUTH_CLIENT_DIT = client;
		        RB_OAUTH_PASSWORD_DIT = password;		        
			}
			if (s.equalsIgnoreCase("sit"))
			{
				RB_OAUTH_CLIENT_SIT = client;
		        RB_OAUTH_PASSWORD_SIT = password;
			}
			if (s.equalsIgnoreCase("stg"))
			{
				RB_OAUTH_CLIENT_STG = client;
		        RB_OAUTH_PASSWORD_STG = password;
			}
			
		}      
	}
	private static void setRB(String[] env)
	{
		for(String s: env)
		{
			if (s.isEmpty())
				continue;
			String url = readLine(s + ".rb.url.base");
			String token = url + readLine(s + ".rb.url.token");
			String client = readLine(s + ".rb.client");
			String password = readLine(s + ".rb.password");
			if (s.equalsIgnoreCase("dyn"))
			{
				RB_URL_DIT = url;
		        RB_URL_TOKEN_DIT = token;
		        RB_CLIENT_DIT = client;
		        RB_PWD_DIT = password;		        
			}
			if (s.equalsIgnoreCase("sit"))
			{
				RB_URL_SIT = url;
		        RB_URL_TOKEN_SIT = token;
		        RB_CLIENT_SIT = client;
		        RB_PWD_SIT = password;
			}
			if (s.equalsIgnoreCase("stg"))
			{
				RB_URL_STG = url;
		        RB_URL_TOKEN_STG = token;
		        RB_CLIENT_STG = client;
		        RB_PWD_STG = password;
			}
			if (s.equalsIgnoreCase("prd"))
			{
				RB_URL_PRD = url;
		        RB_URL_TOKEN_PRD = token;
		        RB_CLIENT_PRD = client;
		        RB_PWD_PRD = password;
			}
		}      
	}
	private static void setET(String[] env)
	{
		for(String s: env)
		{
			if (s.isEmpty())
				continue;
			String url = readLine(s + ".exacttarget.url");
			String user = readLine(s + ".exacttarget.user");
			String password = readLine(s + ".exacttarget.password");
			if (s.equalsIgnoreCase("dyn"))
			{
				ET_URL_DIT = url;
				ET_USER_DIT = user;
		        ET_PWD_DIT = password;		        
			}
			if (s.equalsIgnoreCase("sit"))
			{
				ET_URL_SIT = url;
				ET_USER_SIT = user;
		        ET_PWD_SIT = password;
			}
			if (s.equalsIgnoreCase("stg"))
			{
				ET_URL_STG = url;
				ET_USER_STG = user;
		        ET_PWD_STG = password;
			}
			if (s.equalsIgnoreCase("prd"))
			{
				ET_URL_PRD = url;
				ET_USER_PRD = user;
		        ET_PWD_PRD = password;
			}
		} 
	}
	
	
	public static AppUtils.Env getEnv(String value)
	{
		if (value.equalsIgnoreCase("sit"))
			return AppUtils.Env.SIT;		
		if (value.equalsIgnoreCase("stg"))
			return AppUtils.Env.STG;
		if (value.equalsIgnoreCase("prd"))
			return AppUtils.Env.PRD;
		else
			return AppUtils.Env.DIT;
	}
	public static String getProxySwitch()
	{
		return PROXY_SWITCH;
	}
	public static String getProxyUrl()
	{
		return PROXY_URL;
	}
	public static int getProxyPort()
	{
		return PROXY_PORT;
	}
	public static String getXmlDir()
	{
		return DIR_XML;
	}
	public static String getXmlRespDir()
	{
		return DIR_XML_RESPONSE;
	}
	public static String getXsltDir()
	{
		return DIR_XSL;
	}
	public static String getRemoveDeviceUrl()
	{
		return URL_REMOVEDEVICE;
	}
 	public static String getOlUrlWeb(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return OL_URL_WEB_DIT;
    	case SIT:
    		return OL_URL_WEB_SIT;
    	case STG:
    		return OL_URL_WEB_STG;
    	case PRD:
    		return OL_URL_WEB_PRD;
    	default:
    		return "";   		
    	}
    }
    public static String getOlUrlCsr(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return OL_URL_CSR_DIT;
    	case SIT:
    		return OL_URL_CSR_SIT;
    	case STG:
    		return OL_URL_CSR_STG;
    	case PRD:
    		return OL_URL_CSR_PRD;
    	default:
    		return "";   		
    	}
    }
    public static String getRbOauthClient(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return RB_OAUTH_CLIENT_DIT;
    	case SIT:
    		return RB_OAUTH_CLIENT_SIT;
    	case STG:
    		return RB_OAUTH_CLIENT_STG;
    	case PRD:
    		return RB_OAUTH_APIKEY_PRD;
    	default:
    		return "";   		
    	}
    }
    public static String getRbOauthApikey_PRD()
    {
    	return RB_OAUTH_APIKEY_PRD;
    }
    public static String getRbOauthApikey_CSR_PRD()
    {
    	return RB_OAUTH_APIKEY_CSR_PRD;
    }
    public static String getRbOauthPwd(Env env, String scope)
    {
    	switch(env)
    	{
    	case DIT:
    		return RB_OAUTH_PASSWORD_DIT;
    	case SIT:
    		return RB_OAUTH_PASSWORD_SIT;
    	case STG:
    		return RB_OAUTH_PASSWORD_STG;
    	case PRD:
    	{
    		if (scope.equalsIgnoreCase("csr"))
    			return RB_OAUTH_PASSWORD_CSR_PRD;
    		else
    			return RB_OAUTH_PASSWORD_PRD;
    	}
    	default:
    		return "";   		
    	}
    }    
    public static String getScmCareUrl(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return SCM_URL_DIT;
    	case SIT:
    		return SCM_URL_SIT;
    	case STG:
    		return SCM_URL_STG;
    	case PRD:
    		return SCM_URL_PRD;
    	default:
    		return "";   		
    	}
    }    
    public static String getScmUsageUrl(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return SCM_URL_USAGE_DIT;
    	case SIT:
    		return SCM_URL_USAGE_SIT;
    	case STG:
    		return SCM_URL_USAGE_STG;
    	case PRD:
    		return SCM_URL_USAGE_PRD;
    	default:
    		return "";   		
    	}
    }
    public static String getScmCancelUrl(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return SCM_URL_CANCEL_DIT;
    	case SIT:
    		return SCM_URL_CANCEL_SIT;
    	case STG:
    		return SCM_URL_CANCEL_STG;
    	case PRD:
    		return SCM_URL_CANCEL_PRD;
    	default:
    		return "";   		
    	}
    }
    public static String getScmKeystore(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return SCM_SSL_KEYSTORE_DIT;
    	case SIT:
    		return SCM_SSL_KEYSTORE_SIT;
    	case STG:
    		return SCM_SSL_KEYSTORE_STG;
    	case PRD:
    		return SCM_SSL_KEYSTORE_PRD;
    	default:
    		return "";   		
    	}
    }
    public static String getScmKeystorePwd(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return SCM_SSL_KEYSTORE_PASSWORD_DIT;
    	case SIT:
    		return SCM_SSL_KEYSTORE_PASSWORD_SIT;
    	case STG:
    		return SCM_SSL_KEYSTORE_PASSWORD_STG;
    	case PRD:
    		return SCM_SSL_KEYSTORE_PASSWORD_PRD;
    	default:
    		return "";   		
    	}
    }
    public static String getScmKeystoreType(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return SCM_SSL_KEYSTORE_TYPE_DIT;
    	case SIT:
    		return SCM_SSL_KEYSTORE_TYPE_SIT;
    	case STG:
    		return SCM_SSL_KEYSTORE_TYPE_STG;
    	case PRD:
    		return SCM_SSL_KEYSTORE_TYPE_PRD;
    	default:
    		return "";   		
    	}
    }
    public static String getRbTokenUrl(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return RB_URL_TOKEN_DIT;
    	case SIT:
    		return RB_URL_TOKEN_SIT;
    	case STG:
    		return RB_URL_TOKEN_STG;
    	case PRD:
    		return RB_URL_TOKEN_PRD;
    	default:
    		return "";   		
    	}
    }    
    public static String getRbUrl(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return RB_URL_DIT;
    	case SIT:
    		return RB_URL_SIT;
    	case STG:
    		return RB_URL_STG;
    	case PRD:
    		return RB_URL_PRD;
    	default:
    		return "";   		
    	}
    }    
    public static String getRbClient(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return RB_CLIENT_DIT;
    	case SIT:
    		return RB_CLIENT_SIT;
    	case STG:
    		return RB_CLIENT_STG;
    	case PRD:
    		return RB_CLIENT_PRD;
    	default:
    		return "";   		
    	}
    }  
    public static String getRbPwd(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return RB_PWD_DIT;
    	case SIT:
    		return RB_PWD_SIT;
    	case STG:
    		return RB_PWD_STG;
    	case PRD:
    		return RB_PWD_PRD;
    	default:
    		return "";   		
    	}
    }
    /*public static String getVmsAffiliateId(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return VMS_AFFILIATE_ID_DIT;
    	case SIT:
    		return VMS_AFFILIATE_ID_SIT;
    	case STG:
    		return VMS_AFFILIATE_ID_STG;
    	case PRD:
    		return VMS_AFFILIATE_ID_PRD;
    	default:
    		return "";   		
    	}
    } */
    public static String getVmsAccountManagerUrl(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return VMS_URL_ACCOUNT_MANAGER_DIT;
    	case SIT:
    		return VMS_URL_ACCOUNT_MANAGER_SIT;
    	case STG:
    		return VMS_URL_ACCOUNT_MANAGER_STG;
    	case PRD:
    		return VMS_URL_ACCOUNT_MANAGER_PRD;
    	default:
    		return "";   		
    	}
    } 
    public static String getVmsRightsLockerManagerUrl(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return VMS_URL_RIGHTS_LOCKER_MANAGER_DIT;
    	case SIT:
    		return VMS_URL_RIGHTS_LOCKER_MANAGER_SIT;
    	case STG:
    		return VMS_URL_RIGHTS_LOCKER_MANAGER_STG;
    	case PRD:
    		return VMS_URL_RIGHTS_LOCKER_MANAGER_PRD;
    	default:
    		return "";   		
    	}
    }    
    public static String getVmsDeviceManagerUrl(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return VMS_URL_DEVICE_MANAGER_DIT;
    	case SIT:
    		return VMS_URL_DEVICE_MANAGER_SIT;
    	case STG:
    		return VMS_URL_DEVICE_MANAGER_STG;
    	case PRD:
    		return VMS_URL_DEVICE_MANAGER_PRD;
    	default:
    		return "";   		
    	}
    } 
    public static String getEtUrl(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return ET_URL_DIT;
    	case SIT:
    		return ET_URL_SIT;
    	case STG:
    		return ET_URL_STG;
    	case PRD:
    		return ET_URL_PRD;
    	default:
    		return "";   		
    	}   		
    }
    public static String getEtUser(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return ET_USER_DIT;
    	case SIT:
    		return ET_USER_SIT;
    	case STG:
    		return ET_USER_STG;
    	case PRD:
    		return ET_USER_PRD;
    	default:
    		return "";   		
    	}  		
    }
    public static String getEtPwd(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return ET_PWD_DIT;
    	case SIT:
    		return ET_PWD_SIT;
    	case STG:
    		return ET_PWD_STG;
    	case PRD:
    		return ET_PWD_PRD;
    	default:
    		return "";   		
    	}
    }
    public static String getPluckUrl(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return PLUCK_URL_DIT;
    	case SIT:
    		return PLUCK_URL_SIT;
    	case STG:
    		return PLUCK_URL_STG;
    	case PRD:
    		return PLUCK_URL_PRD;
    	default:
    		return "";   		
    	}
    }
    public static String getPluckSharedSecretKey(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return PLUCK_KEY_DIT;
    	case SIT:
    		return PLUCK_KEY_SIT;
    	case STG:
    		return PLUCK_KEY_STG;
    	case PRD:
    		return PLUCK_KEY_PRD;
    	default:
    		return "";   		
    	}
    }  
    
    public static String getDSUrl(Env env)
    {
    	switch(env)
    	{
    	case DIT:
    		return DS_URL_DIT;
    	case SIT:
    		return DS_URL_SIT;
    	case STG:
    		return DS_URL_STG;
    	case PRD:
    		return DS_URL_PRD;
    	default:
    		return "";   		
    	}
    }
    
    public String genTransactionID()
	{
		UUID sTransID = UUID.randomUUID();
		return sTransID.toString();
	}
    public String genOLTransactionTime()
	{
		// Sample:2011-10-12T07:07:31-04:00
		
		TimeZone tz = TimeZone.getTimeZone("America/New_York");
		Calendar cal = Calendar.getInstance(tz); //returns a calendar set to the local time 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		format.setCalendar(cal);  //explicitly set the calendar into the date formatter
		
		String sTime = format.format(cal.getTime());
		return sTime.substring(0, 22) + ":" + sTime.substring(22);
	}
    public String getToday()
    {
    	TimeZone tz = TimeZone.getTimeZone("America/New_York");
		Calendar cal = Calendar.getInstance(tz); //returns a calendar set to the local time in Chicago                     // timestamp now                           // set cal to date
    	cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
    	cal.set(Calendar.MINUTE, 0);                 // set minute in hour
    	cal.set(Calendar.SECOND, 0);                 // set second in minute
    	cal.set(Calendar.MILLISECOND, 0);            // set millis in second
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		format.setCalendar(cal);  //explicitly set the calendar into the date formatter
		
		String sTime = format.format(cal.getTime());
		return sTime.substring(0, 22) + ":" + sTime.substring(22);
    }
    public String getDate(int yearincrease, int monthincrease, int dayincrease)
    {
    	//2011-10-12T00:00:00.000
    	TimeZone tz = TimeZone.getTimeZone("America/New_York");
    	Calendar cal = Calendar.getInstance(tz);
    	cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
    	cal.set(Calendar.MINUTE, 0);                 // set minute in hour
    	cal.set(Calendar.SECOND, 0);                 // set second in minute
    	cal.set(Calendar.MILLISECOND, 0);
    	cal.add(Calendar.YEAR, yearincrease);
    	cal.add(Calendar.MONTH, monthincrease);
    	cal.add(Calendar.DATE, dayincrease);
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");// timezone Z
		format.setCalendar(cal);  //explicitly set the calendar into the date formatter
    	
		String sTime = format.format(cal.getTime());
		return sTime;
		//return sTime.substring(0, 22) + ":" + sTime.substring(22);
    }
    public String getSCMDate(int yearincrease, int monthincrease, int dayincrease)
    {
    	String sDate = getDate(yearincrease, monthincrease, dayincrease);
    	return sDate.substring(5, 7) + "/" + sDate.substring(8,10) + "/" + sDate.substring(0,4);
    }
    
    public boolean isValidPCN(String pcn)
    {
    	if (pcn != null && pcn.length() == 14)
    	{
    		String sPattern = "^[0-9]*$";
			Pattern pattern = Pattern.compile(sPattern);
			Matcher matcher = pattern.matcher(pcn);
			if (matcher.find())
				return true;
    	}
    	return false;
    }
    public boolean isValidEnv(String e)
    {
    	if (e != null && e.length() > 0)
    	{
    		for (String s : ENV)
    		{
    			if (s.length() > 0)
    			{
    				if (e.equalsIgnoreCase("dit") && s.equalsIgnoreCase("dyn"))
    					return true;
    				if (e.equalsIgnoreCase(s))
    					return true;
    			}
    		}
    	}
    	return false;
    }
    @Test
	public void doTest()
	{
    	String[] ss= TimeZone.getAvailableIDs() ;
		for(String s : ss)
		{
			if (s.startsWith("America"))
				logger.info(s);
		}
	}
}
