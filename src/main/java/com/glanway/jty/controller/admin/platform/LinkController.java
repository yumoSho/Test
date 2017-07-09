package com.glanway.jty.controller.admin.platform;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.platform.Link;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.platform.LinkService;
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
* @文件名: LinkController.java
* @功能描述: 友情链接管理类控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/link")
public class LinkController extends BaseController {
	
	@Autowired
	private LinkService linkService;
	@Autowired
    private DictionaryService dictionaryService;
	
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		
//		List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
//		modelMap.put("advertisementTypes",JSONUtil.getJSONString(dictionaries).toString());
		return "admin/link/index";
	}
	
	/**
	 * 异步请求节友情链接信息列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<Link> list(@Qualifier("tu.")Filters filters, Pageable pageable) {
		Page<Link> list = linkService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}
	
	/** 
	* @功能描述: 进入友情链接添加页
	* @return       
	*/
	@RequestMapping("add")
	public String add(ModelMap modelMap) {
		return "admin/link/add";
	}
	
	/** 
	* @功能描述: 添加友情链接信息
	* @param
	* @return       
	*/
	@RequestMapping("save")
	public String save(Link link) {
		linkService.save(link);
		return "redirect:/admin/link/index";
	}
	
	/** 
	* @功能描述: 进入编辑页面
	* @param id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable(value="id") Long id) {
        ModelAndView mav = new ModelAndView();
        Link  link = linkService.find(id);
        mav.addObject("link", link);
        mav.setViewName("/admin/link/edit");
		return mav;
	}
	
	/** 
	* @功能描述: 修改友情链接信息
	* @param
	* @return       
	*/
	@RequestMapping("update")
	public String update(Link link) {
		Long userId = super.user.getId();
		link.setLastModifiedBy(userId);
		linkService.update(link);
		return "redirect:/admin/link/index";
	}
	
	/**
	 * 删除友情链接
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Map<String, Object> delete(@RequestParam("id") Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();  
		boolean result = linkService.batchDeleteLink(ids);
		map.put("success", result);
	    return map;
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	
}
