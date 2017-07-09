/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: sh
 * FileName: CouponTemplServiceImpl.java
 * PackageName: com.glanway.jty.service.operations.impl
 * Date: 2016/6/2110:33
 **/
package com.glanway.jty.service.operations.impl;

import com.glanway.jty.dao.operations.CouponDao;
import com.glanway.jty.dao.operations.CouponTemplDao;
import com.glanway.jty.entity.operations.Coupon;
import com.glanway.jty.entity.operations.CouponTempl;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.operations.CouponService;
import com.glanway.jty.utils.NumberUtil;
import com.glanway.jty.utils.PinyinUtil;
import com.glanway.jty.utils.TimeUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>名称: </p>
 * <p>说明: TODO</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/6/2110:33
 * @version: 1.0
 */
@Service
@Transactional
public class CouponServiceImpl extends BaseServiceImpl<Coupon, Long> implements CouponService {

    @Autowired
    private CouponTemplDao couponTemplDao;

    @Autowired
    private CouponDao couponDao;

    @Override
    public void saveCoupon(Coupon coupon, Long templateId) throws InvocationTargetException, IllegalAccessException {
        CouponTempl couponTempl = couponTemplDao.find(templateId);
//        Store store = storeDao.find(coupon.getStoreId());
        coupon.setStatus(Coupon.STATUS_WFF);
        coupon.setCreatedDate(new Date());
//        String prefix = PinyinUtil.getPinYinHeadChar(store.getNamegetName()).toUpperCase();
        String prefix = "JTY";
        String code  = prefix + TimeUtil.format(new Date(),TimeUtil.FORMAT_YYMMDDHHMMSS);
        BeanUtils.copyProperties(coupon, couponTempl);
        for (int i = 0; i < coupon.getAmount(); i++) {
            coupon.setId(null);
            coupon.setCode(code + NumberUtil.zeroFill(i+1,3));
            couponDao.save(coupon);
        }
    }

    @Override
    public List<Coupon> findList(Map param) {
        return couponDao.findByStore(param);
    }

    @Override
    public List<Coupon> findListByMember(Map param) {
        return couponDao.findList(param);
    }

    @Override
    public void grant(Long templateId, Long storeId,String memberIds,String couponName) {
        Map<String,Object> param = new HashMap<>();
        param.put("templateId",templateId);
        param.put("storeId",storeId);
        param.put("couponName",couponName);
        param.put("status",Coupon.STATUS_WFF);
        List<Coupon> list = couponDao.findList(param);
        String[] idStrs = memberIds.split(",");
        int len = list.size();
        int i;
        for( i = 0;i < idStrs.length; i++){
            Long memberId = Long.valueOf(idStrs[i]);
            if(i == len){
                Coupon coupon = list.get(0);
                coupon.setId(null);
                coupon.setMemberId(memberId);
                coupon.setGrantDate(new Date());
                coupon.setStatus(Coupon.STATUS_YFF);
                String prefix = PinyinUtil.getPinYinHeadChar(coupon.getStoreName()).toUpperCase();
                String code  = prefix + TimeUtil.format(new Date(),TimeUtil.FORMAT_YYMMDDHHMMSS);
                coupon.setCode(code + (idStrs.length - i));
                couponDao.save(coupon);
            }else{
                Coupon coupon = list.get(i);
                coupon.setMemberId(memberId);
                coupon.setGrantDate(new Date());
                coupon.setStatus(Coupon.STATUS_YFF);
                couponDao.update(coupon);
            }

        }
    }
}
