package com.glanway.jty.service.customer.impl;

import com.glanway.jty.dao.customer.BalancePaymentDetailDao;
import com.glanway.jty.entity.customer.BalancePaymentDetail;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.customer.BalancePaymentDetailService;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.utils.DateUtils;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 余额收入/支出明细 Service实现类
 * @author SongZhe
 */
@Service("BalancePaymentDetailService")
@Transactional(propagation=Propagation.REQUIRES_NEW,isolation=Isolation.REPEATABLE_READ)
public class BalancePaymentDetailServiceImpl extends BaseServiceImpl<BalancePaymentDetail, Long> implements BalancePaymentDetailService {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BalancePaymentDetailDao balancePaymentDetailDao;
	
	@Override
	public boolean recharge(Long memberId,Long consumeMemberId,Integer type, BigDecimal amount,BigDecimal giftAmount, String costWay) {
		
		try {
			BalancePaymentDetail balancePaymentDetail = new BalancePaymentDetail();
			balancePaymentDetail.setRechargeCode(getbalancePaymentDetailCode(new Date()));
			balancePaymentDetail.setMemberId(memberId);
			if(consumeMemberId != null){
				balancePaymentDetail.setConsumeMemberId(consumeMemberId);
			}
			
			if(type == BalancePaymentDetail.TYPE_RECHARGE && giftAmount == null){
				giftAmount = BigDecimal.ZERO;
			}
			balancePaymentDetail.setType(type);
			balancePaymentDetail.setAmount(amount);
			balancePaymentDetail.setGiftAmount(giftAmount);
			balancePaymentDetail.setCostWay(costWay);
			Date currentDate = new Date();
			balancePaymentDetail.setStartTime(currentDate);
			balancePaymentDetail.setCreatedBy(memberId);
			balancePaymentDetail.setCreatedDate(currentDate);
			if (BalancePaymentDetail.TYPE_RECHARGE == type) {
				balancePaymentDetail.setRemark("充值");
			} else if (BalancePaymentDetail.TYPE_REBATE == type) {
				balancePaymentDetail.setRemark("佣金");
			}
			Member member = memberService.find(memberId);
			BigDecimal memberBalance = member.getBalance();
			if (memberBalance == null) {
				memberBalance = new BigDecimal(0.00);
			}
			
			//余额累加
			BigDecimal resultBalance = memberBalance.add(amount);
			
			//充值有返利，需要累加到余额里
			if(giftAmount != null && giftAmount.compareTo(BigDecimal.ZERO) > 0){
				resultBalance = resultBalance.add(giftAmount);
			}
			
			balancePaymentDetail.setBalance(resultBalance);
			save(balancePaymentDetail);
			//更新T_MEMBER表该用户的余额
			Member _member = new Member();
			_member.setId(memberId);
			_member.setBalance(resultBalance);
			memberService.update(_member);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean consume(Long memberId, BigDecimal amount) {
		// TODO Auto-generated method stub
		
		try {
			Member member = memberService.find(memberId);
			if (member.getBalance() == null || member.getBalance().compareTo(amount) < 0
					|| amount.compareTo(BigDecimal.ZERO) < 0) {// 余额不足 或者消费金额不合法
				return false;
			}
			BalancePaymentDetail balancePaymentDetail = new BalancePaymentDetail();
			balancePaymentDetail.setRechargeCode(getbalancePaymentDetailCode(new Date()));
			balancePaymentDetail.setMemberId(memberId);
			balancePaymentDetail.setAmount(amount);
			balancePaymentDetail.setType(BalancePaymentDetail.TYPE_CONSUME);
			balancePaymentDetail.setRemark("余额消费");
			Date currentDate = new Date();
			balancePaymentDetail.setStartTime(currentDate);
			balancePaymentDetail.setCreatedBy(memberId);
			balancePaymentDetail.setCreatedDate(currentDate);
			BigDecimal resultDecimal = member.getBalance().subtract(amount);
			balancePaymentDetail.setBalance(resultDecimal);
			save(balancePaymentDetail);
			//更新T_MEMBER表该用户的余额
			Member _member = new Member();
			_member.setId(memberId);
			_member.setBalance(resultDecimal);
			memberService.update(_member);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
   
	public String getbalancePaymentDetailCode(Date date){
        BalancePaymentDetail balancePaymentDetail = new BalancePaymentDetail();
        String baymentDetailCode = "BP";
        int x = 0;
        do{
            Random r = new Random();
            x = r.nextInt(9000)+1000;//为变量赋随机值1000-9999
            baymentDetailCode = DateUtils.date2Str(date, DateUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);
            baymentDetailCode = baymentDetailCode+String.valueOf(x);
            Map<String,Object> map = Maps.newConcurrentMap();
            map.put("rechargeCode", baymentDetailCode);
            balancePaymentDetail = balancePaymentDetailDao.findOne(map);
        } while(null != balancePaymentDetail);
        return baymentDetailCode+String.valueOf(x);
    }
 
}