package com.lanou.cn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanou.cn.mapper.BaseMapper;
import com.lanou.cn.service.BaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by admin on 28/6/17.
 */
@Service
public class BaseServiceImpl implements BaseService {

	public static final String HOST_URL = "http://192.168.2.1:8280";
	//public static final String HOST_URL = "http://192.168.2.25:8888";

	//@Component  //@Resource
	@Autowired  // 类区分
	private BaseMapper baseMapper;

	@Override
	public Map<String, String> loginValidate(String username, String password) {
		Map<String,String> result = new HashMap<>();
		String massage = "";

		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();

		try {
			subject.login(token);
			if (subject.isAuthenticated()) {
				result.put("result","success");
				result.put("loginUser",username);
				return result;
			} else {
				result.put("result","invalid");
				return result;
			}
		} catch (IncorrectCredentialsException e) {
			massage = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (ExcessiveAttemptsException e) {
			massage = "登录失败次数过多";
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (LockedAccountException e) {
			massage = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (DisabledAccountException e) {
			massage = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (ExpiredCredentialsException e) {
			massage = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (UnknownAccountException e) {
			massage = "帐号不存在. There is no user with username of " + token.getPrincipal();
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (UnauthorizedException e) {
			massage = "您没有得到相应的授权！" + e.getMessage();
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		}
	}


	@Override
	public Set<String> findUserAuthList(int id) {
		return baseMapper.findUserAuthList(id);
	}
	@Override
	public Map<String, Object> findUserAuthInfoByName(String username) {
		return baseMapper.findUserByUsername(username);
	}


	/**
	 * 登录
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> login(Map<String, Object> params) {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("username",(String) params.get("username"));
		bodyMap.add("password",(String) params.get("password"));
		Map<String,Object> map = restTemplate.postForObject(HOST_URL + "/rest/login.do",bodyMap, Map.class);

		return map;
	}

	/**
	 * 注册
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> register(Map<String, Object> params){
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<String, Object>();
		bodyMap.setAll(params);
		Map<String,Object> map = restTemplate.postForObject(HOST_URL + "/rest/register.do",bodyMap, Map.class);
		System.out.println(map);
		return map;
	}

	/**
	 * 菜单展示
	 * @param loginUser
	 * @return
	 */
	@Override
	public Map<String,Object> home(String loginUser){
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<String, Object>();
		Map<String,Object> map = restTemplate.postForObject(HOST_URL + "/rest/home.do",bodyMap, Map.class);
		System.out.println(map);
		return map;
	}
	/**
	 * 修改密码
	 *  @param params
	 * @return
	 */
	@Override
	public Map<String,Object> updatePwd(Map<String,Object> params){
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<String, Object>();
		bodyMap.setAll(params);
		Map<String,Object> map = restTemplate.postForObject(HOST_URL + "/rest/updatePwd.do",bodyMap, Map.class);
		System.out.println(map);
		return map;
	}

	/**
	 *修改时查询个人信息并回显
	 * @param params
	 * @return
	 */
	@Override
	public Map<String,Object> findUserInfo(Map<String,Object> params){
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<String, Object>();
		bodyMap.setAll(params);
		Map<String,Object> map = restTemplate.postForObject(HOST_URL + "/rest/findUserInfo.do",bodyMap, Map.class);
		System.out.println(map);
		return map;
	}

	/**
	 * 修改用户信息
	 * @param params
	 * @return
	 */
	@Override
	public Map<String,Object> userInfoForm(Map<String,Object> params){
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<String, Object>();
		bodyMap.setAll(params);
		Map<String,Object> map = restTemplate.postForObject(HOST_URL + "/rest/userInfoForm.do",bodyMap, Map.class);
		System.out.println(map);
		return map;
	}
	@Override
	public List<LinkedHashMap<String,Object>> findAllMenuList() {
		return baseMapper.findAllMenuList();
	}

	@Override
	public PageInfo<Map<String, Object>> findAllUsersPageList(Map<String,Object> params) {
		Integer currentPage = params.get("currentPage") == null ? 1:Integer.parseInt((String)params.get("currentPage"));
		//Integer size = params.get("size") == null ? 5:Integer.parseInt((String)params.get("size"));

		PageHelper.startPage(currentPage, 5);
		List<Map<String,Object>> list = baseMapper.findUsers(null);
		//用PageInfo对结果进行包装
		PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list);
		//测试PageInfo全部属性
		/*System.out.println(page.getPageNum());
		System.out.println(page.getPageSize());
		System.out.println(page.getStartRow());
		System.out.println(page.getEndRow());
		System.out.println(page.getTotal());
		System.out.println(page.getPages());
		System.out.println(page.getFirstPage());
		System.out.println(page.getLastPage());
		System.out.println(page.isHasPreviousPage());
		System.out.println(page.isHasNextPage());
		System.out.println(page.getList().size());*/
		return page;
	}


	@Override
	public Map<String, Object> findUsers(Map<String, Object> params) {
		List<Map<String,Object>> list = baseMapper.findUsers(params);
		Map<String,Object> result = new HashMap<>();

		if(CollectionUtils.isEmpty(list)){
			baseMapper.register(params);
			result.put("result","success");
		}
		else{
			result.put("result","repetition");
		}
		return result;
	}

}
