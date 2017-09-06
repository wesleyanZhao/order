package com.lanou.cn.service.impl;


import com.lanou.cn.mapper.ExchangeMapper;
import com.lanou.cn.service.ExchangeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    @Resource
    private ExchangeMapper exchangeMapper;

     public  List<Map<String ,String >>  judgeGoods(String prd_dtl_no){
     List<Map<String ,String>>list  =exchangeMapper.getGoodsW(prd_dtl_no);  //得到商品仓库的名字和数量
     return   list;
    }


    /**
     * 根据商品明细号 ，来判断是否支持退货
     * @param
     * @return
     */
    public  String getReturn(Map<String,String > map){

         return exchangeMapper.getReturn(map);

    }


    /**
     * 根据订单编号来获得当前时间，和可售后时间
     * @param ord_no
     * @return
     */
    public Map<String,Object> getDate( String ord_no){
       Map<String,Object> map=  new HashMap<>();

       return  exchangeMapper.getDate(ord_no);
    }


   public  void  insertReturn( Map<String,String > map){
       exchangeMapper.insertReturn(map);

   }


}


