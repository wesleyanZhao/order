package com.lanou.cn.controller;

import com.github.pagehelper.PageInfo;
import com.lanou.cn.service.BackService;
import com.lanou.cn.service.ExchangeService;
import com.lanou.cn.service.OrderService;
import com.lanou.cn.service.RestService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lanou on 2017/7/31.
 */
@RestController
@RequestMapping("/rest")
public class RESTController {

    static final String HOST_URL_PRD = "http://192.168.2.9:8888";
    static final String HOST_URL_ORD = "http://192.168.2.235:8088";

    @Resource
    private OrderService orderService;

    @Resource
    private BillService billService;

    @Resource
    private RestService restService;

    @Resource
    private BackService backService;

    @Resource
    private ExchangeService exchangeService;

    @RequestMapping("change")
    public Map<String,String> changeGoods(@RequestParam Map<String,String> params) throws ParseException {

        Map<String,String> map=new HashMap<>();
        String  prd_dtl_no=params.get("prdDtlNo");
        String  ord_no=params.get("ordNo");
        String  ord_dtl_no=params.get("ordDtlNo");
        String isReturn=exchangeService.getReturn(params);//根据明细表来获得是否可用
        if(isReturn.equals("n")){
            map.put("result","fail");
            return map;
        }

        Map<String,Object> map1= exchangeService.getDate(ord_no);//根据订单编号来获取下单的时候和可用时间
        Date date= (Date) map1.get("service_date");;
        SimpleDateFormat sdf=new SimpleDateFormat("yy-mm-dd");
        Date date3=new Date();
        if( date3.getTime()>date.getTime() ){
            map.put("result","fail");
            return map;
        }
        RestTemplate restTemplate =new RestTemplate();
        MultiValueMap<String,Object>  babyMap=new LinkedMultiValueMap<String,Object>();
        babyMap.add("prdDtlNo",prd_dtl_no);
        List<Map<String,Object>> list = restTemplate.postForObject(HOST_URL_PRD+"/rest/getWare.do", babyMap,List.class);
        if(list.isEmpty()){
            map.put("result","fail");
            return  map;

        }
        map.put("result","success");
        exchangeService.insertReturn(params);
        return map;
    }

    //参数：退货商品backPrd  换货商品barterPrd  会员id id
    @RequestMapping("barterDifferent")
    @ResponseBody
    public Map<String,Object> barterDif(@RequestParam Map<String,Object> params){
        System.out.println(params);
        Map<String,Object> result = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,Object> backMap = new LinkedMultiValueMap<>();
        backMap.add("prdDtlNo",params.get("barterPrdNo"));
        Map<String,Object> backResult = restTemplate.postForObject(HOST_URL_PRD+"/rest/getPrdDetailed.do",backMap,Map.class);//查询换的商品信息
        MultiValueMap<String,Object> orderMap = new LinkedMultiValueMap<>();
        //差订单里商品信息
        orderMap.add("prdDtlNo",params.get("backPrdNo"));
        orderMap.add("ordNo", params.get("ordNo"));
        Map<String,Object> orderResult = restTemplate.postForObject(HOST_URL_ORD+"/rest/findOnlyOrdDtlInfo.do",orderMap,Map.class);//退货商品信息
        String isReturn = (String) orderResult.get("isReturn");
        if("y".equals(isReturn)){
            result.put("product",backResult);
            result.put("order",orderResult);
            result.put("ordDtlNo",orderResult.get("ordDtlNo"));
            result.put("vipId",params.get("vipId"));
            result.put("vipNo",params.get("vipNo"));
            result.put("ordNo",params.get("ordNo"));
            try{
                result = restService.barter(result);
            }catch (Exception e){
                e.printStackTrace();
                result.put("result","failure");
            }
        }else{
            result.put("result","canNotReturn");
        }
        return result;
    }

    @RequestMapping("backSales")
    public Map<String,Object> backSales(@RequestParam Map<String,Object> params){
        System.out.println(params);
        Map<String,Object> map=backService.backThing(params);
        return map;
    }

    /**
     * 添加订单状态表
     * @param params
     */
    @RequestMapping("addOrderStateChange")
    public Map<String,Object> addOrderStateChange(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();
        try {
            orderService.addOrderStateChange(params);
            result.put("result","success");
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("result","failure");
        }
        return result;
    }

    /**
     * 添加订单接口
     */
    @RequestMapping("addOrder")
    public Map<String,Object> addOrder(@RequestParam Map<String,Object> params){
        try {
            orderService.addOrder(params);
            params.put("result","success");
        }
        catch (Exception e){
            params.put("result","failure");
        }
        return params;
    }


    /**
     * 添加订单详情接口
     */
    @RequestMapping("addOrderDtl")
    public Map<String,Object> addOrderDtl(@RequestParam Map<String,Object> params){
       try {
           orderService.addOrderDtl(params);
           params.put("result","success");
       }
       catch (Exception e){
           params.put("result","failure");
       }

        return params;
    }
    /**
     * 添加订单详情接口
     */
    @RequestMapping("addOrdDtl")
    public Map<String,Object> addOrdDtl(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();
        try {
            orderService.addOrderDtl(params);
            result.put("result","success");
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("result","failure");
        }
        return result;
    }

    /**
     * 查询订单编号
     * @param params
     * @return
     */
    @RequestMapping("selectOrderNo")
    public Map<String,Object> selectOrderNo(@RequestParam Map<String,Object> params){
          return orderService.selectOrderNo(params);
    }

    /**
     * 支付后订单完善
     * @param params
     */
    @RequestMapping("updateOrder")
    public Map<String,Object> updateOrder(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();
        try{
            orderService.updateOrder(params);
            result.put("result","success");
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("result","failure");
        }
        return result;
    }

    /**
     * 支付后订单明细完善
     * @param params
     */
    @RequestMapping("updateOrderDtl")
    public Map<String,Object> updateOrderDtl(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();
        try{
            orderService.updateOrderDtl(params);
            result.put("result","success");
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("result","failure");
        }
        return result;
    }



    /**
     * 订单取消（查询订单详情）
     * 需要数据：页面传来的订单明细编号
     * 回传数据：
     * 商品明细编号prd_dtl_d，仓库编号w_no，占用库存数occ_count，余额支付cash_amt，积分支付cpn_amt
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
    @RequestMapping("CancellationOfOrder")
    public Map<String, Object> CancellationOfOrder(@RequestParam Map<String, Object> params) {
        Map<String,Object> result = new HashMap<>();
        System.out.println(params);
        result=orderService.CancellationOfOrder(params);
        return result;
    }
    /**
     * 会员查询订单 分页
     * @param params
     * @return
     */
    @RequestMapping("findOrderInfo")
    public Map<String,Object> vipFindAllCpnInfo(@RequestParam Map<String,Object> params){
        Map<String,Object> cpnMap=new HashMap<>();
        PageInfo<Map<String, Object>> pageInfo = orderService.findAllOrderPageList(params);
        //返回值
        cpnMap.put("pageNum",pageInfo.getPageNum());
        cpnMap.put("total",pageInfo.getTotal());
        cpnMap.put("pages",pageInfo.getPages());
        cpnMap.put("list",pageInfo.getList());
        return cpnMap;
    }
    /**
     * 会员查询订单明细 分页
     * @param params
     * @return
     */
    @RequestMapping("findOrderDtlInfo")
    public Map<String,Object> findOrderDtlInfo(@RequestParam Map<String,Object> params){
        System.out.println(params);
        Map<String,Object> cpnMap=new HashMap<>();
        PageInfo<Map<String, Object>> pageInfo = orderService.findOrderDtlInfoList(params);
        //返回值
        cpnMap.put("pageNum",pageInfo.getPageNum());
        cpnMap.put("total",pageInfo.getTotal());
        cpnMap.put("pages",pageInfo.getPages());
        cpnMap.put("list",pageInfo.getList());
        return cpnMap;
    }
    /**
     *  查订单明细   返回一条
     * @param params
     * @return
     */
    @RequestMapping("findOnlyOrdDtlInfo")
    public Map<String,Object> findOnlyOrdDtlInfo(@RequestParam Map<String,Object> params){
        System.out.println(params);
        Map<String,Object> onlyOrdDtlInfo = orderService.findOnlyOrdDtlInfo(params);
        return onlyOrdDtlInfo;
    }

    /**
     *  修改订单状态
     * @param params
     * @return
     */
    @RequestMapping("updaterdStsCd")
    public Map<String,String> updaterdStsCd(@RequestParam Map<String,Object> params) {
        Map<String,String> result =new HashMap<>();
        try {
            orderService.updaterdStsCd(params);
            result.put("result","success");
        }catch (Exception e){
            e.printStackTrace();
            result.put("result","failure");
        }
        return result;
    }

    @RequestMapping("findOrdDtlIsReturn")
    public Map<String,Object> findOrdDtlIsReturn(@RequestParam Map<String,Object> params){
        System.out.println(params);
        Map<String,Object> findOrdDtlIsReturnResult = orderService.findOrdDtlIsReturn(params);
        return findOrdDtlIsReturnResult;
    }

    /**
     * 会员降级系统
     * 需要数据：
     * 会员编号（vip_no）
     * @param params
     * @return
     */
    @RequestMapping("UpdateVipLevel")
    public Map<String, Object> UpdateVipLevel(@RequestParam Map<String, Object> params) {
        Map<String,Object> result = new HashMap<>();
        System.out.println(params);
        result=orderService.UpdateVipLevel(params);
        return result;
    }

    /**
     * 添加订单支付记录表
     * @param params
     * @return
     */
    @RequestMapping("addOrdPayRec")
    public Map<String,Object> addOrdPayRec(@RequestParam Map<String,Object> params){
        return orderService.addOrdPayRec(params);
    }

    /**
     * 平摊积分
     * @param params
     * @return
     */
    @RequestMapping("avePoint")
    public Map<String,Object> avePoint(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();
        result = orderService.avePoint(params);
        return result;
    }

    /**
     * 计算积分加倍返回
     * @param params
     * @return
     */
    @RequestMapping("doubleIntegral")
    Map<String,Object> doubleIntegral(@RequestParam Map<String, Object> params){
        return null;
    }

    /**
     * 添加订单关系表
     * @param params
     * @return
     */
    @RequestMapping("addOrderW")
    public Map addOrderW(@RequestParam Map<String,Object> params){
        System.out.println("添加订单仓储关系表: "+params);
        return orderService.addOrderW(params);
    }

    /**
     * 添加签收 成功后
     * @param params
     * @return
     */
    @RequestMapping("addSignin")
    public Map<String,Object> addSignin(@RequestParam Map<String,Object> params){
        System.out.println("order+addSigin111111");
        Map<String,Object> result=new HashMap<>();
        billService.addSignin(params);
        System.out.println(params);
        result.put("result", "success");
        return result;
    }

    /**
     * 返回支付状态表的ord_sts
     * @param params
     * @return
     */
    @RequestMapping("findOrdsts")
    public List<Map<String,Object>>  findOrdsts(@RequestParam Map<String,Object> params){
        List<Map<String, Object>> result = billService.findOrdsts(params);
        return result;
    }

    /**
     * 根据ordNo,vipNo查询 prdDtlNo，spNo,ordSum,prdCount
     * @param params
     * @return
     */
    @RequestMapping("findPrdDtlNo")
    public List<Map<String,Object>> findPrdDtlNo(@RequestParam Map<String,Object> params){
        System.out.println("order+addSigin222222");
        System.out.println(params);
        List<Map<String,Object>> result=billService.findPrdDtlNo(params);
        System.out.println(result);
        return result;
    }

    /**
     * 添加发票信息
     * @param params
     * @return
     */
    @RequestMapping("addBillInfo")
    public Map<String, Object> addBillInfo(@RequestParam Map<String, Object> params) {

        Map<String, Object> result = new HashMap<>();
        try {
            billService.addBillInfo(params);
            result.put("result", "success");
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("result", "error");
        }
        return result;
    }

    /**
     * 查询订单仓储关系表并解除占用和库存出库
     * @param params
     * @return
     */
    @RequestMapping("findOrdWR")
    public Map<String,Object> findOrdWR(@RequestParam Map<String, Object> params){
        return orderService.findOrdWR(params);
    }

    /**
     * 添加订单商品评论
     * @param params
     * @return
     */
    @RequestMapping("addEvaluate")
    public  Map<String,Object> addEvaluate(@RequestParam Map<String,Object> params){
        System.out.println(params);
        Map<String,Object> resulu=orderService.addEvaluate(params);
        return resulu;
    }

    /**
     * 查询订单主信息和订单明细信息
     * @param params
     * @return
     */
    @RequestMapping("findOrderInfoAndDtl")
    public Map<String,Object> findOrderInfoAndDtl(@RequestParam Map<String,Object> params){
        PageInfo<Map<String,Object>> pageInfo=orderService.findOrderInfoAndDtl(params);
        Map<String,Object> result=new HashMap<>();

        result.put("pageNum",pageInfo.getPageNum());
        result.put("total",pageInfo.getTotal());
        result.put("pages",pageInfo.getPages());
        result.put("list",pageInfo.getList());

        return result;
    }

    /**
     * 查询订单状态
     * @param ordNo
     * @return
     */
    @RequestMapping("findSts")
    public Map<String,Object> findSts(@RequestParam String ordNo){
        return orderService.findSts(ordNo);
    }

    /**
     * 查询订单评价
     * @param params
     * @return
     */
    @RequestMapping("getPrdCom")
    public List<Map<String,Object>> getPrdCom(@RequestParam Map<String,Object> params){
        List<Map<String,Object>> result = orderService.getPrdCom(params);//参数：prdNo
        System.out.println(result);
        return result;
    }

    @RequestMapping("findPrdDtl")
    public List<Map<String,Object>> findPrdDtl(@RequestParam String ordNo){
        return orderService.findPrdDtl(ordNo);
    }

    /**
     * 更新订单状态为已签收
     * @param params
     * @return
     */
    @RequestMapping("findOrdStsCd")
    public Map<String,Object> findOrdStsCd(@RequestParam Map<String,Object> params){
        Map<String,Object> result=new HashMap<>();
        billService.updateOrdStsCd(params);
        result.put("result","success");
        return result;
    }
}

