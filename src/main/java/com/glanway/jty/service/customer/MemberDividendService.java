package com.glanway.jty.service.customer;

import com.glanway.jty.controller.admin.customer.GradeDetailVo;
import com.glanway.jty.entity.customer.Grade;
import com.glanway.jty.entity.customer.MemberDividend;
import com.glanway.jty.service.BaseService;

/**
* </b>Member Service
*  @author  generator
*  @Time     2016-04-12
*  @version 1.0
*/
public interface MemberDividendService extends BaseService<MemberDividend,Long> {

    void mySave( GradeDetailVo gradeDetailVo);

    /**
     * 会员返利
     * @param memberId
     * @param orderId
     */
    void recharge(Long memberId, Long orderId,String costWay);

}
