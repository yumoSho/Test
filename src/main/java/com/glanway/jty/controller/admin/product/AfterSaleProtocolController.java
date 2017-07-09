package com.glanway.jty.controller.admin.product;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.common.Constants;
import com.glanway.jty.common.json.JSONUtil;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.entity.platform.SeoSetting;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.platform.SeoSettingService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
* @文件名: AfterSaleProtocolController.java
* @功能描述: 售后保障控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/afterSaleProtocol")
public class AfterSaleProtocolController extends BaseController{


	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private SeoSettingService seoSettingService;
	
	/**
	 * SEO信息管理列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		
		//从数据字典中获取注册平台信息
		Map<String,String> param = Maps.newConcurrentMap();
		param.put("dicCode",Constants.DT_SHBZ);
		Dictionary dictionary = dictionaryService.findOne(param);
		modelMap.put("dictionary",dictionary);
		return "admin/afterSaleProtocol/index";
	}


	@RequestMapping("update")
	public String update(Dictionary dictionary) {
		Long userId = super.user.getId();

		Map<String,String> param = Maps.newConcurrentMap();
		param.put("dicCode",Constants.DT_SHBZ);
		Dictionary _dictionary = dictionaryService.findOne(param);
		dictionary.setId(_dictionary.getId());
		dictionaryService.update(dictionary);
		return "redirect:/admin/afterSaleProtocol/index";
	}
	

	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
