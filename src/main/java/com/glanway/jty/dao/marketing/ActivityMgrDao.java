package com.glanway.jty.dao.marketing;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.marketing.ActivityMgr;

import java.util.Map;

/**
 * 活动管理Dao
 *  @author  LiuJc
 *  @Time     2016/5/5
 */
public interface ActivityMgrDao extends BaseDao<ActivityMgr,Long> {

    ActivityMgr findDetail(Map<String, Object> map);

    int  countByProvinceId(Map<String,Object> map);
}
