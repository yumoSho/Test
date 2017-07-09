package com.glanway.jty.controller.activity;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.marketing.ActivityGoods;
import com.glanway.jty.entity.marketing.ActivityMgr;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.service.BaseService;
import com.glanway.jty.service.marketing.ActivityMgrService;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 首页
 */
@Controller

public class ActivityController extends BaseController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ActivityMgrService activityMgrService;


    @RequestMapping("/activity")
    public String index(@RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "8") int size,ModelMap modelMap,HttpServletRequest request,Long provinceId,HttpSession session) {
        Filters filters = Filters.create().lt("startDate", new Date()).gt("endDate", new Date());
        String sessionStringProvince = (String)session.getAttribute(Constants.COOKIE_STRING);
        boolean flag = (StringUtil.notEmpty(sessionStringProvince) && !"0".equals(sessionStringProvince));
        Long  sessionProvince= Long.valueOf( flag ?sessionStringProvince:"0");
        if( null != provinceId && 0 != provinceId ){
            filters.eq(BaseService.PROVINCE,provinceId);
        }else if(null != sessionProvince && 0 != sessionProvince){
            filters.eq(BaseService.PROVINCE,sessionProvince);
        }else{
            filters.eq(BaseService.PROVINCE,999);
        }
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setPageSize(size);
        Sort sort = pageRequest.getSort();
        sort.asc("sort");
        Page<ActivityMgr> activities = activityMgrService.findPage(filters, pageRequest);
        modelMap.put("activities", activities);
        return "pc/activity/activeList";
    }

    @RequestMapping("/activity/detail/{id}")
    public String activityDetail(@PathVariable("id") Long id,ModelMap modelMap,HttpServletRequest request,HttpSession session,Long provinceId) {
        String sessionStringProvince = (String)session.getAttribute(Constants.COOKIE_STRING);
        boolean flag = (StringUtil.notEmpty(sessionStringProvince) && !"0".equals(sessionStringProvince));
        Long  sessionProvince= Long.valueOf( flag ?sessionStringProvince:"0");
        final Long  finalProvinceId;
        if( null != provinceId && 0 != provinceId ){
            finalProvinceId  =provinceId;
        }else if(null != sessionProvince && 0 != sessionProvince){
            finalProvinceId = sessionProvince;
        }else{
            finalProvinceId = null;
        }

        ActivityMgr activityMgr = activityMgrService.findDetail(id,finalProvinceId);
        if(null != activityMgr){
            List<ActivityGoods> activityGoodses = activityMgr.getActivityGoodses();
            if(!CollectionUtils.isEmpty(activityGoodses)){
                for (ActivityGoods ag:activityGoodses ){
                    Goods goods = ag.getGoods();
                    ag.setActivity(activityMgr);
                    goods.setActivityGoods(ag);
                    goods.setPromotePrice(goods.getPrice().multiply(new BigDecimal(0.1)).multiply(activityMgr.getDiscount()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                }
            }
        }
        modelMap.put("activityMgr",activityMgr);
        return "pc/activity/activeProList";
    }
}



