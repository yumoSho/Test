/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: DeliveryAddressServiceImpl.java
 * PackageName: com.glanway.sh.service.personalcenter.impl
 * Date: 2016/5/315:02
 **/
package com.glanway.jty.service.personalcenter.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glanway.jty.dao.personalcenter.DeliveryAddressDao;
import com.glanway.jty.entity.personalcenter.DeliveryAddress;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.personalcenter.DeliveryAddressService;

import java.util.List;

/**
 * <p>名称:收货地址service </p>
 * <p>说明: 收货地址service</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/5/315:02
 * @version: 1.0
 */
@Service
@Transactional
public class DeliveryAddressServiceImpl extends BaseServiceImpl<DeliveryAddress,Long> implements DeliveryAddressService {

    @Autowired
    private DeliveryAddressDao deliveryAddressDao;
    /**
     * <p>名称：保存</p>
     * <p>描述：保存收货地址的方法</p>
     * @author：tianxuan
     * @see BaseServiceImpl#save(Object)
     * @param da
     */
    @Override
    public void save(DeliveryAddress da){
        if(da.getIsDefault()){
            deliveryAddressDao.defaultCancel(da.getMemberId());
        }
        deliveryAddressDao.save(da);
    }

    @Override
    public void update(DeliveryAddress da){
        if(da.getIsDefault()){
            deliveryAddressDao.defaultCancel(da.getMemberId());
        }
        deliveryAddressDao.update(da);
    }

    /**
     * <p>名称：得到收货地址</p>
     * <p>描述：得到某个会员的所有收货地址信息</p>
     * @author：tianxuan
     * @see DeliveryAddressService#findAllByMemberId(Long)
     * @param memberId 会员id
     * @return  收货地址集合
     */
    @Override
    public List<DeliveryAddress> findAllByMemberId(Long memberId) {
        return deliveryAddressDao.findAllByMemberId(memberId);
    }
}
