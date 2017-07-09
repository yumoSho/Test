package com.glanway.jty.dao.product;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.Model;

/**
 * 模型DAO
 */
public interface ModelDao extends BaseDao<Model, Long> {
    /**
     * 分类是否关联了模型
     * @param mid 模型ID
     * @return boolean 是否已和分类关联
     */
    Boolean isReferenced(Long mid);

    /**
     * 被商品关联的不能删除
     * @param modelId
     */
    Boolean modelHaveBeenUsedForGoods(Long modelId);

    /**
     * 某条模型规格值是否引用
     * @param modelSpecId
     */
    Boolean modelSpecHaveBeenUsedForGoods(Long modelSpecId);

    /**
     * 获取下个ID值
     * @return
     */
    Long modelNextId();
}
