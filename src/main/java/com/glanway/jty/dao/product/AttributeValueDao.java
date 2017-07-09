
package com.glanway.jty.dao.product;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.AttributeValue;

import java.util.Map;

public interface AttributeValueDao extends BaseDao<AttributeValue, Long> {
    /**
     * 通过属性ID 删除所有的属性值
     * @param aid
     */
    void deleteByAttributeId(Long aid);

    /**
     * 通过productId 删除产品自定义属性值
     * @param id
     */
    void deleteProductCustomAttributeValueByProductId(Long id);

    /**
     * 删除通过productID属性值
     * @param pid
     */
    void deleteProductAttributeValue(Long pid);

    /**
     * 保存商品属性值
     * @param params
     */
    void saveProductAttributeValue(Map<String, Object> params);
}