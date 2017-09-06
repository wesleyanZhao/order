package com.lanou.cn.service.impl;

import com.lanou.cn.mapper.BillMapper;
import com.lanou.cn.service.BillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by landfash on 2017/7/8.
 */
@Service
public class BillServiceImpl implements BillService {

    @Resource
    private BillMapper billMapper;

    @Override
    public void addBillInfo(Map<String, Object> params) {
        System.out.println(params+"billserviceImpl");
        billMapper.addBillInfo(params);
    }

    @Override
    public void addSignin(Map<String, Object> params) {
        billMapper.addSignin(params);
    }

    /**
     * 查询商品明细编号，企划编号，同个商品总价，商品数量
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findPrdDtlNo(Map<String, Object> params) {
        System.out.println(params+"billMapper");
        return billMapper.findPrdDtlNo(params);
    }

    @Override
    public List<Map<String, Object>> findOrdsts(Map<String, Object> params) {
        System.out.println(params+"findOrdsts");
        return billMapper.findOrdsts(params);
    }

    @Override
    public void updateOrdStsCd(Map<String, Object> params) {
         billMapper.updateOrdStsCd(params);
    }
}
