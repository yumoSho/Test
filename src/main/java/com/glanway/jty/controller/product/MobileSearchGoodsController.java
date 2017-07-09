package com.glanway.jty.controller.product;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.marketing.ProductPackage;
import com.glanway.jty.entity.marketing.ProductPackageDetail;
import com.glanway.jty.entity.product.*;
import com.glanway.jty.service.marketing.ProductPackagesService;
import com.glanway.jty.service.product.*;
import com.glanway.jty.utils.JSONArrayUtil;
import com.glanway.jty.utils.StringUtil;
import com.google.common.collect.Maps;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.vacoor.mux.common.json.Jacksons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 商品检索
 */
@Controller
public class MobileSearchGoodsController extends BaseController {
    @Autowired
    private GoodsSearchService goodsSearchService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SpecService specService ;
    @Autowired
    private LabelService labelService;
    @Autowired
    private CommentService commentService ;
    @Autowired
    private ProductPackagesService productPackagesService ;

    @RequestMapping("/mobile/input")
    public String input(ModelMap modelMap){
        modelMap.put("show","0");
        return "mobile/product/search";
    }


    @RequestMapping("/mobile/search")
    public String search(String kw, String cat, String brand,Double sp/*stratPrice*/,Double ep/*endprice*/,
                         String[] ppath, String[] order,Long label,Long provinceId,
                         @RequestParam(required = false, defaultValue = "1") int page,HttpSession session,
                         @RequestParam(required = false, defaultValue = "6") int size,Model model){
        String sessionStringProvince = (String)session.getAttribute(Constants.COOKIE_STRING);
        boolean flag = (StringUtil.notEmpty(sessionStringProvince) && !"0".equals(sessionStringProvince));
        Long  sessionProvince= Long.valueOf( flag ?sessionStringProvince:"0");
        if( null != provinceId && 0l != provinceId ){
            /*null*/
        }else if(null != sessionProvince && 0l != sessionProvince){
            provinceId = sessionProvince;
        }
        Map<String, Object> ret = searchGoodsList(kw, cat, brand, sp, ep,provinceId, ppath, order,label, page, size, model);
        model.addAllAttributes(ret);
        if(0 == (long)ret.get("numFound")){
            return "redirect:/mobile/input?show=1";
        }
        Filters filters = Filters.create().eq("disabled", 0);
        List<Label> labels = labelService.findMany(filters, (Pageable) null);
        model.addAttribute("labels",labels);

        return "mobile/product/productList";
    }


    private Map<String, Object> searchGoodsList(String kw, String cat, String brand, Double sp, Double ep,Long provinceId, String[] ppath, String[] order,Long label,
                                                @RequestParam(required = false, defaultValue = "1") int page,
                                                @RequestParam(required = false, defaultValue = "6") int size, Model model) {
        if(null==cat||cat.trim().length()<1){
            ppath=null;
        }

        if(null!=order&&order[0].equals("price-asc")) {
            model.addAttribute("order", "price-asc");
        }
        if(null!=order&&order[0].equals("price-desc")) {
            model.addAttribute("order", "price-desc");
        }

        if(null!=order&&order[0].equals("sales-asc")) {
            model.addAttribute("order", "sales-asc");
        }
        if(null!=order&&order[0].equals("sales-desc")) {
            model.addAttribute("order", "sales-desc");
        }

        if(null!=order && order[0].equals("onSellDate-desc")){
            model.addAttribute("order", "onSellDate");
        }
        if(null==order || (1==order.length && "moren".equals(order[0])) ){
            model.addAttribute("order","moren");
            order=new String[2];
            order[0]="sales-desc";
            order[1]="price-desc";
        }

        int offset = (page - 1) * size;
        Map<String, Object> ret = goodsSearchService.search(kw, cat, brand,provinceId,label, sp/*sPrice*/, ep/*ePrice*/, ppath, offset, size, order);
        SolrDocumentList docs = (SolrDocumentList) ret.get("results");
        if (null != docs) {
            long numFound = docs.getNumFound();
            int totalPages = (int) Math.ceil(numFound / 1.0D / size);
            ret.put("page",page>totalPages?1:page);
            ret.put("numFound", numFound);
            ret.put("totalPages", totalPages);
        }
        return ret;
    }


    @RequestMapping("/mobile/search/list")
    @ResponseBody
    public  Map<String, Object> searchGoods(String kw, String cat, String brand,Double sp/*stratPrice*/,Double ep/*endprice*/,
                                            String[] ppath, String[] order,Long label,Long provinceId,HttpSession session,
                                            @RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "6") int size,Model model){
        String sessionStringProvince = (String)session.getAttribute(Constants.COOKIE_STRING);
        boolean flag = (StringUtil.notEmpty(sessionStringProvince) && !"0".equals(sessionStringProvince));
        Long  sessionProvince= Long.valueOf( flag ?sessionStringProvince:"0");
        if( null != provinceId && 0l != provinceId ){
            /*null*/
        }else if(null != sessionProvince && 0l != sessionProvince){
            provinceId = sessionProvince;
        }
        Map<String, Object> searchGoodsListMap = searchGoodsList(kw, cat, brand, sp, ep, provinceId,ppath, order,label, page, size, model);
        return searchGoodsListMap;
    }


    /**
     * <p>名称：detail</p>.
     * <p>描述：商品详情</p>
     * @author：LiuJC
     * @param id  商品ID
     * @param modelMap
     * @param request
     * @param page 评论页码
     * @param size 评论页数量
     * @return
     */
    @RequestMapping("/mobile/detail/{id}")
    public String detail(@PathVariable("id") Long id,ModelMap modelMap,HttpServletRequest request,HttpSession session,Integer goodsFrom,Long oid,
                         @RequestParam(required = false, defaultValue = "1") int page,Long provinceId,
                         @RequestParam(required = false, defaultValue = "10") int size){
        //-------------------商品基本信息------------------------
        Goods goodsDetail = goodsService.findGoodsDetail(id);

        //商品失效或下架
        if(goodsDetail == null){
            return "mobile/product/productNotFound";
        }


        goodsService.calcMemberPriceAtEveryOne(goodsDetail,goodsFrom,oid,session);
        //goodsService.calcRealSalesPrice(goodsDetail);

        /*其他规格*/
        Filters filter = Filters.create().eq("product_id", goodsDetail.getProduct().getId());
        List<Goods> goods = goodsService.findMany(filter, (Pageable) null);
        List<Spec> specAndValues = specService.findSpecAndValuesByProductId(goodsDetail.getProduct().getId());
        Map<String,Object> map = new HashMap<>();
        for (Iterator<Goods> iter = goods.iterator(); iter.hasNext();) {
            Goods next = iter.next();
            goodsService.calcMemberPriceAtEveryOne(next,goodsFrom,oid,session);
            if(StringUtil.notEmpty(next.getSvStr())){
                Map<String, Object> skuInfo = Maps.newHashMap();
                skuInfo.put("id", next.getId());
                skuInfo.put("stock",next.getStock());
                skuInfo.put("title",next.getTitle());
                skuInfo.put("price",next.getPrice());
                skuInfo.put("promotePrice",next.getPromotePrice());
                skuInfo.put("images",next.getImage());
                map.put(next.getSvStr(), skuInfo);
            }

        }

        modelMap.put("otherGoods",goods);
        modelMap.put("specAndValues",specAndValues);
        modelMap.put("goods",goodsDetail);
        modelMap.put("sv",Jacksons.serialize(map));

        /*-----------------------------商品评论-------------------*/
        PageRequest pageRequest = new PageRequest();
        Filters commentFilters = Filters.create().eq("gId", id).eq("visible",true);
        pageRequest.setPageSize(size);
        pageRequest.setPage(page);
//        pageRequest.setSort(PageRequest.EMPTY_SORT);
        pageRequest.setSort(Sort.create());
        Page<Comment> comments = commentService.findPage(commentFilters,pageRequest);
        if(!CollectionUtils.isEmpty(comments.getData())){
            for (int i = 0; i < comments.getData().size(); i++) {
                Comment comment = comments.getData().get(i);
                StringBuffer memberName = new StringBuffer(comment.getMember().getMemberName());
                if(memberName.length()>=3){
                    memberName.replace(1,memberName.length()-1,"***");
                }
                comment.getMember().setMemberName(memberName.toString());
            }
        }
        modelMap.put("comments",comments);
        /*-----------------评分-------------*/
        double calc = goodsService.calcsCore(id);
        if(0== calc){
            modelMap.put("score",5);
        }else{
            modelMap.put("score",(int)Math.floor(calc));
        }
        /*------------------人气单品---------------------*/
        String sessionStringProvince = (String)session.getAttribute(Constants.COOKIE_STRING);
        boolean flag = (StringUtil.notEmpty(sessionStringProvince) && !"0".equals(sessionStringProvince));
        Long  sessionProvince= Long.valueOf( flag ?sessionStringProvince:"0");
        Category category = goodsDetail.getProduct().getCategory();

        if( null != provinceId && 0l != provinceId ){
            if(null != category && null !=  category.getId()){
                List<Goods> goodses = goodsService.selectGoodsByCategoryId(category.getId(), 5, provinceId);
                for (Goods goodse : goodses) {
                    goodse.getProduct().setCategory(category);
                }
                goodsService.calcMemberPriceAtList(session,goodses);
                modelMap.put("hot",goodses);
            }
        }else if(null != sessionProvince && 0l != sessionProvince){
            provinceId = sessionProvince;
            if(null != category && null !=  category.getId()){
                List<Goods> goodses = goodsService.selectGoodsByCategoryId(category.getId(), 5, provinceId);
                for (Goods goodse : goodses) {
                    goodse.getProduct().setCategory(category);
                }
                goodsService.calcMemberPriceAtList(session,goodses);
                modelMap.put("hot",goodses);
            }
        }else{
            List<Goods> goodses = goodsService.selectGoodsByCategoryId(category.getId(),5,null);
            for (Goods goodse : goodses) {
                goodse.getProduct().setCategory(category);
            }
            goodsService.calcMemberPriceAtList(session,goodses);
            modelMap.put("hot",goodses);
        }
        /*-------------------配件-----------------*/
        List<ProductPackage> accessoy = productPackagesService.
                findProductPackageByProductGoodsId(goodsDetail.getId(), ProductPackage.PACKAGE_TYPE_ACCESSOY);
        for (ProductPackage productPackage : accessoy) {
            List<ProductPackageDetail> packageDetails = productPackage.getPackageDetails();
            for (ProductPackageDetail packageDetail : packageDetails) {
                goodsService.calcMemberPriceAtEveryOne(packageDetail.getGoods(),null,null,session);

            }
        }
        modelMap.put("accessoy",accessoy);
        if(!CollectionUtils.isEmpty(accessoy)){
            modelMap.put("accessoryJson", JSONArrayUtil.stringify(accessoy));
        }



        /*-------------------套餐-------------------*/
        List<ProductPackage> packages = productPackagesService.
                findProductPackageByProductGoodsId(goodsDetail.getId(), ProductPackage.PACKAGE_TYPE_PACKAGE);
        for (ProductPackage aPackage : packages) {
            List<ProductPackageDetail> packageDetails = aPackage.getPackageDetails();
            BigDecimal beforePriceCount = goodsDetail.getPrice();
            for (ProductPackageDetail packageDetail : packageDetails) {
                BigDecimal price = packageDetail.getGoods().getPrice();
                beforePriceCount=beforePriceCount.add(price);
            }
            aPackage.setOriginalPrice(beforePriceCount.setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        modelMap.put("packages",packages);
        if(!CollectionUtils.isEmpty(packages)) {
            modelMap.put("packagesJson", JSONArrayUtil.stringify(packages));
        }
        modelMap.put("goodsFrom",goodsDetail.getGoodsFrom());
        modelMap.put("gf",goodsFrom);
        modelMap.put("oid",oid);
        return "mobile/product/productDetail";
    }

    @ResponseBody
    @RequestMapping("/mobile/comments")
    public Page<Comment> getComments(int page,int size,Long id){
        PageRequest pageRequest = new PageRequest();
        Filters commentFilters = Filters.create().eq("gId", id).eq("visible",true);
        pageRequest.setPageSize(size);
        pageRequest.setPage(page);
        Page<Comment> comments = commentService.findPage(commentFilters,pageRequest);
        if(!CollectionUtils.isEmpty(comments.getData())){
            for (int i = 0; i < comments.getData().size(); i++) {
                Comment comment = comments.getData().get(i);
                StringBuffer memberName = new StringBuffer(comment.getMember().getMemberName());
                if(memberName.length()>=3){
                    memberName.replace(1,memberName.length()-1,"***");
                }
                comment.getMember().setMemberName(memberName.toString());
            }
        }
        return comments;
    }
}
