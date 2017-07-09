package com.glanway.jty.entity.customer;

import java.math.BigDecimal;
import java.util.Date;

import com.glanway.jty.entity.BaseEntity;

/**
 * 余额收入/支出明细 实体类
 * @author SongZhe
 *
 */
public class BalancePaymentDetail extends BaseEntity{
	
	/**
	 * CONSUME_MEMBER_ID
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_RECHARGE = 1;//充值
	public static final int TYPE_REBATE = 2;//佣金
	public static final int TYPE_CONSUME = 3;//账户余额消费
	 
	/**@Fields userId : 会员ID */ 
	private Long memberId;
	
	/**@Fields consumeMemberId :  用户记录提佣 的消费会员的ID*/ 
	private Long consumeMemberId;
	
	/**@Fields consumeMember :  用户记录提佣 的消费会员对象*/ 
	private Member consumeMember;
	
	/**@Fields rechargeNum : 流水单号 */ 
	private String rechargeCode;
	
	/**@Fields type : 收支明细类别 */ 
	private Integer type;
	
/*	*//**@Fields type : 处理状态 *//* 
	private boolean isSuccess;*/
	
	/**@Fields amount : 收入/支出金额 */ 
	private BigDecimal amount;
	
	/**@Fields giftAmount : 赠送金额（仅用于充值返利）*/ 
	private BigDecimal giftAmount;
	
	/**@Fields amount : 余额 */ 
	private BigDecimal balance;
	
	/**@Fields costWay : 充值方式 */ 
	private String costWay;
	
	/**@Fields remittanceNum : 汇款单号 */ 
//	private String remittanceNum;
		
	/**@Fields remark : 备注 */ 
	private String remark;

	/**@Fields startTime : 收入支出时间 */ 
	private Date startTime;
	
	/**@Fields deleted : 是否删除 */ 
	private boolean deleted = Boolean.FALSE;

	

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getRechargeCode() {
		return rechargeCode;
	}

	public void setRechargeCode(String rechargeCode) {
		this.rechargeCode = rechargeCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCostWay() {
		return costWay;
	}

	public void setCostWay(String costWay) {
		this.costWay = costWay;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Member getConsumeMember() {
		return consumeMember;
	}

	public void setConsumeMember(Member consumeMember) {
		this.consumeMember = consumeMember;
	}

	public Long getConsumeMemberId() {
		return consumeMemberId;
	}

	public void setConsumeMemberId(Long consumeMemberId) {
		this.consumeMemberId = consumeMemberId;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}
	

}