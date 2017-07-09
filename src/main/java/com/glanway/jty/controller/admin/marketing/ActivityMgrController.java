package com.glanway.jty.controller.admin.marketing;

import com.glanway.gone.spring.bind.PageableDefault;
import com.glanway.gone.spring.bind.domain.*;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.marketing.ActivityGoods;
import com.glanway.jty.entity.marketing.ActivityMgr;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.service.marketing.ActivityMgrService;
import com.glanway.jty.service.product.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** 
* @文件名: ActivityMgrController.java
* @功能描述: 活动管理类控制器
* @author LiuJC
* @date 2016年5月3日
*  
*/
@Controller
@RequestMapping("/admin/activity")
public class ActivityMgrController extends BaseController{
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ActivityMgrService activityMgrService;
	/*TODO @Autowired
	private IndexingService indexingService;*/
	
	
	@RequestMapping("index")
	public String index(){
		return "admin/activity/index";
	}

	
	/** 
	* @功能描述: 进入活动添加页
	* @return       
	*/
	@RequestMapping("add")
	@Token(save = true)
	public String add(ModelMap model) {
		return "admin/activity/add";
	}

    /**
     *
     * <p>名称：</p>
     * <p>描述：校验名称是否有效</p>
     * @author：LiuJC
     * @param id
     * @param activityName
     */
    @ResponseBody
    @RequestMapping("check")
    public Boolean check(String id, String activityName) {
        Filters filters = Filters.create();

        if (StringUtils.hasText(id)) {
            filters.ne("id", id);
        }
        if (StringUtils.hasText(activityName)) {
            filters.eq("activityName", activityName);
        }
        return activityMgrService.count(filters) < 1;
    }


	@RequestMapping("save")
	@Token(remove = true)
	public String save(ActivityMgr activityMgr, RedirectAttributes redirectAttrs) {
		clean(activityMgr);
		if(null == activityMgr){
			redirectAttrs.addFlashAttribute(ERROR_MSG_KEY, "数据错误");
			return "redirect:/admin/activity/index";
		}
		activityMgrService.save(activityMgr);
		redirectAttrs.addFlashAttribute(SUCCESS_MSG_KEY, "保存成功");
		/*TODO indexingService.rebuildIndex();*/
		return "redirect:/admin/activity/index";
	}

	/***
	 *清理无用数据
	 * */
	public void clean(ActivityMgr activityMgr){
		List<ActivityGoods> activityGoodses = activityMgr.getActivityGoodses();
		if(CollectionUtils.isEmpty(activityGoodses)){
			activityMgr = null;
			return;
		}
		for (Iterator<ActivityGoods> iter = activityGoodses.iterator(); iter.hasNext();) {
			ActivityGoods next = iter.next();
			if(null == next || null == next.getGoods().getId()){
				iter.remove();
			}
		}
	}

	/**
	 * 分页过滤活动管理 for easyui
	 */
	@ResponseBody
	@RequestMapping("list")
	@PageableDefault(sort = {"createdDate-desc"})
	public Page<ActivityMgr> list(Filters filters, Pageable pageable) {
		return activityMgrService.findPage(filters, pageable);
	}


	@RequestMapping("edit/{id}")
	@Token(save = true)
	public String edit(@PathVariable("id") String id, Map<String, Object> model, RedirectAttributes redirectAttributes) {
		Filters filters = Filters.create().eq("tam.id", id);
		Sort sort = Sort.create().add(Sort.Direction.ASC, "sort");
		List<ActivityMgr> activityMgrs = activityMgrService.findMany(filters, sort);
		if(CollectionUtils.isEmpty(activityMgrs) || 1 != activityMgrs.size()){
			redirectAttributes.addFlashAttribute(ERROR_MSG_KEY, "数据有误");
			return "redirect:/admin/activity/index";
		}
		model.put("m", activityMgrs.get(0));
		return "admin/activity/edit";
	}


	@RequestMapping("update")
	@Token(remove = true)
	public String update(ActivityMgr activityMgr, RedirectAttributes redirectAttrs) {
		clean(activityMgr);
		if(null == activityMgr){
			redirectAttrs.addFlashAttribute(ERROR_MSG_KEY, "数据错误");
			return "redirect:/admin/activity/index";
		}
		activityMgrService.update(activityMgr);
		redirectAttrs.addFlashAttribute(SUCCESS_MSG_KEY, "更新成功");
		/*TODO indexingService.rebuildIndex();*/
		return "redirect:/admin/activity/index";
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
		activityMgrService.delete(ids);
		/*TODO  indexingService.rebuildIndex();*/
		return Message.success();
	}


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("get-goodses")
	@ResponseBody
	public Page<Goods> getGoodses(Filters filters,Pageable pageable){
		return goodsService.findGoodsList(filters,pageable);
	}


}
