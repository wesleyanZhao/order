package com.lanou.cn.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Lanou3G on 2017/7/12.
 */
public interface MenuService {

    List<Map<String,Object>> getFirstMenu(Map<String, Object> params);

    Map<String,Object> insertMenuForm(Map<String, Object> params);

    PageInfo<Map<String, Object>> getAllMenu(Map<String, Object> params);

    PageInfo<Map<String, Object>> getAllSelectMenu(Map<String, Object> params);

    Map<String,Object> getCurrentMenu(Map<String, Object> params);

    Map<String,Object> updateMenu(Map<String, Object> params);

}
