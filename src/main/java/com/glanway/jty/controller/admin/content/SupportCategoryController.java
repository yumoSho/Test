package com.glanway.jty.controller.admin.content;


import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.content.SupportCategory;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.service.content.SupportCategoryService;
import com.glanway.jty.service.dictionary.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
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
* @文件名: SupportCategoryController.java
* @功能描述: 帮助中心类别管理类控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/supportCategory")
public class SupportCategoryController extends BaseController {

	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private SupportCategoryService supportCategoryService;
	
	/**
	 * 帮助中心类别管理列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		
//		List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
//		modelMap.put("advertisementTypes",JSONUtil.getJSONString(dictionaries).toString());
		return "admin/supportCategory/index";
	}
	
	/**
	 * 异步请求节帮助中心类别管理信息列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<SupportCategory> list(Filters filters, Pageable pageable) {
		Page<SupportCategory> list = supportCategoryService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}
	
	/** 
	* @功能描述: 进入帮助中心类别管理添加页
	* @return       
	*/
	@RequestMapping("add/{id}")
	public String add(@PathVariable(value="id") Long id,ModelMap modelMap) {
		

//		List<SupportCategory> supportCategories = supportCategoryService.findAll();
		SupportCategory supportCategory = supportCategoryService.find(id);
//		List<Bank> banks = bankService.findAllByOnLine();
//		modelMap.put("banks", banks);
		modelMap.put("supportCategory", supportCategory);
//		modelMap.put("dictionaries_pt", dictionaries);
		return "admin/supportCategory/add";
	}
	
	/** 
	* @功能描述: 进入帮助中心类别管理添加页
	* @return       
	*/
	@RequestMapping("addParent")
	public String addParent(ModelMap modelMap) {
		List<SupportCategory> supportCategories = supportCategoryService.findAll();
		modelMap.put("supportCategories", supportCategories);
		return "admin/supportCategory/addParent";
	}
	
	/** 
	* @功能描述: 添加帮助中心类别管理信息
	* @param supportCategory
	* @return       
	*/
	@RequestMapping("save")
	public String save(SupportCategory supportCategory) {
		
//		if(null==_isEnable){//状态禁用
//			advertisement.setIsEnable(true);
//		}else{//状态启用
//			advertisement.setIsEnable(false);
//		}
//		link.setBankID(this.bankId);
		supportCategoryService.save(supportCategory);
		return "redirect:/admin/supportCategory/index";
	}
	
	/** 
	* @功能描述: 进入编辑页面
	* @param id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable(value="id") Long id) {
        ModelAndView mav = new ModelAndView();
        SupportCategory supportCategory = supportCategoryService.find(id);
        List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_NRGL);
        mav.addObject("dictionaries_pt", dictionaries);
        mav.addObject("supportCategory", supportCategory);
        mav.setViewName("/admin/supportCategory/edit");
		return mav;
	}
	
	/** 
	* @功能描述: 修改帮助中心类别管理信息
	* @param supportCategory 帮助中心类别管理信息
	* @return       
	*/
	@RequestMapping("update")
	public String update(SupportCategory supportCategory) {
		Long userId = super.user.getId();
		supportCategory.setLastModifiedBy(userId);
		supportCategoryService.update(supportCategory);
		return "redirect:/admin/supportCategory/index";
	}
	
	/**
	 * 删除帮助中心类别管理
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Message delete(@RequestParam("id") Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		 supportCategoryService.batchDeleteSupportCategory(ids);
	    return Message.success();
	}
	
	/**
	 * 通过银行ID查询其对应的所有帮助分类
	 * @param
	 * @return
	 */
	@ResponseBody
    @RequestMapping("ajaxListSupportCategoryByBankId")
    public List<SupportCategory> ajaxListSupportCategoryByBankId(){
       
        return supportCategoryService.findAllByBankId();
    }
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
    @RequestMapping("subSupportCategory")
	public List<SupportCategory> subSupportCategory(Long id){
		
		SupportCategory category = new SupportCategory();
		SupportCategory parent = new SupportCategory();
		parent.setId(id);
		category.setParent(parent);
		return supportCategoryService.findByParentId(category);
	}
}
