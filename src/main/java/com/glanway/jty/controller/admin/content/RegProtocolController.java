/*
* Copyright (c) 2005, 2016  glanway.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.glanway.jty.controller.admin.content;

import com.glanway.jty.annotation.Token;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.utils.PropertiesUtil;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Properties;

/**
 * 协议Controller
 *  @author  tianxuan
 *  @Time     2016/4/5
 */
@Controller
@RequestMapping("/admin/regProtocol")
public class RegProtocolController extends BaseController{
	private final static String PATH = "admin/regProtocol/";

	@Autowired
	private DictionaryService dictionaryService;

	@Token(save = true)
	@RequestMapping("index")
	public String add(ModelMap modelMap){
//		Properties conf = PropertiesUtil.getConfig(Constants.REG_CONF);
//		map.put("conf",conf);

		Map<String,String> param = Maps.newConcurrentMap();
		param.put("dicCode","DT_YHZCXY");
		Dictionary dictionary = dictionaryService.findOne(param);
		modelMap.put("dictionary",dictionary);
		return PATH + "add";
	}

	/**
	 * 添加网站信息保存到properties
	 * @param params
	 * @return
     */
	@Token(remove = true)
    @RequestMapping("save")
    public String save(@RequestParam Map params,Dictionary dictionary){
		Map<String,String> param = Maps.newConcurrentMap();
		param.put("dicCode","DT_YHZCXY");
		Dictionary _dictionary = dictionaryService.findOne(param);
		dictionary.setId(_dictionary.getId());
		dictionaryService.update(dictionary);
        return "redirect:/" + PATH +"index";
    }
}
