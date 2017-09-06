package com.lanou.cn.service.impl;

import com.lanou.cn.Utils.calculateUtils;
import com.lanou.cn.mapper.RestMapper;
import com.lanou.cn.service.RestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lanou on 2017/8/1.
 */
@Service
public class RestServiceImpl implements RestService {

    static final String HOST_URL_PLA = "http://192.168.2.39:8088";
    static final String HOST_URL_VIP = "http://192.168.2.25:8888";
    static final String HOST_URL_PRD = "http://192.168.2.9:8888";

    @Resource
    private RestMapper restMapper;

    private Map<String,Object> cal(int level,double diffPrice,Map<String,Object> orderInfo,Map<String,Object> barterProduct){//计算加入企划等之后的价格
        RestTemplate restTemplate = new RestTemplate();

        Map<String,Object> priResult = null;
        if(level==2){
            diffPrice = calculateUtils.mul(diffPrice,0.98);
        }else if(level == 3){
            diffPrice = calculateUtils.mul(diffPrice,0.96);
        }else if(level == 4){
            diffPrice = calculateUtils.mul(diffPrice,0.94);
        }else if(level == 5){
            diffPrice = calculateUtils.mul(diffPrice,0.92);
        }
        RestTemplate restTemplate1 = new RestTemplate();
        MultiValueMap<String,Object> backInfo = new LinkedMultiValueMap<>();
        String spNo = (String) orderInfo.get("spNo");
        if((!("20".equals(spNo)))&&(!("30".equals(spNo)))&&(!("60".equals(spNo)))){
            List<Map<String,Object>> list = new ArrayList<>();
            Map<String,Object> backPrd = new HashMap<>();//换货商品的信息
            backPrd.put("price",diffPrice);
            backPrd.put("prdDtlNo",barterProduct.get("prdDtlNo"));
            backPrd.put("spNo",spNo);
            backPrd.put("num","1");
            list.add(backPrd);
            backInfo.add("product",list);
            System.out.println(backInfo);
            priResult = restTemplate.postForObject(HOST_URL_PLA+"/rest/getGifts.do",backInfo,Map.class);//调用企划接口，计算参加企划后的商品的价格
        }else{
            priResult.put("price",diffPrice);
        }
        return priResult;
    }

    //换货问题：库存修改，用户的消费金额的修改
    @Transactional
    @Override
    public Map<String,Object> barter(Map<String, Object> params) throws Exception{
        Map<String,Object> result = new HashMap<>();
        String vipId = (String)params.get("vipId");
        String vipNo = (String)params.get("vipNo");
        List<Map<String,Object>> dateDiff = restMapper.getDay(params);//获得订单完成到换货的时间差
        int day = 8;
        if(null != dateDiff && dateDiff.size() > 0){
            day = Integer.parseInt(dateDiff.get(0).get("dateDiff").toString());
        }
        Map<String,Object> orderInfo = (Map<String,Object>)params.get("order");//订单信息
        Map<String,Object> barterProduct = (Map<String, Object>) params.get("product");//换的商品信息
        if(day<=7 && day>=0){//售后7天内可退换货
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String,Object> vipMap = new LinkedMultiValueMap<>();
            vipMap.add("id",vipId);
            List<Map<String,Object>> vipInfo = restTemplate.postForObject(HOST_URL_VIP+"/rest/findVipInfo.do",vipMap,List.class);
            int level = (int)vipInfo.get(0).get("vipLevel");//会员等级
            double oldPrice = (double)orderInfo.get("saleSum");
            Map<String,Object> priResultOld = cal(level,oldPrice,orderInfo,barterProduct);
            double backPrice = (double)barterProduct.get("salePrice");
            Map<String,Object> priResultBack = cal(level,backPrice,orderInfo,barterProduct);
            double diffPrice = calculateUtils.sub((double)priResultOld.get("price"),(double)priResultBack.get("price"));
            if(diffPrice>=0){//用户补价格
//                Map<String,Object> priResult = cal(level,diffPrice,orderInfo,barterProduct);
//                String overPrice = (String) priResult.get("price");//计算完的价格
                MultiValueMap<String,Object> payMap = new LinkedMultiValueMap<>();
                payMap.add("payMoney",diffPrice);
                payMap.add("id",vipId);
                payMap.add("vipNo",vipNo);
                Map<String,Object> resultBarter = restTemplate.postForObject(HOST_URL_VIP+"/rest/useMoney.do",payMap,Map.class);
                if("insufficient".equals((String)resultBarter.get("result"))){
                    result.put("result","insufficient");
                    return result;
                }else if("failure".equals((String)resultBarter.get("result"))){
                    result.put("result","failure");
                    return result;
                }
            }else{//返回用户价格
//                Map<String,Object> priResult = cal(level,-diffPrice,orderInfo,barterProduct);
//                String overPrice = priResult.get("price").toString();
                MultiValueMap<String,Object> payMap = new LinkedMultiValueMap<>();
                payMap.add("payMoney",-diffPrice);
                payMap.add("id",vipId);
                payMap.add("vipNo",vipNo);
                Map<String,Object> resultBarter = restTemplate.postForObject(HOST_URL_VIP+"/rest/returnMoney.do",payMap,Map.class);
                if("failure".equals((String)resultBarter.get("result"))){
                    result.put("result","failure");
                    return result;
                }
            }


            MultiValueMap<String,Object> backPrd = new LinkedMultiValueMap<>();
            backPrd.add("prdDtlNo",orderInfo.get("prdDtlNo"));
            backPrd.add("prdNum","1");
            Map<String,Object> backResult = restTemplate.postForObject(HOST_URL_PRD+"/rest/backWareCount.do",backPrd,Map.class);//退还原商品
            if("success".equals((String)backResult.get("result"))){
                MultiValueMap<String,Object> barterPrd = new LinkedMultiValueMap<>();
                barterPrd.add("prdDtlNo",barterProduct.get("prdDtlNo"));
                barterPrd.add("prdNum","1");
                Map<String,Object> barterResult = restTemplate.postForObject(HOST_URL_PRD+"/rest/barterGoods.do",barterPrd,Map.class);//计算换的商品的库存
                if("failure".equals((String)barterResult.get("result"))){
                    return barterResult;
                }
                result.put("result","success");
                if("success".equals((String)result.get("result"))){
                    restMapper.addOrder(params);//加订单主表
                    List<Map<String,Object>> oldOrder = restMapper.getOrderId(params);
                    if(null != oldOrder && oldOrder.size()>0){
                        restMapper.addStatus(oldOrder.get(0));//加订单状态
                        orderInfo.put("ordNo",oldOrder.get(0).get("oldOrdNo"));
                        orderInfo.put("prdNo",barterProduct.get("prdNo"));
                        orderInfo.put("prdDtlNo",barterProduct.get("prdDtlNo"));
                        orderInfo.put("prdDtlName",barterProduct.get("prdDtlName"));
                        restMapper.addDtlOrd(orderInfo);//加订单明细表
                        result.put("result","success");
//                        List<Map<String,Object>> ordDtl = restMapper.getOrdDtl(orderInfo);
//                        if(null !=  ordDtl && ordDtl.size() > 0){
//                            Map<String,Object> ordDtlMap = new HashMap<>();
//                            ordDtlMap.put("ordNo",ordDtl.get(0).get("ordNo"));
//                            ordDtlMap.put("ordDtlNo",params.get("ordDtlNo"));
//                            restMapper.addReturnInfo(ordDtlMap);
//                        }
                        restMapper.addReturnInfo(params);
                    }
                }
            }else{
                return backResult;
            }
        }else {
            result.put("rsult","overTime");
        }
        return result;
    }
}
