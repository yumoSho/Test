package com.glanway.jty.dao.product;

import com.glanway.gone.dao.MyBatisMapper;
import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.Spec;

import java.util.List;


/**
 * 规格DAO
 */
@MyBatisMapper
public interface SpecDao extends BaseDao<Spec, Long> {
    /**
     * 通过产品id查询规格和规格值
     * @param pid
     * @return
     */
    List<Spec> findSpecAndValuesByProductId(Long pid);

    /**
     * 此规格是否已被商品使用
     * @param specId
     * @return
     */
    Boolean specHaveBeenUsedForGoods(Long specId);

    /**
     * 获取下个ID值
     * @return
     */
    Long specNextId();

}