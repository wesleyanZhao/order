package com.lanou.cn.service;

import java.util.List;
import java.util.Map;


public interface ExchangeService {

    /**
     *     得到商品仓库的名字和数量
     * @param prd_dtl_no
     * @return
     */
    List< Map<String ,String > > judgeGoods(String prd_dtl_no);


    /**
     * 根据订单编号来获得当前时间，和可售后时间
     * @param ord_no
     * @return
     */
    Map<String,Object>  getDate(String ord_no);

    /**
     * 根据商品明细号 ，来判断是否支持退货
     * @param
     * @return
     */
    String getReturn(Map<String, String> map);


    void  insertReturn(Map<String, String> map);

}
