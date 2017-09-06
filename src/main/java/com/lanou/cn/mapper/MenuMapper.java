package com.lanou.cn.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by Lanou3G on 2017/7/12.
 */
public interface MenuMapper {

    /**
     * 获取一级菜单
     * @return
     */
    @Select("select name,id from menu where p_id = 0")
    List<Map<String,Object>> getFirstMenu();

    /**
     * 添加菜单
     * @param params
     * @return
     */
    @Insert("insert into menu values(0,#{params.p_id},#{params.name},111,#{params.is_used})")
    void insertMenuForm(@Param("params") Map<String, Object> params);

    /**
     * 分页查询
     * @return
     */
    List<Map<String,Object>> getAllMenu();

    /**
     * 条件查询菜单
     * @return
     */
    List<Map<String,Object>> getAllSelectMenu(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询当前菜单信息
     * @param params
     * @return
     */
    @Select("select p_id as pId,name,url,is_used as isUsed from menu where id = #{params.id}")
    Map<String,Object> getCurrentMenu(@Param("params") Map<String, Object> params);

    /**
     * 更新当前菜单信息
     * @param params
     */
    @Update("update menu set p_id = #{params.pId},name = #{params.name},url=#{params.url},is_used=#{params.isUsed} where id = #{params.id}")
    void updateMenu(@Param("params") Map<String, Object> params);
}
