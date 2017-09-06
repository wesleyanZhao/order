package com.lanou.cn.shiro;

import com.lanou.cn.service.BaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 28/6/17.
 */
@Component("myRealm")
public class MyRealm extends AuthorizingRealm {

	public static final Logger logger = LoggerFactory.getLogger(MyRealm.class);

	public static final String SESSION_USER_KEY = "shiro_";

	@Resource
	private BaseService baseService;

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SimpleAuthorizationInfo info = null;
		Map<String,Object> user = (Map<String,Object>) SecurityUtils.getSubject().getSession().getAttribute(MyRealm.SESSION_USER_KEY);
		if(!CollectionUtils.isEmpty(user)) {
			info= new SimpleAuthorizationInfo();
			info.addRole("admin");
			Set<String> list = (Set<String>)user.get("authList");
			info.addStringPermissions(list);
		}
		return info;
	}

	/**
	 * 认证回调函数，登录信息和用户验证信息验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		// 验证用户是否可以登录
		Map<String,Object> user = baseService.findUserAuthInfoByName(tokenToUser((UsernamePasswordToken) authcToken));
		if(CollectionUtils.isEmpty(user)) {
			throw new UnknownAccountException();
		}
		Set<String> authList = baseService.findUserAuthList((int)user.get("id"));
		user.put("authList",authList);
		// 设置session
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute(MyRealm.SESSION_USER_KEY, user);
		//当前 Realm 的 name
		String realmName = this.getName();
		//登陆的主要信息: 可以是一个实体类的对象, 但该实体类的对象一定是根据 token 的 username 查询得到的.
		Object principal = authcToken.getPrincipal();
		return new SimpleAuthenticationInfo(principal, user.get("password"), realmName);
	}

	/**
	 * 转换 AuthenticationToken
	 * @param authcToken
	 * @return
	 */
	private String tokenToUser(UsernamePasswordToken authcToken) {
		return authcToken.getUsername();
	}
}