/*
* Copyright (c) 2005, 2016  glanway.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.glanway.jty.controller.admin.operations;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.operations.Coupon;
import com.glanway.jty.entity.operations.CouponTempl;
import com.glanway.jty.service.operations.CouponService;
import com.glanway.jty.service.operations.CouponTemplService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 优惠券Controller
 *  @author  tianxuan
 *  @Time     2016/4/1
 */

@Controller
@RequestMapping("/admin/coupon")
public class CouponController extends BaseController{
	private final static String PATH = "admin/coupon/";

	@Autowired
	private CouponService couponService;


	@Autowired
	private CouponTemplService couponTemplService;


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"), true));
	}
	/**
	 * 跳转到优惠券模板列表页
	 * @return 页面
     */
	@RequestMapping("index")
	public String index(){
		return PATH + "index";
	}


	/**
	 * 列表页分页数据查询方法
	 * @param filters filters
	 * @param pageable pageable
     * @return 分页数据
     */
	@RequestMapping("list")
	@ResponseBody
	public Page<Coupon> list(Filters filters, Pageable pageable){
		filters.eq("deleted",0);
    	return couponService.findPage(filters,  pageable);
	}

	/**
	 * 跳转到添加页面
	 * @return  页面
     */
	@Token(save = true)
	@RequestMapping("add")
	public ModelAndView add(ModelMap map){
		List<CouponTempl> couponTemplList = couponTemplService.findMany(new Filters().eq("deleted",0), Sort.create());
		map.put("couponTemplList",couponTemplList);
		return new ModelAndView(PATH + "add");
	}

	/**
	 * 保存优惠券模板并生成后台用户
	 * @param 
	 * @return 到优惠券模板列表
     */
	@Token(remove = true)
    @RequestMapping("save")
    public String save(Coupon coupon,Long templateId) throws InvocationTargetException, IllegalAccessException {
		couponService.saveCoupon(coupon,templateId);
		return "redirect:/"+PATH + "index";
    }

	/**
	 * 删除店铺
	 * @param id 店铺id
	 * @return com.glanway.gone.spring.bind.domain.Message
     */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") Long[] id){
		couponService.delete(id);
		return ImmutableMap.<String, Object>of("success", true);
	}
}
