/*
* Copyright (c) 2005, 2016  glanway.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.glanway.jty.controller.admin.operations;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.operations.CouponTempl;
import com.glanway.jty.service.operations.CouponTemplService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 优惠券模板Controller
 *  @author  tianxuan
 *  @Time     2016/4/1
 */

@Controller
@RequestMapping("/admin/couponTempl")
public class CouponTemplController extends BaseController{
	private final static String PATH = "admin/couponTempl/";

	@Autowired
	private CouponTemplService couponTemplService;

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
	public Page<CouponTempl> list(Filters filters, Pageable pageable){
		filters.eq("deleted",0);
    	return couponTemplService.findPage(filters,  pageable);
	}

	/**
	 * 跳转到添加页面
	 * @return  页面
     */
	@Token(save = true)
	@RequestMapping("add")
	public ModelAndView add(){
		return new ModelAndView(PATH + "add");
	}

	/**
	 * 保存优惠券模板并生成后台用户
	 * @param 
	 * @return 到优惠券模板列表
     */
	@Token(remove = true)
    @RequestMapping("save")
    public String save(CouponTempl couponTempl) {
		couponTemplService.save(couponTempl);
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
		couponTemplService.delete(id);
		return ImmutableMap.<String, Object>of("success", true);
	}

	/**
	 * 跳转到编辑界面
	 * @param id
	 * @param modelMap
     * @return	页面
     */
	@Token(save = true)
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable(value="id") Long id,ModelMap modelMap){
		CouponTempl CouponTempl = couponTemplService.find(id);
		modelMap.put("couponTempl",CouponTempl);
		return PATH + "edit";
	}

	/**
	 * 更新优惠券模板
	 * @param CouponTempl
	 * @return	页面
     */
	@Token(remove = true)
	@RequestMapping("update")
	public String update(CouponTempl CouponTempl){
		couponTemplService.update(CouponTempl);
		return "redirect:/"+ PATH + "index";
	}
}
