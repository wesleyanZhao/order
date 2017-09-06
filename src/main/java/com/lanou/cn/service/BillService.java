package com.lanou.cn.service;

import java.util.List;
import java.util.Map;

/**
 * Created by landfash on 2017/7/8.
 */
public interface BillService {
    void addBillInfo(Map<String, Object> params);
    void addSignin(Map<String,Object> params);
    List<Map<String,Object>> findPrdDtlNo(Map<String,Object> params);
    List<Map<String,Object>> findOrdsts(Map<String,Object> params);
    void updateOrdStsCd(Map<String,Object> params);
}
