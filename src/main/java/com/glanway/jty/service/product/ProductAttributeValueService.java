package com.glanway.jty.service.product;

import com.glanway.jty.entity.product.ProductAttributeValue;

public interface ProductAttributeValueService {

    void insert(ProductAttributeValue pav);

    ProductAttributeValue getAttributeValueByProductIdAndAttributeId(ProductAttributeValue pav);
}
