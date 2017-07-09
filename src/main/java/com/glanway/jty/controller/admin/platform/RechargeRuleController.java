package com.glanway.jty.controller.admin.platform;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.platform.Link;
import com.glanway.jty.entity.platform.RechargeRule;
import com.glanway.jty.service.platform.RechargeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
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
@RequestMapping("/admin/rechargeRule")
public class RechargeRuleController extends BaseController {
	
	@Autowired
	private RechargeRuleService rechargeRuleService;
	
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		
//		List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
//		modelMap.put("advertisementTypes",JSONUtil.getJSONString(dictionaries).toString());
		return "admin/rechargeRule/index";
	}
	
	/**
	 * 异步请求节友情链接信息列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<RechargeRule> list(@Qualifier("tu.")Filters filters, Pageable pageable) {
		filters.eq("deleted",false);
		Page<RechargeRule> list = rechargeRuleService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}
	
	/** 
	* @功能描述: 进入友情链接添加页
	* @return       
	*/
	@RequestMapping("add")
	public String add(ModelMap modelMap) {
		return "admin/rechargeRule/add";
	}
	
	/** 
	* @功能描述: 添加友情链接信息
	* @param
	* @return       
	*/
	@RequestMapping("save")
	public String save(RechargeRule rechargeRule) {
		Integer money = rechargeRule.getMoney();
		Integer discount = rechargeRule.getDiscount();
		rechargeRule.setRtMoney(money * discount /100);
		rechargeRuleService.save(rechargeRule);
		return "redirect:/admin/rechargeRule/index";
	}
	
	/** 
	* @功能描述: 进入编辑页面
	* @param id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable(value="id") Long id) {
        ModelAndView mav = new ModelAndView();
		RechargeRule  rechargeRule = rechargeRuleService.find(id);
        mav.addObject("rechargeRule", rechargeRule);
        mav.setViewName("/admin/rechargeRule/edit");
		return mav;
	}
	
	/** 
	* @功能描述: 修改友情链接信息
	* @param
	* @return       
	*/
	@RequestMapping("update")
	public String update(RechargeRule rechargeRule) {
		Long userId = super.user.getId();
		rechargeRule.setLastModifiedBy(userId);
		rechargeRuleService.update(rechargeRule);
		return "redirect:/admin/rechargeRule/index";
	}
	
	/**
	 * 删除友情链接
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Message delete(@RequestParam("id") Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();  
		rechargeRuleService.delete(ids);
	    return Message.success();
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	
}
