package com.glanway.jty.dao.order;



import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.order.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderDao extends BaseDao<Order, Long> {

    String MEMBER_FILTERS_PROP = "_member_filters";

    String ORDER_FILTERS_PROP = "_order_filters";

    List<Order> findManyForPage(Map<String, Object> params);

    int findCountForPage(Map<String, Object> params);

    int countOrderByMemberId(Map<String, Object> params);

    Order getOrderById(long id);

    void updateOrderStatusById(Map<Object, Object> map);

    void receiveOrderById(Map<Object, Object> map);

    void payOrderById(Map<Object, Object> map);

    void payOrderByGroupCode(Map<Object, Object> map);

    void finishOrderById(Map<Object, Object> map);

    void deliverOrderById(Map<Object, Object> map);

    void updateDeliverCodeByOrderId(Map<Object, Object> map);

    Map<String, Object> countStatus(Map<Object, Object> map);

    List<Order> findOrderByMemberId(Map<String, Object> map);

    void saveOrder(Order order);

    void deleteOrderById(long id);

    int isOrderInMember(Map<String, Object> map);

    BigDecimal countAmountPriceByPayment(Map<String, Object> map);

    void autoUpdateOrderToFinish(Map<String, Object> map);

    void autoUpdateOrderToCancle(Map<String, Object> map);

    List<Long> getOrderIdsByStatusAndDay(Map<String, Object> map);

    int countComment(Map param);

}
