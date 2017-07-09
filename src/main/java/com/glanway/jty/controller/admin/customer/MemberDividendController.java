package com.glanway.jty.controller.admin.customer;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.Grade;
import com.glanway.jty.entity.customer.MemberDividend;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.service.customer.GradeService;
import com.glanway.jty.service.customer.MemberDividendService;
import com.glanway.jty.service.product.CategoryService;
import com.glanway.jty.utils.JSONArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/admin/memberDividend")
public class MemberDividendController extends BaseController{
	@Autowired
	private MemberDividendService memberDividendService;
	@Autowired
	private CategoryService categoryService;

	/**
	 * 资讯管理列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		List<Category> categoryList =  categoryService.topCategory(null,null);
		modelMap.put("categoryList", JSONArrayUtil.stringify(categoryList));
		modelMap.put("categoryList2", categoryList);
		List<MemberDividend> mdList = memberDividendService.findAll();
		modelMap.put("mdList", mdList);
		return "admin/memberDividend/index";
	}

	/** 
	* @功能描述: 保存
	* @param
	* @return       
	*/
	@RequestMapping("save")
	public String save(GradeDetailVo gdv) {
		memberDividendService.mySave(gdv);
		return "redirect:/admin/memberDividend/index";
	}
}
