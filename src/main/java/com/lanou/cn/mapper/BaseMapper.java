package com.lanou.cn.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 28/6/17.
 */
public interface BaseMapper {

	/**
	 * 根据username查询登录用户
	 * @param username
	 * @return
	 */
	@Select("select us.id, us.username,us.password from users us where us.username = #{username}")
	Map<String,Object> findUserByUsername(@Param("username") String username);

	/**
	 * 根据username查询登录用户
	 * @param id
	 * @return
	 */
	@Select("select urs.url from user_role_relational urs where urs.user_id=#{id}")
	Set<String> findUserAuthList(@Param("id") int id);

	/**
	 * 获取全部菜单列表
	 * @return
	 */
	@Select("select id,p_id pid,name node ,url from menu where is_used = 'y'")
	List<LinkedHashMap<String,Object>> findAllMenuList();


	/**
	 * 注册
	 * @param params
	 */
	void register(@Param("params") Map<String,Object> params);


	List<Map<String,Object>> findUsers(@Param("params") Map<String,Object> params);
}
