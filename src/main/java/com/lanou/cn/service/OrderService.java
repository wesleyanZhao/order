package com.lanou.cn.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Lanou3G on 2017/7/12.
 */

public interface OrderService {

    //查询订单
    PageInfo<Map<String, Object>> findAllOrderPageList(Map<String, Object> params);
    /**
     * 查询所有的省
     * @return
     */
    List<Map<String,Object>> findProvinceList();
    /**
     * 根据省查询市
     * @param provinceId
     * @return
     */
    List<Map<String,Object>> findCityList(String provinceId);

    /**
     * 会员降级系统
     * 需要数据：
     * 会员编号（vip_no）
     * @param params
     */
    Map<String,Object> UpdateVipLevel(Map<String, Object> params);


    Map<String,Object> CancellationOfOrder(Map<String, Object> params);
    /**
     * 查询一条订单
     * @param params
     * @return
     */
    List<Map<String,Object>> findOrder(Map<String, Object> params);

    /**
     * 修改订单信息
     * @param params
     */
    void updateOrderInfoForm(Map<String, Object> params);

    /**
     * 查询订单明细
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> findOrderDtlInfoList(Map<String, Object> params);


    /**
     * 修改订单明细信息
     * @param params
     */
    void updateOrderDtlInfo(Map<String, Object> params);
    /**
     * 修改订单状态
     * @param params
     */
    void updaterdStsCd(@Param("params") Map<String, Object> params);
    /**
     * 通过订单明细 查询一条
     * @param params
     * @return
     */
    Map<String,Object> findOnlyOrdDtlInfo (Map<String, Object> params);

    /**
     * 是否能退货
     * @param params
     * @return
     */
    Map<String,Object> findOrdDtlIsReturn (Map<String, Object> params);

    /**
     * 添加订单
     */
    void addOrder(Map<String,Object> params);

    /**
     * 添加订单详情
     */
    void addOrderDtl(Map<String,Object> params);

    /**
     * 查询订单编号
     */
    Map<String,Object> selectOrderNo(Map<String,Object> params);

    /**
     * 支付后订单完善
     */
    void updateOrder(Map<String,Object> params);

    /**
     * 支付后订单明细完善
     */
    void updateOrderDtl(Map<String,Object> params);
    /**
     * 添加订单支付记录表
     * @param params
     * @return
     */
    Map<String,Object> addOrdPayRec(Map<String, Object> params);

    /**
     * 计算积分加倍返回
     * @param params
     * @return
     */
    Map<String,Object> doubleIntegral(Map<String, Object> params);

    /**
     * 计算积分均摊
     * @param params
     * @return
     */
    Map<String,Object> avePoint(Map<String, Object> params);

    /**
     * 添加订单关系表
     * @param params
     */
    Map<String, Object> addOrderW(Map<String,Object> params);

    /**
     * 查询订单仓储关系表并解除占用和库存出库
     * @param params
     * @return
     */
    Map<String,Object> findOrdWR(Map<String,Object> params);

    /**
     * 添加订单状态表
     * @param params
     */
    void addOrderStateChange(Map<String,Object> params);


    /**
     * 添加订单商品评论
     * @param params
     * @return
     */
    Map<String,Object> addEvaluate(Map<String,Object> params);

    /**
     * 查询订单主信息和订单明细信息
     * @param params
     * @return
     */
    PageInfo<Map<String,Object>> findOrderInfoAndDtl(Map<String,Object> params);


    /**
     * 查询订单状态
     * @param ordNo
     * @return
     */
    Map<String,Object> findSts(String ordNo);

    /**
     * 查询订单评价
     * @param params
     * @return
     */
    List<Map<String,Object>> getPrdCom(Map<String,Object> params);

    /**
     * 根据ordNo查询订单的sale_sum，prd_count，prd_dtl_no
     * @param ordNo
     * @return
     */
    List<Map<String,Object>> findPrdDtl(String ordNo);







}
