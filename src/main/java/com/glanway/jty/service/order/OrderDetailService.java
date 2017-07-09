package com.glanway.jty.service.order;



import com.glanway.jty.entity.order.OrderDetail;
import com.glanway.jty.service.BaseService;

import java.util.List;

public interface OrderDetailService extends BaseService<OrderDetail, Long> {
	
	List<OrderDetail> getOrderDetailByOrderId(long id);

	/**
	 *<p>名称：</p>
	 * <p>描述：根据订单ID，商品ID更新订单详情中已申请退换货数量</p>
	 * @author：kiah
	 * @param orderId 订单ID
	 * @param goodsId	商品ID
	 * @param returnedNum 退换货数量
	 */
	void updateReturnedNum(Long orderId, Long goodsId, int returnedNum);

	/**
	 * <p>名称：updateCommented</p>
	 * <p>描述：更新订单状态</p>
	 * @author：LiuJC
	 */
	void updateCommented(Long id);

}
