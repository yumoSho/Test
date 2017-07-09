package com.glanway.jty.service.product.impl;

import com.glanway.jty.dao.product.AttributeDao;
import com.glanway.jty.entity.product.Attribute;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 属性ServiceImpl
 */
@Service
@Transactional
public class AttributeServiceImpl extends BaseServiceImpl<Attribute,Long> implements AttributeService {

    private AttributeDao attributeDao;
    @Autowired
	public void setAttributeDao(AttributeDao attributeDao){
    	super.setCrudDao(attributeDao);
        this.attributeDao=attributeDao;
    }


}
