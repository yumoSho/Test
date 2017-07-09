/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 *
 * ProjectName: hg
 * FileName: PersonCenterController
 * PackageName:com.glanway.hg.controller.personalcenter
 * Date: 2016/5/13 14:37
 **/
package com.glanway.jty.controller.personalcenter.mobile;

import com.glanway.gone.service.ImageStorageService;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.PageRequest;

import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.BalancePaymentDetail;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.personalcenter.Collect;
import com.glanway.jty.entity.platform.RechargeRule;
import com.glanway.jty.entity.product.Comment;
import com.glanway.jty.entity.product.Consult;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.service.customer.BalancePaymentDetailService;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.logistics.HatProvinceService;
import com.glanway.jty.service.operations.CouponService;
import com.glanway.jty.service.order.OrderDetailService;
import com.glanway.jty.service.order.OrderService;
import com.glanway.jty.service.personalcenter.CollectService;
import com.glanway.jty.service.personalcenter.DeliveryAddressService;
import com.glanway.jty.service.platform.RechargeRuleService;
import com.glanway.jty.service.product.CommentService;
import com.glanway.jty.service.product.ConsultService;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.utils.*;
import com.google.common.collect.Maps;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.ponly.webbase.service.support.AbstractMultipartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人中心
 */

/**
 * <p>名称：个人中心</p>
 * <p>描述：个人中心</p>
 * @author：LiuJC
 * @date：2016/5/13 14:37
 * @version: 1.0
 */
@Controller
@RequestMapping("/mobile/person-center")
public class MobilePersonCenterController extends BaseController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private HatProvinceService hatProvinceService;
 /*   @Autowired
    private AnnounceService announceService;*/
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private ConsultService consultService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CouponService conponService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private BalancePaymentDetailService balancePaymentDetailService;
//    @Autowired
//    private ImageStorageService imageStorageService;
    @Autowired
    private RechargeRuleService rechargeRuleService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private AbstractMultipartService abstractMultipartService;
    /**
     * <p>名称：个人中心首页</p>
     * <p>描述：个人中心首页</p>
     * @date：2016/5/13 14:38
     * @author：LiuJC
     * @param session
     * @param modelMap
     * @return
     */
    @RequestMapping("index")
    public String personCenter(HttpSession session,ModelMap modelMap){
      /*------------------会员信息-----------------------*/
        /*Member __member = new Member();
        __member.setId(12L);
        session.setAttribute(Constants.MEMBER,__member);*/


        Member member = (Member) session.getAttribute(Constants.MEMBER);
        if( null == member ){
            return "redirect:/login";
        }
        if(member != null){
	        Member _member = memberService.find(member.getId());
	        modelMap.put("member",_member);
        }
        modelMap.put("center","center");
        modelMap.put("foot",3);
        modelMap.put("currentTimeInterval", DateUtils.getCurrentTimeInterval());
        return "mobile/personCenter/userCenterIndex";
    }
    @RequestMapping("setting")
    public String setting(HttpSession session,ModelMap modelMap){
        Member member = (Member) session.getAttribute(Constants.MEMBER);
        if(null == member){
            return "redirect:/login";
        }
        modelMap.put("commonText","设置");
        modelMap.put("href","mobile/person-center/index");
        return "mobile/personCenter/setting";
    }

    /**
     * <p>名称：changPasswordPage</p>.
     * <p>描述：个人中心修改密码页面</p>
     * @date：2016/5/13 14:43
     * @author：LiuJC
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("change-password")
    public String  changPasswordPage(ModelMap modelMap,HttpSession session){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if( null == currentUser ){
            return "redirect:/login";
        }
        modelMap.put("commonText","修改密码");
        modelMap.put("href","mobile/person-center/index");
        return "mobile/personCenter/userMessages/changePassword";
    }
    /**
    *
    * <p>名称：个人中心收藏</p>
    * <p>描述：个人中心收藏</p>
    * @date：2016/5/13 14:42
    * @author：LiuJC
    * @param model
    * @param session
    * @param page
    * @param pageSize
    * @return
    */
   @RequestMapping("collected")
   public String commentList(ModelMap model,HttpSession session,
                             @RequestParam(required=false,	defaultValue="1")Integer page,
                             @RequestParam(required=false, defaultValue="10")Integer pageSize){
       Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
       if(null==currentUser){
           return "redirect:/login";
       }
       Filters filters = Filters.create().eq("tmId",currentUser.getId());
       PageRequest pageRequest = new PageRequest();
       pageRequest.setPage(page);
       pageRequest.setPageSize(pageSize);
       pageRequest.getSort().clear();
       Page<Collect> collects = collectService.findPage(filters, pageRequest);
       if(CollectionUtils.isEmpty(collects.getData())){
           if(!page.equals(1)){
               return "redirect:/person-center/collected";
           }
       }
//       for (int i = 0; i <collects.getData().size(); i++) {
//           goodsService.factSalesPrice(collects.getData().get(i).getGoods());
//       }
       for(Collect c:collects){
           goodsService.calcMemberPriceAtEveryOne(c.getGoods(),c.getGoodsFrom(),c.getOtherId(), session);
       }
       model.put("collects",collects);
       model.put("mainIndex",0);
       model.put("isVipCenter",true);
       model.put("index",3);
       model.put("backColor",true);
       model.put("myCollect","myCollect");
       model.put("commonText","商品收藏");
       return "mobile/personCenter/myCollections";
   }

   /**
	 * 手机端收藏 显示更多
	 * @param page
	 * @param pageSize
	 * @return
	 */
   @ResponseBody
   @RequestMapping("/collected/list")
   public Page<Collect> newsListAJAX(@RequestParam(defaultValue="1")Integer page,
                                   @RequestParam( defaultValue="10")Integer pageSize,HttpSession session){
		   
		   Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
		   Filters filters = Filters.create().eq("tmId",currentUser.getId());
	       PageRequest pageRequest = new PageRequest();
	       pageRequest.setPage(page);
	       pageRequest.setPageSize(pageSize);
	       pageRequest.getSort().clear();
	       Page<Collect> collects = collectService.findPage(filters, pageRequest);
	       return collects;
   }
   
    /*------------------商品评价---------------------------*/
    @RequestMapping("comment")
    public String comment(ModelMap  modelMap,boolean commented,HttpSession session,
                          @RequestParam(defaultValue = "1")Integer page){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return "redirect:/login";
        }
        modelMap.put("commonText","我的评价");
        modelMap.put("href","mobile/person-center/index");
        if(commented) {
            Filters filters = Filters.create().eq("mId", user.getId());
            Page<Comment> comments = commentService.findPage(filters, new PageRequest(page, 10, new Sort().desc("createdDate")));
            for (Comment comment : comments.getData()) {
                goodsService.calcRealSalesPrice(comment.getGoods());
            }
            modelMap.put("comments",comments);
            return "mobile/personCenter/myEval/hadEval";
        }else{

            Page<Goods> goodses = goodsService.getGoodsByOrderDetailNoCommented(Filters.create(),new PageRequest(page,10,Sort.create() ));
            for (Goods g : goodses) {
                goodsService.calcRealSalesPrice(g);
            }
            modelMap.put("goodses",goodses);
            return "mobile/personCenter/myEval/haveEval";
        }
    }


    @RequestMapping("comment-list")
    public Page commentList(boolean commented,HttpSession session,
                          @RequestParam(defaultValue = "1")Integer page){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return null;
        }
        if(commented) {
            Filters filters = Filters.create().eq("mId", user.getId());
            Page<Comment> comments = commentService.findPage(filters, new PageRequest(page, 10, new Sort().desc("createdDate")));
            for (Comment comment : comments.getData()) {
                goodsService.calcRealSalesPrice(comment.getGoods());
            }
            return comments;
        }else{
            Page<Goods> goodses = goodsService.getGoodsByOrderDetailNoCommented(Filters.create(),new PageRequest(page,10,Sort.create() ));
            for (Goods g : goodses) {
                goodsService.calcRealSalesPrice(g);
            }
            return goodses;
        }
    }

    @RequestMapping("go-comment")
    public String goComment(Long id,Long ordid,String src, ModelMap modelMap){
        modelMap.put("id",id);
        modelMap.put("ordid",ordid);
        modelMap.put("src",src);
        modelMap.put("commonText","我的评价");
        modelMap.put("href","mobile/person-center/comment");
        return "mobile/personCenter/myEval/submitEval";
    }

    @RequestMapping("commenting")
    @ResponseBody
    public Message commenting(HttpServletRequest request, ModelMap modelMap , HttpSession session , Comment comment){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return Message.fail("",0);
        }
        if(null != comment && null != comment.getGoods() && null != comment.getGoods().getId() &&
                null != comment.getOrderDetail() && StringUtil.notEmpty(comment.getContent()) ){
            Date date = new Date();
            String savePhotoAfter = "";
            if(!CollectionUtils.isEmpty(comment.getPhotoList())){

                for( int i=0; i<comment.getPhotoList().size();i++){
                    if(StringUtil.notEmpty(comment.getPhotoList().get(i))){
                        savePhotoAfter+=headImageSave(request,comment.getPhotoList().get(i),user.getId(),i)+",";
                    }
                }
            }
            comment.setPhotos(savePhotoAfter);
            comment.setMember(user);
            comment.setCommentTime(date);
            commentService.saveComment(comment);
            return Message.success();
        }else{
            return Message.fail("请补充评价信息");
        }

    }

    public String headImageSave(HttpServletRequest req,String base64,Long memberId,int index){/*data:image/jpeg;base64,*/
        String basePath = req.getServletPath();
        String action = req.getPathInfo();
        if(StringUtil.notEmpty(base64)){
            byte[] decode = Base64.decode(base64);
            InputStream sbs = new ByteArrayInputStream(decode);
            String path="";
            try {
//                path = abstractMultipartService.store(new Date().getTime()+".jpg",sbs);
                String fileName = AESUtil.encrypt(new Date().getTime()+memberId+""+index,"mobileImg");
                path = abstractMultipartService.store("/storage/images/"+fileName+".jpg",sbs,false);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                return path;
            }
        }else{
            return null;
        }
    }

    @RequestMapping("consult")
    public String consult(ModelMap modelMap,HttpSession session,@RequestParam(defaultValue = "1")Integer page){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return "redirect:/login";
        }
        Filters filters = Filters.create().eq("tmId", user.getId());
        Page<Consult> consults = consultService.findPage(filters, new PageRequest(page, 10, new Sort().desc("createdDate")));
        for(Consult consult:consults){
            goodsService.calcRealSalesPrice(consult.getGoods());
        }
        modelMap.put("consults",consults);
        modelMap.put("comment","我的咨询");
        modelMap.put("href","mobile/person-center/index");
        return "mobile/personCenter/myEval/myAsk";
    }

    @RequestMapping("consult-list")
    @ResponseBody
    public Page consultList(ModelMap modelMap,HttpSession session,@RequestParam(defaultValue = "1")Integer page){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return null;
        }
        Filters filters = Filters.create().eq("tmId", user.getId());
        Page<Consult> consults = consultService.findPage(filters, new PageRequest(page, 10, new Sort().desc("createdDate")));
        for(Consult consult:consults){
            goodsService.calcRealSalesPrice(consult.getGoods());
        }
        return  consults;
    }

    /**
     *
     * <p>名称：我的充值记录</p>
     * <p>描述：我的充值记录</p>
     * @date：2016/9/7 14:08
     * @author：SongZhe
     * @param model
     * @param session
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("rechangeHistory")
    public String rechangeHistory(ModelMap model,HttpSession session,
                              @RequestParam(required=false,	defaultValue="1")Integer page,
                              @RequestParam(required=false, defaultValue="10")Integer pageSize){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if(null==currentUser){
            return "redirect:/login";
        }
        Filters filters = Filters.create().eq("memberId", currentUser.getId()).eq("type", BalancePaymentDetail.TYPE_RECHARGE);
        PageRequest pageRequest = new PageRequest();
        Sort sort = new Sort();
        sort.desc("startTime");
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        pageRequest.setSort(sort);
        Page<BalancePaymentDetail> balancePaymentDetails = balancePaymentDetailService.findPage(filters, pageRequest);
        if(CollectionUtils.isEmpty(balancePaymentDetails.getData())){
            if(!page.equals(1)){
                return "redirect:/person-center/collected";
            }
        }
//       for (int i = 0; i <collects.getData().size(); i++) {
//           goodsService.factSalesPrice(collects.getData().get(i).getGoods());
//       }
        model.put("balancePaymentDetails",balancePaymentDetails);
        model.put("commonText","充值记录");
        return "mobile/personCenter/myAccount/rechangeHistory";
    }

    /**
     * 手机端我的充值记录 显示更多
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("rechangeHistory/list")
    public Page<BalancePaymentDetail> rechangeHistoryList(@RequestParam(defaultValue="1")Integer page,
                                      @RequestParam( defaultValue="10")Integer pageSize,HttpSession session){

        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        Filters filters = Filters.create().eq("memberId", currentUser.getId()).eq("type", BalancePaymentDetail.TYPE_RECHARGE);
        PageRequest pageRequest = new PageRequest();
        Sort sort = new Sort();
        sort.desc("startTime");
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        pageRequest.setSort(sort);
        Page<BalancePaymentDetail> balancePaymentDetails = balancePaymentDetailService.findPage(filters, pageRequest);
        return balancePaymentDetails;
    }


    /**
     *
     * <p>名称：我的邀请</p>
     * <p>描述：我的邀请</p>
     * @date：2016/9/8 10:28
     * @author：SongZhe
     * @param model
     * @param session
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("myInvate")
    public String myInvate(ModelMap model,HttpSession session,
                                  @RequestParam(required=false,	defaultValue="1")Integer page,
                                  @RequestParam(required=false, defaultValue="10")Integer pageSize){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return "redirect:/login";
        }

        Filters filters = Filters.create().eq("recommendedId", user.getId());
        PageRequest pageRequest = new PageRequest();
        Sort sort = new Sort();
        sort.desc("registerDate");
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        pageRequest.setSort(sort);


        Page<Member> members = memberService.findPage(filters, pageRequest);

        model.put("commonText","我的邀请");
        model.put("myInvateMembers", members);

        return "mobile/personCenter/userMessages/myInvate";
    }

    /**
     * 手机端我的邀请显示更多
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("myInvate/list")
    public Page<Member> myInvateList(@RequestParam(defaultValue="1")Integer page,
                                                          @RequestParam( defaultValue="10")Integer pageSize,HttpSession session){

        Member user = (Member) session.getAttribute(Constants.MEMBER);
        Filters filters = Filters.create().eq("recommendedId", user.getId());
        PageRequest pageRequest = new PageRequest();
        Sort sort = new Sort();
        sort.desc("registerDate");
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        pageRequest.setSort(sort);
        Page<Member> members = memberService.findPage(filters, pageRequest);
        return members;
    }

    /**
     * 我的返利列表
     * @param page
     * @param pageSize
     * @param session
     * @param modelMap
     * @return
     */
    @RequestMapping("myBackMoney")
    public String myBackMoney(@RequestParam(defaultValue = "1")Integer page,@RequestParam(required=false, defaultValue="10")Integer pageSize,HttpSession session,ModelMap modelMap){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return "redirect:/login";
        }
        Filters filters = Filters.create().eq("memberId", user.getId()).eq("type", BalancePaymentDetail.TYPE_REBATE);
        PageRequest pageRequest = new PageRequest();
        Sort sort = new Sort();
        sort.desc("startTime");
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        pageRequest.setSort(sort);
        Page<BalancePaymentDetail> balancePaymentDetails = balancePaymentDetailService.findPage(filters, pageRequest);

        modelMap.put("commonText","我的提佣");
        modelMap.put("balancePaymentDetails",balancePaymentDetails);
        return "mobile/personCenter/userMessages/myBackMoney";
    }

    /**
     * 手机端我的邀请显示更多
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("myBackMoney/list")
    public Page<BalancePaymentDetail> myBackMoneyList(@RequestParam(defaultValue="1")Integer page,
                                     @RequestParam( defaultValue="10")Integer pageSize,HttpSession session){

        Filters filters = Filters.create().eq("memberId", user.getId()).eq("type", BalancePaymentDetail.TYPE_REBATE);
        PageRequest pageRequest = new PageRequest();
        Sort sort = new Sort();
        sort.desc("startTime");
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        pageRequest.setSort(sort);
        Page<BalancePaymentDetail> balancePaymentDetails = balancePaymentDetailService.findPage(filters, pageRequest);
        return balancePaymentDetails;
    }

    /**
     * 跳转到充值页面
     * @param modelMap
     * @return
     */
    @RequestMapping("recharge")
    public String recharge(ModelMap modelMap){
        List<RechargeRule> rechargeRuleList = rechargeRuleService.findList();
        Member memberNew = memberService.find(member.getId());
        modelMap.put("rechargeRuleList",rechargeRuleList);
        modelMap.put("balance",memberNew.getBalance());
        modelMap.put("commonText","充值");
        return "mobile/personCenter/myAccount/recharge";
    }
}

