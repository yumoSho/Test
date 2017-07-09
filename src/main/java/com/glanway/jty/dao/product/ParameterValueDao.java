package com.glanway.jty.dao.product;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.ParameterValue;

/**
 * 模型参数值Dao
 */
public interface ParameterValueDao extends BaseDao<ParameterValue, Long> {
    /**
     * 通过产品id 删除参数值
     * @param pid
     */
    void deleteParameterValueByProductId(Long pid);
}