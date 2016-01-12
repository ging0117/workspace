package com.verizon.zoetool.service;

import com.verizon.zoetool.utils.AppUtils;
import com.verizon.zoetool.utils.HttpService;

public class DeviceManager {
	private String sUser;
	
	public DeviceManager(String user)
	{
		sUser = user;
	}
	public String removeDevice(String env, String deviceid)
	{
		String sURL = AppUtils.getRemoveDeviceUrl() + "env=" + env + "&uuid=" + deviceid;//externalid
		HttpService http = new HttpService(sUser);
		String sResponse = http.doGetCall("RemoveDevice", sURL);
		return sResponse;
	}
}
