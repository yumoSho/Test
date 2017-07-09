package com.glanway.jty.controller.admin.platform;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.common.Constants;
import com.glanway.jty.common.json.JSONUtil;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.entity.platform.FlowBanner;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.platform.FlowBannerService;
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
* @文件名: FlowBannerController.java
* @功能描述: 轮播图管理类控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/flowBanner")
public class FlowBannerController extends BaseController {


	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private FlowBannerService flowBannerService;

	/**
	 * 数据绑定
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}

	/**
	 * 轮播图管理列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		
		//从数据字典中获取注册平台信息
		List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
		modelMap.put("zcTypes", JSONUtil.getJSONString(dictionaries).toString());
		return "admin/flowBanner/index";
	}
	
	/**
	 * 异步请求节轮播图管理列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<FlowBanner> list(@Qualifier("tu.")Filters filters, Pageable pageable) {
		Page<FlowBanner> list = flowBannerService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}
	
	/** 
	* @功能描述: 进入轮播图管理添加页
	* @return       
	*/
	@RequestMapping("add")
	public String add(ModelMap modelMap) {
		List<Dictionary> dictionaries_plat = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
		List<Dictionary> dictionaries_page = dictionaryService.findBySuperDicCode(Constants.DT_FBYM);
		modelMap.put("dictionaries_plat", dictionaries_plat);
		modelMap.put("dictionaries_page", dictionaries_page);
		return "admin/flowBanner/add";
	}
	
	/** 
	* @功能描述: 添加轮播图管理信息
	* @param flowBanner
	* @return       
	*/
	@RequestMapping("save")
	public String save(FlowBanner flowBanner) {
		flowBannerService.save(flowBanner);
		return "redirect:/admin/flowBanner/index";
	}
	
	/** 
	* @功能描述: 进入编辑页面
	* @param id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable(value="id") Long id) {
        ModelAndView mav = new ModelAndView();
        FlowBanner flowBanner = flowBannerService.find(id);
        List<Dictionary> dictionaries_plat = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
		List<Dictionary> dictionaries_page = dictionaryService.findBySuperDicCode(Constants.DT_FBYM);

        mav.addObject("flowBanner", flowBanner);
        mav.addObject("dictionaries_plat", dictionaries_plat);
        mav.addObject("dictionaries_page", dictionaries_page);
//        mav.addObject("supportCategories", supportCategories);
        mav.setViewName("/admin/flowBanner/edit");
		return mav;
	}
	
	/** 
	* @功能描述: 修改轮播图管理信息
	* @param
	* @return       
	*/
	@RequestMapping("update")
	public String update(FlowBanner flowBanner) {
		Long userId = super.user.getId();
		flowBanner.setLastModifiedBy(userId);
		flowBannerService.update(flowBanner);
		return "redirect:/admin/flowBanner/index";
	}
	
	/**
	 * 删除轮播图管理
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Map<String, Object> delete(@RequestParam("id") Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();  
		boolean result = flowBannerService.batchDeleteFlowBanner(ids);
		map.put("success", result);
	    return map;
	}
	
//	/**
//	 * AJAX检查要添加或修改的记录是否已存在
//	 * @param flowBanner
//	 * @return
//	 */
//	@RequestMapping("checkIsPageNameAndPlatNameAndBankExists")
//	@ResponseBody
//	public Map<String, Boolean> checkIsExists(FlowBanner flowBanner){
//		return flowBannerService.checkIsPageNameAndPlatNameAndBankExists(flowBanner);
//	}

	
}
