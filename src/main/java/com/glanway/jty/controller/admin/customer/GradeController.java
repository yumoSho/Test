package com.glanway.jty.controller.admin.customer;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.content.News;
import com.glanway.jty.entity.content.NewsType;
import com.glanway.jty.entity.customer.Grade;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.service.content.NewsService;
import com.glanway.jty.service.content.NewsTypeService;
import com.glanway.jty.service.customer.GradeService;
import com.glanway.jty.service.product.CategoryService;
import com.glanway.jty.utils.JSONArrayUtil;
import com.glanway.jty.utils.JsonUtil;
import org.apache.ibatis.annotations.Param;
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
* @文件名: NewsController.java
* @功能描述: 资讯管理类控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/grade")
public class GradeController extends BaseController{
	@Autowired
	private GradeService gradeService;

	@Autowired
	private CategoryService categoryService;
	
	
	/**
	 * 资讯管理列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		

		return "admin/grade/index";
	}
	
	/**
	 * 异步请求节资讯管理信息列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<Grade> list(Filters filters, Pageable pageable) {
		Page<Grade> list = gradeService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}
	
	/** 
	* @功能描述: 进入资讯管理添加页
	* @return       
	*/
	@RequestMapping("add")
	public String add(ModelMap modelMap) {
		List<Category> categoryList =  categoryService.topCategory(null,null);
		modelMap.put("categoryList", JSONArrayUtil.stringify(categoryList));
		return "admin/grade/add";
	}
	
	/** 
	* @功能描述: 保存
	* @param
	* @return       
	*/
	@RequestMapping("save")
	public String save(Grade grade,GradeDetailVo gdv) {
		gradeService.mySave(grade,gdv);
		return "redirect:/admin/grade/index";
	}
	
	/** 
	* @功能描述: 进入编辑页面
	* @param id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable(value="id") Long id,ModelMap modelMap) {
		List<Category> categoryList =  categoryService.topCategory(null,null);
		modelMap.put("categoryList", JSONArrayUtil.stringify(categoryList));
		modelMap.put("categoryList2", categoryList);
		Grade grade = gradeService.find(id);
		modelMap.put("grade",grade);
   		return "/admin/grade/edit";
	}
	
	/** 
	* @功能描述: 修改资讯管理信息
	* @param
	* @return       
	*/
	@RequestMapping("update")
	public String update(Grade grade, GradeDetailVo gdv) {
		gradeService.myUpdate(grade,gdv);
		return "redirect:/admin/grade/index";
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
		 gradeService.delete(ids);
	    return Message.success();
	}
}
