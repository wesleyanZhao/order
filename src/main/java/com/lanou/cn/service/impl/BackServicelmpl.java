package com.lanou.cn.service.impl;

import com.lanou.cn.mapper.BackMapper;
import com.lanou.cn.service.BackService;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lanou on 2017/8/1.
 */
@Service
public class BackServicelmpl implements BackService {

    static final String HOST_URL_VIP = "http://192.168.2.25:8888";
    static final String HOST_URL_PRD = "http://192.168.2.9:8888";

    @Resource
    private BackMapper backMapper;

    @Override
    public Map<String, Object> backThing(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> chang = backMapper.findOnlyOrdDtlInfo(params);
        // /已签收
        if ((int) chang.get("ordStsCd") == 500) {
            String vipId = (String) params.get("vipId");
            String vipNo = (String) params.get("vipNo");
            System.out.println(vipId);
            //部分退
            if (params.get("result").equals("part")) {
                Map<String, Object> list = backMapper.back(params);
                //判断是否可退
                if (list.get("isReturn").equals("y")) {
                    Date ordStsDate = (Date) chang.get("ordCrtDate");//签收日期
                    Date dt = new Date();//当前日期
                    Calendar rightNow = Calendar.getInstance();
                    rightNow.setTime(ordStsDate);
                    rightNow.add(Calendar.DAY_OF_YEAR, 7);//在收货基础上再加7天
                    if (rightNow.getTime().compareTo(dt) > 0) {
                        Map<String, Object> map1 = new HashMap<>();//接返回是否用积分的result
                        MultiValueMap<String, Object> bodyMap9 = new LinkedMultiValueMap<>();
                        double payNum = 0.0 - (double) list.get("cashAmt");
                        bodyMap9.add("id", vipId);
                        bodyMap9.add("payMoney", payNum);//返回余额支付
                        Map<String, Object> result6 = restTemplate.postForObject(HOST_URL_VIP+"/rest/updateVipInfo.do", bodyMap9, Map.class);
                        if (result6.get("result").equals("success")) {
                            MultiValueMap<String, Object> bodyMap2 = new LinkedMultiValueMap<>();
                            bodyMap2.add("id", vipId);
//                                    bodyMap2.add("ordDtlNo", params.get("ordDtlNo"));
                            bodyMap2.add("vipNo", vipNo);
                            bodyMap2.add("payMoney", list.get("cashAmt"));//返回余额支付
                            bodyMap2.add("payIntegral", list.get("igAmt"));//返回的积分
                            Map<String, Object> result = restTemplate.postForObject(HOST_URL_VIP+"/rest/returnOrUse.do", bodyMap2, Map.class);
                            map1 = result;
                        } else {
                            map.put("result", "fail");
                        }
                        //返回库数量
                        if (map1.get("result").equals("success")) {
                            MultiValueMap<String, Object> bodyMap1 = new LinkedMultiValueMap<>();
                            bodyMap1.add("prdDtlNo", list.get("prdDtlNo"));
                            bodyMap1.add("prdNum", list.get("prdCount"));
                            Map<String, Object> result3 = restTemplate.postForObject(HOST_URL_PRD+"/rest/backWareCount.do", bodyMap1, Map.class);
                            if (result3.get("result").equals("success")) {
                                backMapper.returnsOrder((String) params.get("ordDtlNo"), (String) params.get("ordNo"));
                                map.put("result", "success");
                                System.out.println("成功");
                            } else {
                                MultiValueMap<String, Object> bodyMap10 = new LinkedMultiValueMap<>();
                                double payNum1 = (double) list.get("cashAmt");
                                bodyMap9.add("id", vipId);
                                bodyMap9.add("payMoney", payNum);//返回余额支付
                                Map<String, Object> result10 = restTemplate.postForObject(HOST_URL_VIP+"/rest/updateVipInfo.do", bodyMap9, Map.class);
                                map.put("result", "fail");
                                System.out.println("失败");
                            }
                        } else {
                            map.put("result", "fail");
                        }
                    }
                } else {
                    map.put("result", "fail");
                }
                //全部退货
            }
            if (params.get("result").equals("all")) {
                //判断是否可退
                Date ordStsDate = (Date) chang.get("ordCrtDate");//签收日期
                System.out.println(ordStsDate);
                Date dt = new Date();//当前日期
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(ordStsDate);
                rightNow.add(Calendar.DAY_OF_YEAR, 7);//在收货基础上再加7天
                if (rightNow.getTime().compareTo(dt) > 0) {
                    List<Map<String, Object>> list6 = backMapper.backThing(params);
                    Map<String, Object> map2 = new HashMap<>();
                    Map<String, Object> map3 = new HashMap<>();
                    for (int i = 0; i < list6.size(); i++) {
                        //判断是否使用积分
                        if ("200".equals((String) list6.get(i).get("payType"))) {
                            BigDecimal bigDecimal = new BigDecimal((Double) list6.get(i).get("payCount"));
                            System.out.println(bigDecimal.toString());
                            Double payCount = Double.parseDouble(bigDecimal.toString());
                            Double payCount1 = 0.0;
                            for (int j = 0; j < list6.size(); j++) {
                                if (("100").equals((String) list6.get(j).get("payType"))) {
                                    BigDecimal bigDecima2 = new BigDecimal((Double) list6.get(j).get("payCount"));
                                    payCount1 = Double.parseDouble(bigDecima2.toString());
                                    break;
                                }
                            }
                            MultiValueMap<String, Object> bodyMap9 = new LinkedMultiValueMap<>();
                            double payNum = 0.0 - (double) payCount1;
                            bodyMap9.add("id", vipId);
                            bodyMap9.add("payMoney", payNum);//返回余额支付
                            Map<String, Object> result8 = restTemplate.postForObject(HOST_URL_VIP+"/rest/updateVipInfo.do", bodyMap9, Map.class);
                            if (result8.get("success").equals("result")) {
                                MultiValueMap<String, Object> bodyMap8 = new LinkedMultiValueMap<>();
                                bodyMap8.add("id", vipId);
//                                bodyMap8.add("ordDtlNo", params.get("ordDtlNo"));
                                bodyMap8.add("vipNo", vipNo);
                                bodyMap8.add("payMoney", payCount1);
                                bodyMap8.add("payIntegral", payCount);
                                Map<String, Object> result = restTemplate.postForObject(HOST_URL_VIP+"/rest/returnOrUse.do", bodyMap8, Map.class);
                                map2 = result;
                                break;
                            } else {
                                map.put("result", "fail");
                            }
                            break;
                        } else if (("100").equals((String) list6.get(i).get("payType"))) {
                            for (int j = 0; j < list6.size(); j++) {
                                if (!("200").equals((String) list6.get(j).get("payType"))) {
                                    BigDecimal bigDecima2 = new BigDecimal((Double) list6.get(i).get("payCount"));
                                    Double payCount = Double.parseDouble((bigDecima2).toString());
                                    MultiValueMap<String, Object> bodyMap9 = new LinkedMultiValueMap<>();
                                    double payNum = 0.0 - (double) payCount;
                                    bodyMap9.add("id", vipId);
                                    bodyMap9.add("payMoney", payNum);//返回余额支付
                                    Map<String, Object> result8 = restTemplate.postForObject(HOST_URL_VIP+"/rest/updateVipInfo.do", bodyMap9, Map.class);
                                    System.out.println(result8.get("result") + "bb");
                                    System.out.println(payCount + "a" + vipId + "a" + payCount + "a" + vipNo);
                                    if (("success").equals((String)result8.get("result"))) {
                                        MultiValueMap<String, Object> bodyMap7 = new LinkedMultiValueMap<>();
                                        bodyMap7.add("id", vipId);
//                                        bodyMap7 .add("ordDtlNo", params.get("ordDtlNo"));
                                        bodyMap7.add("vipNo", vipNo);
                                        bodyMap7.add("payMoney", payCount);
                                        bodyMap7.add("payIntegral", "0");
                                        Map<String, Object> result = restTemplate.postForObject(HOST_URL_VIP+"/rest/returnOrUse.do", bodyMap7, Map.class);
                                        map3 = result;

                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                    //返回库数量
                    if (("success").equals((String) map2.get("result"))) {
                        List<Map<String, Object>> count = backMapper.prdCountOrDtlNo(params);
                        System.out.println(count);
                        Map<String, Object> result = new HashMap<>();
                        System.out.println(count.size() + "次数");
                        for (int k = 0; k < count.size(); k++) {
                            MultiValueMap<String, Object> bodyMap1 = new LinkedMultiValueMap<>();
//                                    bodyMap1.add("prdDtlNo", list.get("prdDtlNo"));
//                                    bodyMap1.add("prdNum", list.get("prdCount"));
                            bodyMap1.add("prdDtlNo", count.get(k).get("prdDtlNo"));
                            bodyMap1.add("prdNum", count.get(k).get("prdCount"));
                            Map<String, Object> result5 = restTemplate.postForObject(HOST_URL_PRD+"/rest/backWareCount.do", bodyMap1, Map.class);
                            System.out.println(result5 + "aa");
                            result = result5;
                            backMapper.returnsOrder((String) count.get(k).get("ordDtlNo"), (String) params.get("ordNo"));
                        }
                        if (("success").equals((String) result.get("result"))) {
                            map.put("result", "success");
                            System.out.println("成功");
                        } else {
                            map.put("result", "fail");
                            for (int i = 0; i < list6.size(); i++) {
                                if ("200".equals((String) list6.get(i).get("payType"))) {
                                    BigDecimal bigDecimal = new BigDecimal((Double) list6.get(i).get("payCount"));
                                    System.out.println(bigDecimal.toString());
                                    Double payCount = Double.parseDouble(bigDecimal.toString());
                                    Double payCount1 = 0.0;
                                    for (int j = 0; j < list6.size(); j++) {
                                        if (("100").equals((String) list6.get(j).get("payType"))) {
                                            BigDecimal bigDecima2 = new BigDecimal((Double) list6.get(j).get("payCount"));
                                            payCount1 = Double.parseDouble(bigDecima2.toString());
                                            break;
                                        }
                                    }
                                    MultiValueMap<String, Object> bodyMap9 = new LinkedMultiValueMap<>();
                                    double payNum = (double) payCount1;
                                    bodyMap9.add("id", vipId);
                                    bodyMap9.add("payMoney", payNum);//返回余额支付
                                    Map<String, Object> result8 = restTemplate.postForObject(HOST_URL_VIP+"/rest/updateVipInfo.do", bodyMap9, Map.class);
                                    if (result8.get("result").equals("success")) {
                                        MultiValueMap<String, Object> bodyMap8 = new LinkedMultiValueMap<>();
                                        bodyMap8.add("id", vipId);
//                                bodyMap8.add("ordDtlNo", params.get("ordDtlNo"));
                                        bodyMap8.add("vipNo", vipNo);
                                        bodyMap8.add("payMoney", -payCount1);
                                        bodyMap8.add("payIntegral", -payCount);
                                        Map<String, Object> result5 = restTemplate.postForObject(HOST_URL_VIP+"/rest/returnOrUse.do", bodyMap8, Map.class);
                                        map2 = result5;
                                        break;
                                    }
                                    System.out.println("失败");
                                }
                            }
                        }
                        if (("failure").equals((String) map2.get("result"))) {
                            for (int i = 0; i < list6.size(); i++) {
                                if ("200".equals((String) list6.get(i).get("payType"))) {

                                    BigDecimal bigDecimal = new BigDecimal((Double) list6.get(i).get("payCount"));
                                    System.out.println(bigDecimal.toString());
                                    Double payCount = Double.parseDouble(bigDecimal.toString());
                                    Double payCount1 = 0.0;
                                    for (int j = 0; j < list6.size(); j++) {
                                        if (("100").equals((String) list6.get(j).get("payType"))) {
                                            BigDecimal bigDecima2 = new BigDecimal((Double) list6.get(j).get("payCount"));
                                            payCount1 = Double.parseDouble(bigDecima2.toString());
                                            break;
                                        }
                                    }
                                    MultiValueMap<String, Object> bodyMap9 = new LinkedMultiValueMap<>();
                                    double payNum = (double) payCount1;
                                    bodyMap9.add("id", vipId);
                                    bodyMap9.add("payMoney", payNum);//返回余额支付
                                    Map<String, Object> result8 = restTemplate.postForObject(HOST_URL_VIP+"/rest/updateVipInfo.do", bodyMap9, Map.class);
                                    map.put("result", "fail");
                                    break;
                                }
                            }
                        } else {
                            map.put("result", "fail");
                        }

                    }
                    if (("success").equals((String) map3.get("result"))) {
                        List<Map<String, Object>> count = backMapper.prdCountOrDtlNo(params);
                        System.out.println(count);
                        Map<String, Object> result = new HashMap<>();
                        System.out.println(count.size() + "次数");
                        for (int k = 0; k < count.size(); k++) {
                            MultiValueMap<String, Object> bodyMap1 = new LinkedMultiValueMap<>();
//                                    bodyMap1.add("prdDtlNo", list.get("prdDtlNo"));
//                                    bodyMap1.add("prdNum", list.get("prdCount"));
                            bodyMap1.add("prdDtlNo", count.get(k).get("prdDtlNo"));
                            bodyMap1.add("prdNum", count.get(k).get("prdCount"));
                            Map<String, Object> result5 = restTemplate.postForObject(HOST_URL_PRD+"/rest/backWareCount.do", bodyMap1, Map.class);
                            System.out.println(result5 + "aa");
                            result = result5;
                            backMapper.returnsOrder((String) count.get(k).get("ordDtlNo"), (String) params.get("ordNo"));
                        }
                        if (("success").equals((String) result.get("result"))) {
                            map.put("result", "success");
                            System.out.println("成功");
                        } else {
                            map.put("result", "fail");
                            for (int i = 0; i < list6.size(); i++) {
                                if (("100").equals((String) list6.get(i).get("payType"))) {
                                    for (int j = 0; j < list6.size(); j++) {
                                        if (!("200").equals((String) list6.get(j).get("payType"))) {
                                            BigDecimal bigDecima2 = new BigDecimal((Double) list6.get(i).get("payCount"));
                                            Double payCount = Double.parseDouble((bigDecima2).toString());
                                            MultiValueMap<String, Object> bodyMap9 = new LinkedMultiValueMap<>();
                                            double payNum = (double) payCount;
                                            bodyMap9.add("id", vipId);
                                            bodyMap9.add("payMoney", payNum);//返回余额支付
                                            Map<String, Object> result8 = restTemplate.postForObject(HOST_URL_VIP+"/rest/updateVipInfo.do", bodyMap9, Map.class);
                                            System.out.println(map2.get("result") + "bb");
                                            System.out.println(payCount + "a" + vipId + "a" + payCount + "a" + vipNo);
                                            if (result8.get("success").equals("success")) {
                                                MultiValueMap<String, Object> bodyMap7 = new LinkedMultiValueMap<>();
                                                bodyMap7.add("id", vipId);
//                                        bodyMap7 .add("ordDtlNo", params.get("ordDtlNo"));
                                                bodyMap7.add("vipNo", vipNo);
                                                bodyMap7.add("payMoney", -payCount);
                                                bodyMap7.add("payIntegral", "0");
                                                Map<String, Object> result7 = restTemplate.postForObject(HOST_URL_VIP+"/rest/returnOrUse.do", bodyMap7, Map.class);
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (("failure").equals((String) map3.get("result"))) {
                        for (int i = 0; i < list6.size(); i++) {
                            if (("100").equals((String) list6.get(i).get("payType"))) {
                                for (int j = 0; j < list6.size(); j++) {
                                    if (!("200").equals((String) list6.get(j).get("payType"))) {
                                        BigDecimal bigDecima2 = new BigDecimal((Double) list6.get(i).get("payCount"));
                                        Double payCount = Double.parseDouble((bigDecima2).toString());
                                        MultiValueMap<String, Object> bodyMap9 = new LinkedMultiValueMap<>();
                                        double payNum = (double) payCount;
                                        bodyMap9.add("id", vipId);
                                        bodyMap9.add("payMoney", payNum);//返回余额支付
                                        Map<String, Object> result8 = restTemplate.postForObject(HOST_URL_VIP+"/rest/updateVipInfo.do", bodyMap9, Map.class);
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                } else {
                    map.put("result", "fail");
                }
            }
        }
        return map;
    }
}
