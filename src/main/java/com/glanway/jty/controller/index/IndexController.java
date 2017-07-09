/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: jintianyuan
 * FileName: IndexController.java
 * PackageName: com.glanway.jty.controller.index
 * Date: 2016/8/1914:04
 **/
package com.glanway.jty.controller.index;/**
 * Created by chao on 2016/8/19.
 */

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.controller.admin.content.NewsController;
import com.glanway.jty.entity.content.ContentManagement;
import com.glanway.jty.entity.content.News;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.entity.marketing.CategoryGoodsCommend;
import com.glanway.jty.entity.marketing.DiscountGoods;
import com.glanway.jty.entity.marketing.NewGoods;
import com.glanway.jty.entity.platform.Advertisement;
import com.glanway.jty.entity.platform.FlowBanner;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.Label;
import com.glanway.jty.service.BaseService;
import com.glanway.jty.service.content.ContentManagementService;
import com.glanway.jty.service.content.NewsService;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.marketing.CategoryGoodsCommendService;
import com.glanway.jty.service.marketing.DiscountGoodsService;
import com.glanway.jty.service.marketing.NewGoodsService;
import com.glanway.jty.service.platform.AdvertisementService;
import com.glanway.jty.service.platform.FlowBannerService;
import com.glanway.jty.service.product.CategoryService;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.service.product.LabelService;
import com.glanway.jty.service.product.ProductService;
import com.glanway.jty.utils.CacheUtil;
import com.glanway.jty.utils.MemberUtil;
import com.glanway.jty.utils.StringUtil;
import com.glanway.jty.utils.UserAgent;
import com.google.common.collect.Maps;

import org.ponly.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.Cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>名称: IndexController</p>
 * <p>说明: PC端首页</p>
 * <p>修改记录：（2016/8/19 14:05 - LiuJC - 创建）</p>
 *
 * @author：tianxuan
 * @date：2016/8/1914:04
 * @version: 1.0
 */
@Controller
public class IndexController extends BaseController {
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
    @Autowired
    private ContentManagementService contentManagementService ;
    @Autowired
    private LabelService labelService;
    @Autowired
    private ProductService productService;
    /**
     * <p>名称：index</p>.
     * <p>描述：PC 首页数据绑定</p>
     * @author：LiuJC
     * @date：2016/8/19 14:08
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/")
    public String index(ModelMap modelMap,HttpServletRequest request,Long provinceId,HttpSession session){
        /*List<HatProvince> allProvince = (List<HatProvince>)cacheUtil.getCacheValue(Constants.ALL_PROVINCE);*/
        UserAgent ua = UserAgent.parse(request);
        Date date = new Date();
        /*************banenr****************/
        String dicCode;
        List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_YHLX);
        Filters labelFilters = Filters.create().eq("disabled", 0);
        List<Label> labels = labelService.findMany(labelFilters, (Pageable) null);
        Long dicId;
        if(ua.isMobile()){//手机
            dicId = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_ZCPT,Constants.DT_ZCPT_MOBILE).getId();
            return "redirect:/mobile/";
        }else{//pc
            dicId = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_ZCPT,Constants.DT_ZCPT_PC).getId();
        }
        Long dicPageId = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_FBYM,Constants.DT_FBYM_INDEX).getId();//首页

        Filters filters = Filters.create().eq("isShow", 1).eq("isShow",1)
                .eq("dictionaryPageId",dicPageId).eq("dictionaryPlatformId",dicId).lt("beginDate", date).gt("endDate",date);
        Sort sort = Sort.create().desc("sortNum");
        List<FlowBanner> flowBanners = flowBannerService.findMany(filters,sort);
        modelMap.put("flowBanners", flowBanners);
        /**************资讯****************/
        filters.clear();
        filters = Filters.create().eq("isHot", 1).eq("isShow",1);
        sort.clear();
        sort.desc("sortNum");
        PageRequest  pageRequest = new PageRequest(1,7,sort);
        List<News> newses = newsService.findMany(filters, pageRequest);
        modelMap.put("newses",newses);
        /**************优惠劵下面的广告****************/
        Dictionary dic_ad = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_GGPOS, Constants.DT_GGPOS_ITOPBANNER);
        filters.clear();
        sort.clear();
        filters.eq("pos", dic_ad.getDicCode()).eq("statusType",1).eq("deviceType",1).eq("deleted",0);
        sort.desc("sortNum");
        pageRequest = new PageRequest(1,1,sort);
        List<Advertisement> advertisement = advertisementService.findMany(filters, pageRequest);
        if(!CollectionUtils.isEmpty(advertisement)){
            modelMap.put("advertisement",advertisement.get(0));
        }
        /****************banner 下面的广告*****************/
        Dictionary dic_ads = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_GGPOS, Constants.DT_GGPOS_IMIDDLE);
        filters.clear();
        sort.clear();
        filters.eq("pos", dic_ads.getDicCode()).eq("statusType",1).eq("deviceType",1).eq("deleted",0);
        sort.desc("sortNum");
        pageRequest = new PageRequest(1,4,sort);
        List<Advertisement> advertisements = advertisementService.findMany(filters, pageRequest);
        modelMap.put("advertisements",advertisements);
        /**************爆品推荐****************/
        filters.clear();
        sort.clear();
        String sessionStringProvince = (String)session.getAttribute(Constants.COOKIE_STRING);
        boolean flag = (StringUtil.notEmpty(sessionStringProvince) && !"0".equals(sessionStringProvince));
        Long  sessionProvince= Long.valueOf( flag ?sessionStringProvince:"0");

        if( null != provinceId && 0 != provinceId ){
            filters.eq(BaseService.PROVINCE,provinceId);
        }else if(null != sessionProvince && 0 != sessionProvince){
            filters.eq(BaseService.PROVINCE,sessionProvince);
        }
        sort.asc("sort");
        pageRequest = new PageRequest(1,3,sort);
        List<NewGoods> newGoodses = newGoodsService.findMany(filters, pageRequest);
        modelMap.put("newGoodses",newGoodses);
        /**************限时推荐****************/
        filters.clear();
        filters = filters.lt("startDate", date).gt("endDate", date);
        if( null != provinceId && 0 != provinceId ){
            filters.eq(BaseService.PROVINCE,provinceId);
        }else if(null != sessionProvince && 0 != sessionProvince){
            filters.eq(BaseService.PROVINCE,sessionProvince);
        }
        pageRequest = new PageRequest(1,4,sort);
        List<DiscountGoods> discountGoodses = discountGoodsService.findMany(filters, pageRequest);
        for (DiscountGoods discountGoodse : discountGoodses) {
            Goods g = discountGoodse.getGoods();
            BigDecimal price = g.getPrice();
            BigDecimal discount = discountGoodse.getDiscount();
            discountGoodse.getGoods().setPromotePrice(price.multiply(discount.multiply(new BigDecimal(0.1))).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            discountGoodse.getGoods().setProduct(productService.find(g.getProduct().getId()));
        }
        modelMap.put("discountGoodses", discountGoodses);
        /**************楼层推荐****************/
        filters.clear();
        sort.clear();
        if( null != provinceId && 0 != provinceId ){
            filters.eq(BaseService.PROVINCE,provinceId);
        }else if(null != sessionProvince && 0 != sessionProvince){
            filters.eq(BaseService.PROVINCE,sessionProvince);
        }
        List<CategoryGoodsCommend> categoryGoodsCommends = categoryGoodsCommendService.findMany(filters, sort.asc("sort").asc("floor"));
        List<CategoryGoodsCommend> firstList = new ArrayList<>();
        List<CategoryGoodsCommend>  secondList = new ArrayList<>();
        List<CategoryGoodsCommend>  thirdList = new ArrayList<>();
        Iterator iter = categoryGoodsCommends.iterator();
        while(iter.hasNext()){
            CategoryGoodsCommend categoryGoodsCommend = (CategoryGoodsCommend) iter.next();
            if(0 ==categoryGoodsCommend.getCategoryGoodses().size()){
                iter.remove();
            }else{
                Integer floor = categoryGoodsCommend.getFloor();
                if(floor.equals(1)){
                    firstList.add(categoryGoodsCommend);
                }else if(floor.equals(2)){
                    secondList.add(categoryGoodsCommend);
                }else{
                    thirdList.add(categoryGoodsCommend);
                }
            }

        }
         Collections.sort(firstList);
         Collections.sort(secondList);
         Collections.sort(thirdList);
        modelMap.put("first",firstList);
        modelMap.put("second",secondList);
        modelMap.put("third",thirdList);
        /**************底部广告位****************/
        Dictionary dicAdsFoot = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_GGPOS, Constants.DT_GGPOS_IFOOT);
        filters.clear();
        sort.clear();
        filters.eq("pos", dicAdsFoot.getDicCode()).eq("statusType",1).eq("deviceType",1).eq("deleted",0);
        sort.desc("sortNum");
        pageRequest = new PageRequest(1,4,sort);
        List<Advertisement> footAdvertisements = advertisementService.findMany(filters, pageRequest);
        modelMap.put("footAdvertisements",footAdvertisements);

        /**************优惠劵专区 图片****************/

        Map<String,Object> couponParamMap = Maps.newConcurrentMap();
        couponParamMap.put("dictionaryId", dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_NRGL,"YHJZQ").getId());
        ContentManagement couponContentManagement= contentManagementService.findOne(couponParamMap);
        modelMap.put("couponContentManagement",couponContentManagement);
        modelMap.put("labels",labels);

        return "pc/index/Index";
    }


    /**
     * <p>名称：getCategory</p>.
     * <p>描述：导航分类树</p>
     * @author：LiuJC
     * @date：2016/8/22 11:42
     * @return List<Category>
     */
    @ResponseBody
    @RequestMapping("/category")
    public List<Category> getCategory() {
        return categoryService.getCategoryTree();
    }




    /**
     * <p>名称：index</p>.
     * <p>描述：手机端首页数据绑定</p>
     * @author：LiuJC
     * @date：2016/9/2 14:29
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("mobile/")
    public String mobileIndex(ModelMap modelMap,HttpServletRequest request,Long provinceId,HttpSession session){
        /*List<HatProvince> allProvince = (List<HatProvince>)cacheUtil.getCacheValue(Constants.ALL_PROVINCE);*/
        UserAgent ua = UserAgent.parse(request);
        Date date = new Date();
        /*************banenr****************/
        String dicCode;
        List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_YHLX);
        Long dicId;
        Filters labelFilters = Filters.create().eq("disabled", 0);
        List<Label> labels = labelService.findMany(labelFilters, (Pageable) null);
        if(ua.isMobile()){//手机
            dicId = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_ZCPT,Constants.DT_ZCPT_MOBILE).getId();
        }else{//pc
            dicId = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_ZCPT,Constants.DT_ZCPT_PC).getId();
        }
        Long dicPageId = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_FBYM,Constants.DT_FBYM_INDEX).getId();//首页

        Filters filters = Filters.create().eq("isShow", 1).eq("isShow",1)
                .eq("dictionaryPageId",dicPageId).eq("dictionaryPlatformId",dicId).lt("beginDate", date).gt("endDate",date);
        Sort sort = Sort.create().desc("sortNum");
        List<FlowBanner> flowBanners = flowBannerService.findMany(filters,sort);
        modelMap.put("flowBanners", flowBanners);
        /**************资讯****************/
        filters.clear();
        filters = Filters.create().eq("isHot", 1).eq("isShow",1);
        sort.clear();
        sort.desc("sortNum");
        PageRequest  pageRequest = new PageRequest(1,2,sort);
        List<News> newses = newsService.findMany(filters, pageRequest);
        modelMap.put("newses",newses);
        /**************优惠劵下面的广告****************/
        Dictionary dic_ad = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_GGPOS, Constants.DT_GGPOS_ITOPBANNER);
        filters.clear();
        sort.clear();
        filters.eq("pos", dic_ad.getDicCode()).eq("statusType",1).eq("deviceType",2).eq("deleted",0);
        sort.desc("sortNum");
        pageRequest = new PageRequest(1,1,sort);
        List<Advertisement> advertisement = advertisementService.findMany(filters, pageRequest);
        if(!CollectionUtils.isEmpty(advertisement)){
            modelMap.put("advertisement",advertisement.get(0));
        }
        /****************banner 下面的广告*****************/
        Dictionary dic_ads = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_GGPOS, Constants.DT_GGPOS_IMIDDLE);
        filters.clear();
        sort.clear();
        filters.eq("pos", dic_ads.getDicCode()).eq("statusType",1).eq("deviceType",2).eq("deleted",0);
        sort.desc("sortNum");
        pageRequest = new PageRequest(1,4,sort);
        List<Advertisement> advertisements = advertisementService.findMany(filters, pageRequest);
        modelMap.put("advertisements",advertisements);
        /**************爆品推荐****************/
        filters.clear();
        sort.clear();
        String sessionStringProvince = (String)session.getAttribute(Constants.COOKIE_STRING);
        boolean flag = (StringUtil.notEmpty(sessionStringProvince) && !"0".equals(sessionStringProvince));
        Long  sessionProvince= Long.valueOf( flag ?sessionStringProvince:"0");

        if( null != provinceId && 0 != provinceId ){
            filters.eq(BaseService.PROVINCE,provinceId);
        }else if(null != sessionProvince && 0 != sessionProvince){
            filters.eq(BaseService.PROVINCE,sessionProvince);
        }
        sort.asc("sort");
        pageRequest = new PageRequest(1,3,sort);
        List<NewGoods> newGoodses = newGoodsService.findMany(filters, pageRequest);
        modelMap.put("newGoodses",newGoodses);
        /**************限时推荐****************/
        filters.clear();
        filters = filters.lt("startDate", date).gt("endDate", date);
        if( null != provinceId && 0 != provinceId ){
            filters.eq(BaseService.PROVINCE,provinceId);
        }else if(null != sessionProvince && 0 != sessionProvince){
            filters.eq(BaseService.PROVINCE,sessionProvince);
        }
        pageRequest = new PageRequest(1 ,4,sort);
        List<DiscountGoods> discountGoodses = discountGoodsService.findMany(filters, pageRequest);
        for (DiscountGoods discountGoodse : discountGoodses) {
            BigDecimal price = discountGoodse.getGoods().getPrice();
            BigDecimal discount = discountGoodse.getDiscount();
            discountGoodse.getGoods().setPromotePrice(price.multiply(discount.multiply(new BigDecimal(0.1))).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        modelMap.put("discountGoodses", discountGoodses);
        /**************楼层推荐****************/
        filters.clear();
        sort.clear();
        if( null != provinceId && 0 != provinceId ){
            filters.eq(BaseService.PROVINCE,provinceId);
        }else if(null != sessionProvince && 0 != sessionProvince){
            filters.eq(BaseService.PROVINCE,sessionProvince);
        }
        List<CategoryGoodsCommend> categoryGoodsCommends = categoryGoodsCommendService.findMany(filters, sort.asc("sort").asc("floor"));
        List<CategoryGoodsCommend> firstList = new ArrayList<>();
        List<CategoryGoodsCommend>  secondList = new ArrayList<>();
        List<CategoryGoodsCommend>  thirdList = new ArrayList<>();
        Iterator iter = categoryGoodsCommends.iterator();
        while(iter.hasNext()){
            CategoryGoodsCommend categoryGoodsCommend = (CategoryGoodsCommend) iter.next();
            if(0 ==categoryGoodsCommend.getCategoryGoodses().size()){
                iter.remove();
            }else{
                Integer floor = categoryGoodsCommend.getFloor();
                if(floor.equals(1)){
                    firstList.add(categoryGoodsCommend);
                }else if(floor.equals(2)){
                    secondList.add(categoryGoodsCommend);
                }else{
                    thirdList.add(categoryGoodsCommend);
                }
            }

        }
        modelMap.put("first",firstList);
        modelMap.put("second",secondList);
        modelMap.put("third",thirdList);
        /**************底部广告位****************/
        Dictionary dicAdsFoot = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_GGPOS, Constants.DT_GGPOS_IFOOT);
        filters.clear();
        sort.clear();
        filters.eq("pos", dicAdsFoot.getDicCode()).eq("statusType",1).eq("deviceType",2).eq("deleted",0);
        sort.asc("sortNum");
        pageRequest = new PageRequest(1,4,sort);
        List<Advertisement> footAdvertisements = advertisementService.findMany(filters, pageRequest);
        modelMap.put("footAdvertisements",footAdvertisements);

        /**************优惠劵专区 图片****************/

        Map<String,Object> couponParamMap = Maps.newConcurrentMap();
        couponParamMap.put("dictionaryId", dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_NRGL,"YHJZQ").getId());
        ContentManagement couponContentManagement= contentManagementService.findOne(couponParamMap);
        modelMap.put("couponContentManagement",couponContentManagement);
        modelMap.put("labels",labels);



        return "mobile/index/index";
    }

    @RequestMapping("mobile/addSelect")
    public String selectAdd(){
        return "mobile/index/addrSelect";
    }
    @RequestMapping("mobile/shareLink")
    public String shareLink(HttpSession session,HttpServletRequest request,ModelMap modelMap){

        //分享链接
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if(currentUser != null){
            String recommendedCode = MemberUtil.generateRecommendedCode(currentUser.getId());
            String shareUrl = WebUtils.getApplicationUrl(request)+"/reg?recommendedCode="+recommendedCode;
            modelMap.put("shareUrl",shareUrl);
        }
        return "mobile/index/shareLink";
    }
    @RequestMapping("mobile/userSuggest")
    public String userSuggest(){
        return "mobile/index/userSuggest";
    }
    @RequestMapping("mobile/allKindList")
    public String allKindList(ModelMap modelMap){
        List<Category> categoryTree = categoryService.getCategoryTree();
        modelMap.put("categories",categoryTree);
        return "mobile/index/allKindList";
    }
}
