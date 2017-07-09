package com.glanway.jty.dao.product;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.ModelSpec;

import java.util.List;

/**
 * 模型规格DAO
 */
public interface ModelSpecDao extends BaseDao<ModelSpec, Long> {
    /**
     * 通过模型ID来查找模型所关联的规格
     * @param mid 模型ID
     * @return List<ModelSpec> 模型集合
     */
    List<ModelSpec> findModelSpecs(Long mid);

    /**
     * 通过模型ID 删除模型规格
     * @param mid
     */
    void deleteModelSpecs(Long mid);

    /**
     * 模型规格是否已被引用
     * @param msId 模型规格id
     * @return boolean 是否被引用
     */
    Boolean isReferenced(Long msId);
}