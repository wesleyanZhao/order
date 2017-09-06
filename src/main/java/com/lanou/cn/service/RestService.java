package com.lanou.cn.service;

import java.util.Map;

/**
 * Created by lanou on 2017/8/1.
 */
public interface RestService {

    /**
     * 用户异品换货
     * @param params
     * @throws Exception
     */
    Map<String,Object> barter(Map<String, Object> params) throws Exception;
}
