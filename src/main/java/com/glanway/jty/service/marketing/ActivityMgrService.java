/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 *
 * ProjectName: hg
 * FileName: ActivityMgrService.java
 * PackageName: com.glanway.hg.service.activity
 * Date: 2016年4月29日下午5:48:34
 **/
package com.glanway.jty.service.marketing;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.marketing.ActivityMgr;
import com.glanway.jty.service.BaseService;

/**
 * <p>名称：活动管理service接口</p>
 * <p>描述：活动管理service接口</p>
 * @author：LiuJC
 * @date：2016/5/13 14:04
 * @version: 1.0
 */
public interface ActivityMgrService extends BaseService<ActivityMgr,Long> {
    /*活动管理商品查询*/

    /**
     * <p>名称：活动管理商品查询</p>
     * <p>描述：活动管理商品查询</p>
     * @author：LiuJC
     * @param id 活动id
     * @return
     */
    ActivityMgr findDetail(Long id,Long provinceId);
}
