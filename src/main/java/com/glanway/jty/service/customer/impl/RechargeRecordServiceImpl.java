package com.glanway.jty.service.customer.impl;

import com.glanway.jty.entity.customer.RechargeRecord;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.customer.RechargeRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/8/2.
 */
@Service
@Transactional
public class RechargeRecordServiceImpl extends BaseServiceImpl<RechargeRecord,Long> implements RechargeRecordService{
}
