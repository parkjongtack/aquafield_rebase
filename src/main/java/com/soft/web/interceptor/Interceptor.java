package com.soft.web.interceptor;

import com.soft.web.util.Util;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter
{
  private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);

  @Value("#{config['admin.access.ip']}")
  String accessIp;

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { String reqUrl = request.getRequestURI().toString();
    String returnurl = Util.encodeString(request.getRequestURI().toString() + (request.getQueryString() == null ? "" : new StringBuilder("?").append(request.getQueryString()).toString()));

    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");
    
    HttpSession session = request.getSession();
    String remoteIp = request.getRemoteAddr();

    if (reqUrl.startsWith("/secu_admaf/admin")) {
      if ((returnurl.toLowerCase().indexOf("login.af") != -1) || (returnurl.toLowerCase().indexOf("action.af") != -1)) returnurl = "/secu_admaf/admin/index.af";
      Map admin = (Map)session.getAttribute("_sessionAdmin");
      if ((admin == null) || (admin.get("SESSION_ADMIN_ID") == null) || ("".equals(admin.get("SESSION_ADMIN_ID").toString()))) {
        logger.info("URL=>" + reqUrl);
        logger.info(">> interceptor catch!!! admin is null.. ");
        session.invalidate();
        Util.htmlPrint("<script>top.location.href='/secu_admaf/admin/login.af?returnurl=" + returnurl + "';</script>", response);
        return false;
      }

      if ((StringUtils.isNotBlank(this.accessIp)) && (this.accessIp.indexOf("," + remoteIp + ",") == -1)) {
        logger.debug("관리자 접속 불가 IP : " + remoteIp);
        Util.htmlPrint("접속이 허용되지 않았습니다.", response);
        return false;
      }
    }
    else if (reqUrl.startsWith("/secu_admaf/admdesk")) {
      if ((returnurl.toLowerCase().indexOf("login.af") != -1) || (returnurl.toLowerCase().indexOf("action.af") != -1)) returnurl = "/secu_admaf/admdesk/index.af";
      Map admin = (Map)session.getAttribute("_sessionDeskAdmin");
      if ((admin == null) || (admin.get("SESSION_ADMIN_ID") == null) || ("".equals(admin.get("SESSION_ADMIN_ID").toString()))) {
        logger.info("URL=>" + reqUrl);
        logger.info(">> interceptor catch!!! desk admin is null.. ");
        session.invalidate();
        Util.htmlPrint("<script>top.location.href='/secu_admaf/admdesk/login.af?returnurl=" + returnurl + "';</script>", response);
        return false;
      }

      if ((StringUtils.isNotBlank(this.accessIp)) && (this.accessIp.indexOf("," + remoteIp + ",") == -1)) {
        logger.debug("데스크관리자 접속 불가 IP : " + remoteIp);
        Util.htmlPrint("접속이 허용되지 않았습니다.", response);
        return false;
      }
    }
    else if ((reqUrl.contains("/mypage")) || (reqUrl.contains("/reserve"))) {
      Map memberInfo = (Map)session.getAttribute("MEM_INFO");
      
		if(memberInfo ==  null){			
			memberInfo = (Map) session.getAttribute("MEM_INFO2");
		}      
      
      Map admin = (Map)session.getAttribute("_sessionAdmin");
      Map admdesk = (Map)session.getAttribute("_sessionDeskAdmin");

      if ((admin != null) && (!"".equals(admin))) memberInfo = admin;
      if ((admdesk != null) && (!"".equals(admdesk))) memberInfo = admdesk;
      if ((memberInfo == null) || ("".equals(memberInfo))) {
        logger.info("URL=>" + reqUrl);
        logger.info(">> interceptor catch!!! memberinfo is null.. ");

        //session.setAttribute("NO_LOGIN", "Y");
      }
    }
    return true;
  }
}