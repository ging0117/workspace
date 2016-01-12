package com.verizon.zoetool.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import org.apache.log4j.Logger;

public class SoapService {
	private static final Logger logger = Logger.getLogger(SoapService.class);
	private String sUser;
	
	public SoapService(String user)
	{
		sUser = user;
	}
	
	public String callVMSServer(String apiName, String url, Map<String, String> map)
	{
		XmlHandler xh = new XmlHandler(sUser);
		String sPostBody = xh.genPostBody(apiName + ".xml", map);
		
		printUrl(apiName, url);	
		printRequestPostBody(apiName, sPostBody);
		
		String[] strResp = getResponseMsg(url, sPostBody);
		
		String sCode = strResp[0];
		String sMessage = strResp[1].replaceAll("(\\r|\\n)", "");
		if (sCode.equalsIgnoreCase("200"))
		{
			printResponse(apiName, sMessage, "200");
		}
		else
		{
			String sDesc = xh.extractInnerText(sMessage, "TITLE");
			if (sDesc.length() > 0)
			{
				sMessage = sDesc;
			}
			else
			{
				sDesc = xh.extractInnerText(sMessage, "title");
				if (sDesc.length() > 0)
					sMessage = sDesc;
			}
			printResponse(apiName, sMessage, sCode);
			StringBuilder sb = new StringBuilder();
			sb.append("<httperror>");
			sb.append("<code>" + sCode + "</code>");
			sb.append("<message>" + sMessage + "</message>");
			sb.append("</httperror>");
			return sb.toString();
		}
		return strResp[1];
	}
	
	
	public String callETServer(String apiName, String url, Map<String, String> map)
	{
		XmlHandler xh = new XmlHandler(sUser);
		String sPostBody = xh.genPostBody(apiName + ".xml", map);
		
		printUrl(apiName, url);	
		printRequestPostBody(apiName, sPostBody);
		
		String[] strResp = getResponseMsg(url, sPostBody);
		
		String sCode = strResp[0];
		String sMessage = strResp[1].replaceAll("(\\r|\\n)", "");
		if (sCode.equalsIgnoreCase("200"))
		{
			printResponse(apiName, sMessage, "200");
			return sMessage;
		}
		else if (sMessage.indexOf("<soap:Envelope") != -1)
		{
			int a = sMessage.indexOf("<soap:Reason>");
			int b = sMessage.indexOf("</soap:Reason>");
			int c = "<soap:Reason>".length();
			if (a != -1 && b != -1 && a + c < b)
			{
				sMessage = sMessage.substring(a + c, b);
				sMessage = sMessage.replace("<", "&lt;").replace(">", "&gt;");
			}
			printResponse(apiName, sMessage, sCode);
			StringBuilder sb = new StringBuilder();
			sb.append("<httperror>");
			sb.append("<code>" + sCode + "</code>");
			sb.append("<message>" + sMessage + "</message>");
			sb.append("</httperror>");
			return sb.toString();
		}
		else
		{
			int a = sMessage.indexOf("<title>");
			int b = sMessage.indexOf("</title>");
			int c = "<title>".length();
			if (a != -1 && b != -1 && a + c < b)
			{
				sMessage = sMessage.substring(a + c, b);
			}
			printResponse(apiName, sMessage, sCode);
			StringBuilder sb = new StringBuilder();
			sb.append("<httperror>");
			sb.append("<code>" + sCode + "</code>");
			sb.append("<message>" + sMessage + "</message>");
			sb.append("</httperror>");
			return sb.toString();
		}
	}

	private String[] getResponseMsg(String URLStr, String requestBody)
	{
		String[] arrayCodeMessage = new String[2];
		if (requestBody == null || requestBody.isEmpty())
		{
			arrayCodeMessage[0] = "";
			arrayCodeMessage[1] = "";
			return arrayCodeMessage;
		}
		int iLength = requestBody.length();
		String sLength = String.valueOf(iLength);
		
		HttpURLConnection connection = null;
		DataOutputStream dos = null;
		BufferedReader reader = null;
		
		StringBuffer strResult = new StringBuffer();
		
		String encodedString = "d3N1c2VyOndzdXNlcg==";

		try {
			URL url = new URL(URLStr);
			
			if (AppUtils.getProxySwitch().equalsIgnoreCase("on"))
			{
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(AppUtils.getProxyUrl(), AppUtils.getProxyPort()));
				connection = (HttpURLConnection) url.openConnection(proxy);
			}
			else
			{
				connection = (HttpURLConnection) url.openConnection();
			}
			
			connection.setDoOutput(true);
			connection.setUseCaches(false);
	
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
			
			connection.setRequestProperty("Content-Length", sLength);
			connection.setRequestProperty ("Authorization", "Basic " + encodedString);
			connection.setRequestProperty("Connection", "Close");
		    connection.setAllowUserInteraction(true);
			connection.setRequestProperty ("Accept-Encoding", "gzip,deflate");
			connection.setConnectTimeout(5000); //set timeout to 5 seconds
			connection.setAllowUserInteraction(true);
	
			
			dos = new DataOutputStream(connection.getOutputStream ());
			dos.writeBytes(requestBody);
			dos.flush();
			
			connection.connect();
	
			int repCode = connection.getResponseCode();
			arrayCodeMessage[0] = Integer.toString(repCode);
		
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	
			String inputLine = "";
			//Only single line of inputLine. Use loop to process it,  just in the case there are multiply lines of input
			while ((inputLine = reader.readLine()) != null) 
			{
				strResult.append(inputLine);
			}
				
			arrayCodeMessage[1] = strResult.toString();
			
		
		} catch (MalformedURLException e){
			logger.info("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
			arrayCodeMessage[1] = e.getMessage();	
		} catch (IOException e) {
			logger.info("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
			arrayCodeMessage[0] = "";
			arrayCodeMessage[1] = e.getMessage();
		} finally{
			
			ResourceReleasor rr = new ResourceReleasor(sUser);
			if (reader != null)
				rr.safeCloseReader(reader);
			if (dos != null)
				rr.safeCloseOutputStream(dos);
			if (connection != null)
				connection.disconnect();
		}
		return arrayCodeMessage;

	}

	
	
	private void printUrl(String apiName, String url)
	{
		// ET API
		if (apiName.equalsIgnoreCase("ETGetSystemStatus"))
		{
			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[url=" + url + "]");
		}
		// VMS API
		else if (apiName.equalsIgnoreCase("VMSFindAccountByEmail"))
		{
			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[url=" + url + "]");
		}
	}

	private void printRequestPostBody(String apiName, String postBody)
	{
		String sText = postBody.replaceAll("(\\r|\\n)", "").replaceAll("\\s+", " ");
		
		// ET API
		if (apiName.equalsIgnoreCase("ETGetSystemStatus"))
		{
			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[request=" + sText + "]");
			XmlHandler xh = new XmlHandler(sUser);
			sText = xh.hideSensitiveInfo(sText, "wsse:Username");
			
			int a = sText.indexOf("<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">");
			int b = "<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">".length();
			int c = sText.indexOf("</wsse:Password>");
			if (a != -1 && c != -1 && a + b <= c)
			{
				sText = sText.substring(0, a + b) + "***" + sText.substring(c);
			}
			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[request=" + sText + "]");	
		}
		// VMS API
		else if (apiName.equalsIgnoreCase("VMSFindAccountByEmail"))
		{
			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[request=" + sText + "]");
		}
	}
	private void printResponse(String apiName, String response, String code)
	{
		String sText = response.replaceAll("(\\r|\\n)", "").replaceAll("\\s+", " ");
		
		// ET API
		if (apiName.equalsIgnoreCase("ETGetSystemStatus"))
		{
			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[responsecode=" + code + "]\t[response=" + sText + "]");
		}
		// VMS API
		else if (apiName.equalsIgnoreCase("VMSFindAccountByEmail"))
		{
			logger.info("[user=" + sUser + "]\t[api=" + apiName + "]\t[responsecode=" + code + "]\t[response=" + sText + "]");
		}
	}

}
