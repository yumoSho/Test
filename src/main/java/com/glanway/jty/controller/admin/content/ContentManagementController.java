package com.glanway.jty.controller.admin.content;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.content.ContentManagement;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.service.content.ContentManagementService;
import com.glanway.jty.service.dictionary.DictionaryService;


/** 
* @文件名: ContentManagementController.java
* @功能描述: 内容管理管理类控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/contentManagement")
public class ContentManagementController extends BaseController{

	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private ContentManagementService contentManagementService;
	

	
	/**
	 * 内容管理列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		
//		List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
//		modelMap.put("advertisementTypes",JSONUtil.getJSONString(dictionaries).toString());
		return "admin/content/index";
	}
	
	/**
	 * 异步请求节内容管理信息列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<ContentManagement> list(@Qualifier("tu.")Filters filters, Pageable pageable) {
		
		
		Page<ContentManagement> list = contentManagementService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}
	
	/** 
	* @功能描述: 进入内容管理添加页
	* @return       
	*/
	@RequestMapping("add")
	public String add(ModelMap modelMap) {
		
		List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_NRGL);
	
		modelMap.put("dictionaries_pt", dictionaries);
		return "admin/content/add";
	}
	
	/** 
	* @功能描述: 添加内容管理信息
	* @param contentManagement
	* @return       
	*/
	@RequestMapping("save")
	public String save(ContentManagement contentManagement) {
		
//		if(null==_isEnable){//状态禁用
//			advertisement.setIsEnable(true);
//		}else{//状态启用
//			advertisement.setIsEnable(false);
//		}
//		link.setBankID(this.bankId);
		contentManagementService.save(contentManagement);
		return "redirect:/admin/contentManagement/index";
	}
	
	/** 
	* @功能描述: 进入编辑页面
	* @param id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable(value="id") Long id) {
        ModelAndView mav = new ModelAndView();
        ContentManagement contentManagement = contentManagementService.find(id);
        List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_NRGL);
        mav.addObject("dictionaries_pt", dictionaries);
        mav.addObject("contentManagement", contentManagement);
        mav.setViewName("/admin/content/edit");
		return mav;
	}
	
	/** 
	* @功能描述: 修改内容管理信息
	* @param contentManagement 内容管理信息
	* @return       
	*/
	@RequestMapping("update")
	public String update(ContentManagement contentManagement) {
//		if(null==_isEnable){//状态禁用
//			advertisement.setIsEnable(true);
//		}else{//状态启用
//			advertisement.setIsEnable(false);
//		}
//		link.setBankID(this.bankId);
		contentManagementService.update(contentManagement);
		return "redirect:/admin/contentManagement/index";
	}
	
	/**
	 * 删除内容管理
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Map<String, Object> delete(@RequestParam("id") Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();  
		boolean result = contentManagementService.batchDeleteLink(ids);
		map.put("success", result);
	    return map;
	}
	
	/**
	 * 验证除自己以外 是否有标题和银行相同的数据对象
	 * @param contentManagement
	 * @return
	 */
	@RequestMapping("checkIsTitleAndBankExists")
	@ResponseBody
	public Map<String, Boolean> checkIsExists(ContentManagement contentManagement){
//		long titleId,long bankId
		return contentManagementService.checkIsTitleAndBankExists(contentManagement);
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
