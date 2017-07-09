/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: jintianyuan
 * FileName: IndexController.java
 * PackageName: com.glanway.jty.controller.index
 * Date: 2016/8/1914:04
 **/
package com.glanway.jty.controller.flashSale;/**
 * Created by chao on 2016/8/19.
 */

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.marketing.DiscountGoods;
import com.glanway.jty.entity.platform.FlowBanner;
import com.glanway.jty.service.BaseService;
import com.glanway.jty.service.content.NewsService;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.marketing.CategoryGoodsCommendService;
import com.glanway.jty.service.marketing.DiscountGoodsService;
import com.glanway.jty.service.marketing.NewGoodsService;
import com.glanway.jty.service.platform.AdvertisementService;
import com.glanway.jty.service.platform.FlowBannerService;
import com.glanway.jty.service.product.CategoryService;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.utils.CacheUtil;
import com.glanway.jty.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>名称: FlashSaleController</p>
 * <p>说明: 限时抢购</p>
 * <p>修改记录：）</p>
 *
 * @author：SongZhe
 * @date：2016/8/1914:04
 * @version: 1.0
 */
@Controller
@RequestMapping("/mobile/timed-panic-buying")
public class MobileFlashSaleController extends BaseController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private FlowBannerService flowBannerService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private NewGoodsService newGoodsService ;
    @Autowired
    private DiscountGoodsService discountGoodsService;
    @Autowired
    private CategoryGoodsCommendService categoryGoodsCommendService;
    @Autowired
    private CategoryService categoryService ;

    /**
     * <p>名称：限时抢购</p>.
     * <p>描述：手机限时抢购</p>
     * @author：SongZhe
     * @date：2016/8/27 15:08
     * @param page
     * @param pageSize
     * @param modelMap
     * @param request
     * @param provinceId
     * @param session
     * @param type
     * @return
     */
    @RequestMapping("index")
    public String index(@RequestParam(defaultValue = "1")Integer page,@RequestParam(required=false, defaultValue="6")Integer pageSize,ModelMap modelMap,HttpServletRequest request,Long provinceId,HttpSession session,Integer type){
    	
    	String target = "mobile/saleProduct/doingNow";
    	String sessionStringProvince = (String)session.getAttribute(Constants.COOKIE_STRING);
        boolean flag = (StringUtil.notEmpty(sessionStringProvince) && !"0".equals(sessionStringProvince));

    	Long  sessionProvince= Long.valueOf( flag ?sessionStringProvince:"0");
    	
    	Date date = new Date();
        Filters filters = Filters.create();
        Sort sort = Sort.create().asc("sort");
      
        //正在进行
        filters = filters.lt("startDate", date).gt("endDate", date);
        
        //即将开始
        if(type != null && type == 1){
        	filters.clear();
        	filters.gt("startDate",date);
//        	date=DateUtils.getAfterDateByInt(date,4);
        	filters = filters.gt("startDate", date);//活动开始时间大于当前时间
        	target = "mobile/saleProduct/comingSoon";
        }
        
        
        PageRequest pageRequest = new PageRequest();
      
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        pageRequest.setSort(sort);
        if( null != provinceId && 0 != provinceId){
            filters.eq(BaseService.PROVINCE,provinceId);
        }else if(null != sessionProvince && 0 != sessionProvince){
            filters.eq(BaseService.PROVINCE,sessionProvince);
        }
//        pageRequest = new PageRequest(1 ,3,sort);
        Page<DiscountGoods> discountGoodses = discountGoodsService.findPage(filters, pageRequest);
        for (DiscountGoods discountGoodse : discountGoodses) {
            BigDecimal price = discountGoodse.getGoods().getPrice();
            BigDecimal discount = discountGoodse.getDiscount();
            discountGoodse.getGoods().setPromotePrice(price.multiply(discount.multiply(new BigDecimal(0.1))).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        modelMap.put("discountGoodses", discountGoodses);

        return target;
    }

    @RequestMapping("list")
    @ResponseBody
    public Page getList(HttpServletRequest request,@RequestParam(defaultValue = "1")Integer page,@RequestParam(required=false, defaultValue="6")Integer pageSize,HttpSession session,Integer type,Long provinceId){
        String sessionStringProvince = (String)session.getAttribute(Constants.COOKIE_STRING);
        boolean flag = (StringUtil.notEmpty(sessionStringProvince) && !"0".equals(sessionStringProvince));
        Long  sessionProvince= Long.valueOf( flag ?sessionStringProvince:"0");
        Date date = new Date();
        Filters filters = Filters.create();
        Sort sort = Sort.create().asc("sort");

        //正在进行
        filters = filters.lt("startDate", date).gt("endDate", date);

        //即将开始
        if(type != null && type == 1){
            filters.clear();
            filters.gt("startDate",date);
//        	date=DateUtils.getAfterDateByInt(date,4);
            filters = filters.gt("startDate", date);//活动开始时间大于当前时间
        }

        PageRequest pageRequest = new PageRequest(page,pageSize,sort);
        if( null != provinceId && 0 != provinceId){
            filters.eq(BaseService.PROVINCE,provinceId);
        }else if(null != sessionProvince && 0 != sessionProvince){
            filters.eq(BaseService.PROVINCE,sessionProvince);
        }
        Page<DiscountGoods> discountGoodses = discountGoodsService.findPage(filters, pageRequest);
        for (DiscountGoods discountGoodse : discountGoodses) {
            BigDecimal price = discountGoodse.getGoods().getPrice();
            BigDecimal discount = discountGoodse.getDiscount();
            discountGoodse.getGoods().setPromotePrice(price.multiply(discount.multiply(new BigDecimal(0.1))).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        return discountGoodses;
    }

   
}
