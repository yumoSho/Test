package com.glanway.jty.service.product.impl;

import com.glanway.jty.dao.product.AttributeValueDao;
import com.glanway.jty.entity.product.AttributeValue;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.AttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 属性值ServiceImpl
 */
@Service
@Transactional
public class AttributeValueServiceImpl extends BaseServiceImpl<AttributeValue,Long> implements AttributeValueService {

	private AttributeValueDao attributeValueDao;

	@Autowired
	public void setAttributValueDao(AttributeValueDao attributeValueDao){
		super.setCrudDao(attributeValueDao);

	}


}
