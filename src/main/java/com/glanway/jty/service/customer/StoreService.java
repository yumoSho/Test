package com.glanway.jty.service.customer;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.customer.Store;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 店铺service接口
 *  @author  tianxuan
 *  @Time     2016/4/1
 */
public interface StoreService extends BaseService<Store,Long> {

    /**
     * 保存店铺
     * @param store
     * @param labels
     * @throws CustomException
     */
    void saveStore(Store store,String[] labels) throws CustomException;

    /**
     * 更新店铺
     * @param store
     * @param labels
     * @throws CustomException
     */
    void updateStore(Store store,String[] labels) throws CustomException;

    /**
     * <p>名称：店铺是否存在</p>
     * <p>描述：店铺是否存在</p>
     * @author：tianxuan
     * @param param
     * @return
     */
    Boolean isExits(Map param);

    /**
     * 导出的查询
     * @param filters
     * @param pageable
     * @return
     */
    List<Store> findToExport(Filters filters, Pageable pageable);

    /**
     * 根据名称查找店铺
     * @param offset
     * @param size
     * @param keyword
     * @return
     */
    List<Store> findByName(int offset, int size, String keyword);

    /**
     * 查询出所有店铺的名字和id 给手机端使用
     * @return
     */
    List<Store> findListForMobile();
}
