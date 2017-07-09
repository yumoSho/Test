package com.glanway.jty.controller.admin.marketing;

import com.glanway.gone.spring.bind.PageableDefault;
import com.glanway.gone.spring.bind.domain.*;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.marketing.DiscountGoods;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.service.marketing.DiscountGoodsService;
import com.glanway.jty.service.product.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/** 
* @文件名: ActivityMgrController.java
* @功能描述: 活动管理类控制器
* @author LiuJC
* @date 2016年5月3日
*  
*/
@Controller
@RequestMapping("/admin/discount")
public class DiscountGoodsController extends BaseController{
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private DiscountGoodsService discountGoodsService;

	
	
	@RequestMapping("index")
	public String index(){
		return "admin/discount/index";
	}

	
	/** 
	* @功能描述:
	* @return       
	*/
	@RequestMapping("add")
	@Token(save = true)
	public String add(ModelMap model) {
		List<DiscountGoods> discountGoodses = discountGoodsService.findMany((Filters) null, Sort.create().add(Sort.Direction.ASC, "sort"));
		model.put("discountGoodses",discountGoodses);
		return "admin/discount/add";
	}




	@RequestMapping("save")
	@Token(remove = true)
	public String save(ActivityGoodses activityGoodses, RedirectAttributes redirectAttrs) {
		List<DiscountGoods> newGoods = Arrays.asList(null == activityGoodses.getDiscountGoods()?new DiscountGoods[0]:activityGoodses.getDiscountGoods());
		List<DiscountGoods> arrayList = new ArrayList<>(newGoods);
		discountGoodsService.saveOrUpdate(arrayList);
		redirectAttrs.addFlashAttribute(SUCCESS_MSG_KEY, "保存成功");
		return "redirect:/admin/discount/index";
	}


	/*不会了  spring mvc 不能直接接受数组对象  还得包到对象中去  奇怪*/
	public static class ActivityGoodses{
		private DiscountGoods[] discountGoods;

		public DiscountGoods[] getDiscountGoods() {
			return discountGoods;
		}

		public void setDiscountGoods(DiscountGoods[] discountGoods) {
			this.discountGoods = discountGoods;
		}
	}



	/**
	 * 分页过滤活动管理 for easyui
	 */
	@ResponseBody
	@RequestMapping("list")
	@PageableDefault(sort = {"createdDate-desc"})
	public Page<DiscountGoods> list(Filters filters, Pageable pageable) {
		return discountGoodsService.findPage(filters, pageable);
	}





	/**
	 * 删除活动
	 * @param ids 活动ID数组
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Message delete(@RequestParam("id") Long[] ids) {
		if(null == ids && 0==ids.length){
			return Message.fail("没有有效的活动");
		}
		discountGoodsService.delete(ids);
		return Message.success();
	}


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/*@RequestMapping("get-goodses")
	@ResponseBody
	public Page<Goods> getGoodses(Filters filters,Pageable pageable){
		return goodsService.findGoodsForActivityPage(filters,pageable);
	}*/
	
}
