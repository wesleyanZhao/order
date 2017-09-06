package com.lanou.cn.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;


/**
 * shiro权限验证过滤器
 * @author
 *
 */
@Component("authorizationFilter")
public class AuthorizationFilter extends PermissionsAuthorizationFilter {

	public static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		logRequest(req);
		
		Subject subject = getSubject(request, response);
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		
		int i = uri.indexOf(contextPath);
		if (i > -1) {
			uri = uri.substring(i + contextPath.length());
		}
		
		if (uri.indexOf(";") != -1) {
			uri = uri.substring(0, uri.indexOf(";JSESSIONID"));
		}
		
		if (StringUtils.isBlank(uri)) {
			uri = "/";
		}
		if ("/".equals(uri)) {
			return true;
		}
		return subject.isPermitted(uri);
	}
	
	private void logRequest(HttpServletRequest request) {
    	StringBuilder sb = new StringBuilder("\n");
	    if (logger.isDebugEnabled()) {
	    	sb.append("//=========================================================================\n");
	    	sb.append("URI : " + request.getRequestURI()).append("\n");
	    	sb.append("URL : " + request.getRequestURL()).append("\n");
	    	sb.append("IP : " + request.getRemoteAddr()).append("\n");
	    	sb.append("Referer URI : " + request.getHeader("referer")).append("\n");  
	    	sb.append("Method : " + request.getMethod()).append("\n");
	    	//sb.append("User Agent : " + request.getHeader("User-Agent")).append("\n");
	    	sb.append("Session : " + request.getSession().getId()).append("\n");
	    	sb.append("Locale : " + request.getLocale().getCountry()).append("\n");
	    	sb.append("Parameters : \n");
	    	
            Enumeration<?> e = request.getParameterNames();
            
            if(e.hasMoreElements()){
                String pName = "";
                String pValue = "";
                do {
                    pName = (String)e.nextElement();
                    pValue = request.getParameter(pName);                    
                    sb.append(pName + " : [" + pValue + "]\n");
                } while(e.hasMoreElements());
            }else{
            	sb.append(" is Empty \n");
            	
            }
            sb.append("=========================================================================//\n");
            logger.debug(sb.toString());
        }
	}

}
