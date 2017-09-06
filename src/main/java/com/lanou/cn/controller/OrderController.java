package com.lanou.cn.controller;

import com.github.pagehelper.PageInfo;
import com.lanou.cn.service.BillService;
import com.lanou.cn.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 27/6/17.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	private static final String LOGIN_INFO = "loginInfo";
	@Resource
	private OrderService orderService;
	@Resource
	private BillService billService;

	/**
	 *  打开管理订单
	 * @param params
	 * @return
	 */
	@RequestMapping("orderPage")
	public ModelAndView orderPage(@RequestParam Map<String,Object> params){
		System.out.println(params);
		ModelAndView modelAndView = new ModelAndView();
		List<Map<String,Object>> cityList = new ArrayList<>();
		List<Map<String,Object>> provinceList=orderService.findProvinceList();

		//List<Map<String,Object>> resultType = cpnService.findCpnType();
		//modelAndView.addObject("resultType",resultType);
		PageInfo<Map<String, Object>> pageInfo = orderService.findAllOrderPageList(params);

		if (!StringUtils.isEmpty(params.get("provinceId"))){
			String fatherId= (String) params.get("provinceId");
			cityList=orderService.findCityList(fatherId);
		}

		modelAndView.addObject("cityList",cityList);
		modelAndView.addObject("provinceList",provinceList);
		modelAndView.addObject("page",pageInfo);
		modelAndView.addObject("list",pageInfo.getList());
		modelAndView.addObject("params",params);


		modelAndView.setViewName("/order/orderPage");
		return modelAndView;
	}
	/**
	 * 查询城市名
	 * @param provinceId
	 * @return
	 */
	@RequestMapping("findCityList")
	@ResponseBody
	public List<Map<String,Object>> findCityList(@RequestParam String provinceId){
		String fatherId= provinceId;
		List<Map<String,Object>> cityList = orderService.findCityList(fatherId);
		return cityList;
	}

	/**
	 * 存发票接口
	 * @param params
	 * @return
	 */
	@RequestMapping("addBillInfo")
	@ResponseBody
	public Map<String, Object> addBillInfo(@RequestParam Map<String, Object> params) {
		System.out.println("231123123");
		Map<String, Object> result = new HashMap<>();
		String billAmount = (String) params.get("billAmount");
		String billHeader = (String) params.get("billHeader");
		double billSum = Double.parseDouble(billAmount);
		System.out.println(billAmount);
		System.out.println("if之前");

		List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("list");
		for (int i = 0; i < list.size(); i++) {

			if (billSum >= 1500 || list.get(i).get("tpCd").equals("100") || list.get(i).get("tpCd").equals("110")) {
				System.out.println("进入if一");
				System.out.println(billHeader);
				//result.put("result","error");
				if (StringUtils.isEmpty(billHeader)) {
					System.out.println("为空");
					result.put("result", "error");
					break;
				} else {
					billService.addBillInfo(params);
					result.put("result", "success");
					break;
				}
			} else {
				System.out.println("不满足条件时");
				if (StringUtils.isEmpty(billHeader)) {
					result.put("result", "error");
					break;
				} else {
					System.out.println("111111");
					System.out.println(params);
					billService.addBillInfo(params);
					System.out.println("s22222222");
					result.put("result", "success");
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 进入修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping("updateOrderInfo")
	public ModelAndView updateOrderInfo(@RequestParam String id){
		ModelAndView modelAndView=new ModelAndView();
		//查询该id的一条信息
		Map<String,Object> params= new HashMap<>();
		params.put("id",id);
		List<Map<String,Object>> list= orderService.findOrder(params);

		List<Map<String,Object>> cityList = new ArrayList<>();
		List<Map<String,Object>> provinceList=orderService.findProvinceList();
		if (!StringUtils.isEmpty(list.get(0).get("addrP"))){
			String fatherId= list.get(0).get("addrP").toString();
			cityList=orderService.findCityList(fatherId);

			/*if (!StringUtils.isEmpty(params.get("provinceId"))){
				String fatherId= (String) params.get("provinceId");
				cityList=orderService.findCityList(fatherId);
			}
*/

		}
		System.out.println(cityList);
		modelAndView.addObject("params",list.get(0));
		modelAndView.addObject("cityList",cityList);
		modelAndView.addObject("provinceList",provinceList);
		modelAndView.setViewName("order/updateOrder");
		return modelAndView;
	}
	/**
	 * 修改订单form提交
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping("updateOrderInfoForm")
	@ResponseBody
	public Map<String,Object> updateOrderInfoForm(@RequestParam Map<String,Object> params, HttpServletRequest request ) {
		Object sessionObj = request.getSession().getAttribute(LOGIN_INFO);
		Map<String,Object> result=new HashMap<>();
		if(sessionObj != null){
			params.put("username",sessionObj);
		}
		try {
			orderService.updateOrderInfoForm(params);
			result.put("result","success");
		}
		catch (Exception e){
			result.put("result","failure");
		}
		return result;
	}

	/**
	 * 订单明细管理
	 * @param params
	 * @return
	 */
	@RequestMapping("orderDtlInfoPage")
	public ModelAndView orderDtlInfoPage(@RequestParam Map<String,Object> params){
		ModelAndView modelAndView = new ModelAndView();
		PageInfo<Map<String, Object>> pageInfo = orderService.findOrderDtlInfoList(params);
		modelAndView.addObject("page",pageInfo);
		modelAndView.addObject("list",pageInfo.getList());

		modelAndView.addObject("params",params);
		modelAndView.setViewName("/order/orderDtlInfoPage");
		return modelAndView;
	}
	/**
	 * 进入订单明细修改页面
	 * @param id
	 * @return
	 */

	@RequestMapping("updateOrderDtlInfo")
	public ModelAndView updateOrderDtlInfo(@RequestParam String id){
		ModelAndView modelAndView=new ModelAndView();
		//查询该id的一条信息
		Map<String,Object> params= new HashMap<>();
		params.put("id",id);
		PageInfo<Map<String, Object>> pageInfo = orderService.findOrderDtlInfoList(params);
		List<Map<String,Object>> list= pageInfo.getList();
		modelAndView.addObject("params",list.get(0));
		modelAndView.setViewName("order/updateOrderDtlInfo");
		return modelAndView;
	}

	@RequestMapping("updateOrderDtlInfoForm")
	@ResponseBody
	public Map<String,Object> updateOrderDtlInfoForm(@RequestParam Map<String,Object> params, HttpServletRequest request ) {
		Object sessionObj = request.getSession().getAttribute(LOGIN_INFO);
		Map<String,Object> result=new HashMap<>();
		if(sessionObj != null){
			params.put("username",sessionObj);
		}
		try {
			orderService.updateOrderDtlInfo(params);
			result.put("result","success");
		}
		catch (Exception e){
			result.put("result","failure");
		}

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
	 * 添加签收信息
	 * @param params
	 * @return
	 */

}
