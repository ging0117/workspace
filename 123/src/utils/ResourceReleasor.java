package com.verizon.zoetool.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.apache.log4j.Logger;

public class ResourceReleasor {
	private static final Logger logger =Logger.getLogger(ResourceReleasor.class);
	String sUser;
	
	public ResourceReleasor(String user)
	{
		sUser = user;
	}
	public void safeCloseOutputStream(OutputStream os)
	{
		if (os != null)
			try {
				os.close();
			} catch(IOException e) {
				logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
			}
	}
	public void safeCloseInputStream(InputStream is)
	{
		if (is != null)
			try {
				is.close();
			} catch(IOException e) {
				logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
			}
	}
	public void safeCloseReader(Reader br)
	{
		if (br != null)
			try {
				br.close();
			} catch (IOException e) {
				logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
			}
	}
	public void safeCloseWriter(Writer writer)
	{
		if (writer != null)
			try {
				writer.close();
			} catch(IOException e) {
				logger.error("[user=" + sUser + "]\t[message=" + e.getMessage() + "]");
			}
	}
}
