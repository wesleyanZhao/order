package com.lanou.cn.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanou.cn.mapper.OrderMapper;
import com.lanou.cn.service.OrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lanou3G on 2017/7/12.
 */
@Service
public class OrderServiceImpl implements OrderService {

    public static final String HOST_URL = "http://192.168.2.1:8280";

    @Resource
    private OrderMapper orderMapper;

    private int num=0;

    public static final String HOST_URL1 = "http://192.168.2.1:8180";
    public static final String HOST_URL2 = "http://192.168.2.25:8888";
    static final String HOST_URL_PRD = "http://192.168.2.9:8888";



    @Override
    public PageInfo<Map<String, Object>> findAllOrderPageList(Map<String, Object> params) {

        Integer currentPage = params.get("currentPage") == null ? 1:Integer.parseInt((String)params.get("currentPage"));



        //设置一页的个数
        PageHelper.startPage(currentPage, 5);
        List<Map<String,Object>> list = orderMapper.findOrder(params);
        //用PageInfo对结果进行包装
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list);
        return page;
    }

    /**
     * 添加订单
     */
    @Override
    public void addOrder(Map<String, Object> params) {
        orderMapper.addOrder(params);
    }

    /**
     *  添加订单详情
     */
    public void addOrderDtl(Map<String,Object> params){
        orderMapper.addOrderDtl(params);
    }

    /**
     * 查询订单编号
     */
    @Override
    public Map<String, Object> selectOrderNo(Map<String, Object> params) {
        return orderMapper.selectOrderNo(params);
    }

    /**
     * 支付后订单完善
     * @param params
     */
    @Override
    public void updateOrder(Map<String, Object> params) {
       orderMapper.updateOrder(params);
    }

    /**
     * 支付后订单明细完善
     * @param params
     */
    @Override
    public void updateOrderDtl(Map<String, Object> params) {
         orderMapper.updateOrderDtl(params);
    }

    /**
     * 添加订单支付记录表
     * cash_amt,ig_amt,cpn
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> addOrdPayRec(Map<String, Object> params) {
        Map<String,Object> result = new HashMap<>();
        try {
            //获取支付方式
            if(!StringUtils.isEmpty(params.get("cashAmt"))){
                params.put("payType","100");
                params.put("payCount",params.get("cashAmt"));
                orderMapper.addOrdPayRec(params);
            }
            if(!StringUtils.isEmpty(params.get("igAmt"))){
                params.put("payType","200");
                params.put("payCount",params.get("igAmt"));
                orderMapper.addOrdPayRec(params);
            }
            if(!StringUtils.isEmpty(params.get("cpnAmt"))){
                params.put("payType","300");
                params.put("payCount",params.get("cpnAmt"));
                orderMapper.addOrdPayRec(params);
            }
            result.put("result","success");
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("result","failure");
        }
        return result;
    }

    /**
     * 计算积分加倍返回
     * ***************************************************************************************
     * 积分加倍：支付成功时，将积分返还到账户积分
     *          某个商品满足积分加倍，返他积分*N的数额
     *          退货完成时，将积分从用户当中扣除
     *
     *
     * ****************************************************************************************
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> doubleIntegral(Map<String, Object> params) {

        return null;
    }

    /**
     * 计算积分均摊
     * ****************************************************************************************
     * 积分平摊：在用户点击支付时
     *          进行积分平摊，将积分添加到订单明细中
     *          在用户进行退单时，将积分退换到账户中去
     *          根据每个名单明细的价格去按照百分比平摊价格
     *
     * ****************************************************************************************
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> avePoint(Map<String, Object> params) {
        //参数salePrice,num,prdDtlNo
        System.out.println(params);

        return params;
    }




    @Override
    public List<Map<String, Object>> findProvinceList() {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
        List<Map<String, Object>> findProvinceList = restTemplate.postForObject(HOST_URL + "/rest/findProvince.do",bodyMap, List.class);


        return findProvinceList;

    }

    @Override
    public List<Map<String, Object>> findCityList(String provinceId) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
        bodyMap.add("provinceId",provinceId);
        System.out.println(provinceId);
        List<Map<String, Object>> findProvinceList = restTemplate.postForObject(HOST_URL + "/rest/findCity.do",bodyMap, List.class);
        return findProvinceList;
    }


    @Override
    public List<Map<String,Object>>  findOrder(Map<String, Object> params) {
        return orderMapper.findOrder(params);
    }

    /**
     * 修改订单
     * @param params
     */
    @Override
    @Transactional
    public void updateOrderInfoForm(Map<String, Object> params) {
        System.out.println("添加订单状态表"+params);
        Map<String, Object> paramsState = new HashMap<>();
        paramsState.put("ordNo",params.get("ordNo"));
        paramsState.put("ordStsCd",params.get("ordStsCd"));
        /*订单状态更改表*/
        orderMapper.addOrderStateChange(paramsState);
        orderMapper.updateOrderInfo(params);
    }

    /**
     * 查询商品明细
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> findOrderDtlInfoList(Map<String, Object> params) {
        Integer currentPage = params.get("currentPage") == null ? 1:Integer.parseInt((String)params.get("currentPage"));
        //设置一页的个数
        PageHelper.startPage(currentPage, 5);
        List<Map<String,Object>> list = orderMapper.findOrderDtlInfo(params);
        //用PageInfo对结果进行包装
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list);
        return page;
    }
    /**
     * 修改订单状态
     * @param params
     */
    public void updaterdStsCd(@Param("params") Map<String, Object> params){
            orderMapper.updaterdStsCd(params);
    }

    /**
     * 修改订单明细
     * @param params
     */
    @Override
    public void updateOrderDtlInfo(Map<String, Object> params) {
        orderMapper.updateOrderDtlInfo(params);

    }
    /**
     * 通过订单明细 查询一条
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findOnlyOrdDtlInfo(Map<String, Object> params) {
        List<Map<String,Object>> list = orderMapper.findOrderDtlInfo(params);
        Map<String,Object> onlyOrdDtlInfo= list.get(0);
        return onlyOrdDtlInfo;
    }

    /**
     * 通过订单编号   查询明细中的isReturn
     * all
     * part
     * fail
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findOrdDtlIsReturn(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String,Object>> list = orderMapper.findOrdDtlIsReturn(params);
        int a=0;
        for (int i=0;i<list.size();i++){
            if("y".equals(list.get(i).get("isReturn"))){

                a=a+1;
            }
        }

        System.out.println("111");
        System.out.println(a);
        System.out.println(list.size());
        if(list.size()==a){
            result.put("result","all");
        }
        if(list.size() > a && a>0){
            result.put("result","part");
        }
        if(0==a){
            result.put("result","fail");
        }
        return result;
    }

    /**
     * 会员降级系统
     * 需要数据：
     * 会员编号（vip_no）
     * @param params
     */
    @Override
    public Map<String,Object> UpdateVipLevel(Map<String, Object> params) {

        //查询本月退货记录

        List<Map<String,Object>> list= orderMapper.findStsCd(params);
        for (int i=0;i<list.size();i++){
            Map<String,Object> osc=  list.get(i);
            int oc= (int) osc.get("oc");
            if(oc==200){
                num=num+1;
            }else {
                num=0;
            }
        }
        System.out.println(num);
        params.put("returnGoods",num);
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.setAll(params);
        Map<String,Object> result = restTemplate.postForObject(HOST_URL+"/rest/UpdateVipLevel.do",bodyMap,Map.class);

        return result;
    }

    /**
     * 订单取消（查询订单详情）
     * 需要数据：页面传来的订单明细编号
     * 回传数据：
     * 商品明细编号prd_dtl_no，仓库编号w_no，占用库存数occ_count，余额支付cash_amt，积分支付cpn_amt
     * 会员编号vip_no
     * **************************************************************************************
     * 商品返还（将商品占用数返还到它原本的仓库中）
     * 需要数据：
     * 商品明细编号prd_dtl_d，仓库编号w_no，占用库存数occ_count
     * 返回值：success或error
     * **************************************************************************************
     * 积分现金返还（余额支付与积分支付返回给用户）
     * 需要数据：
     * 余额支付cash_amt，积分支付cpn_amt，页面传来的用户id
     * 返回值：success或error
     * **************************************************************************************
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> CancellationOfOrder(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        if (200==Integer.parseInt((String) params.get("ordStsCd"))) {
        //try抓住本程序所有的异常，如有异常，向前台返回error
        try {
            //建立一个判断集
            boolean a = true;
            //用传来的订单编号去明细表中取到和次订单编号对应的订单明细编号
            List<Map<String, Object>> list = orderMapper.findOrdDtlNo(params);
            //因为有可能取到多个订单编号所以进行一个遍历，取到每一个订单明细编号，使得每个订单明细编号都进行一次操作
            for (int i = 0; i < list.size(); i++) {
                //每次进入循环是开始判断，如果！a跳出循环
                if (!a) {
                    break;
                }
                Map<String, Object> ordDtlNoMap = list.get(i);
                String ordDtlNo = (String) ordDtlNoMap.get("ordDtlNo");
                //取到订单明细编号后去进行一个select查找商品明细编号，仓库编号，占用仓库数，余额支付数，积分支付数。
                List<Map<String, Object>> list1 = orderMapper.findOrderIn(ordDtlNo);
                //因为有可能取到多组数值，每一组都要进行一次操作所以进行一个遍历
                for (int j = 0; j < list1.size(); j++) {
                    Map<String, Object> OrderIn = list1.get(j);
                    String prdDtlD = (String) OrderIn.get("prdDtlNo");
                    Double cashAmt = (Double) OrderIn.get("cashAmt");
                    Double cpnAmt = (Double) OrderIn.get("cpnAmt");
                    String wNo = (String) OrderIn.get("wNo");
                    int occCount = (int) OrderIn.get("occCount");
                    //拿到商品明细编号，仓库编号和占用库存数放到map集合里等待调用
                    MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
                    bodyMap.add("prdDtlD", prdDtlD);
                    bodyMap.add("wNo", wNo);
                    bodyMap.add("occCount", occCount);
                    RestTemplate restTemplate = new RestTemplate();
                    //调用商品返还接口，参数map，返回值success或error
                    Map<String, Object> result = restTemplate.postForObject(HOST_URL_PRD + "/rest/cancelPayWCount.do", bodyMap, Map.class);
                    //拿到使用积分数和使用现金数，从params中拿到前台传来的用户id，放到map1中等待调用
                    MultiValueMap<String, Object> bodyMap1 = new LinkedMultiValueMap<>();
                    String id = (String) params.get("vipId");
                    String vipNo = (String) params.get("vipNo");
                    bodyMap1.add("id", id);
                    bodyMap1.add("vipNo", vipNo);
                    bodyMap1.add("payMoney", cashAmt);
                    bodyMap1.add("payIntegral", cpnAmt);
                    bodyMap1.add("ordNo", params.get("ordNo"));
                    //调用积分现金返还接口，参数map1，返回值success或error
                    Map<String, Object> result1 = restTemplate.postForObject(HOST_URL2 + "/rest/returnOrUse.do", bodyMap1, Map.class);
                    //判断每次执行是否返还成功，若不成功，则!a 准备跳出
                    if ("success".equals(result.get("result")) && "success".equals(result1.get("result")) ) {
                        System.out.println("返还成功");
                    } else {
                        a = false;
                        break;
                    }
                }
            }
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, Object> bodyMap2 = new LinkedMultiValueMap<>();
            bodyMap2.add("ordNo", params.get("ordNo"));
            //调用查找积分返还接口
            Map<String, Object> returnIn= restTemplate.postForObject(HOST_URL2 + "/rest/findReturnIn.do", bodyMap2, Map.class);
            MultiValueMap<String, Object> bodyMap1 = new LinkedMultiValueMap<>();
            bodyMap1.add("id", params.get("vipId"));
            bodyMap1.add("vipNo", params.get("vipNo"));
            bodyMap1.add("payIntegral", returnIn.get("pointNum"));
            bodyMap1.add("ordNo", params.get("ordNo"));
            //调用积分现金返还接口，参数map1，返回值success或error
            Map<String, Object> result1 = restTemplate.postForObject(HOST_URL2 + "/rest/usePoint.do", bodyMap1, Map.class);
            params.put("ordStsCd",600);
            orderMapper.updaterdStsCd(params);
            orderMapper.addOrderStateChange(params);
            if ("success".equals(result1.get("result")) ) {
                System.out.println("返还成功");
            } else {
                a = false;
            }

            if (a) {
                resultMap.put("result", "success");
                System.out.println("取消成功");
            } else {
                resultMap.put("result", "error");
            }
            //如有异常，向前台返回error
        } catch (Exception e) {
            resultMap.put("result", "error");
        }
        }

        if (100==Integer.parseInt((String) params.get("ordStsCd"))) {
            try {

                params.put("ordStsCd",600);

                params.put("ordStsCd1",600);

                //建立一个判断集
                boolean a = true;
                //用传来的订单编号去明细表中取到和次订单编号对应的订单明细编号
                List<Map<String, Object>> list = orderMapper.findOrdDtlNo(params);
                //因为有可能取到多个订单编号所以进行一个遍历，取到每一个订单明细编号，使得每个订单明细编号都进行一次操作
                for (int b = 0; b < list.size(); b++) {
                    //每次进入循环是开始判断，如果！a跳出循环
                    if (!a) {
                        break;
                    }
                    Map<String, Object> ordDtlNoMap = list.get(b);
                    String ordDtlNo = (String) ordDtlNoMap.get("ordDtlNo");
                    //取到订单明细编号后去进行一个select查找商品明细编号，仓库编号，占用仓库数，余额支付数，积分支付数。
                    List<Map<String, Object>> list1 = orderMapper.findOrderIn(ordDtlNo);
                    //因为有可能取到多组数值，每一组都要进行一次操作所以进行一个遍历
                    for (int c = 0; c < list1.size(); c++) {
                        Map<String, Object> OrderIn = list1.get(c);
                        String prdDtlD = (String) OrderIn.get("prdDtlNo");
                        String wNo = (String) OrderIn.get("wNo");
                        int occCount = (int) OrderIn.get("occCount");
                        //拿到商品明细编号，仓库编号和占用库存数放到map集合里等待调用
                        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
                        bodyMap.add("prdDtlD", prdDtlD);
                        bodyMap.add("wNo", wNo);
                        bodyMap.add("occCount", occCount);
                        RestTemplate restTemplate = new RestTemplate();
                        //调用商品返还接口，参数map，返回值success或error
                        Map<String, Object> result = restTemplate.postForObject(HOST_URL1 + "/rest/cancelPayWCount.do", bodyMap, Map.class);
                        if ("success".equals(result.get("result"))) {
                            System.out.println("返还成功");
                        } else {
                            a = false;
                            break;
                        }
                    }
                }
                params.put("ordStsCd",600);

                orderMapper.updaterdStsCd(params);
                orderMapper.addOrderStateChange(params);

                if (a) {
                    resultMap.put("result", "success");
                    System.out.println("取消成功");
                } else {
                    resultMap.put("result", "error");
                }
                //如有异常，向前台返回error
            }catch (Exception e){
                e.printStackTrace();
                resultMap.put("result", "error");
            }


        }
        return resultMap;

    }

    /**
     * 添加订单关系表
     * @param params
     */
    @Override
    public Map<String, Object> addOrderW(Map<String, Object> params) {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> orderW = new HashMap<>();

        Map<String,Object> order = ( Map<String,Object>)JSONObject.parse((String) params.get("order"));
        JSONArray jsonArray =JSONArray.parseArray((String) params.get("ware"));
        try {
            if(!CollectionUtils.isEmpty(jsonArray)){
                for(int i=0 ;i<jsonArray.size();i++) {
                    orderW.put("ordNo",order.get("ordNo"));
                    orderW.put("ordDtlNo",order.get("ordDtlNo"));
                    orderW.put("prdNo",order.get("prdNo"));
                    orderW.put("prdDtlNo",order.get("prdDtlNo"));
                    JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                    JSONArray ja = (JSONArray)jsonObject.get("wareInfo");
                    for(int j=0;j<ja.size();j++) {
                        if((jsonObject.get("prdDtlNo")).equals(order.get("prdDtlNo"))){
                            JSONObject jobj = (JSONObject)ja.get(j);
                            orderW.put("wNo",jobj.get("wNo"));
                            orderW.put("num",jobj.get("num"));
                            int row = orderMapper.addOrderW(orderW);
                            if(row > 0){
                                result.put("result","success");
                            }
                            else {
                                result.put("result","failure");
                                break;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("result","failure");
        }
        return result;
    }

    /**
     * 查询订单仓储关系表并解除占用和库存出库
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findOrdWR(Map<String, Object> params) {
        //查询订单仓储关系表获取商品仓储信息
        List<Map<String, Object>> list = orderMapper.findOrdWR(params);
        Map<String,Object> result = new HashMap<>();
        if(!CollectionUtils.isEmpty(list)){
            //调用库存计算接口
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String,Object> bodyMap= new LinkedMultiValueMap<>();
            bodyMap.add("order",list);
            result = restTemplate.postForObject(HOST_URL_PRD+"/rest/completePay.do",bodyMap,Map.class);
        }
        return result;
    }

    /**
     * 添加订单状态表
     * @param params
     */
    @Override
    public void addOrderStateChange(Map<String, Object> params) {
        orderMapper.addOrderStateChange(params);
    }


    /**
     * 添加订单评论
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> addEvaluate(Map<String, Object> params) {
        System.out.println(params);
        Map<String,Object> result=new HashMap<>();
        String prdDtlNo= (String) params.get("prdDtlNo");
        if(null==prdDtlNo){
            result.put("result","error");
        }else{
            result.put("result","success");
            orderMapper.addEvaluate(params);
        }
        return result;
    }

    /**
     * 使用分页查询订单信息和订单详情
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> findOrderInfoAndDtl(Map<String, Object> params) {
        Integer currentPage = params.get("currentPage") == null ? 1:Integer.parseInt((String)params.get("currentPage"));
        System.out.println(currentPage+"团长是智障");
        PageHelper.startPage(currentPage, 5); /*每页显示的页数*/
        List<Map<String,Object>> list=orderMapper.findOrderInfoAndDtl(params);/*将订单信息和详细信息放入list集合中*/
        System.out.println(list+"狮子好");
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list); /*使用分页*/
        return page;
    }


    /**
     * 查询订单状态
     * @param ordNo
     * @return
     */
    @Override
    public Map<String, Object> findSts(String ordNo) {
        return orderMapper.findSts(ordNo);
    }

    /**
     * 查询订单评价
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> getPrdCom(Map<String, Object> params) {
        return orderMapper.getPrdCom(params);
    }

    /**
     * 根据ordNo查询订单的sale_sum，prd_count，prd_dtl_no
     * @param ordNo
     * @return
     */
    @Override
    public List<Map<String, Object>> findPrdDtl(String ordNo) {
        return orderMapper.findPrdDtl(ordNo);
    }
}
