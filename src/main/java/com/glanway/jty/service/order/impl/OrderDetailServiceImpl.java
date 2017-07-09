package com.glanway.jty.service.order.impl;


import com.glanway.jty.dao.order.OrderDetailDao;
import com.glanway.jty.entity.order.OrderDetail;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.order.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderDetailService")
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail, Long> implements OrderDetailService {
	@Autowired
    private OrderDetailDao orderDetailDao;
	@Override
	public List<OrderDetail> getOrderDetailByOrderId(long id) {
		return orderDetailDao.getOrderDetailByOrderId(id);
	}

	/**
	 *<p>名称：</p>
	 * <p>描述：根据订单ID，商品ID更新订单详情中已申请退换货数量</p>
	 * @author：kiah
	 * @param orderId 订单ID
	 * @param goodsId	商品ID
	 * @param returnedNum 退换货数量
     */
	@Override
	public void updateReturnedNum(Long orderId, Long goodsId, int returnedNum) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderId",orderId);
		map.put("goodsId",goodsId);
		map.put("returnedNum",returnedNum);
		orderDetailDao.updateReturnedNum(map);
	}
	/**
	 * <p>名称：updateCommented</p>
	 * <p>描述：更新订单状态</p>
	 * @author：LiuJC
	 */
	@Override
	public void updateCommented(Long id) {
		orderDetailDao.updateCommented(id);
	}
}
