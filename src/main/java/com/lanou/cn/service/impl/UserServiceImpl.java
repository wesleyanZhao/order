package com.lanou.cn.service.impl;

import com.lanou.cn.mapper.UserMapper;
import com.lanou.cn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lanou3G on 2017/7/11.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> getUserInfo(Map<String,Object> params) {
        return userMapper.getUserInfo(params);
    }

    @Override
    public Map<String,Object> userIsExist(Map<String, Object> params) {

        //根据username 查到 id 在根据id判断是否有信息，都修改
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> userInfo = userMapper.getUserInfo(params);
        System.out.println(params);
        try {
            if(!CollectionUtils.isEmpty(userInfo)){
                int id = (int)userInfo.get("uId");
                params.put("uiId",id);
                userMapper.updateUserInfo(params);
                result.put("result","success");
            }
            else {
                userMapper.insertUserInfo(params);
                result.put("result","success");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("result","failure");
        }
        return result;
    }

    @Override
    public Map<String, Object> getPassword(Map<String,Object> params) {
        Map<String,Object> map = userMapper.getPassword((String) params.get("username"));
        Map<String,Object> result = new HashMap<>();
        if(params.get("oldPassword").equals(params.get("oldPassword1"))){
            try {
                if(map.get("password").equals(params.get("oldPassword"))){
                    System.out.println(params);
                    userMapper.updatePassword(params);
                    result.put("result","success");
                }
                else{
                    result.put("result","failure");
                }
            }
            catch (Exception e){
                e.printStackTrace();
                result.put("result","failure");
            }
        }
        else{
            result.put("result","failure");
        }
        return result;
    }

    @Override
    public void updateAvatar(Map<String, Object> params) {
        Map<String,Object> map = userMapper.getUserId((String) params.get("username"));
        params.put("user_id",map.get("id"));
        userMapper.updateAvatar(params);
    }


}
