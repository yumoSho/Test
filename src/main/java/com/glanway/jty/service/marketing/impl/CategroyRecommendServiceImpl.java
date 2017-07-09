package com.glanway.jty.service.marketing.impl;


import com.glanway.jty.dao.marketing.CategoryRecommendDao;
import com.glanway.jty.entity.marketing.CategoryRecommend;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.marketing.CategoryRecommendService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategroyRecommendServiceImpl extends BaseServiceImpl<CategoryRecommend, Long> implements CategoryRecommendService {

	Logger logger = Logger.getLogger(CategroyRecommendServiceImpl.class);

	@Autowired
	private CategoryRecommendDao categoryRecommendDao;

}
