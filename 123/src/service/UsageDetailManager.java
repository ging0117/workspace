package com.verizon.zoetool.service;

import com.verizon.zoetool.scm.SCMUsageDetail;
import com.verizon.zoetool.utils.AppUtils;

public class UsageDetailManager {
	private String sHtmlSCMUsageDetail;
	private String sUser;
	
	public UsageDetailManager(String user)
	{
		sUser = user;
	}
	public void getUsageDetail(AppUtils.Env env, String userid, String eventseq)
	{
		SCMUsageDetail scm = new SCMUsageDetail(sUser, env);
		sHtmlSCMUsageDetail = scm.getUsageDetail(userid, eventseq);
	}
	public String getHtml_UsageDetail()
	{
		return sHtmlSCMUsageDetail;
	}
}
