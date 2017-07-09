package com.glanway.jty.service.order;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.order.Order;
import com.glanway.jty.service.BaseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService extends BaseService<Order, Long> {
	/**
	 * <p>名称：查找订单</p>
	 * <p>描述：根据条件查找订单</p>
	 * @author：kiah
	 * @param beginDate
	 * @param endDate
	 * @param memberFilters
	 * @param filters
	 * @param pageable
     * @return
     */
	Page<Order> findPage(String beginDate, String endDate, Filters memberFilters, Filters filters, Pageable pageable,Filters commFilters);

	/**
	 * <p>名称：导出订单</p>
	 * <p>描述：查找导出时需要的订单结果集</p>
	 * @author：kiah
	 * @param beginDate
	 * @param endDate
	 * @param memberFilters
	 * @param filters
	 * @param pageable
     * @return
     */
	List<Order> ListForExport(String beginDate, String endDate, Filters memberFilters, Filters filters, Pageable pageable);

	/**
	 * <p>名称：获得订单</p>
	 * <p>描述：根据订单ID获得订单</p>
	 * @author：kiah
	 * @param id 订单ID
	 * @return
     */
	Order getOrderById(long id);

	/**
	 * <p>名称：更新订单状态</p>
	 * <p>描述：根据订单ID更新订单状态</p>
	 * @author：kiah
	 * @param id 订单ID
	 * @param status 状态
     */
	void updateOrderStatusById(long id, int status);

	/**
	 * <p>名称：确认收货</p>
	 * <p>描述：根据订单ID，确认此订单收货并更新收货时间为当前时间</p>
	 * @author：kiah
 	 * @param id 订单ID
     */
	void receiveOrderById(long id);

	/**
	 * <p>名称：订单支付</p>
	 * <p>描述：根据订单ID，确认此订单已支付并更新支付时间为当前时间</p>
	 * @author：kiah
	 * @param id 订单ID
	 * @param payment 支付方式
	 * @param payPrice 支付金额
	 */
	void payOrderById(long id, String payment, Double payPrice);

	/**
	 * <p>名称：订单支付</p>
	 * <p>描述：根据订单组号，确认此订单已支付并更新支付时间为当前时间</p>
	 * @author：kiah
	 * @param groupCode 订单组号
	 * @param payment 支付方式
	 * @param bank 银行卡号（银联支付）
	 */
	void payOrderByGroupCode(String groupCode, String payment);

	/**
	 * <p>名称：添加物流单号</p>
	 * <p>描述：根据订单ID更新此订单物流单号</p>
	 * @author：kiah
	 * @param orderId 订单ID
	 * @param deliverCode 物流单号
     */
	void updateDeliverCodeByOrderId(long orderId, String deliverCode, String deliverCompanyCode);

	/**
	 * <p>名称：查找特定状态订单个数</p>
	 * <p>描述：查找订单状态为status1和status2的订单个数，返货map 接收指的Key为COUNT1和COUNT2 </p>
	 * @author：kiah
	 * @param status1
	 * @param status2
	 * @param memberId 会员ID，此字段可为空
     * @return
     */
	Map<String,Object> countStatus(Integer status1, Integer status2, Integer status3, Long memberId);

	/**
	 * 统计未评价的商品数量
	 * @param param
	 * @return
     */
	int countComment(Map param);


	/**
	 * <p>名称：根据会员ID查找订单</p>
	 * <p>描述：根据会员ID以及其他条件查找订单</p>
	 * @author：kiah
	 * @param memberId 会员id
	 * @param status	订单状态
	 * @param para		商品标题或订单号
	 * @param beginDate	开始日期（基于订单创建日期查询）
	 * @param endDate	结束日期（基于订单创建日期查询）
	 * @param page		分页用，表示当前为第几页
     * @param size		分页用，表示每页显示个数
     * @return
     */
	Page<Order> findOrderByMemberID(Long memberId, Integer status, String para, String beginDate, String endDate, int page, int size);

	/**
	 * 拿到会员中心首页的两条订单列表信息
	 * @param memberId
	 * @return
	 */
	List<Order> findOrderListByMemberID(Long memberId);
	/**
	 *<p>名称：保存订单</p>
	 * <p>描述：保存订单基本信息，保存订单详情，保存订单发票信息</p>
	 * @author：kiah
	 * @param order
     * @return
     */
	String saveOrder(Order order,List<Long> goodsIdList,Integer orderForm,Long memberId);

	/**
	 * <p>名称：保存订单LIST</p>
	 * <p>描述：保存LIST中所有订单的订单信息，订单详情，订单发票信息</p>
	 * @author：kiah
	 * @param orderList
	 * @return
     */
	String saveOrderByList(List<Order> orderList, List<Long> goodsIdList, Integer orderForm, Long memberId);

	/**
	 * <p>名称：取消订单</p>
	 * <p>描述：根据订单ID取消订单</p>
	 * @author：kiah
	 * @param orderId
     */
	void cancleOrderById(Long orderId);

	/**
	 * <p>名称：删除订单</p>
	 * <p>描述：根据订单ID删除订单</p>
	 * @author：kiah
	 * @param orderId
	 */
	void deleteOrderById(Long orderId);

	/**
	 * <p>名称：交易完成</p>
	 * <p>描述：根据订单ID将订单状态更新为交易完成并更新商品销量</p>
	 * @author：kiah
	 * @param orderId
	 */
	void finishOrderById(Long orderId);

	/**
	 * <p>名称：订单发货</p>
	 * <p>描述：根据订单ID将订单状态更新为待收货并更新订单收货时间</p>
	 * @author：kiah
	 * @param orderId
	 */
	void deliverOrderById(Long orderId);

	/**
	 * <p>名称：验证此订单是否属于此会员</p>
	 * <p>描述：验证此订单是否属于此会员</p>
	 * @author：kiah
	 * @param orderId 订单ID
	 * @param memberId	会员ID
     * @return
     */
	boolean isOrderInMember(Long orderId, Long memberId);

	/**
	 * <p>名称：</p>
	 * <p>描述：根据会员ID和订单组号查询所有订单的总金额（支付专属）</p>
	 * @author：kiah
	 * @param MemberId  会员ID
	 * @param groupCode  订单组号
     * @return 订单总金额
     */
	BigDecimal countAmountPriceByPayment(Long MemberId, String groupCode, Long orderId);

	/**
	 * <p>名称：定时任务自动更新订单状态以及相应日期</p>
	 * <p>描述：
	 *TODO 更新所有待收货且日期大于30天的订单为已收货
	 * 更新所有确认收货且日期大于七天的订单为交易成功
	 * </p>
	 * @author：kiah
	 */
	void autoUpdateOrderStatus();


}
