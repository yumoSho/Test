package com.glanway.jty.dao.customer;





import java.util.List;
import java.util.Map;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.customer.Store;

/**
 * 店铺Dao
 *  @author  tianxuan
 *  @Time     2016/4/1
 */
public interface StoreDao extends BaseDao<Store,Long> {
    /**
     * 根据ID批量删除
     * @param ids
     */
    void deleteBatch(Long[] ids);

    Integer isExist(Map param);

    /**
     * 根据名字检索 select2
     * @param param
     * @return
     */
    List<Store> findByName(Map param);

    /**
     * 查询出所有店铺的名字和id 给手机端使用
     * @return
     */
    List<Store> findListForMobile();
}
