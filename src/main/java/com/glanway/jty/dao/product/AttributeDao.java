
package com.glanway.jty.dao.product;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.Attribute;

import java.util.List;


public interface AttributeDao extends BaseDao<Attribute, Long> {
    /**
     * 查找模型属性
     * @param mid
     * @return
     */
    List<Attribute> findModelAttributes(Long mid);

    /**
     * 通过模型ID 删除模型属性;
     * @param mid
     */
    void deleteModelAttributes(Long mid);

}