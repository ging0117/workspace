package com.verizon.zoetool.utils;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

import java.io.File;
import java.io.FileInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import org.apache.log4j.*;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpService {
	private static final Logger logger =Logger.getLogger(HttpService.class);
    private static final String PROXY_HOST = AppUtils.getProxyUrl();
    private static final int PROXY_PORT = AppUtils.getProxyPort();
    String sUser;
    
    public HttpService(String user)
    {
    	sUser = user;
    }
    
    private String genPostBody(String filename, Map<String, String> map)
    {
    	XmlHandler xh = new XmlHandler(sUser);
		String sPostBody = xh.readFile(AppUtils.getXmlDir() + filename);
		
		for(String key: map.keySet())
		{
			String value = map.get(key);
			sPostBody = sPostBody.replace(key, value);
		}
		return sPostBody;
    }
    private void printUrl(String apiName, String url)
    {
    	// OAuth APIs
    	if (apiName.equalsIgnoreCase("CreateOAuthKey")
    			|| apiName.equalsIgnoreCase("GetOAuthToken"))
    	{
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[url=" + url + "]");
    	}
    	// OL APIs
    	else if (apiName.equalsIgnoreCase("GetCustomer") 
    			|| apiName.equalsIgnoreCase("GetCustomerReference")
    			|| apiName.equalsIgnoreCase("GetDevices_CSR")
    			|| apiName.equalsIgnoreCase("GetProductDetailByProductID")
    			|| apiName.equalsIgnoreCase("GetRefundHistory_CSR")
    			|| apiName.equalsIgnoreCase("GetSubscriptionHistory_CSR")
    			|| apiName.equalsIgnoreCase("GetTransactionHistory_CSR")
    			|| apiName.equalsIgnoreCase("GetWatchHistory_CSR")
    			|| apiName.equalsIgnoreCase("ProductSearch"))
    	{
    		int a = url.indexOf("?access_token=");
    		if ( a != -1)
    		{
    			String sText = url.substring(0, a) + "?access_token=***";
    			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[url=" + sText + "]");
    		}
    	}
    	// SCM APIs
    	else if (apiName.equalsIgnoreCase("SCMCancelPurchase")
    			|| apiName.equalsIgnoreCase("SCMUsage")
    			|| apiName.equalsIgnoreCase("SCMUsageDetail")
    			|| apiName.equalsIgnoreCase("SCMViewPurchases")
    			|| apiName.equalsIgnoreCase("SCMCheckPrice")
    			|| apiName.equalsIgnoreCase("SCMGetSubscriberProfile"))
    	{
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[url=" + url + "]");
    	}
    	// RB API
    	else if (apiName.equalsIgnoreCase("GetRBAccessToken"))
    	{
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[url=" + url + "]");
    	}
    	// RB APIs
    	else if (apiName.equalsIgnoreCase("RBGetCustomerProfile")
    			|| apiName.equalsIgnoreCase("RBGetCustomerPreferences"))
    	{
    		int a = url.indexOf("?access_token=");
    		if ( a != -1)
    		{
    			String sText = url.substring(0, a) + "?access_token=***";
    			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[url=" + sText + "]");
    		}
    	}
    	// ROVI API
    	else if (apiName.equalsIgnoreCase("rovi"))
    	{
    		String sText = url;
    		int a = sText.indexOf("&sig=");
    		if (a != -1)
    		{
    			sText = sText.substring(0, a) + "&sig=***";
    		}
    		
    		a = sText.indexOf("?apikey=");
    		int b = "?apikey=".length();
    		int c = sText.indexOf("&entitytype=");
    		if ( a != -1 && c != -1 && a + b <= c)
    		{
    			sText = sText.substring(0, a + b) + "***" + sText.substring(c);
    		}
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[url=" + sText + "]");
    	}
    	// DS
    	else if (apiName.equalsIgnoreCase("DSService"))
    	{
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[url=" + url + "]");
    	}
    	
    }
    private void printRequestPostBody(String apiName, String postBody)
    {
    	String sText = postBody.replaceAll("(\\r|\\n)", "").replaceAll("\\s+", " ");
    	
    	XmlHandler xh = new XmlHandler(sUser);
    	
    	// OAuth API
    	if (apiName.equalsIgnoreCase("CreateOAuthKey"))
    	{
    		sText = xh.hideSensitiveInfo(sText, "Email");
    		sText = xh.hideSensitiveInfo(sText, "Password");
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[request=" + sText + "]");
    		return;
    	}
    	// OAuth API
    	else if (apiName.equalsIgnoreCase("GetOAuthToken"))
    	{
    		sText = xh.hideSensitiveInfo(sText, "ApiKey");
    		sText = xh.hideSensitiveInfo(sText, "Password");
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[request=" + sText + "]");
    		return;
    	}
    	// OL APIs
    	else if (apiName.equalsIgnoreCase("GetCustomer") 
    			|| apiName.equalsIgnoreCase("GetCustomerReference")
    			|| apiName.equalsIgnoreCase("GetDevices_CSR")
    			|| apiName.equalsIgnoreCase("GetProductDetailByProductID")
    			|| apiName.equalsIgnoreCase("GetRefundHistory_CSR")
    			|| apiName.equalsIgnoreCase("GetSubscriptionHistory_CSR")
    			|| apiName.equalsIgnoreCase("GetTransactionHistory_CSR")
    			|| apiName.equalsIgnoreCase("GetWatchHistory_CSR")
    			|| apiName.equalsIgnoreCase("ProductSearch"))
    	{
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[request=" + sText + "]");
    		return;
    	}
    	// SCM APIs
    	else if (apiName.equalsIgnoreCase("SCMCancelPurchase")
    			|| apiName.equalsIgnoreCase("SCMUsage")
    			|| apiName.equalsIgnoreCase("SCMUsageDetail")
    			|| apiName.equalsIgnoreCase("SCMViewPurchases")
    			|| apiName.equalsIgnoreCase("SCMCheckPrice")
    			|| apiName.equalsIgnoreCase("SCMGetSubscriberProfile"))
    	{
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[request=" + sText + "]");
    	}
    	// RB API
    	else if (apiName.equalsIgnoreCase("GetRBAccessToken"))
    	{
    		int a = sText.indexOf("&client_secret=");
    		int b = "&client_secret=".length();
    		if (a != -1)
    		{
    			sText = sText.substring(0, a + b) + "***";
    		}
    				
    		a = sText.indexOf("&client_id=");
    		b = "&client_id=".length();
    		int c = sText.indexOf("&grant_type=");
    		if (a != -1 && c != -1 && a + b <= c)
    		{
    			String sOriginalText = sText.substring(a + b, c);
    			sText = sText.replace(sOriginalText, "***");
    		}
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[request=" + sText + "]");
    	}
    }
    private void printResponse(String apiName, String response)
    {
    	String sText = response.replaceAll("(\\r|\\n)", "").replaceAll("\\s+", " ");
    	
    	XmlHandler xh = new XmlHandler(sUser);
    	
    	// OAuth API
    	if (apiName.equalsIgnoreCase("CreateOAuthKey"))
    	{
    		sText = xh.hideSensitiveInfo(sText, "ApiKey");
    		sText = xh.hideSensitiveInfo(sText, "Password");
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[response=" + sText + "]");
    		return;
    	}
    	// OAuth API
    	else if (apiName.equalsIgnoreCase("GetOAuthToken"))
    	{
    		sText = xh.hideSensitiveInfo(sText, "Token");
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[response=" + sText + "]");
    		return;
    	}
    	// OL APIs
    	else if (apiName.equalsIgnoreCase("GetCustomer") 
    			|| apiName.equalsIgnoreCase("GetCustomerReference")
    			|| apiName.equalsIgnoreCase("GetDevices_CSR")
    			|| apiName.equalsIgnoreCase("GetProductDetailByProductID")
    			|| apiName.equalsIgnoreCase("GetRefundHistory_CSR")
    			|| apiName.equalsIgnoreCase("GetSubscriptionHistory_CSR")
    			|| apiName.equalsIgnoreCase("GetTransactionHistory_CSR")
    			|| apiName.equalsIgnoreCase("GetWatchHistory_CSR"))
    	{
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[response=" + sText + "]");
    		return;
    	}
    	// OL API
    	else if (apiName.equalsIgnoreCase("ProductSearch"))
    	{
    		int iLength = sText.length();
    		if (iLength > 200)
    			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[response=" + sText.substring(0, 200) + "]");
    		else
    			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[response=" + sText + "]");
    		return;
    	}
    	// SCM APIs
    	else if (apiName.equalsIgnoreCase("SCMCancelPurchase")
    			|| apiName.equalsIgnoreCase("SCMUsage")
    			|| apiName.equalsIgnoreCase("SCMUsageDetail")
    			|| apiName.equalsIgnoreCase("SCMViewPurchases")
    			|| apiName.equalsIgnoreCase("SCMCheckPrice")
    			|| apiName.equalsIgnoreCase("SCMGetSubscriberProfile"))
    	{
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[response=" + sText + "]");
    	}
    	// RB API
    	else if (apiName.equalsIgnoreCase("GetRBAccessToken"))
    	{
    		try {
    			JSONObject json = new JSONObject(sText);
    			json.put("access_token", "***");
    			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[response=" + json.toString() + "]");
    			
    		} catch (JSONException e) {
    			logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
    			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[response=" + sText + "]");
    		}

    		
    	}
    	// RB APIs
    	else if (apiName.equalsIgnoreCase("RBGetCustomerProfile")
    			|| apiName.equalsIgnoreCase("RBGetCustomerPreferences"))
    	{
    		
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[response=" + sText + "]");
    	}
    	// ROVI API
    	else if (apiName.equalsIgnoreCase("rovi"))
    	{
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[response=" + sText + "]");
    	}
    	// DS
    	else if (apiName.equalsIgnoreCase("DSService"))
    	{
    		logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[response=" + sText + "]");
    	}
    }

    
	public String callOLServer(String apiName, String url, Map<String, String> map)
	{
		String sPostBody = genPostBody(apiName + ".xml", map);
		
		printUrl(apiName, url);
		printRequestPostBody(apiName, sPostBody);
		
		String sResponse = doHttpPost(url, sPostBody, "text/xml");
		
		printResponse(apiName, sResponse);
		
		return sResponse;
	}
	
	public String callSCMServer(AppUtils.Env env, String apiName, String url, Map<String, String> map)
	{
		String sPostBody = genPostBody(apiName + ".xml", map);
			
		printUrl(apiName, url);
		printRequestPostBody(apiName, sPostBody);
		
		String sResponse = doHttpPost_scm(env, url, sPostBody, "text/xml");
		
		printResponse(apiName, sResponse);
		
		return sResponse;
	}

	// RB, ROVI, VMS
	public String doGetCall(String apiName, String url)
	{
		printUrl(apiName, url);
		
		String sResponse = doHttpGet(url);
		
		printResponse(apiName, sResponse);
		
		return sResponse;
	}
	public String callRBServer(String apiName, String url, String postBody, String contentType, Map<String, String> map)
	{
		if (postBody == null)
			postBody = genPostBody(apiName + ".xml", map);
		
		printUrl(apiName, url);
		printRequestPostBody(apiName, postBody);	
		
		String sResponse = doHttpPost(url, postBody, contentType);
		
		printResponse(apiName, sResponse);
		
		return sResponse;
	}

	private String doHttpPost_scm(AppUtils.Env env, String urlStr, String postBody, String contentType)
	{
		String sResponse = "";
		DefaultHttpClient httpclient = null;
		BufferedReader br = null;
		FileInputStream fis = null;
        try 
        {
        	String sKeystore = AppUtils.getScmKeystore(env);
        	String sType = AppUtils.getScmKeystoreType(env);
        	String sPwd = AppUtils.getScmKeystorePwd(env);
        	
            KeyStore keyStore  = KeyStore.getInstance(sType);
            fis = new FileInputStream(new File(sKeystore));

            keyStore.load(fis, sPwd.toCharArray());         

            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
            SSLSocketFactory sslSocketFactory = new SSLSocketFactory(keyStore, sPwd, null);
            schemeRegistry.register(new Scheme("https", 443, sslSocketFactory));
            
            HttpParams params = new BasicHttpParams();
            httpclient = new DefaultHttpClient(new BasicClientConnectionManager(schemeRegistry), params);
            
	        if (AppUtils.getProxySwitch().equalsIgnoreCase("on"))
			{
	        	HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
	        	httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			}

	        HttpPost post = new HttpPost(urlStr);
	        
	        StringEntity se = new StringEntity(postBody, "UTF-8");
	        se.setContentType(contentType);
	        post.setHeader("ContentType", contentType);
	        post.setEntity(se); 
	        
	        HttpResponse response = httpclient.execute(post);
	        
	        int iCode = response.getStatusLine().getStatusCode();
	        
	        if (iCode == 200)
	        {
		        HttpEntity entity = response.getEntity();
				
	            if (entity != null) {
	                br = new BufferedReader(new InputStreamReader(entity.getContent()));
		            
		            String readLine;
			        while(((readLine = br.readLine()) != null)) {
			        	sResponse += readLine;
			        }
	            }
	            EntityUtils.consume(entity);
	        }
	        else
	        {
	        	logger.error("[user=" + sUser + "]\t[api=doHttpPost_scm]\t[message=Method failed:" + response.getStatusLine() + "]");
	        	StringBuilder sBuilder = new StringBuilder();
				sBuilder.append("<httperror>");
				sBuilder.append("<code>" + response.getStatusLine().getStatusCode() + "</code>");
				sBuilder.append("<message>" + response.getStatusLine() + "</message>");
				sBuilder.append("</httperror>");
				sResponse = sBuilder.toString();
	        }
        }
        catch(FileNotFoundException e)
        {
        	logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
        	String sMessage = e.getMessage();
        	sResponse = "<httperror><code></code><message>" + sMessage + "</message></httperror>";
        }
        catch(KeyStoreException e)
        {
        	logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
        	String sMessage = e.getMessage();
        	sResponse = "<httperror><code></code><message>" + sMessage + "</message></httperror>";
        }
        catch(NoSuchAlgorithmException e)
        {
        	logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
        	String sMessage = e.getMessage();
        	sResponse = "<httperror><code></code><message>" + sMessage + "</message></httperror>";
        }
        catch(CertificateException e)
        {
        	logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
        	String sMessage = e.getMessage();
        	sResponse = "<httperror><code></code><message>" + sMessage + "</message></httperror>";
        }
        catch(KeyManagementException e)
        {
        	logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
        	String sMessage = e.getMessage();
        	sResponse = "<httperror><code></code><message>" + sMessage + "</message></httperror>";
        }
        catch(UnrecoverableKeyException e)
        {
        	logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
        	String sMessage = e.getMessage();
        	sResponse = "<httperror><code></code><message>" + sMessage + "</message></httperror>";
        }
        catch(IOException e)
        {
        	logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
        	String sMessage = e.getMessage();
        	sResponse = "<httperror><code></code><message>" + sMessage + "</message></httperror>";
        }
        finally {
        	if (br != null)
        	{
        		ResourceReleasor rr = new ResourceReleasor(sUser);
        		rr.safeCloseReader(br);
        	}
        	if (fis != null)
        	{
        		ResourceReleasor rr = new ResourceReleasor(sUser);
        		rr.safeCloseInputStream(fis);
        	}
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
        	if (httpclient != null)
        		httpclient.getConnectionManager().shutdown();
        }

		return sResponse;		
	}
	
	
	private String doHttpGet(String url)
	{
		HttpClient client = new HttpClient();
		BufferedReader br = null;		
		GetMethod method = new GetMethod(url);
		StringBuilder sBuilder = new StringBuilder();
		
		if (AppUtils.getProxySwitch().equalsIgnoreCase("on"))
		{
			HostConfiguration config = client.getHostConfiguration();
	        config.setProxy(PROXY_HOST, PROXY_PORT);
	        Credentials credentials = new UsernamePasswordCredentials("", "");
	        AuthScope authScope = new AuthScope(PROXY_HOST, PROXY_PORT);
	        client.getState().setProxyCredentials(authScope, credentials);
		}
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) 
			{
				logger.error("[user=" + sUser + "]\t[api=doHttpGet]\t[message=Method failed:" + method.getStatusLine() + "]");
				sBuilder.append("<httperror>");
				sBuilder.append("<code>" + method.getStatusCode() + "</code>");
				sBuilder.append("<message>" + method.getStatusLine() + "</message>");
				sBuilder.append("</httperror>");
			}
			else
			{
				br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
				String readLine;
				while(((readLine = br.readLine()) != null)) 
				{
					sBuilder.append(readLine);
				}
			}
		} 
		catch (HttpException ex) {
			logger.error("[user=" + sUser + "]\t[api=doHttpGet]\t[message=" + ex.getMessage() + "]");
		}
		catch (IOException ex) {
			sBuilder.setLength(0);
			logger.error("[user=" + sUser + "]\t[api=doHttpGet]\t[message=" + ex.getMessage() + "]");
		}
		finally
		{
			
			if (br != null)
			{
				ResourceReleasor rr = new ResourceReleasor(sUser);
				rr.safeCloseReader(br);
			}
        		
			method.releaseConnection();
		}
		return sBuilder.toString();
	}

	
	private String doHttpPost(String url, String postBody, String contentType) {
		String response = "";
		int statusCode = -1;
		BufferedReader br = null;
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Test Client");

		PostMethod method = new PostMethod(url);
		
		if (AppUtils.getProxySwitch().equalsIgnoreCase("on"))
		{
			HostConfiguration config = client.getHostConfiguration();
	        config.setProxy(PROXY_HOST, PROXY_PORT);
	        Credentials credentials = new UsernamePasswordCredentials("", "");
	        AuthScope authScope = new AuthScope(PROXY_HOST, PROXY_PORT);
	        client.getState().setProxyCredentials(authScope, credentials);
		}
		try {

			method.setRequestEntity(new StringRequestEntity(postBody, contentType, null));
		      
			statusCode = client.executeMethod(method);

			br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
	        String readLine;
	        while(((readLine = br.readLine()) != null)) {
	        	response += readLine;
	        }
	        
	        if (statusCode != HttpStatus.SC_OK) 
			{
				logger.error("[user=" + sUser + "]\t[api=doHttpPost]\t[message=Method failed:" + method.getStatusLine() + "]");
				if (response.indexOf("<ZoeResponse>") != -1)
				{
					return response;
				}
				XmlHandler xh = new XmlHandler(sUser);
				String sMessage = xh.extractInnerText(response, "title");
				response = "<httperror><code>" + statusCode + "</code><message>" + sMessage + "</message></httperror>";
			}

		} 
		catch (IOException ex) 
		{
			logger.error("[user=" + sUser + "]\t[api=doHttpPost]\t[message=" + ex.getMessage() + "]");
			if (statusCode != -1)
				response = "<httperror><code>" + statusCode + "</code><message>" + ex.getMessage() + "</message></httperror>";
			else
				response = "<httperror><code></code><message>" + ex.getMessage() + "</message></httperror>";
		} 
		finally {
			
			if (br != null)
			{
				ResourceReleasor rr = new ResourceReleasor(sUser);
        		rr.safeCloseReader(br);
        	}
			method.releaseConnection();
		}
		return response;
	}
}
