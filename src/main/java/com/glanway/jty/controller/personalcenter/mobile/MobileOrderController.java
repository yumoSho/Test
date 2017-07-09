/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p>
 * ProjectName: hg
 * FileName: MobileOrderController.java
 * PackageName: com.glanway.controller.personalcenter
 * Date: 2016/5/30 14:53
 **/
package com.glanway.jty.controller.personalcenter.mobile;


import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.order.Order;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.logistics.LogisticsInfoService;
import com.glanway.jty.service.order.OrderService;
import com.glanway.jty.utils.logistics.RouteVO;
import com.glanway.jty.utils.logistics.common.DomainConstants;
import com.glanway.jty.utils.logistics.common.KuaiDi100Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>名称: </p>
 * <p>说明: TODO</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：kiah
 * @date：2016/5/30 14:53
 * @version: 1.0
 */
@Controller("mobileOrderController")
@RequestMapping("/mobile/myCenter/order")
public class MobileOrderController extends BaseController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private LogisticsInfoService logisticsInfoService;

    /**
     * <p>名称：</p>
     * <p>描述：跳转到个人中心我的订单页面</p>
     *
     * @return ModeAndView
     * @author：kiah
     */
    @RequestMapping("orderList")
    public String orderList(ModelMap map,
                            HttpServletRequest request,
                            HttpSession session,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "5") int pageSize,
                            @RequestParam(value = "status", required = false) Integer status,
                            @RequestParam(value = "beginDate", required = false) String beginDate,
                            @RequestParam(value = "endDate", required = false) String endDate) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        String paraStr = null;
        String beginDateStr = null;
        String endDateStr = null;
        if (null != status && -1 == status) {
            status = null;
        }
        if (null != beginDate && !"".equals(beginDate)) {
            beginDateStr = beginDate + " 00:00:00";
        }
        if (null != endDate && !"".equals(endDate)) {
            endDateStr = endDate + " 23:59:59";
        }
        Page<Order> orderList = orderService.findOrderByMemberID(memberId, status, paraStr, beginDateStr, endDateStr, page, pageSize);
//        orderService.isReturn(orderList.getData());
        map.put("orderList", orderList);
        map.put("totalPages", orderList.getTotalPages());
        map.put("commonText", "我的订单");
        return "mobile/personCenter/order/myOrder";
    }

    /**
     * <p>名称：</p>
     * <p>描述：跳转到个人中心我的订单页面</p>
     *
     * @return ModeAndView
     * @author：kiah
     */
    @RequestMapping("orderListByAjax")
    @ResponseBody
    public Page<Order> toPageAjax(ModelMap map,
                                  HttpServletRequest request,
                                  HttpSession session,
                                  @RequestParam(required = false, defaultValue = "1") int page,
                                  @RequestParam(required = false, defaultValue = "5") int pageSize,
                                  @RequestParam(value = "status", required = false) Integer status,
                                  @RequestParam(value = "beginDate", required = false) String beginDate,
                                  @RequestParam(value = "endDate", required = false) String endDate) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        String paraStr = null;
        String beginDateStr = null;
        String endDateStr = null;
        if (null != status && -1 == status) {
            status = null;
        }
        if (null != beginDate && !"".equals(beginDate)) {
            beginDateStr = beginDate + " 00:00:00";
        }
        if (null != endDate && !"".equals(endDate)) {
            endDateStr = endDate + " 23:59:59";
        }
        return orderService.findOrderByMemberID(memberId, status, paraStr, beginDateStr, endDateStr, page, pageSize);
    }

    /**
     * <p>名称：</p>
     * <p>描述：手机端显示更多用</p>
     *
     * @return ModeAndView
     * @author：kiah
     */
    @RequestMapping("orderListForPage")
    @ResponseBody
    public Page<Order> orderListForPage(HttpSession session,
                                        @RequestParam(required = false, defaultValue = "1") int page,
                                        @RequestParam(required = false, defaultValue = "5") int size,
                                        @RequestParam(value = "status", required = false) Integer status,
                                        @RequestParam(value = "beginDate", required = false) String beginDate,
                                        @RequestParam(value = "endDate", required = false) String endDate) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        String paraStr = null;
        String beginDateStr = null;
        String endDateStr = null;
        if (null != status && -1 == status) {
            status = null;
        }
        if (null != beginDate && !"".equals(beginDate)) {
            beginDateStr = beginDate + " 00:00:00";
        }
        if (null != endDate && !"".equals(endDate)) {
            endDateStr = endDate + " 23:59:59";
        }
        Page<Order> orderList = orderService.findOrderByMemberID(memberId, status, paraStr, beginDateStr, endDateStr, page, size);
        return orderList;
    }

    /**
     * <p>名称：订单详情</p>
     * <p>描述：跳转至订单详情页面</p>
     *
     * @param map
     * @param orderId 订单ID
     * @return
     * @author：kiah
     */
    @RequestMapping("orderDetails/{orderId}")
    public String orderDetails(ModelMap map, HttpSession session, @PathVariable("orderId") Long orderId) {
        String url = "";
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        //验证此订单是否为已登录的会员的订单
        boolean flag = orderService.isOrderInMember(orderId, memberId);
        if (flag) {
            Order order = orderService.getOrderById(orderId);
           /* //快递100获取物流信息
            String channle = DomainConstants.LOGISTICS_CHANNEL_CODE_KUAIDI100;
            String shunfeng = KuaiDi100Constants.COMPANY_CODE_SHUNFENG;
            List<RouteVO> routeVOList = null;
            if (null != order && null != order.getDeliverCode() && !"".equals(order.getDeliverCode())) {
                String trackingNumber = order.getDeliverCode();
                routeVOList = logisticsInfoService.getRouteVoList(channle, trackingNumber, shunfeng);

            }
            map.put("routeVOList", routeVOList);*/
            map.put("order", order);
            url = "mobile/personCenter/order/orderDetail";
        } else {
            throw new CustomException("系统错误");
        }
        map.put("commonText", "订单详情");
        return url;
    }
    @RequestMapping("expressDetail/{orderId}")
    public String aa(ModelMap map,@PathVariable("orderId") Long orderId){
        Order order = orderService.getOrderById(orderId);
        //快递100获取物流信息
        String channle = DomainConstants.LOGISTICS_CHANNEL_CODE_KUAIDI100;
//            String channelCode = KuaiDi100Constants.COMPANY_CODE_SHENTONG;
        String channelCode = order.getDeliverCompanyCode();//得到物流公司的代码
        List<RouteVO> routeVOList = null;
        if (null != order && null != order.getDeliverCode() && !"".equals(order.getDeliverCode())) {
            String trackingNumber = order.getDeliverCode();
            routeVOList = logisticsInfoService.getRouteVoList(channle, trackingNumber, channelCode);

        }
        map.put("routeVOList", routeVOList);
        map.put("order", order);
        map.put("commonText", "物流详情");
        return "mobile/personCenter/order/expressDetail";
    }

    /**
     * <p>名称：取消订单</p>
     * <p>描述：根据订单ID取消订单</p>
     *
     * @param orderId
     * @return
     * @author：kiah
     */
    @RequestMapping("cancleOrder/{orderId}")
    public String cancleOrder(@PathVariable("orderId") Long orderId, HttpSession session) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        //验证此订单是否为已登录的会员的订单
        boolean flag = orderService.isOrderInMember(orderId, memberId);
        if (flag) {
            orderService.cancleOrderById(orderId);
        }
        return "redirect:/mobile/myCenter/order/orderList";
    }

    /**
     * <p>名称：确认收货</p>
     * <p>描述：根据订单ID确认收货</p>
     *
     * @param orderId 订单ID
     * @return
     * @author：kiah
     */
    @RequestMapping("receive/{orderId}")
    public String receive(@PathVariable("orderId") Long orderId, HttpSession session) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        //验证此订单是否为已登录的会员的订单
        boolean flag = orderService.isOrderInMember(orderId, memberId);
        if (flag) {
            orderService.receiveOrderById(orderId);
            orderService.finishOrderById(orderId);
        }
        return "redirect:/mobile/person-center/comment";
    }

    /**
     * <p>名称：订单交易完成</p>
     * <p>描述：更新订单状态为已完成并更新订单中商品销量</p>
     *
     * @param orderId 订单ID
     * @return
     * @author：kiah
     */
    @RequestMapping("finish/{orderId}")
    public String finish(@PathVariable("orderId") Long orderId, HttpSession session) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        //验证此订单是否为已登录的会员的订单
        boolean flag = orderService.isOrderInMember(orderId, memberId);
        if (flag) {
            orderService.finishOrderById(orderId);
        }
        return "redirect:/mobile/myCenter/order/orderList";
    }

    /**
     * <p>名称：删除</p>
     * <p>描述：根据订单ID删除订单</p>
     *
     * @param orderId
     * @return
     * @author：kiah
     */
    @RequestMapping("deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Long orderId, HttpSession session) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        //验证此订单是否为已登录的会员的订单
        boolean flag = orderService.isOrderInMember(orderId, memberId);
        if (flag) {
            orderService.deleteOrderById(orderId);
        }
        return "redirect:/mobile/myCenter/order/orderList";
    }
}
