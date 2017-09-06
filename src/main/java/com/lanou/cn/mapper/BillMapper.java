package com.lanou.cn.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by landfash on 2017/7/8.
 */
public interface BillMapper {
   /**
    * 添加发票信息
    * @param params
    */
   void addBillInfo(@Param("params") Map<String, Object> params);

   /**
    * 添加签收状态
    * @param params
    */
   void addSignin(@Param("params") Map<String,Object> params);

   /**
    * 查询调企划返现接口 所需数据prd_dtl_no,sp_no,ord_sum,prd_count
    * @param params
    * @return
    */
   @Select("select prd_dtl_no,sp_no,ord_sum,prd_count from order_dtl_info where ord_no = #{params.ordNo} and vip_no= #{params.vipNo}")
   List<Map<String,Object>> findPrdDtlNo(@Param("params") Map<String,Object> params);

   /**
    * 查询点击收货后订单状态是否为500
    * @param params
    * @return
    */
   @Select("select ord_sts from order_state_change where ord_no = #{params.ordNo}")
   List<Map<String,Object>> findOrdsts(@Param("params") Map<String,Object> params);

   /**
    * 更新订单状态
    * @param params
    */
   @Update("update order_info set ord_sts_cd='500' where ord_no = #{params.ordNo}")
   void updateOrdStsCd(@Param("params") Map<String,Object> params);

}
