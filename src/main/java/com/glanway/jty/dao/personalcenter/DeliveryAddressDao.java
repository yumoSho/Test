/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: DeliveryAddressDao.java
 * PackageName: com.glanway.sh.dao.personalcenter
 * Date: 2016/5/315:04
 **/
package com.glanway.jty.dao.personalcenter;



import java.util.List;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.personalcenter.DeliveryAddress;

/**
 * <p>名称: 收货地址Dao</p>
 * <p>说明: 收货地址Dao</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：Administrator
 * @date：2016/5/315:04
 * @version: 1.0
 */
public interface DeliveryAddressDao extends BaseDao<DeliveryAddress,Long> {
    /**
     * <p>名称：取消默认收货地址</p>
     * <p>描述：取消默认收货地址</p>
     * @author：tianxuan
     * @param memberId  会员id
     */
    void defaultCancel(Long memberId);

    /**
     * <p>名称：得到某会员的所有收货地址</p>
     * <p>描述：得到某会员的所有收货地址</p>
     * @author：tianxuan
     * @param memberId
     * @return  收货地址集合
     */
    List<DeliveryAddress> findAllByMemberId(Long memberId);
}
