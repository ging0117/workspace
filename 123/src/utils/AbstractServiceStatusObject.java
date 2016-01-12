package com.verizon.zoetool.utils;

import java.util.ArrayList;
import java.util.List;

import com.verizon.zoetool.domain.SystemStatus;

public abstract class AbstractServiceStatusObject {
	public List<SystemStatus> list;
	public AbstractServiceStatusObject()
	{
		list = new ArrayList<SystemStatus>();
	}
	public abstract void initStatusList(int size);
	public void clearStatusList()
	{
		list.clear();
	}
	public String printStatusList(String serviceName)
	{
		StringBuilder sb = new StringBuilder();
		if (list.size() > 0)
		{
			sb.append("<tr><th rowspan=\"" + list.size() + "\">" + serviceName + "</th>");
			for(int i = 0; i < list.size(); i++)
			{
				SystemStatus ss = list.get(i);
				if (i != 0)
					sb.append("<tr>");
				sb.append(ss.toString());
				sb.append("</tr>");
			}
		}
		clearStatusList();
		return sb.toString();
	}
	public void updateStatusNode(int i, boolean success, String code, String message)
	{
		SystemStatus ss = list.get(i);
		if (success)
			ss.setSuccess(1);
		else
			ss.setSuccess(-1);
		ss.setCode(code);
		ss.setMessage(message);
		list.set(i, ss);
	}
}
