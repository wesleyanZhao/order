package com.lanou.cn.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Lanou3G on 2017/7/12.
 */
public interface OrderMapper {

    //订单分页
    List<Map<String,Object>> findOrder(@Param("params") Map<String, Object> params);

    /**
     * 订单添加
     */
    void addOrder(Map<String,Object> params);

    /**
     * 添加订单详情
     */
    void addOrderDtl(Map<String,Object> params);

    /**
     * 查询订单编号
     */
    Map<String,Object> selectOrderNo(@Param("params") Map<String,Object> params);

    /**
     * 支付后订单完善
     * @param params
     */
    void updateOrder(@Param("params") Map<String,Object> params);

    /**
     * 支付后订单明细完善
     * @param params
     */
    void updateOrderDtl(@Param("params") Map<String,Object> params);
    /**
     * 查询所有的省
     * @return
     */
    List<Map<String,Object>> findProvinceList();

    /**
     * 查找城市名
     * @param provinceId
     * @return
     */
    List<Map<String,Object>> findCityList(@Param("provinceId") int provinceId);

    /**
     * 修改订单
     * @param params
     */
    void updateOrderInfo(@Param("params") Map<String, Object> params);
    /**
     * 修改订单状态
     * @param params
     */
    void updaterdStsCd(@Param("params") Map<String, Object> params);
    /**
     * 订单明细分页
     * @param params
     * @return
     */
    List<Map<String,Object>> findOrderDtlInfo(@Param("params") Map<String, Object> params);

    /**
     * 修改订单明细
     * @param params
     */
    void updateOrderDtlInfo(@Param("params") Map<String, Object> params);


    /**
     * 通过订单编号   查询明细中的isReturn
     * all
     * part
     * fail
     * @param params
     * @return
     */
    List<Map<String,Object>> findOrdDtlIsReturn(@Param("params") Map<String, Object> params);

    /**
     * 会员降级系统
     * 需要数据：
     * 会员编号（vip_no）
     * @param params
     */
    List<Map<String,Object>> findStsCd(@Param("params") Map<String, Object> params);


    /**
     * 订单取消（查询订单详情）
     * 需要数据：订单明细编号
     * 回传数据：
     * 商品明细编号prd_dtl_d，商品数量prd_count，会员编号vip_no，余额支付cash_amt，积分支付cpn_amt
     * @param ordDtlNo
     * @return
     */
    List<Map<String,Object>> findOrderIn(@Param("ordDtlNo") String ordDtlNo);

    List<Map<String,Object>> findOrdDtlNo(@Param("params") Map<String, Object> params);

    /**
     * 添加订单支付记录表
     * @param params
     */
    void addOrdPayRec(@Param("params") Map<String, Object> params);

    /**
     * 添加订单关系表
     * @param params
     */
    int addOrderW(@Param("params") Map<String,Object> params);

    /**
     * 查询订单仓储关系表
     * @param params
     * @return
     */
    List<Map<String,Object>> findOrdWR(@Param("params") Map<String,Object> params);

    /**
     * 添加订单状态表
     * @param params
     */
    void addOrderStateChange(@Param("params") Map<String,Object> params);

    /**
     * 查询返还的积分
     * @param params
     * @return
     */
    Map<String,Object> findReturnIn(@Param("params") Map<String,Object> params);




    /**
     * 添加订单评价
     * @param params
     */
    void addEvaluate(@Param("params") Map<String,Object> params);




    /**
     * 查询订单主信息和明细
     * @param params
     * @return
     */
    List<Map<String,Object>> findOrderInfoAndDtl(@Param("params") Map<String,Object> params);



    /**
     * 查询订单状态
     * @param ordNo
     * @return
     */
    @Select("select ord_sts ordSts,ord_sts_date ordStsDate from order_state_change where ord_no=#{ordNo}")
    Map<String,Object> findSts(@Param("ordNo") String ordNo);

    /**
     * 查询评价
     * @param params
     * @return
     */
    List<Map<String,Object>> getPrdCom(Map<String,Object> params);

    /**
     * 根据ordNo查询订单的sale_sum，prd_count，prd_dtl_no
     * @param ordNo
     * @return
     */
    List<Map<String,Object>> findPrdDtl(@Param("ordNo") String ordNo);

}
