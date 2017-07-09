/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: OrderController.java
 * PackageName: com.glanway.jty.controller.orders
 * Date: 2016/5/39:57
 **/
package com.glanway.jty.controller.cart;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.cart.OrderTemp;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.entity.operations.Coupon;
import com.glanway.jty.entity.order.Order;
import com.glanway.jty.entity.order.OrderDetail;
import com.glanway.jty.entity.personalcenter.DeliveryAddress;
import com.glanway.jty.entity.personalcenter.MemberInvoice;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.SpecValue;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.cart.OrderTempService;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.logistics.HatProvinceService;
import com.glanway.jty.service.operations.CouponService;
import com.glanway.jty.service.order.OrderService;
import com.glanway.jty.service.personalcenter.DeliveryAddressService;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.utils.CacheUtil;
import com.glanway.jty.utils.UserAgent;
import com.glanway.jty.vo.OrderTempVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>名称: 前台订单添加controller</p>
 * <p>说明: 前台订单添加controller</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/5/39:57
 * @version: 1.0
 */
@Controller("cartOrderController")
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Autowired
    private HatProvinceService hatProvinceService;
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private OrderTempService orderTempService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private MemberService memberService;
    @Autowired
    CacheUtil cacheUtil;
    @Autowired
    private DictionaryService dictionaryService ;


    @RequestMapping
    public String main() {
        return "order/add";
    }

    /**
     * <p>名称：立即购买</p>
     * <p>描述：跳转到订单添加页面</p>
     *
     * @return ModeAndView
     * @author：tianxuan
     */
    @RequestMapping("add")
    public String add(long goodsId, int count,Integer goodsFrom, Long otherId, ModelMap map, HttpSession session) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        orderTempService.initTempGoodsByGoods(goodsId, count,goodsFrom,otherId, memberId, session);

        Map<String, Object> mapData = this.findData(memberId,Constants.ORDER_FROM_LJGM);
        map.putAll(mapData);
        List<Dictionary> bySuperDicCode = dictionaryService.findBySuperDicCode(Constants.PeiSongShiJian);
        map.put("pssj",bySuperDicCode);
        map.put("orderFrom", Constants.ORDER_FROM_LJGM);
        String path = "order/add";
        if (this.isMobile) {
            path = "mobile/" + path;
        } else {
            path = "pc/" + path;
        }
        return path;
    }

    /**
     * 配件组合购买
     * @param goodsId 商品id数组
     * @param buyCount  购买数量数组（默认必须传递主商品购买数量，配件数量（要么都传递，要么都不传递））
     * @param map
     * @param session
     * @return
     */
    @RequestMapping("addByPeiJian")
    public String addByPeiJian(Long[] goodsId, Integer[] buyCount,Integer goodsFrom, Long otherId,ModelMap map, HttpSession session) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        orderTempService.initTempGoodsByPeiJian(goodsId, buyCount, goodsFrom,otherId, memberId,session);
        Map<String, Object> mapData = this.findData(memberId,Constants.ORDER_FROM_PJ);
        map.putAll(mapData);
        map.put("orderFrom", Constants.ORDER_FROM_PJ);
        List<Dictionary> bySuperDicCode = dictionaryService.findBySuperDicCode(Constants.PeiSongShiJian);
        map.put("pssj",bySuperDicCode);
        String path = "order/add";
        if (this.isMobile) {
            path = "mobile/" + path;
        }else {
            path = "pc/" + path;
        }
        return path;
    }

    /**
     * 套餐购买
     * @param packageId  套餐Id
     * @param map
     * @param session
     * @return
     */
    @RequestMapping("addByTaoCan")
    public String addByTaoCan(Long packageId, ModelMap map, HttpSession session) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        cacheUtil.setCache("common.cache");
        cacheUtil.setCacheValue("MEMBER_CT_ID_" + memberId ,packageId);//将该会员的套餐Id放入缓存
        orderTempService.initTempGoodsByTaoCan(packageId,memberId,session);
        Map<String, Object> mapData = this.findData(memberId,Constants.ORDER_FROM_TC);
        map.putAll(mapData);
        map.put("orderFrom", Constants.ORDER_FROM_TC);
        List<Dictionary> bySuperDicCode = dictionaryService.findBySuperDicCode(Constants.PeiSongShiJian);
        map.put("pssj",bySuperDicCode);
        String path = "order/add";
        if (this.isMobile) {
            path = "mobile/" + path;
        }else {
            path = "pc/" + path;
        }
        return path;
    }

    /**
     * <p>名称：</p>
     * <p>描述：从购物车提交结算</p>
     *
     * @param cartIds 购物车记录id
     * @param map     返回值
     * @param session
     * @return
     * @author：tianxuan
     */
    @RequestMapping("addByCart")
    public String addByCart(@RequestParam(value = "cartId", required = false) Long[] cartIds, ModelMap map, HttpSession session) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        orderTempService.initTempGoodsByCart(cartIds, memberId, session);
        Map<String, Object> mapData = this.findData(memberId, Constants.ORDER_FROM_GWC);
        map.putAll(mapData);
        map.put("orderFrom", Constants.ORDER_FROM_GWC);
        List<Dictionary> bySuperDicCode = dictionaryService.findBySuperDicCode(Constants.PeiSongShiJian);
        map.put("pssj",bySuperDicCode);
        String path = "order/add";
        if (this.isMobile) {
            path = "mobile/" + path;
        }else {
            path = "pc/" + path;
        }
        return path;
    }

    /**
     * <p>名称：保存</p>
     * <p>描述：保存订单方法</p>
     *
     * @param addressId 收货地址id
     * @param
     * @param session
     * @author：tianxuan
     */
    @RequestMapping("save")
    @ResponseBody
    public Message save(Long addressId, Long couponId, Integer orderFrom,String timeSelect,String remark,HttpSession session, HttpServletRequest request) {
        if (null == addressId) {
            throw new CustomException("请选择收货地址");
        }
        Member member = ((Member) session.getAttribute(Constants.MEMBER));
        Long memberId = member.getId();
        OrderTempVo otv = orderTempService.findAllByMemberId(memberId, addressId,orderFrom);
        DeliveryAddress address = deliveryAddressService.find(addressId);
        List<Long> goodsIdList = new ArrayList<>();//商品Id 保存成功后从购物车中删除
        /*设置订单*/
//        List<Order> orderList = new ArrayList<>();
            Order order = new Order();
            order.setStatus(Constants.ORDER_STATUS_DZF);//待支付
            //设置应付金额
            BigDecimal totalPrice = (otv.getTotalPriceAndTotalFreight());
            order.setPsDate(timeSelect);//配送时间
        if(null !=couponId && otv.getCanUseCoupon()){
            Coupon coupon = couponService.find(couponId);
            order.setCouponId(couponId);
            order.setCouponName(coupon.getCouponName());
            order.setDiscount(coupon.getDiscount());
            order.setPrice(totalPrice.subtract(coupon.getDiscount()).doubleValue());
        }else{
            order.setPrice(totalPrice.doubleValue());
        }
            /*设置注册来源*/
            UserAgent ua = UserAgent.parse(request);
            order.setSource(ua.isMobile() ? "手机" : "PC");
            order.setMemberId(memberId);
            order.setMemberName(member.getMemberName());
            order.setReceiver(address.getConsignee());
            order.setContact(address.getConsigneePhone());
            order.setAddress(address.getFieldOne() + address.getFieldTwo() + address.getFieldThree() + address.getAddress());
        if(!otv.getBaoYou()){
            order.setDeliverPrice(otv.getTotalFreight().doubleValue());
        }else{
            order.setDeliverPrice(0D);
        }

            order.setDeleted(false);
            order.setMember(member);
            order.setRemark(remark);

            /*设置订单详情*/
            List<OrderDetail> orderDetails = new ArrayList<>();
            List<OrderTemp> otList = otv.getGoodsList();
            for (OrderTemp ot : otList) {
                goodsIdList.add(ot.getCartId());
                List<Goods> goodsList = goodsService.findMany(Filters.create().eq("id", ot.getGoodsId()), Sort.create());
                Goods goods = goodsList.get(0);
                OrderDetail detail = new OrderDetail();
                // 计算价格
//                goodsService.calcMemberPriceAtEveryOne(goods,session); //计算价格
                detail.setGoodsId(goods.getId());
                detail.setGoodsCode(goods.getSn());
                detail.setGoodsName(goods.getTitle());
                //设置规格
                List<SpecValue> specValues = goods.getSpecValues();
                final StringBuffer stringBuffer = new StringBuffer();
                if (null == specValues) {
                    specValues = new ArrayList<>();
                }
                for (SpecValue sv : specValues) {
                    stringBuffer.append(sv.getSpec().getName() + ":" + sv.getName()).append(" ");
                }
                detail.setGoodsSpec(stringBuffer.toString());
                detail.setGoodsNum(ot.getBuyCount());
                detail.setGoodsFrom(ot.getGoodsFrom());
                // 打折前的价格
                BigDecimal goodsPrice = goods.getPrice();
                detail.setGoodsPrice(goodsPrice.doubleValue());//打折前价格
                detail.setSellPrice(ot.getPrice().doubleValue());//交易价（折后金额）
                detail.setDiscount(goodsPrice.subtract(ot.getPrice())); //折扣
                detail.setGoodsImage(ot.getGoodsImg());
                orderDetails.add(detail);
            }
            order.setOrderDetails(orderDetails);
//            orderList.add(order);

//        String groupCode = orderService.saveOrderByList(orderList, goodsIdList, orderFrom, memberId);
        orderService.saveOrder(order,goodsIdList, orderFrom, memberId);
        Map<String, Object> map = new HashMap<>();
        map.put("orderGroupCode", order.getId());
        map.put("payTotalPrice", order.getPrice());
        return Message.success(map);
    }

    /**
     * <p>名称：订单提交成功</p>
     * <p>描述：订单提交成功</p>
     *
     * @return
     * @author：tianxuan
     */
    @RequestMapping("success")
    public String success(String payTotalPrice, String orderGroupCode, ModelMap modelMap) {
        modelMap.put("orderGroupCode", orderGroupCode);
        modelMap.put("payTotalPrice", payTotalPrice);
        String path ;
        if(this.isMobile){
            path = "forward:/order/selectPayment";
        }else {
            path = "pc/order/success";
        }
        return path;
    }

    /**
     * <p>名称：选择支付方式</p>
     * <p>描述：选择支付方式</p>
     *
     * @return
     * @author：tianxuan
     */
    @RequestMapping("selectPayment")
    public String selectPayment(Long id, String orderGroupCode,Double payTotalPrice, ModelMap modelMap) {
        modelMap.put("orderGroupCode", orderGroupCode);
        Member memberNew = memberService.find(member.getId());
        if(null != id){
            modelMap.put("orderGroupCode", id);
        }
        BigDecimal balance = memberNew.getBalance();
        if(null == balance || balance.doubleValue() < 0.01){
            balance = new BigDecimal(0.00);
        }
        modelMap.put("balance",balance);
        modelMap.put("payTotalPrice",  payTotalPrice );
        String path = "order/selectPayment";
        if(this.isMobile){
            modelMap.put("isMobile",isMobile);
            path = "mobile/" + path;
        }else {
            path = "pc/" + path;
        }
        return path;
    }

    /**
     * <p>名称：支付成功</p>
     * <p>描述：支付成功</p>
     *
     * @return
     * @author：tianxuan
     */
    @RequestMapping("paySuccess")
    public String paySuccess() {
        String path = "order/paySuccess";
        if(this.isMobile){
            path = "mobile/" + path;
        }else {
            path = "pc/" + path;
        }
        return path;
    }

    /**
     * 支付失败
     * @return
     */
    @RequestMapping("payFail")
    public String payFail() {
        String path = "order/payFail";
        if(this.isMobile){
            path = "mobile/" + path;
        }else {
            path = "pc/" + path;
        }
        return path;
    }
    /**
     * <p>名称：获取提交订单页所需要的数据</p>
     * <p>描述：获取提交订单页所需要的数据</p>
     *
     * @param memberId
     * @return
     * @author：tianxuan
     */
    private Map<String, Object> findData(Long memberId, Integer orderFrom) {
        Map<String, Object> rtMap = new HashMap<>();
        //所有省
        List<HatProvince> hatProvinceList = hatProvinceService.listAllProvince();
        //所有收货地址
        List<DeliveryAddress> addressList = deliveryAddressService.findAllByMemberId(memberId);
        //如果有默收货地址，就是用默认收货地址，如果没有就使用最后一个地址
        Long defaultAddressId  = null;
        for(DeliveryAddress address : addressList){
            if(address.getIsDefault()){
                defaultAddressId = address.getId();
                break;
            }
        }
        if(addressList.size() > 0 && defaultAddressId == null){
            defaultAddressId =  addressList.get(0).getId();
        }
        OrderTempVo orderTempVo = orderTempService.findAllByMemberId(memberId, defaultAddressId, orderFrom);

        if(orderTempVo.getCanUseCoupon()){
            //该用户所有的优惠券
            Map param = new HashMap();
            param.put("memberId",memberId);
            param.put("status",Coupon.STATUS_YFF);
            List<Coupon> couponList = couponService.findListByMember(param);
            rtMap.put("couponList", couponList);
        }
        rtMap.put("addressList", addressList);
        rtMap.put("provinces", hatProvinceList);
        rtMap.put("otv", orderTempVo);
        rtMap.put("defaultAddressId", defaultAddressId);
        return rtMap;
    }
}
