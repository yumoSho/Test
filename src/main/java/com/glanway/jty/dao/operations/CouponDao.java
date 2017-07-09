package com.glanway.jty.dao.operations;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.operations.Coupon;

import java.util.List;
import java.util.Map;

/**
 * 优惠券Dao
 *  @author  tianxuan
 *  @Time     2016/4/1
 */
public interface CouponDao extends BaseDao<Coupon,Long> {
    /**
     * 根据ID批量删除
     * @param ids
     */
    void deleteBatch(Long[] ids);

    List<Coupon> findByStore(Map param);

    List<Coupon> findList(Map param);
}
