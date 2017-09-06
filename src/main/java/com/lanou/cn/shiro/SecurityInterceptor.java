package com.lanou.cn.shiro;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by admin on 28/6/17.
 */
public class SecurityInterceptor implements HandlerInterceptor {

	/**
	 * 登录url
	 */
	private static final String LOGIN_URL = "/base/login.do";

	/**
	 * admin_session
	 */
	private static final String LOGIN_INFO = "loginInfo";

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		HttpSession session = httpServletRequest.getSession(true);
		Object sessionObj = httpServletRequest.getSession().getAttribute(LOGIN_INFO);
		if(null != sessionObj) {
			return true;
		}
		httpServletResponse.sendRedirect(LOGIN_URL);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}
}