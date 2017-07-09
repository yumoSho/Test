package com.glanway.jty.service.platform.impl;


import com.glanway.jty.dao.platform.RechargeRuleDao;
import com.glanway.jty.entity.platform.RechargeRule;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.platform.RechargeRuleService;
import org.simpleframework.xml.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transient
public class RechargeRoleServiceImpl extends BaseServiceImpl<RechargeRule, Long> implements RechargeRuleService {

    @Autowired
    private RechargeRuleDao rechargeRuleDao;

    @Override
    public List<RechargeRule> findList() {
        return rechargeRuleDao.findAll();
    }
}
