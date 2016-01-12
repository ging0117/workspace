package com.verizon.zoetool.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.*;
import org.junit.Test;

public class XmlHandler {

	private static final Logger logger = Logger.getLogger(XmlHandler.class);
	private String sUser;
	
	public XmlHandler(String user)
	{
		sUser = user;
	}
		
	public String parseXmlResponseToHtml(String xml, String sApiName)
	{
		return parseXmlResponseToHtml(xml, sApiName, null);
	}
	
	public String parseXmlResponseToHtml(String xml, String sApiName, Map<String, String> mapParam)
	{
		String sHtml = "";
		
		if (xml.length() == 0)
			return sHtml;


		StringWriter writer = null;
		
		try {
			ClassLoader cl = this.getClass().getClassLoader();
			if (cl == null)
				return sHtml;
			URL url = cl.getResource(AppUtils.getXsltDir() + sApiName + ".xsl");
			
			logger.info(AppUtils.getXsltDir() + sApiName + ".xsl");
			
			logger.info(url);
			
			Source source = new StreamSource(new StringReader(xml));
			Source xsl = new StreamSource(new File(url.toURI()));
			
			writer = new StringWriter();		    
			Result result = new StreamResult(writer);		    
			TransformerFactory factory = TransformerFactory.newInstance();		    
			Transformer transformer = factory.newTransformer(xsl);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			if (mapParam != null)
			{
				for (Map.Entry<String, String> entry : mapParam.entrySet()) 
				{
					transformer.setParameter(entry.getKey(), entry.getValue());
				}
			}			
			
			transformer.transform(source, result);
			
			sHtml= writer.toString();
			logger.info("[user=" + sUser + "]\t[api=" + sApiName + "]\t[html=" + writer.toString().replaceAll("(\\r|\\n)", "").replaceAll("\\s+", " ") + "]");
		} 
		catch (TransformerException e) {
			logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
		} 
		catch (URISyntaxException e) {
			logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
		}
		finally {			
			if (writer != null)
			{
				ResourceReleasor rr = new ResourceReleasor(sUser);
				rr.safeCloseWriter(writer);		
			}						
		}
		return sHtml;
	}
	
	
	public String readFile(String filename)
	{
		StringBuilder sBuilder = new StringBuilder();
		
		BufferedReader br = null;
		
		try {
			
			// From ClassLoader, all paths are "absolute" already - there's no context
			// from which they could be relative. Therefore you don't need a leading slash.
			ClassLoader cl = this.getClass().getClassLoader();
			if (cl == null)
				return "";
			URL url = cl.getResource(filename);			

			br = new BufferedReader(new FileReader(new File(url.toURI())));
			
		    char[] buf = new char[10240];
		    int numRead = 0;
		    while ((numRead = br.read(buf)) != -1) {
		        sBuilder.append(buf, 0, numRead);
		    }
		}
		catch(URISyntaxException ex)
		{
			logger.error("[user=" + sUser + "]\t[api=readFile]\t[message=" + ex.getMessage() + "]");
		}
		catch(IOException ex)
		{
			logger.error("[user=" + sUser + "]\t[api=readFile]\t[message=" + ex.getMessage() + "]");
		}
		finally 
		{
			if (br != null)
			{
				ResourceReleasor rr = new ResourceReleasor(sUser);
				rr.safeCloseReader(br);
			}						
		}
		return sBuilder.toString();
	}
	
	public String genPostBody(String filename, Map<String, String> map)
    {
    	String sPostBody = readFile(AppUtils.getXmlDir() + filename);

		for(String key: map.keySet())
		{
			String value = map.get(key);
			sPostBody = sPostBody.replace(key, value);
		}
		return sPostBody;
    }
	public String extractInnerText(String xml, String tagname)
	{
		if (xml.length() == 0)
			return "";
		
		String sValue = "";
		int a = xml.indexOf("<" + tagname + ">");
		int b = xml.indexOf("</" + tagname + ">");
		int c = tagname.length() + 2;
		if (a != -1 && b != -1 && a + c < b)
			sValue = xml.substring(a + c, b);
		return sValue;
	}
	public String hideSensitiveInfo(String xml, String tagname)
	{
		if (xml.length() == 0)
			return "";
		String sOutput = xml;
		int a = xml.indexOf("<" + tagname + ">");
		int b = xml.indexOf("</" + tagname + ">");
		int c = tagname.length() + 2;
		if (a != -1 && b != -1 && a + c < b)
		{
			sOutput = xml.substring(0, a + c) + "***" + xml.substring(b);
		}
		return sOutput;
	}
	@Test
	public void test()
	{
	}

}
