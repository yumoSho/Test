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
import com.glanway.jty.common.Constants;
import com.glanway.jty.common.json.JSONUtil;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.entity.operations.Coupon;
import com.glanway.jty.entity.operations.CouponTempl;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.operations.CouponService;
import com.glanway.jty.service.operations.CouponTemplService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券发放Controller
 *  @author  tianxuan
 *  @Time     2016/4/1
 */

@Controller
@RequestMapping("/admin/couponGrant")
public class CouponGrantController extends BaseController{
	private final static String PATH = "admin/couponGrant/";

	@Autowired
	private CouponService couponService;


	@Autowired
	private CouponTemplService couponTemplService;

	@Autowired
	private DictionaryService dictionaryService;

	@Token(save = true)
	@RequestMapping("index")
	public ModelAndView index(ModelMap map){
		Map params = new HashMap();
		List<Coupon> coupons = couponService.findList(params);
		map.put("coupons",coupons);
		return new ModelAndView(PATH + "index");
	}

	/**
	 * 发放优惠券
	 * @param
	 * @return 到优惠券模板列表
	 */
	@Token(remove = true)
	@RequestMapping("save")
	public String save(Long templateId,Long storeId,String memberIds,String couponName) {
		couponService.grant(templateId,storeId,memberIds,couponName);
		return "redirect:/"+PATH + "index";
	}
}
