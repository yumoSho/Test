package com.glanway.jty.controller.personalcenter;

import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.order.Order;
import com.glanway.jty.service.logistics.LogisticsInfoService;
import com.glanway.jty.service.order.OrderService;
import com.glanway.jty.utils.logistics.RouteVO;
import com.glanway.jty.utils.logistics.common.DomainConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by chenxingfei on 2016/5/4.
 */
@Controller("orderController")
@RequestMapping("/personalcenter")
public class OrderController extends BaseController {
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
    public String toPage(ModelMap map, HttpSession session,
                         @RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "5") int size,
                         @RequestParam(value = "type", required = false) Integer type,
                         @RequestParam(value = "status", required = false) Integer status,
                         @RequestParam(value = "para", required = false) String para,
                         @RequestParam(value = "beginDate", required = false) String beginDate,
                         @RequestParam(value = "endDate", required = false) String endDate) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        String paraStr = null;
        String beginDateStr = null;
        String endDateStr = null;
        if (null != status && -1 == status) {
            status = null;
        }
        if (null != type) {
            if (1 == type) {
                paraStr = "%" + para + "%";
            } else if (2 == type) {
                if (!"".equals(beginDate)) {
                    beginDateStr = beginDate + " 00:00:00";
                }
                if (!"".equals(endDate)) {
                    endDateStr = endDate + " 23:59:59";
                }
            }
        }
        Page<Order> orderList = orderService.findOrderByMemberID(memberId, status, paraStr, beginDateStr, endDateStr, page, size);
//        orderService.isReturn(orderList.getData());
        boolean isHave = true;
        if(status == null && orderList.getData().size() < 1){
            isHave = false;
        }
        map.put("orderList", orderList);
        map.put("mainIndex", Constants.MYCENTER_MYTRAD);
        map.put("index", Constants.MYCENTER_MYTRAD_MYORDER);
        map.put("isVipCenter", true);
        map.put("backColor", true);
        map.put("index",1);
        map.put("personalCenterNav","orderList");
        map.put("isHave",isHave);

        return "pc/mycenter/order/orderList";
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
        return "redirect:/personalcenter/orderList";
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
        return "redirect:/personalcenter/orderList";
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
    public String receive(@PathVariable("orderId") Long orderId, HttpSession session,ModelMap  modelMap) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        //验证此订单是否为已登录的会员的订单
        boolean flag = orderService.isOrderInMember(orderId, memberId);
        if (flag) {
            orderService.receiveOrderById(orderId);
            orderService.finishOrderById(orderId);
            modelMap.put("orderId",orderId);
        }
        return "redirect:/person-center/comment";
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
        return "redirect:/personalcenter/orderList";
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
        map.put("index",1);
        String url = "";
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        //验证此订单是否为已登录的会员的订单
        boolean flag = orderService.isOrderInMember(orderId, memberId);
        if (flag) {
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
            map.put("mainIndex", Constants.MYCENTER_MYTRAD);
            map.put("index", Constants.MYCENTER_MYTRAD_MYORDER);
            map.put("personalCenterNav","orderList");
            map.put("isVipCenter", true);
            map.put("backColor", true);
            url = "pc/mycenter/order/orderDetails";
        } else {
            return "redirect:/personalcenter/orderList";
        }
        return url;
    }

    /**
     * <p>名称：申请退换货</p>
     * <p>描述：跳转至退换货申请页面</p>
     *
     * @param map
     * @param orderId 订单ID
     * @return
     * @author：kiah
     */
    @RequestMapping("returnApply/{orderId}")
    public String returnApply(ModelMap map, HttpSession session, @PathVariable("orderId") Long orderId) {
        String url = "";
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        //验证此订单是否已登录的会员
        boolean flag = orderService.isOrderInMember(orderId, memberId);
        if (flag) {
            Order order = orderService.getOrderById(orderId);
            map.put("order", order);
            map.put("mainIndex", Constants.MYCENTER_MYTRAD);
            map.put("index", Constants.MYCENTER_MYTRAD_MYRETURN);
            map.put("isVipCenter", true);
            map.put("backColor", true);
            url = "mycenter/order/returnApply";
        } else {
            return "redirect:/personalcenter/orderList";
        }
        return url;
    }


}

