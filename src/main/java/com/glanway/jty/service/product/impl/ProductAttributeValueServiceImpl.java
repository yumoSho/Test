package com.glanway.jty.service.product.impl;

import com.glanway.jty.dao.product.ProductAttributeValueDao;
import com.glanway.jty.entity.product.ProductAttributeValue;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.ProductAttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProductAttributeValueServiceImpl extends BaseServiceImpl<ProductAttributeValue,Long> implements ProductAttributeValueService {
    @Autowired
    private ProductAttributeValueDao productAttributeValueDao;


    public void insert(ProductAttributeValue pav){
        productAttributeValueDao.insert(pav);
    }

    @Override
    public ProductAttributeValue getAttributeValueByProductIdAndAttributeId(ProductAttributeValue pav) {
        return productAttributeValueDao.getAttributeValueByProductIdAndAttributeId(pav);
    }

}
