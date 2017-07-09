/*
* Copyright (c) 2005, 2016  glanway.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.glanway.jty.controller.admin.system;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.system.OperationLog;
import com.glanway.jty.service.system.OperationLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* </b>OperationLog Controller
*  @author  generator
*  @Time     2016-04-14
*  @version 1.0
*/

@Controller("adminOperationLogController")
@RequestMapping("/admin/operationLog")
public class OperationLogController extends BaseController{
	private final static String PATH = "admin/operationLog/";
	private  static Log logger= LogFactory.getLog(OperationLogController.class);
	@Autowired(required=false) //自动注入
	private OperationLogService operationLogService;

	@RequestMapping("index")
	public String index(){
		return PATH + "index";
	}




	@RequestMapping("list")
	@ResponseBody
	public Page<OperationLog> list(Filters filters,Pageable pageable){
		filters.eq("operateResult",OperationLog.SUCCESS);
    	return operationLogService.findPage(filters,  pageable);
	}




}
