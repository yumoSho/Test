/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 *
 * ProjectName: hg
 * FileName: PersonCenterController
 * PackageName:com.glanway.hg.controller.personalcenter
 * Date: 2016/5/13 14:37
 **/
package com.glanway.jty.controller.personalcenter;

import com.glanway.gone.spring.bind.domain.*;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.content.Feedback;
import com.glanway.jty.entity.customer.BalancePaymentDetail;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.entity.operations.Coupon;
import com.glanway.jty.entity.order.Order;
import com.glanway.jty.entity.personalcenter.Collect;
import com.glanway.jty.entity.personalcenter.DeliveryAddress;
import com.glanway.jty.entity.platform.RechargeRule;
import com.glanway.jty.entity.product.Comment;
import com.glanway.jty.entity.product.Consult;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.content.FeedbackService;
import com.glanway.jty.service.customer.BalancePaymentDetailService;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.logistics.HatProvinceService;
import com.glanway.jty.service.operations.CouponService;
import com.glanway.jty.service.order.OrderService;
import com.glanway.jty.service.personalcenter.CollectService;
import com.glanway.jty.service.personalcenter.DeliveryAddressService;
import com.glanway.jty.service.platform.RechargeRuleService;
import com.glanway.jty.service.product.CommentService;
import com.glanway.jty.service.product.ConsultService;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.utils.CacheUtil;
import com.glanway.jty.utils.DateUtils;
import com.glanway.jty.utils.StringUtil;
import com.glanway.jty.utils.TimeUtil;
import com.google.common.collect.Maps;
import org.apache.solr.handler.dataimport.HTMLStripTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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
@RequestMapping("/person-center")
public class PersonCenterController extends BaseController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private HatProvinceService hatProvinceService;
  /*  @Autowired
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
    private BalancePaymentDetailService balancePaymentDetailService;
    @Autowired
    private RechargeRuleService rechargeRuleService;
    @Autowired
	private FeedbackService feedbackService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private DictionaryService dictionaryService;
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
        Member member = (Member) session.getAttribute(Constants.MEMBER);
        if(null == member){
            return "redirect:/login";
        }
        modelMap.put("member",member);

        Long memberId = member.getId();
        /*------------------订单数量统计----------------------*/
        //订单数量统计
        Map<String, Object> countStatus = orderService.countStatus(Constants.ORDER_STATUS_DZF, Constants.ORDER_STATUS_DSH,Constants.ORDER_STATUS_DFH,memberId);
        //待评价数量统计
        Map param = new HashMap();
        param.put("memberId",memberId);
        int count4 =  orderService.countComment(param);
        countStatus.put("COUNT4",count4);
        /*-------------------订单列表---------------------*/
        //查询需要要在首页展示的订单列表
        List<Order> orderList = orderService.findOrderListByMemberID(memberId);

        /*------------------公告信息----------------------*/
        //最新6条公告
     /*   List<Announce> announces = announceService.findMany(Filters.create().eq("isShow",1),new PageRequest(1,6, Sort.create().desc("createdDate")));
        modelMap.put("announces",announces);*/
        modelMap.put("orderList",orderList);
        modelMap.put("orderCount",countStatus);
        
        modelMap.put("currentTimeInterval",DateUtils.getCurrentTimeInterval());
        return "pc/mycenter/myCenterIndex";
    }

    /**
     * <p>名称：个人中心信息页</p>
     * <p>描述：个人中心信息页</p>
     * @date：2016/5/13 14:39
     * @author：LiuJC
     * @param user 前台用户
     * @param session
     * @param modelMap
     * @return
     */
    @RequestMapping("info")
    public String personInfo(Member user,HttpSession session,ModelMap modelMap){
        Member member = (Member) session.getAttribute(Constants.MEMBER);
        if(null == member){
            return "redirect:/login";
        }
        if(null != user && null !=  user.getSex() && StringUtil.notEmpty( user.getPhone()) /*&& StringUtil.notEmpty(user.getBirthdate())*/){
            user.setId(member.getId());
            memberService.update(user);
            member =memberService.find(member.getId());
            member.setPassword(null);
            session.setAttribute(Constants.MEMBER,member);
            return "redirect:/person-center/index";
        }
        member = (Member) session.getAttribute(Constants.MEMBER);
        modelMap.put("member",member);
        modelMap.put("personalCenterNav","info");
        return "pc/mycenter/mycenter/myMessage";
    }

    /**
     * 校验名称是否有效
     * <p>名称：校验名称是否有效</p>
     * <p>描述：校验名称是否有效 要区分银行</p>
     * @date：2016/5/13 14:40
     * @author：LiuJC
     * @param memberName 用户昵称
     * @param session
     */
    @ResponseBody
    @RequestMapping("check")
    public Boolean check( String memberName,HttpSession session) {
        Member member = (Member) session.getAttribute(Constants.MEMBER);
        if(null == member){
            return false;
        }
        return memberService.memberNameRepeat(member.getId(),memberName) < 1;
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
        modelMap.put("personalCenterNav","change-password");
        return "pc/mycenter/mycenter/changePWD";
    }


    /**
     * <p>名称：修改密码</p>
     * <p>描述：修改密码</p>
     * @author：LiuJC
     * @date：2016/5/13 14:44
     * @param session
     * @param oldPwd
     * @param newPwd
     * @param msg
     * @return
     */
    @ResponseBody
    @RequestMapping("update-pwd")
    public Message updatePwd(HttpSession session, String oldPwd, String newPwd, String msg) {
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return Message.fail("");
        }
        HashMap<String, Object> findMap = Maps.newHashMap();
        findMap.put("id",user.getId());
        try {
            Boolean  flag = memberService.validatePasswordEqual(findMap, newPwd,oldPwd);
            if(!flag){
                return Message.fail("原始密码错误");
            }else if(newPwd.equals(oldPwd)) {
                return Message.fail("新旧密码不能相同");
            }
            user = memberService.setPassword(findMap, newPwd,oldPwd);
        } catch(CustomException e){
            e.printStackTrace();
            return Message.fail("请检查原始密码是否正确");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return Message.fail("系统异常,请稍候再试");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return Message.fail("系统异常,请稍候再试");
        }
        session.removeAttribute(Constants.MEMBER);
        return Message.success();
    }


    /**
     * <p>名称：加入收藏</p>
     * <p>描述：加入收藏</p>
     * @date：2016/5/13 14:40
     * @author：LiuJC
     * @param goodsid 商品id
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("add-collect")
    public synchronized Message addCollect(Long goodsid,Integer goodsFrom,Long otherId,HttpSession session){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if(null==currentUser){
            return Message.fail("");
        }else{
            if(null== goodsid){
                return Message.fail("0");
            }
            try {
                collectService.addCollect(currentUser.getId(),goodsid,goodsFrom,otherId);
            }catch (CustomException e){
                return Message.fail("数据异常");
            }
            return Message.success();

        }
    }

    /**
     * <p>名称：取消收藏</p>
     * <p>描述：取消收藏</p>
     * @date：2016/5/13 14:41
     * @author：LiuJC
     * @param goodsid 商品id
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("remove-collect")
    public synchronized Message removeCollect(Long goodsid,Integer goodsFrom,Long otherId,HttpSession session){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if(null==currentUser){
            return Message.fail("");
        }else{
            if(null == goodsid){
                return Message.fail("0");
            }
            Filters filters = Filters.create().eq("tgId", goodsid).eq("tmId", currentUser.getId());
                    if(null!= goodsFrom &&0!= goodsFrom){
                        filters.eq("goodsFrom",goodsFrom);
                        if(null != otherId && 0!= goodsFrom){
                            filters.eq("otherId", otherId);
                        }else{
                            filters.isNull("otherId");
                        }
                    }else{
                        filters.isNull("goodsFrom");
                        filters.isNull("otherId");
                    }

            List<Collect> collects = collectService.findMany(filters, (PageRequest) null);
            if(!CollectionUtils.isEmpty(collects) && 1== collects.size()){
                collectService.delete(collects.get(0));
                return Message.success();
            }
        }
        return Message.success();
    }

    /**
     *
     * <p>名称：个人中的取消收藏</p>
     * <p>描述：批量个人中的取消收藏</p>
     * @author：LiuJC
     * @param  id  商品id
     */
    @RequestMapping("remove-Selected")
    @ResponseBody
    public Message removeSelected(Long[] id,HttpSession session){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if(null==currentUser){
            Message.fail("");
        }else{
            if(null == id){
                return Message.fail("0");
            }
            Filters filters = Filters.create().eq("tgId", id).eq("tmId", currentUser.getId());
            List<Collect> collects = collectService.findMany(filters, (PageRequest) null);
            if(!CollectionUtils.isEmpty(collects) ){
                for(Collect e:collects){
                    collectService.delete(e);
                }
                return Message.success();
            }
        }
        return Message.success();
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
        for(Collect c:collects){
            goodsService.calcMemberPriceAtEveryOne(c.getGoods(),c.getGoodsFrom(),c.getOtherId(), session);
        }
        model.put("collects",collects);
        model.put("personalCenterNav","collected");
        return "pc/mycenter/mytrade/myCollections";
    }


    /**
     * <p>名称：商品详情页加入是否已收藏</p>
     * <p>描述：商品详情页加入是否已收藏</p>
     * @author：LiuJC
     * @date：2016/5/13 14:42
     * @param goodsid
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("check-collected")
    public Message checkCollected(Long goodsid ,Integer goodsFrom,Long otherId,HttpSession session){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if(null==currentUser){
            return Message.fail("");
        }else{
            if(null == goodsid){
                return Message.fail("0");
            }
            Filters filters = Filters.create();
            filters.add("tgId", Filters.Operator.EQ,goodsid);
            filters.add("tmId", Filters.Operator.EQ,currentUser.getId());
            if(null!= goodsFrom &&0!= goodsFrom){
                filters.eq("goodsFrom",goodsFrom);
                if(null != otherId && 0!= goodsFrom){
                    filters.eq("otherId",otherId);
                }else{
                    filters.isNull("otherId");
                }
            }else{
                filters.isNull("goodsFrom");
                filters.isNull("otherId");
            }
            List<Collect> collectList = collectService.findMany(filters, (PageRequest) null);
            if(!CollectionUtils.isEmpty(collectList) && 1==collectList.size()){
                return Message.success();
            }else{
                return Message.fail("0");
            }
        }
    }


    /**
     * <p>名称：收货地址</p>
     * <p>描述：收货地址</p>
     * @date：2016/5/13 14:44
     * @author：LiuJC
     * @param session
     * @param modelMap
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("address")
    public String address(HttpSession session ,ModelMap modelMap,
                          @RequestParam(required=false,	defaultValue="1")Integer page,
                          @RequestParam(required=false, defaultValue="10")Integer pageSize){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return "redirect:/login";
        }
        Filters filters = Filters.create().eq("memberId", user.getId());
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        pageRequest.getSort().clear();
        Page<DeliveryAddress> deliveryAddress = deliveryAddressService.findPage(filters, pageRequest);
        if(CollectionUtils.isEmpty(deliveryAddress.getData())){
            if(!page.equals(1)){
                return "redirect:/person-center/address";
            }
        }
        modelMap.put("deliveryAddress",deliveryAddress);
        modelMap.put("personalCenterNav","address");
        modelMap.put("provinces",hatProvinceService.listAllProvince());
        return "pc/mycenter/mycenter/myAddress";
    }

    /**
     * <p>名称：个人中心地址编辑</p>
     * <p>描述：个人中心地址编辑</p>
     * @date：2016/5/13 14:45
     * @author：LiuJC
     * @param id
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public Message eidtAddress(Long id,HttpSession session){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return Message.fail("");
        }
        if(null != id){
            DeliveryAddress userAddress = deliveryAddressService.find(id);
            return Message.success(userAddress);
        }
        return Message.fail("请选择要编辑的地址");
    }

    /**
     * <p>名称：删除地址</p>
     * <p>描述：删除地址</p>
     * @author：LiuJC
     * @date：2016/5/13 14:45
     * @param id 地址id
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("del-add")
    public Message delAddress(Long id,HttpSession session){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return Message.fail("");
        }
        if(null != id){
            deliveryAddressService.delete(id);
            return Message.success();
        }
        return Message.fail("请选择要编辑的地址");
    }

    /**
     * <p>名称：删除地址</p>
     * <p>描述：删除地址</p>
     * @author：LiuJC
     * @date：2016/5/13 14:45
     * @param id 地址id
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("set-def")
    public Message setDefaultAddresss(Long id,HttpSession session){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return Message.fail("");
        }
        if(null != id){
            DeliveryAddress deliveryAddress = new DeliveryAddress();
            deliveryAddress.setMemberId(user.getId());
            deliveryAddress.setId(id);
            deliveryAddress.setIsDefault(true);
            deliveryAddressService.update(deliveryAddress);
            return Message.success();
        }
        return Message.fail("请选择要编辑的地址");
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
        modelMap.put("index",3);
        return "mycenter/mytrade/myAdvice";
    }

    @RequestMapping("del-consult")
    @ResponseBody
    public Message delConsult(Long[] id,HttpSession session){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return Message.fail("",0);
        }
        consultService.delete(id);
        return Message.success();
    }

    @RequestMapping("comment")
    public String comment(ModelMap  modelMap,boolean commented,HttpSession session,
                          @RequestParam(defaultValue = "1")Integer page,Long orderId){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return "redirect:/login";
        }
        modelMap.put("personalCenterNav","comment");
        if(commented) {
            Filters filters = Filters.create().eq("mId", user.getId());
            Page<Comment> comments = commentService.findPage(filters, new PageRequest(page, 10, new Sort().desc("createdDate")));
            for (Comment comment : comments.getData()) {
                goodsService.calcRealSalesPrice(comment.getGoods());
            }
            modelMap.put("comments",comments);
            return "pc/mycenter/mytrade/myHadEval";
        }else{
            Filters filters = Filters.create().eq("mId", user.getId());
            if(orderId != null){
                filters.eq("torId",orderId);
            }
            Page<Goods> goodses = goodsService.getGoodsByOrderDetailNoCommented(filters,new PageRequest(page,10,Sort.create() ));
            for (Goods g : goodses) {
                goodsService.calcRealSalesPrice(g);
            }
            modelMap.put("goodses",goodses);
            return "pc/mycenter/mytrade/myEval";
        }
    }

    @RequestMapping("commenting")
    @ResponseBody
    public Message commenting(ModelMap modelMap ,HttpSession session ,Comment comment){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return Message.fail("",0);
        }
        if(null != comment && null != comment.getGoods() && null != comment.getGoods().getId() &&
                null != comment.getOrderDetail() && StringUtil.notEmpty(comment.getContent()) ){
            Date date = new Date();
            comment.setMember(user);
            comment.setCommentTime(date);
            commentService.saveComment(comment);
            return Message.success();
        }else{
            return Message.fail("请补充评价信息");
        }

    }
   /* @RequestMapping("/announces")
    public String announceList(ModelMap modelMap,HttpSession session,@RequestParam(defaultValue = "1")Integer page){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return "redirect:/login";
        }
        Page<Announce> announces = announceService.findPage(Filters.create().eq("isShow",false),new PageRequest(page , 5 ,Sort.create().desc("createdDate")));
        modelMap.put("announces",announces);
        modelMap.put("index",9);
        return "mycenter/mycenter/announceList";
    }

    @RequestMapping("/announces/{id}")
    public String announceDetail(@PathVariable("id")Long id,ModelMap modelMap,HttpSession session,@RequestParam(defaultValue = "1")Integer page){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return "redirect:/login";
        }
        Announce announce = announceService.find(id);
        if(null == announce){
            return "redirect:/person-center/announces";
        }
        modelMap.put("index",9);
        modelMap.put("announce",announce);
        return "mycenter/mycenter/announceDetail";
    }*/

    @RequestMapping("coupon")
    public String conpon(@RequestParam(defaultValue = "1")Integer page,@RequestParam(defaultValue = "0")int type,HttpSession session,ModelMap modelMap){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return "redirect:/login";
        }
        Filters filters = Filters.create();
        PageRequest pageRequest = new PageRequest(page,10,Sort.create());
        filters.eq("a.memberId", user.getId());
        if(0 == type){//未使用
            filters.lt("a.beginDate", new Date()).gt("a.endDate", new Date()).eq("a.status", 2);
        }else if( 1 == type){//已过期
            filters.lt("a.beginDate", new Date()).lt("a.endDate", new Date()).eq("a.status",2);
        }else if( 2 == type){//使用记录
            filters.eq("a.status", 3);
        }
        Page<Coupon> coupons = conponService.findPage(filters, pageRequest);
        /*-------------------count--------------------------*/
        int notUsed = conponService.count(Filters.create().eq("a.memberId", user.getId()).lt("a.beginDate", new Date()).gt("a.endDate", new Date()).eq("a.status", 2));
        int overdue = conponService.count(Filters.create().eq("a.memberId", user.getId()).lt("a.beginDate", new Date()).lt("a.endDate", new Date()).eq("a.status", 2));
        int used = conponService.count(Filters.create().eq("a.memberId", user.getId()).eq("a.status", 3));
        modelMap.put("coupons", coupons);
        modelMap.put("notUsed",notUsed);
        modelMap.put("overdue",overdue);
        modelMap.put("personalCenterNav","coupon");
        modelMap.put("used",used);
        modelMap.put("type",type);
        return "pc/mycenter/mycenter/mySaleCard";
    }
    
    /**
     * 我的邀请
     * @param page
     * @param
     * @param session
     * @param modelMap
     * @return
     */
    @RequestMapping("myInvate")
    public String myInvate(@RequestParam(defaultValue = "1")Integer page,@RequestParam(required=false, defaultValue="10")Integer pageSize,HttpSession session,ModelMap modelMap){
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
        
        /* Filters filters = Filters.create();
        PageRequest pageRequest = new PageRequest(page,5,Sort.create());
        filters.eq("a.memberId", user.getId());
       if(0 == type){//未使用
            filters.lt("a.beginDate", new Date()).gt("a.endDate", new Date()).eq("a.status", 2);
        }else if( 1 == type){//已过期
            filters.lt("a.beginDate", new Date()).lt("a.endDate", new Date()).eq("a.status",2);
        }else if( 2 == type){//使用记录
            filters.eq("a.status", 3);
        }*/
//        Page<Coupon> coupons = conponService.findPage(filters, pageRequest);
        Page<Member> members = memberService.findPage(filters, pageRequest);

        modelMap.put("personalCenterNav","myInvate");
        modelMap.put("members", members);

        return "pc/mycenter/mycenter/myInvate";
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
        
        modelMap.put("personalCenterNav","myBackMoney");
        modelMap.put("balancePaymentDetails",balancePaymentDetails);
        return "pc/mycenter/mycenter/myBackMoney";
    }
    
    /**
     * 我的充值记录
     * @param page
     * @param pageSize
     * @param session
     * @param modelMap
     * @return
     */
    @RequestMapping("myRechargeRecord")
    public String myRechargeRecord(@RequestParam(defaultValue = "1")Integer page,@RequestParam(required=false, defaultValue="10")Integer pageSize,HttpSession session,ModelMap modelMap){
        Member user = (Member) session.getAttribute(Constants.MEMBER);
        if (null == user) {
            return "redirect:/login";
        }
        Filters filters = Filters.create().eq("memberId", user.getId()).eq("type", BalancePaymentDetail.TYPE_RECHARGE);
        PageRequest pageRequest = new PageRequest();
        Sort sort = new Sort();
        sort.desc("startTime");
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        pageRequest.setSort(sort);
        Page<BalancePaymentDetail> balancePaymentDetails = balancePaymentDetailService.findPage(filters, pageRequest);
        
        modelMap.put("personalCenterNav","myRechargeRecord");
        modelMap.put("balancePaymentDetails",balancePaymentDetails);
        return "pc/mycenter/mycenter/myRechargeRecord";
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

        //充值协议
        Map<String,String> param = Maps.newConcurrentMap();
        param.put("dicCode","DT_YHCZXY");
        Dictionary _dictionary = dictionaryService.findOne(param);
        modelMap.put("dictionary",_dictionary);
        return "pc/mycenter/mycenter/recharge";
    }

    /**
     * 充值支付
     * @param rechargeId
     * @return
     */
    @RequestMapping("rechargePay")
    public String rechargePay(Long rechargeId,ModelMap map){
        RechargeRule rechargeRule = rechargeRuleService.find(rechargeId);
        //把需要支付的充值 对象放入缓存，充值时，再取出
        cacheUtil.setCache("common.cache");
        cacheUtil.setCacheValue("RECHARGE_" + member.getId(), rechargeRule);
        map.put("rechargePay",true);
        map.put("orderGroupCode","CZ" + member.getId() + TimeUtil.format(new Date(),TimeUtil.FORMAT_YYMMDDHHMMSS));
        map.put("payType",2);
        map.put("isMobile",isMobile);
        String path = "order/selectPayment";
        if(this.isMobile){
            path = "mobile/" + path;
            map.put("commonText","支付方式");
        }else {
            path = "pc/" + path;
        }
        return path;
    }
    /** 
	* @功能描述: 添加用户反馈内容管理信息
	* @param
	* @return       
	*/
	@ResponseBody
	@RequestMapping("saveByAjax")
	public Map<String,Object> saveByAjax(Feedback feedback) {
		Map<String,Object> resultMap = Maps.newConcurrentMap();
		try {
			Member member = memberService.find(Long.parseLong(feedback.getMemberId()));
			if(member.getEmail() == null && member.getPhone() == null){
				resultMap.put("reslut", false);
				return resultMap;
			}
			
			feedback.setPhone(member.getPhone());
			feedback.setEmail(member.getEmail());
			feedback.setMemberName(member.getMemberName());
			feedback.setDeleted(Boolean.FALSE);
			feedback.setFlag(Boolean.FALSE);
			feedback.setCreatedDate(new Date());
			feedbackService.save(feedback);
			resultMap.put("reslut", true);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("reslut", false);
		}
		return resultMap;
	}
    
    /* @ResponseBody
 	
    @RequestMapping("myBackMoneyRechargeTest")
 	
    public Boolean myBackMoneyTest(Long memberId,Long consumeMemberId, Integer type,BigDecimal amount,BigDecimal giftAmount,String costWay) {

        return balancePaymentDetailService.recharge(memberId,consumeMemberId, type, amount, giftAmount, costWay);
 	
    }

    @ResponseBody
 	
    @RequestMapping("myBackMoneyconsumeTest")
 	
    public Boolean myBackMoneyconsumeTest(Long memberId,BigDecimal amount) {

        return balancePaymentDetailService.consume(memberId, amount);
 	
    }*/
}
