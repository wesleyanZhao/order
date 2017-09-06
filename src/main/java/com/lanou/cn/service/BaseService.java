package com.lanou.cn.service;

import com.github.pagehelper.PageInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 28/6/17.
 */
public interface BaseService {
	Map<String, String> loginValidate(String username, String password);
	Set<String> findUserAuthList(int id);
	Map<String, Object> findUserAuthInfoByName(String username);


	/**
	 * 登录
	 * @param params
	 * @return
	 */
	Map<String,Object> login(Map<String, Object> params);

	/**
	 * 注册
	 * @param params
	 * @return
	 */
	Map<String, Object> register(Map<String, Object> params);

	/**
	 * 菜单展示
	 * @param loginUser
	 * @return
	 */
	Map<String,Object> home(String loginUser);

	/**
	 * 修改用户信息
	 * @param params
	 * @return
	 */
	Map<String,Object> userInfoForm(Map<String, Object> params);

	/**
	 *修改时查询个人信息并回显
	 * @param params
	 * @return
	 */
	Map<String,Object> findUserInfo(Map<String, Object> params);

	/**
	 * 修改密码
	 *  @param params
	 * @return
	 */
	public Map<String,Object> updatePwd(Map<String, Object> params);


	List<LinkedHashMap<String,Object>> findAllMenuList();

	PageInfo<Map<String, Object>> findAllUsersPageList(Map<String, Object> params);


	Map<String, Object> findUsers(Map<String, Object> params);

}
