package com.lanou.cn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanou.cn.mapper.MenuMapper;
import com.lanou.cn.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lanou3G on 2017/7/12.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Map<String, Object>> getFirstMenu(Map<String, Object> params) {
        List<Map<String, Object>> list = menuMapper.getFirstMenu();
        return list;
    }

    @Override
    public Map<String,Object> insertMenuForm(Map<String, Object> params) {
        Map<String,Object> result =new HashMap<>();
        if("1".equals(params.get("p_id1"))){
            try {
                params.put("p_id",params.get("p_id2"));
                menuMapper.insertMenuForm(params);
                result.put("result","success");
            }
            catch (Exception e){
                e.printStackTrace();
                result.put("result","fail");
            }
        }
        else{
            try {
                params.put("p_id",0);
                menuMapper.insertMenuForm(params);
                result.put("result","success");
            }
            catch (Exception e){
                e.printStackTrace();
                result.put("result","fail");
            }
        }
        return result;
    }

    @Override
    public PageInfo<Map<String, Object>> getAllMenu(Map<String, Object> params) {
        //获取当前页码
        Integer currentPage = params.get("currentPage") == null ? 1:Integer.parseInt((String)params.get("currentPage"));
        PageHelper.startPage(currentPage,2);

        //分页操作
        List<Map<String,Object>> list = menuMapper.getAllMenu();
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list);

        return page;
    }

    @Override
    public PageInfo<Map<String, Object>> getAllSelectMenu(Map<String, Object> params) {
        Integer currentPage = params.get("currentPage") == null ? 1:Integer.parseInt((String)params.get("currentPage"));
        PageHelper.startPage(currentPage,5);

        List<Map<String,Object>> list = menuMapper.getAllSelectMenu(params);
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list);

        return page;
    }

    @Override
    public Map<String, Object> getCurrentMenu(Map<String, Object> params) {

        Map<String, Object> menu = menuMapper.getCurrentMenu(params);
        return menu;
    }

    @Override
    public Map<String, Object> updateMenu(Map<String, Object> params) {
        Map<String,Object> result = new HashMap<>();
        try{
            menuMapper.updateMenu(params);
            result.put("result","success");
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("result","failure");
        }
        return result;
    }


}
