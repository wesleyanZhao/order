package com.lanou.cn.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by lanou on 2017/8/
 */
public interface ExchangeMapper {
    /**
     * 根据明细编号去得到相应的仓库号，和仓库数量
     * @param
     * @return
     */
    @Select("select w_no,w_count from ware_prd_r where prd_dtl_no=#{prd_dtl_no} ")
    List<Map<String, String>> getGoodsW(@Param("prd_dtl_no") String prd_dtl_no);

    /**
     * 根据商品明细号 ，来判断是否支持退货
     * @param
     * @return
     */
   @Select("select is_return from order_dtl_info where  ord_no=#{map.ordNo} and prd_dtl_no=#{map.prdDtlNo} and ord_dtl_no=#{map.ordDtlNo}")
   String getReturn(@Param("map") Map<String, String> map);

    /**
     * 根据订单编号来获得当前时间，和可售后时间
     * @param ord_no
     * @return
     */
    @Select("select ord_crt_date,service_date from order_info where ord_no=#{ord_no}")
    Map<String,Object> getDate(@Param("ord_no") String ord_no);

    @Insert("insert into returns_order(ord_no,ord_dtl_no,ope_type,ope_date) values(#{map.ordNo},#{map.ordDtlNo},'20',now())")
    void  insertReturn(@Param("map") Map<String, String> map);



}
