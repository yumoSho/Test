package com.glanway.jty.controller.admin.platform;

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
* @文件名: SeoSettingController.java
* @功能描述: SEO信息管理类控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/seoSetting")
public class SeoSettingController extends BaseController{


	
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
		
	/*	//从数据字典中获取注册平台信息
		List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
		modelMap.put("zcTypes",JSONUtil.getJSONString(dictionaries).toString());*/
		return "admin/seoSetting/index";
	}
	
	/**
	 * 异步请求节SEO信息管理列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<SeoSetting> list(@Qualifier("tu.")Filters filters, Pageable pageable) {
		Page<SeoSetting> list = seoSettingService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}
	
	/** 
	* @功能描述: 进入SEO信息管理添加页
	* @return       
	*/
	@RequestMapping("add")
	public String add(ModelMap modelMap) {
		List<Dictionary> dictionaries_plat = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
		List<Dictionary> dictionaries_page = dictionaryService.findBySuperDicCode(Constants.DT_YMMC);
		modelMap.put("dictionaries_plat", dictionaries_plat);
		modelMap.put("dictionaries_page", dictionaries_page);
		return "admin/seoSetting/add";
	}
	
	/** 
	* @功能描述: 添加SEO信息管理信息
	* @param
	* @return       
	*/
	@RequestMapping("save")
	public String save(SeoSetting seoSetting) {
		

		seoSettingService.save(seoSetting);
		return "redirect:/admin/seoSetting/index";
	}
	
	/** 
	* @功能描述: 进入编辑页面
	* @param id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable(value="id") Long id) {
        ModelAndView mav = new ModelAndView();
        SeoSetting seoSetting = seoSettingService.find(id);
        List<Dictionary> dictionaries_plat = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
		List<Dictionary> dictionaries_page = dictionaryService.findBySuperDicCode(Constants.DT_YMMC);

        mav.addObject("seoSetting", seoSetting);
        mav.addObject("dictionaries_plat", dictionaries_plat);
        mav.addObject("dictionaries_page", dictionaries_page);
//        mav.addObject("supportCategories", supportCategories);
        mav.setViewName("/admin/seoSetting/edit");
		return mav;
	}
	
	/** 
	* @功能描述: 修改SEO信息管理信息
	* @param
	* @return       
	*/
	@RequestMapping("update")
	public String update(SeoSetting seoSetting) {
		Long userId = super.user.getId();

		seoSetting.setLastModifiedBy(userId);
		seoSettingService.update(seoSetting);
		return "redirect:/admin/seoSetting/index";
	}
	
	/**
	 * 删除SEO信息管理
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Map<String, Object> delete(@RequestParam("id") Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();  
		boolean result = seoSettingService.batchDeleteSeoSetting(ids);
		map.put("success", result);
	    return map;
	}
	
	/**
	 * AJAX检查要添加或修改的记录是否已存在
	 * @param seoSetting
	 * @return
	 */
	@RequestMapping("checkIsPageNameAndPlatNameAndBankExists")
	@ResponseBody
	public Map<String, Boolean> checkIsExists(SeoSetting seoSetting){
		return seoSettingService.checkIsPageNameAndPlatNameAndBankExists(seoSetting);
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
