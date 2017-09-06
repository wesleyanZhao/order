package com.lanou.cn.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lanou on 2017/8/2.
 */
public interface RestMapper {

    /**
     * 获得申请换货时与签收商品时的时间差
     * @param params
     * @return
     */
    List<Map<String,Object>> getDay(Map<String, Object> params);

    /**
     * 想订单表中加换货单信息
     * @param params
     */
    void addOrder(Map<String, Object> params);

    /**
     * 添加订单状态
     * @param params
     */
    void addStatus(Map<String, Object> params);

    /**
     * 得到刚插入的数据的订单编号
     * @return
     */
    List<Map<String,Object>> getOrderId(Map<String, Object> params);

    /**
     * 换货信息加入订单明细表
     * @param params
     */
    void addDtlOrd(Map<String, Object> params);

    /**
     * 获得订单明细信息
     * @param params
     * @return
     */
    List<Map<String,Object>> getOrdDtl(Map<String, Object> params);

    /**
     * 添加换货单信息
     * @param params
     */
    void addReturnInfo(Map<String, Object> params);
}
