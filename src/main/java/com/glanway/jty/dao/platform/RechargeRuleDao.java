package com.glanway.jty.dao.platform;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.platform.Link;
import com.glanway.jty.entity.platform.RechargeRule;

import java.util.List;

/**
 * 链接管理Dao层
 * @author Songzhe
 *
 */
public interface RechargeRuleDao extends BaseDao<RechargeRule, Long> {
	List<RechargeRule> findAll();
}
