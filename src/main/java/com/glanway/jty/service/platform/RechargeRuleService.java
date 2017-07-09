package com.glanway.jty.service.platform;


import com.glanway.jty.entity.platform.RechargeRule;
import com.glanway.jty.service.BaseService;

import java.util.List;


public interface RechargeRuleService extends BaseService<RechargeRule, Long> {
    List<RechargeRule> findList();
}
