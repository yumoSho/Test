package com.glanway.jty.dao.operations;



import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.operations.CouponTempl;

/**
 * 优惠券模板Dao
 *  @author  tianxuan
 *  @Time     2016/4/1
 */
public interface CouponTemplDao extends BaseDao<CouponTempl,Long> {
    /**
     * 根据ID批量删除
     * @param ids
     */
    void deleteBatch(Long[] ids);
}
