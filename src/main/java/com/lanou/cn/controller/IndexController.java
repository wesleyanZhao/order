package com.lanou.cn.controller;

import com.github.pagehelper.PageInfo;
import com.lanou.cn.service.BaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by admin on 27/6/17.
 */
@Controller
@RequestMapping("/index")
public class IndexController {

	@Resource
	private BaseService baseService;

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("home")
	public ModelAndView home(String loginUser) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("base/home");
		Map<String,Object> map = baseService.home(loginUser);
		modelAndView.addObject("result",map.get("result"));
		modelAndView.addObject("loginUser",map.get("loginUser"));
		return modelAndView;
	}

	/**
	 * 分页例子
	 * @return
	 */
	@RequestMapping("mainPage")
	public ModelAndView mainPage(@RequestParam Map<String,Object> params){
		ModelAndView modelAndView = new ModelAndView();
		// 后期需要优化
		PageInfo<Map<String, Object>> pageInfo = baseService.findAllUsersPageList(params);
		modelAndView.addObject("page",pageInfo);
		modelAndView.addObject("list",pageInfo.getList());
		modelAndView.setViewName("/base/mainPage");
		return modelAndView;
	}


}
