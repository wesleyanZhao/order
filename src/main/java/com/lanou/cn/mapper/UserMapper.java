package com.lanou.cn.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * Created by Lanou3G on 2017/7/11.
 */
public interface UserMapper {


    @Select("select id from users where username = #{username}")
    Map<String,Object> getUserId(@Param("username") String username);

    /**
     * 查询用户个人信息
     * @param params
     * @return
     */
    Map<String,Object> getUserInfo(@Param("params") Map<String, Object> params);

    @Select("select id from user_info where user_id = #{userId}")
    Map<String,Object> getUserInfoId(@Param("userInfo_id") int userInfo_id);

    void insertUserInfo(@Param("params") Map<String, Object> params);

    //@Update("update user_info set nickname=#{params.nickname},birthday=#{params.birthday},sign=#{params.sign} where id = #{params.userId}")
    void updateUserInfo(@Param("params") Map<String, Object> params);

    @Select("select password from users where username = #{username}")
    Map<String,Object> getPassword(@Param("username") String username);

    @Update("update users set password=#{params.newPassword} where username = #{params.username}")
    void updatePassword(@Param("params") Map<String, Object> params);

    @Update("update user_info set avatar = #{params.avatar} where user_id = #{params.userId}")
    void updateAvatar(@Param("params") Map<String, Object> params);
}
