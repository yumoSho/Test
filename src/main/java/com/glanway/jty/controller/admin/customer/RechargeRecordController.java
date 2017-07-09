package com.glanway.jty.controller.admin.customer;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.Grade;
import com.glanway.jty.entity.customer.RechargeRecord;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.service.customer.GradeService;
import com.glanway.jty.service.customer.RechargeRecordService;
import com.glanway.jty.service.product.CategoryService;
import com.glanway.jty.utils.JSONArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
* @文件名: NewsController.java
* @功能描述: 资讯管理类控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/rechargeRecord")
public class RechargeRecordController extends BaseController{
	@Autowired
	private RechargeRecordService rechargeRecordService;

	
	
	/**
	 * 资讯管理列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		return "admin/rechargeRecord/index";
	}
	
	/**
	 * 异步请求节资讯管理信息列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<RechargeRecord> list(Filters filters, Pageable pageable) {
		Page<RechargeRecord> list = rechargeRecordService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}

	
	/**
	 * 删除资讯管理
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Message delete(@RequestParam("id") Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		rechargeRecordService.delete(ids);
	    return Message.success();
	}
}
