package com.glanway.jty.dao.product;

import com.glanway.jty.entity.product.ProductAttributeValue;

public interface ProductAttributeValueDao {

    void insert(ProductAttributeValue pav);
    ProductAttributeValue getAttributeValueByProductIdAndAttributeId(ProductAttributeValue pav);
}
