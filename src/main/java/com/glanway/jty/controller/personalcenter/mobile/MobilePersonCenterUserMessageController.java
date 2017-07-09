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
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.operations.Coupon;
import com.glanway.jty.entity.order.Order;
import com.glanway.jty.entity.personalcenter.Collect;
import com.glanway.jty.entity.personalcenter.DeliveryAddress;
import com.glanway.jty.entity.product.Comment;
import com.glanway.jty.entity.product.Consult;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.Model;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.logistics.HatProvinceService;
import com.glanway.jty.service.operations.CouponService;
import com.glanway.jty.service.order.OrderService;
import com.glanway.jty.service.personalcenter.CollectService;
import com.glanway.jty.service.personalcenter.DeliveryAddressService;
import com.glanway.jty.service.product.CommentService;
import com.glanway.jty.service.product.ConsultService;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.utils.AESUtil;
import com.glanway.jty.utils.Base64;
import com.glanway.jty.utils.StringUtil;
import com.google.common.collect.Maps;
import org.ponly.webbase.service.MultipartService;
import org.ponly.webbase.service.support.AbstractMultipartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
 * 个人中心-用户信息
 */

/**
 * <p>名称：个人中心-用户信息/p>
 * <p>描述：个人中心-用户信息</p>
 * @author：SongZhe
 * @date：2016/8/16 11:08
 * @version: 1.0
 */
@Controller
@RequestMapping("/mobile/person-center/userMessages")
public class MobilePersonCenterUserMessageController extends BaseController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private HatProvinceService hatProvinceService;
//    @Autowired
//    private AnnounceService announceService;
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
    @Qualifier("multipartService")
    private MultipartService abstractMultipartService;
    /**
     * <p>名称：changPasswordPage</p>.
     * <p>描述：个人中心修改个人资料页面</p>
     * @date：2016/5/13 14:43
     * @author：SongZhe
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("userMessage")
    public String  userMessage(ModelMap modelMap,HttpSession session){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if( null == currentUser ){
            return "redirect:/login";
        }
        Member _member = memberService.find(currentUser.getId());
        modelMap.put("member", _member);
        modelMap.put("commonText","编辑资料");
        return "mobile/personCenter/userMessages/userMessage";
    }
    
    /**
     * <p>名称：updateUserMessage</p>.
     * <p>描述：个人中心修改个人资料</p>
     * @date：2016/8/16 11:22
     * @author：SongZhe
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("updateUserMessage")
    public String  updateUserMessage(HttpServletRequest request,ModelMap modelMap,HttpSession session,Member member){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if( null == currentUser ){
            return "redirect:/login";
        }
        if(member.getHeadImage() != null && !"".equals(member.getHeadImage())){
        	member.setHeadImage(headImageSave(request,member.getHeadImage(),member.getId()));
        }else{//不更新头像
        	member.setHeadImage(null);
        }
        
        
        memberService.update(member);
//        Member _member = memberService.find(currentUser.getId());
//        modelMap.put("member", _member);
        return "redirect:/mobile/person-center/index";
    }
    
    /**
     * <p>名称：manageAddr</p>.
     * <p>描述：个人中心我的收货地址列表</p>
     * @date：2016/8/16 13:27
     * @author：SongZhe
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("manageAddr")
    public String  manageAddr(ModelMap modelMap,HttpSession session){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if( null == currentUser ){
            return "redirect:/login";
        }
        List<DeliveryAddress> deliveryAddresses = deliveryAddressService.findAllByMemberId(currentUser.getId());
        modelMap.put("deliveryAddresses", deliveryAddresses);
        modelMap.put("commonText", "收货地址");
        return "mobile/personCenter/userMessages/manageAddr";
    }
    
    /**
     * <p>名称：manageAddr</p>.
     * <p>描述：个人中心我的收货地址列表</p>
     * @date：2016/8/16 13:27
     * @author：SongZhe
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("chuiceAddr")
    public String  chuiceAddr(ModelMap modelMap,HttpSession session){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if( null == currentUser ){
            return "redirect:/login";
        }
        List<DeliveryAddress> deliveryAddresses = deliveryAddressService.findAllByMemberId(currentUser.getId());
        modelMap.put("deliveryAddresses", deliveryAddresses);
        modelMap.put("commonText", "收货地址");
        return "mobile/personCenter/userMessages/chuiceAddr";
    }
    
    /**
     * <p>名称：editAddr</p>.
     * <p>描述：个人中心修改（添加）我的收货页面</p>
     * @date：2016/8/16 13:27
     * @author：SongZhe
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("editAddr")
    public String  editAddr(ModelMap modelMap,HttpSession session,Long id){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if( null == currentUser ){
            return "redirect:/login";
        }
        DeliveryAddress deliveryAddress = deliveryAddressService.find(id);
        if(deliveryAddress != null){
        	modelMap.put("deliveryAddress", deliveryAddress);
        }
        modelMap.put("commonText", "编辑地址");
        return "mobile/personCenter/userMessages/editAddr";
    }
    
    /**
     * <p>名称：saleCoupon</p>.
     * <p>描述：个人中心我的优惠券</p>
     * @date：2016/8/17 11:22
     * @param modelMap
     * @param session
     * @param id
     * @param type
     * @param page
     * @param pageSize
     * @return
     */

    @RequestMapping("saleCoupon")
    public String  saleCoupon(ModelMap modelMap,HttpSession session,Long id,@RequestParam(defaultValue = "0")int type, @RequestParam(required=false,defaultValue="1")Integer page,
            @RequestParam(required=false, defaultValue="10")Integer pageSize){
        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);
        if( null == currentUser ){
            return "redirect:/login";
        }
        Filters filters = Filters.create();
        PageRequest pageRequest = new PageRequest(page,pageSize,Sort.create());
        filters.eq("a.memberId", currentUser.getId());
        if(0 == type){//未使用
            filters.lt("a.beginDate", new Date()).gt("a.endDate", new Date()).eq("a.status", 2);
        }else if( 1 == type){//已过期
            filters.lt("a.beginDate", new Date()).lt("a.endDate", new Date()).eq("a.status",2);
        }
        Page<Coupon> coupons = conponService.findPage(filters, pageRequest);
        int notUsed = conponService.count(Filters.create().eq("a.memberId", currentUser.getId()).lt("a.beginDate", new Date()).gt("a.endDate", new Date()).eq("a.status",  Coupon.STATUS_YFF));
        int overdue = conponService.count(Filters.create().eq("a.memberId", currentUser.getId()).lt("a.beginDate", new Date()).lt("a.endDate", new Date()).eq("a.status",  Coupon.STATUS_YFF));
      
        	modelMap.put("_conpons", coupons);
        	modelMap.put("notUsed", notUsed);
        	modelMap.put("overdue", overdue);
        	modelMap.put("type", type);
        	modelMap.put("commonText", "优惠劵");
        
        return "mobile/personCenter/userMessages/saleCoupon";
    }

    @ResponseBody
    @RequestMapping("saleCoupon/list")
    public Page<Coupon> newsListAJAX(@RequestParam(defaultValue="1")Integer page,
                                      @RequestParam( defaultValue="10")Integer pageSize,HttpSession session,Integer type){

        Member currentUser = (Member) session.getAttribute(Constants.MEMBER);

        Filters filters = Filters.create();
        PageRequest pageRequest = new PageRequest(page,pageSize,Sort.create());
        filters.eq("a.memberId", currentUser.getId());
        if(0 == type){//未使用
            filters.lt("a.beginDate", new Date()).gt("a.endDate", new Date()).eq("a.status", 2);
        }else if( 1 == type){//已过期
            filters.lt("a.beginDate", new Date()).lt("a.endDate", new Date()).eq("a.status",2);
        }
        Page<Coupon> coupons = conponService.findPage(filters, pageRequest);
        return coupons;
    }

    /**
     * 将手机端的base64代码存到本地
     * @param base64
     * @return
     */
    public String headImageSave(HttpServletRequest req, String base64,Long memberId){/*data:image/jpeg;base64,*/

//        String basePath = req.getServletPath();
//        String action = req.getPathInfo();
        if(StringUtil.notEmpty(base64)){
            String  base64Head="data:image/jpeg;base64,";
            if(base64.startsWith(base64Head)){
                byte[] decode = Base64.decode(base64.substring(base64Head.length(), base64.length() - 1));
                InputStream sbs = new ByteArrayInputStream(decode);
                String path="";
                try {
                    String fileName = AESUtil.encrypt(new Date().getTime()+memberId+"","mobileImg");
                    path = abstractMultipartService.store("/storage/images/"+fileName+".jpg",sbs,false);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    return path;
                }
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}
