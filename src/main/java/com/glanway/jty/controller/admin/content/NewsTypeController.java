package com.glanway.jty.controller.admin.content;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.content.News;
import com.glanway.jty.entity.content.NewsType;
import com.glanway.jty.service.content.NewsService;
import com.glanway.jty.service.content.NewsTypeService;
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
import java.util.Map;


/** 
* @文件名: NewsController.java
* @功能描述: 资讯分类管理类控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/newsType")
public class NewsTypeController extends BaseController{


	
	@Autowired
	private NewsTypeService newsTypeService;

	
	/**
	 * 资讯管理列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		

		return "admin/newsType/index";
	}
	
	/**
	 * 异步请求节资讯管理信息列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<NewsType> list(@Qualifier("tu.")Filters filters, Pageable pageable) {
		Page<NewsType> list = newsTypeService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}
	
	/** 
	* @功能描述: 进入资讯管理添加页
	* @return       
	*/
	@RequestMapping("add")
	public String add(ModelMap modelMap) {
		return "admin/newsType/add";
	}
	
	/** 
	* @功能描述: 添加资讯管理信息
	* @param
	* @return       
	*/
	@RequestMapping("save")
	public String save(NewsType newsType) {
		newsTypeService.save(newsType);
		return "redirect:/admin/newsType/index";
	}
	
	/** 
	* @功能描述: 进入编辑页面
	* @param id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable(value="id") Long id) {
        ModelAndView mav = new ModelAndView();
        NewsType newsType = newsTypeService.find(id);
        mav.addObject("newsType", newsType);
        mav.setViewName("/admin/newsType/edit");
		return mav;
	}
	
	/** 
	* @功能描述: 修改资讯管理信息
	* @param
	* @return       
	*/
	@RequestMapping("update")
	public String update(NewsType newsType) {
		newsTypeService.update(newsType);
		return "redirect:/admin/newsType/index";
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
		newsTypeService.myDelete(ids);
		return Message.success();
	}
	
	/**
	 * 验证除自己以外 是否有标题和银行相同的数据对象
	 * @param
	 * @return
	 */

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
