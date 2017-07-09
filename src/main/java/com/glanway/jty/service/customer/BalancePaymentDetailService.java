package com.glanway.jty.service.customer;

import java.math.BigDecimal;

import com.glanway.jty.entity.customer.BalancePaymentDetail;
import com.glanway.jty.service.BaseService;


/**
* 余额收入/支出明细 Service
*  @author  SongZhe
*  @version 1.0
*/
public interface BalancePaymentDetailService extends BaseService<BalancePaymentDetail,Long> {
   
	/**
	 * 账户充值、提佣(佣金)
	 * @param memberId 充值会员ID、佣金所属会员ID
	 * @param consumeMemberId 支付佣金的会员ID(仅type是佣金的时候需要,充值改字段为null)
	 * @param type 充值类别(充值、佣金)
	 * @param amount 充值或提佣金额 
	 * @param giftAmount 充值赠送金额(仅充值时候使用)
	 * @param costWay 充值通道（支付宝、银联等）
	 * @return boolean
	 */
	public boolean recharge(Long memberId,Long consumeMemberId,Integer type,BigDecimal amount,BigDecimal giftAmount,String costWay);
	
	/**
	 * <p>用户余额消费方法</p>
	 * <p>仅针对用余额消费、支付的情况</p>
	 * @param memberId 会员ID
	 * @param amount 消费金额
	 * @return boolean
	 */
	public boolean consume(Long memberId,BigDecimal amount);
}