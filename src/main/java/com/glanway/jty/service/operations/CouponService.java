package com.glanway.jty.service.operations;

import com.glanway.jty.entity.operations.Coupon;
import com.glanway.jty.service.BaseService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 优惠券service接口
 *  @author  tianxuan
 *  @Time     2016/4/1
 */
public interface CouponService extends BaseService<Coupon,Long> {
    /**
     * 保存优惠券
     * @param coupon
     * @param templateId
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    void saveCoupon(Coupon coupon, Long templateId) throws InvocationTargetException, IllegalAccessException;

    /**
     * <p>名称：</p>
     * <p>描述：发放优惠券</p>
     * @author：tianxuan
     * @param templateId  模板id
     * @param storeId  店铺id
     * @param memberIds  会员nid
     * @param couponName  优惠券名称
     */
    void grant(Long templateId, Long storeId, String memberIds, String couponName);

    /**
     * 查询所有可发放优惠券
     * @param param
     * @return
     */
    List<Coupon> findList(Map param);

    List<Coupon> findListByMember(Map param);
}
