package com.glanway.jty.controller.admin.marketing;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.marketing.NewGoods;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.service.marketing.NewGoodsService;
import com.glanway.jty.service.product.GoodsService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 爆品推荐管理
 */
@Controller
@RequestMapping("/admin/newgoods")
public class NewGoodsController extends BaseController {
	

    @Autowired
    private NewGoodsService newGoodsService;

	@Autowired
	private GoodsService goodsService;
	@RequestMapping("index")
	public String index(){
		return "admin/newGoods/index";
	}
	
	@RequestMapping("list")
    @ResponseBody
    public Page<NewGoods> list(Filters filters,Pageable pageable){
		Page<NewGoods> newGoodses = newGoodsService.findPage(filters, pageable);
		return newGoodses;
    }
	
	@RequestMapping("save")
	@Token(save = false)
	public String save(NewGoodses newGoodses){
        List<NewGoods> newGoods = Arrays.asList(null==newGoodses.getNewGoods()?new NewGoods[0]:newGoodses.getNewGoods());
		clean(newGoods);
		List<NewGoods> arrayList = new ArrayList<>(newGoods);
		newGoodsService.saveOrUpdate(arrayList);
        return "redirect:/admin/newgoods/index";
	}
	
	@RequestMapping("edit")
	@Token(save = true)
	public String edit( ModelMap map){
		List<NewGoods> newGoodses = newGoodsService.findMany((Filters) null, Sort.create().add(Sort.Direction.ASC, "sort"));
        map.put("newGoods",newGoodses);
        return "admin/newGoods/add";
	}

	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") Long[] ids){
        newGoodsService.delete(ids);
		return ImmutableMap.<String, Object>of("success", true);
	}


	/*不会了  spring mvc 不能直接接受数组对象  还得包到对象中去  奇怪*/
	public static class NewGoodses{
		private NewGoods[] newGoods;

		public NewGoods[] getNewGoods() {
			return newGoods;
		}

		public void setNewGoods(NewGoods[] newGoods) {
			this.newGoods = newGoods;
		}
	}

	public void clean(List<NewGoods> newGoods){
		for (Iterator<NewGoods> iter = newGoods.iterator(); iter.hasNext();) {
			NewGoods next = iter.next();
			if(null == next || null == next.getGoods() || null ==next.getSort() ){
				iter.remove();
			}
		}
	}

	@RequestMapping("get-goodses")
	@ResponseBody
	public Page<Goods> getGoodses(Filters filters,Pageable pageable){
		Page<Goods> goodses = goodsService.findGoodsList(filters,pageable);
		return goodses;
	}
	
}
