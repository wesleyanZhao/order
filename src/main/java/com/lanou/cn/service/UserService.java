package com.lanou.cn.service;


import java.util.Map;

/**
 * Created by Lanou3G on 2017/7/11.
 */
public interface UserService {

    Map<String,Object> getUserInfo(Map<String, Object> params);

    Map<String,Object> userIsExist(Map<String, Object> params);

    Map<String,Object> getPassword(Map<String, Object> params);

    void updateAvatar(Map<String, Object> params);
}
