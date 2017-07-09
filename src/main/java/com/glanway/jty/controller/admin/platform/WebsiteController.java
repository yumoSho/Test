/*
* Copyright (c) 2005, 2016  glanway.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.glanway.jty.controller.admin.platform;

import com.glanway.jty.annotation.Token;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.utils.PropertiesUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Properties;

/**
 * 网站设置Controller
 *  @author  tianxuan
 *  @Time     2016/4/5
 */

@Controller("adminWebsiteController")
@RequestMapping("/admin/website")
public class WebsiteController extends BaseController{
	private final static Logger LOG= Logger.getLogger(WebsiteController.class);
	private final static String PATH = "admin/website/";

	@Token(save = true)
	@RequestMapping("index")
	public String add(ModelMap map){
		Properties websiteConf = PropertiesUtil.getConfig(Constants.WEBSITE_CONF);
		map.put("website",websiteConf);
		return PATH + "add";
	}

	/**
	 * 添加网站信息保存到properties
	 * @param params
	 * @return
     */
	@Token(remove = true)
    @RequestMapping("save")
    public String save(@RequestParam Map params){
		Properties properties = new Properties();
		properties.putAll(params);
		PropertiesUtil.writeProperties(properties,Constants.WEBSITE_CONF,"平台设置");
        return "redirect:/" + PATH +"index";
    }
}
