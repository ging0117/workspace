
package com.verizon.zoetool.service;
import com.verizon.zoetool.scm.SCMManagerReqUsers;
import com.verizon.zoetool.utils.AppUtils;


public class SCMManagerReqUsersManager {
    public  String sHtmlSCMManagerReqUsers = "";
    private AppUtils.Env sEnv;
    private String sUser;
    private String pcn;
    
    public SCMManagerReqUsersManager(String userid, String user, AppUtils.Env env)
    {
                    sEnv = env;
                    pcn = userid;
                    sUser = user;
  
    }
    
    public void getSCMManagerReqUsers(String userid, String user, AppUtils.Env env)
    {
                    
    if (pcn != null && pcn.length() > 0)
    
    {                                              
                    SCMManagerReqUsers scm = new SCMManagerReqUsers(pcn, user, env);
                    sHtmlSCMManagerReqUsers = scm.getSCMManagerReqUsers(pcn);
                    
    }
    
    else 
                    sHtmlSCMManagerReqUsers = "<table id=\"tbSCMManagerReqUsers\" class=\"customers\" style=\"display:block;\">"
                                                    + "<tr class=\"alt\"><th colspan=\"2\">SCM Create Customer</th></tr>"
                                                    + "<tr><td>PCN</td><td></td></tr>"
                                                    + "<tr><td>Error Code</td><td></td></tr><tr><td>Error Message</td><td>No PCN Numbers Found</td></tr></table>";;
    }
    
    public String getsHtmlSCMManagerReqUsers()
    {
                    
                    System.out.println("sHtmlSCMManagerReqUsers");
                    
                    return sHtmlSCMManagerReqUsers;
    }
}
