package com.glanway.jty.dao.order;



import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.order.OrderDetail;

import java.util.List;
import java.util.Map;

public interface OrderDetailDao extends BaseDao<OrderDetail, Long> {
	List<OrderDetail> getOrderDetailByOrderId(long id);

	void saveOrderDetail(OrderDetail orderDetail);

	void updateReturnedNum(Map<String, Object> map);

	/**
	 * <p>名称：updateCommented</p>
	 * <p>描述：更新订单状态</p>
	 * @author：LiuJC
	 */
	void updateCommented(Long id);
}
