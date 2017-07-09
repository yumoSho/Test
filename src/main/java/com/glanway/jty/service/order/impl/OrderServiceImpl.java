package com.glanway.jty.service.order.impl;


import com.glanway.gone.dao.CrudDao;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.order.OrderDao;
import com.glanway.jty.dao.order.OrderDetailDao;
import com.glanway.jty.entity.operations.Coupon;
import com.glanway.jty.entity.order.Order;
import com.glanway.jty.entity.order.OrderDetail;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.cart.CartService;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.operations.CouponService;
import com.glanway.jty.service.order.OrderService;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.utils.SerialNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Service("orderService")
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private GoodsService goodsService;
   @Autowired
    private CartService cartService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private CouponService couponService;



    /**
     * 根据开始时间 结束时间 （此时间查询基于创建时间）以及filter查询列表所需要的页面信息
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param filters   过滤条件
     * @param pageable  页面参数
     * @return 订单List集合
     */
    @Override
    public Page<Order> findPage(String beginDate, String endDate, Filters memberFilters, Filters filters, Pageable pageable,Filters commFilters) {
        // TODO Auto-generated method stub
        filters = new IterateNamingTransformFilters(filters);
        memberFilters = new IterateNamingTransformFilters(memberFilters);
        Map<String, Object> params = createParamsMap();
        preQuery(beginDate, endDate, memberFilters, filters, pageable, params,commFilters);
        int total = orderDao.findCountForPage(params);
        List<Order> data = total > 0 ? orderDao.findManyForPage(params) : Collections.<Order>emptyList();
        return new SimplePage<Order>(pageable, data, total);
    }

    /**
     * 根据订单ID查询订单
     *
     * @param id 订单ID
     * @return 订单
     */
    @Override
    public Order getOrderById(long id) {
        // TODO Auto-generated method stub
        return orderDao.getOrderById(id);
    }

    /**
     *<p>名称：确认收货</p>
     * <p>描述：更新订单状态并更新收货时间</p>
     * @author：kiah
     * @param id 订单Id
     */
    @Override
    public void receiveOrderById(long id) {
        Map<Object, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Constants.ORDER_STATUS_YSH);
        map.put("receiveDate", new Date());
        orderDao.receiveOrderById(map);
    }

    /**
     *<p>名称：订单支付</p>
     * <p>描述：根据订单ID，确认此订单已支付并更新支付时间为当前时间</p>
     * @author：kiah
     * @param id 订单Id
     * @param payment 支付方式
     * @param payPrice 支付金额
     */
    @Override
    public void payOrderById(long id,String payment,Double payPrice) {
        Map<Object, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Constants.ORDER_STATUS_DFH);
        map.put("payDate", new Date());
        map.put("payment", payment);
        map.put("payPrice", payPrice);
        map.put("rate", 0l);
        orderDao.payOrderById(map);
    }


    /**
     * <p>名称：订单支付</p>
     * <p>描述：根据订单组号，确认此订单已支付并更新支付时间为当前时间</p>
     * @author：kiah
     * @param groupCode 订单组好
     * @param payment 支付方式
     * @param  （银联支付）
     */
    @Override
    public void payOrderByGroupCode(String groupCode,String payment) {
        Map<Object, Object> map = new HashMap<>();
        map.put("groupCode", groupCode);
        map.put("status", Constants.ORDER_STATUS_DFH);
        map.put("payDate", new Date());
        map.put("payment", payment);
        map.put("rate", 0l);
        orderDao.payOrderByGroupCode(map);
    }

    /**
     * 根据订单ID更新订单状态
     *
     * @param id     订单ID
     * @param status 订单状态
     */
    @Override

    public void updateOrderStatusById(long id, int status) {
        // TODO Auto-generated method stub
        Map<Object, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", status);
        orderDao.updateOrderStatusById(map);
    }

    /**
     * 根据订单ID更新快递单号
     *
     * @param orderId
     * @param deliverCode
     */
    @Override
    public void updateDeliverCodeByOrderId(long orderId, String deliverCode,String deliverCompanyCode) {
        Map<Object, Object> map = new HashMap<>();
        map.put("id", orderId);
        map.put("deliverCode", deliverCode);
        map.put("deliverCompanyCode", deliverCompanyCode);
        orderDao.updateDeliverCodeByOrderId(map);

    }

    /**
     * 根据开始时间 结束时间 （此时间查询基于创建时间）以及filter查询列表所需要的导出的信息
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param filters   过滤条件
     * @param pageable  页面参数
     * @return 订单List集合
     */
    @Override
    public List<Order> ListForExport(String beginDate, String endDate, Filters memberFilters, Filters filters, Pageable pageable) {
        // TODO Auto-generated method stub
        filters = new IterateNamingTransformFilters(filters);
        Map<String, Object> params = createParamsMap();

        preQuery(beginDate, endDate, memberFilters, filters, pageable, params,null);
        int total = orderDao.findCountForPage(params);
        List<Order> data = total > 0 ? orderDao.findManyForPage(params) : Collections.<Order>emptyList();
        return data;
    }

    /**
     * 将过滤条件以及页面参数信息（分页、排序等）添加到Map中
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param filters   过滤条件
     * @param pageable  页面参数
     * @param params    Map集合
     */
    private void preQuery(String beginDate, String endDate, Filters memberFilters, Filters filters, Pageable pageable, Map<String, Object> params,Filters commFilters) {
        if (null != filters) {
            params.put(OrderDao.ORDER_FILTERS_PROP, filters);
        }
        if (null != memberFilters) {
            params.put(OrderDao.MEMBER_FILTERS_PROP, memberFilters);
        }
        if(null != commFilters){
            params.put(OrderDao.FILTERS_PROP, commFilters);
        }
        if (StringUtils.hasText(beginDate) || StringUtils.hasText(endDate)) {
            if (StringUtils.hasText(beginDate) && !StringUtils.hasText(endDate)) {
                endDate = beginDate.substring(0,10)+" 23:59:59";
            } else if (StringUtils.hasText(endDate) && !StringUtils.hasText(beginDate)) {
                beginDate = endDate.substring(0,10)+" 00:00:00";
            } else {

            }
            params.put("beginDate", beginDate);
            params.put("endDate", endDate);
        }
        if (null != pageable) {
            if(pageable.getOffset() > 0){
                params.put(CrudDao.OFFSET_PROP, pageable.getOffset());
            }
            if(pageable.getPageSize() > 0){
                params.put(CrudDao.MAX_RESULTS_PROP, pageable.getPageSize());
            }

            Sort sort = pageable.getSort();
            if (null != sort) {
                params.put(OrderDao.SORT_PROP, new IterateNamingTransformSort(sort));
            }
        }

    }

    /**
     * 查询订单中，订单状态为status1个数，订单状态为status2订单个数
     *
     * @return Map
     */
    @Override
    public Map<String, Object> countStatus(Integer status1, Integer status2,Integer status3,Long memberId) {
        Map<Object, Object> map = new HashMap<>();
        map.put("status1", status1);
        map.put("status2", status2);
        map.put("status3", status3);
        map.put("memberId",memberId);
        return orderDao.countStatus(map);
    }

    @Override
    public int countComment(Map param) {
        return orderDao.countComment(param);
    }

    /**
     * <p>名称：</p>
     * <p>描述：根据会员ID查询订单信息</p>
     *
     * @param memberId
     * @param para
     * @param beginDate
     * @param endDate
     * @return
     * @author：kiah
     */
    @Override
    public Page<Order> findOrderByMemberID(Long memberId, Integer status, String para, String beginDate, String endDate, int page, int size) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("memberId", memberId);
        paramsMap.put("status", status);
        paramsMap.put("para", para);
        paramsMap.put("beginDate", beginDate);
        paramsMap.put("endDate", endDate);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setPageSize(size);
        super.resolvePageableToParamsMap(pageRequest, paramsMap);
        int count = orderDao.countOrderByMemberId(paramsMap);
        List orderList = count > 0 ? orderDao.findOrderByMemberId(paramsMap) : Collections.emptyList();
        return new SimplePage<Order>(pageRequest, orderList, count);
    }

    /**
     * 个人中心首页订单列表的两条订单信息
     * @param memberId
     * @return
     */
    @Override
    public List<Order> findOrderListByMemberID(Long memberId) {
        List<Order> rtList = new ArrayList<>();
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("memberId", memberId);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setPageSize(20);
        super.resolvePageableToParamsMap(pageRequest, paramsMap);
        List<Order> orderList = orderDao.findOrderByMemberId(paramsMap);
        for(Order order : orderList){
            if(Constants.ORDER_STATUS_YQX != order.getStatus()){
                rtList.add(order);
            }
            if(rtList.size() >= 2) break;//只拿两条数据
        }
        return rtList;
    }

    /**
     * <p>名称：</p>
     * <p>描述：新增订单</p>
     *
     * @param order
     * @author：kiah
     */
    @Override
    public String saveOrder(Order order,List<Long> goodsIdList,Integer orderForm,Long memberId) {
        //订单号生成规则
        String orderCode = SerialNum.getMoveOrderNo(Constants.ORDER_CODE_PREFIX);
        order.setCode(orderCode);
        //设置订单所属 组
        String groupCode = order.getGroupCode();
        groupCode = (null != groupCode) ? groupCode : SerialNum.getMoveOrderNo(Constants.ORDER_GROUP_PREFIX);
        order.setGroupCode(groupCode);
        order.setCreatedDate(new Date());
        order.setLastModifiedDate(new Date());
        order.setGroupCode(groupCode);
        //保存订单基本信息
        orderDao.save(order);
        //保存订单详情信息
        List<OrderDetail> orderDetailList = order.getOrderDetails();
        if (null != orderDetailList && 0 != orderDetailList.size()) {
            for (OrderDetail orderDetail : orderDetailList) {
                orderDetail.setOrderId(order.getId());
                orderDetail.setOrderCode(orderCode);
                orderDetailDao.save(orderDetail);
                //修改库存
                goodsService.updateStock(orderDetail.getGoodsNum(), orderDetail.getGoodsId(), false);
                //更新商品销量
                goodsService.updateSaleNum(orderDetail.getGoodsNum(),orderDetail.getGoodsId(),null);
            }
        }
        // 修改用户的优惠圈状态
        Coupon coupon = new Coupon();
        coupon.setId(order.getCouponId());
        coupon.setStatus(Coupon.STATUS_YSY);
        couponService.update(coupon);

        if(orderForm == Constants.ORDER_FROM_GWC){
            cartService.deleteByGoodsId(goodsIdList,memberId);
        }
        return groupCode;
    }

    /**
     * <p>名称：</p>
     * <p>描述：新增订单LIST中所有ORDER</p>
     *
     * @param orderList
     * @author：kiah
     */
    @Override
    public String saveOrderByList(List<Order> orderList,List<Long> goodsIdList,Integer orderForm,Long memberId) {
        //订单所属组编号
        if(null == orderList || 0 == orderList.size()){
            throw new CustomException("请添加商品！");
        }
        String groupCode = SerialNum.getMoveOrderNo(Constants.ORDER_GROUP_PREFIX);
        for (Order order : orderList) {
            order.setGroupCode(groupCode);
            saveOrder(order,goodsIdList, orderForm, memberId);
        }
        if(orderForm == Constants.ORDER_FROM_GWC){
            cartService.deleteByGoodsId(goodsIdList,memberId);
        }
        return groupCode;
    }

    /**
     * <p>名称：</p>
     * <p>描述：根据订单ID取消订单</p>
     *
     * @param orderId
     * @author：kiah
     */
    @Override
    public void cancleOrderById(Long orderId) {
        //更新订单状态
        this.updateOrderStatusById(orderId, Constants.ORDER_STATUS_YQX);
        //更新订单中商品库存
        Order order = this.getOrderById(orderId);
        if (null != order && null != order.getOrderDetails() && 0 != order.getOrderDetails().size())
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                goodsService.updateStock(orderDetail.getGoodsNum(), orderDetail.getGoodsId(), true);
            }
    }

    /**
     * <p>名称：</p>
     * <p>描述：根据订单ID删除订单</p>
     *
     * @param orderId
     * @author：kiah
     */
    @Override
    public void deleteOrderById(Long orderId) {
        orderDao.deleteOrderById(orderId);
    }

    /**
     * <p>名称：订单交易完成</p>
     * <p>描述：更新订单状态为交易完成，并更新交易完成时间、订单中商品销量</p>
     * @author：kiah
     * @param orderId 订单ID
     */
    @Override
    public void finishOrderById(Long orderId) {
        //更新订单状态
        Map map = new HashMap();
        map.put("id",orderId);
        map.put("status",Constants.ORDER_STATUS_YWC);
        map.put("finishDate",new Date());
        orderDao.finishOrderById(map);
        //更新订单中商品销量
//        updateSaleNumByOrderId(orderId,true);
    }

    /**
     * <p>名称：</p>
     * <p>描述：更新订单中商品销量</p>
     * @author：kiah
     * @param orderId 订单ID
     * @param flag 标记 true或者null为加销量 false为减销量
     */
    public  void updateSaleNumByOrderId(Long orderId,boolean flag){
       if(null != orderId){
           Order order = this.getOrderById(orderId);
           if (null != order && null != order.getOrderDetails() && 0 != order.getOrderDetails().size())
               for (OrderDetail orderDetail : order.getOrderDetails()) {
                   goodsService.updateSaleNum(orderDetail.getGoodsNum(), orderDetail.getGoodsId(), flag);
               }
       }
    }

    /**
     * <p>名称：验证此订单是否属于此会员</p>
     * <p>描述：验证此订单是否属于此会员</p>
     * @author：kiah
     * @param orderId 订单ID
     * @param memberId	会员ID
     * @return
     */
    @Override
    public boolean isOrderInMember(Long orderId, Long memberId) {
        boolean flag =false;
        if(null != orderId && null != memberId){
            Map<String,Object> map = new HashMap<>();
            map.put("orderId",orderId);
            map.put("memberId",memberId);
            flag = orderDao.isOrderInMember(map) == 1 ? true : false;
        }
        return flag;
    }

    /**
     * <p>名称：订单发货</p>
     * <p>描述：更新订单状态为已待收货并更新订单发货时间</p>
     * @author：kiah
     * @param orderId
     */
    @Override
    public void deliverOrderById(Long orderId) {
        Map<Object, Object> map = new HashMap<>();
        map.put("id", orderId);
        map.put("status", Constants.ORDER_STATUS_DSH);
        map.put("deliveryDate", new Date());
        orderDao.deliverOrderById(map);
    }

    /**
     * <p>名称：</p>
     * <p>描述：根据会员ID和订单组号查询所有订单的总金额（支付专属）</p>
     * @author：kiah
     * @param MemberId  会员ID
     * @param groupCode  订单组号
     * @return 订单总金额
     */
    @Override
    public BigDecimal countAmountPriceByPayment(Long MemberId, String groupCode, Long orderId) {
        Map map = new HashMap();
        map.put("memberId",MemberId);
        map.put("groupCode",groupCode);
        map.put("id",orderId);
        map.put("status",Constants.ORDER_STATUS_DZF);
        return orderDao.countAmountPriceByPayment(map);
    }

    /**
     * <p>名称：定时任务自动更新订单状态以及相应日期</p>
     * <p>描述：
     *TODO 更新所有待收货且日期大于30天的订单为已收货
     * 更新所有确认收货且日期大于七天的订单为交易成功
     * </p>
     * @author：kiah
     */
    @Override
    public void autoUpdateOrderStatus() {
        //自动更新所有确认收货且日期大于指定天数的订单为交易成功，并更新订单完成时间为收货时间+指定天数
        autoUpdateOrderToFinish();
        //自动取消3天内未支付的订单
        autoUpdateOrderToCancle();

    }

    /**
     * <p>名称：</p>
     * <p>描述：更新所有确认收货且日期大于七天的订单为交易成功
     *         并更新订单完成时间为收货时间+指定天数
     *         更新相应订单中商品销量
     * </p>
     * @author：kiah
     */
    public void autoUpdateOrderToFinish(){
        Map map = new HashMap();
        Map map1 = new HashMap();
        map.put("status",Constants.ORDER_STATUS_YSH);
        map.put("day",Constants.ORDER_FINISH_DAY);
        //获得订单状态为已收货并且在规定期限内还没有点击交易完成的订单ID
        List<Long> orderIds = orderDao.getOrderIdsByStatusAndDay(map);
        map1.put("oldStatus",Constants.ORDER_STATUS_YSH);
        map1.put("newStatus",Constants.ORDER_STATUS_YWC);
        map1.put("day",Constants.ORDER_FINISH_DAY);
        //更新订单状态为已完成、并更新订单完成时间
        orderDao.autoUpdateOrderToFinish(map1);
      /*  //更新订单详情中 商品销量
        if(null != orderIds && !orderIds.isEmpty()){
            for(Long orderId:orderIds){
                updateSaleNumByOrderId(orderId,true);
            }
        }*/
    }

    /**
     * <p>名称：</p>
     * <p>描述：更新所有待支付且日期大于三天的订单为交易取消
     *         并更新订单完成时间为收货时间+指定天数
     *         更新相应订单中商品销量
     * </p>
     * @author：kiah
     */
    public void autoUpdateOrderToCancle(){
        Map map = new HashMap();
        Map map1 = new HashMap();
        map.put("status",Constants.ORDER_STATUS_DZF);
        map.put("day",Constants.ORDER_CANCLE_DAY);
        //获得订单状态为已收货并且在规定期限内还没有点击交易完成的订单ID
        List<Long> orderIds = orderDao.getOrderIdsByStatusAndDay(map);
        map1.put("oldStatus",Constants.ORDER_STATUS_DZF);
        map1.put("newStatus",Constants.ORDER_STATUS_YQX);
        map1.put("day",Constants.ORDER_CANCLE_DAY);
        //更新订单状态为已取消
        orderDao.autoUpdateOrderToCancle(map1);
       /* //更新订单详情中 商品销量
        if(null != orderIds && !orderIds.isEmpty()){
            for(Long orderId:orderIds){
                updateSaleNumByOrderId(orderId,false);
            }
        }*/
    }
}
