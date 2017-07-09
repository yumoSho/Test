package com.glanway.jty.controller.admin.content;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.content.CommonPro;
import com.glanway.jty.entity.content.SupportCategory;
import com.glanway.jty.entity.content.SupportContent;
import com.glanway.jty.service.content.CommonProService;
import com.glanway.jty.service.content.SupportCategoryService;
import com.glanway.jty.service.content.SupportContentService;
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
* @文件名: SupportContentController.java
* @功能描述: 帮助中心内容管理类控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/commonPro")
public class CommonProController extends BaseController{

	@Autowired
	private CommonProService commonProService;
	
	
	/**
	 * 帮助中心内容管理列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		

		return "admin/commonPro/index";
	}
	
	/**
	 * 异步请求节帮助中心内容管理信息列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<CommonPro> list(@Qualifier("tu.")Filters filters, Pageable pageable) {
		Page<CommonPro> list = commonProService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}
	
	/** 
	* @功能描述: 进入帮助中心内容管理添加页
	* @return       
	*/
	@RequestMapping("add")
	public String add(ModelMap modelMap) {
		return "admin/commonPro/add";
	}
	
	/** 
	* @功能描述: 添加帮助中心内容管理信息
	* @param
	* @return       
	*/
	@RequestMapping("save")
	public String save(CommonPro supportContent) {
		if(null == supportContent.getDisplay()){
			supportContent.setDisplay(false);
		}
		commonProService.save(supportContent);
		return "redirect:/admin/commonPro/index";
	}
	
	/** 
	* @功能描述: 进入编辑页面
	* @param id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable(value="id") Long id) {
        ModelAndView mav = new ModelAndView();
        CommonPro supportContent = commonProService.find(id);
        mav.addObject("supportContent", supportContent);
        mav.setViewName("/admin/commonPro/edit");
		return mav;
	}
	
	/** 
	* @功能描述: 修改帮助中心内容管理信息
	* @param
	* @return       
	*/
	@RequestMapping("update")
	public String update(CommonPro supportContent) {
		if(null == supportContent.getDisplay()){
			supportContent.setDisplay(false);
		}
		Long userId = super.user.getId();
		supportContent.setLastModifiedBy(userId);
		commonProService.update(supportContent);
		return "redirect:/admin/commonPro/index";
	}
	
	/**
	 * 删除帮助中心内容管理
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Map<String, Object> delete(@RequestParam("id") Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();  
		boolean result = commonProService.batchDeleteSupportContent(ids);
		map.put("success", result);
	    return map;
	}


	/**
	 * 日期格式化方法
	 * @param binder
     */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
