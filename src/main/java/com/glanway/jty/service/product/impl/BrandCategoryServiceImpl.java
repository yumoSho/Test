package com.glanway.jty.service.product.impl;

import com.glanway.jty.dao.product.BrandCategoryDao;
import com.glanway.jty.entity.product.BrandCategory;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.BrandCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 品牌分类service
 */
@Service
@Transactional
public class BrandCategoryServiceImpl extends BaseServiceImpl<BrandCategory, Long> implements BrandCategoryService {

    private BrandCategoryDao brandCategoryDao;

    @Autowired
    public void setBrandCategoryDao(BrandCategoryDao brandCategoryDao) {
        super.setCrudDao(brandCategoryDao);
        this.brandCategoryDao = brandCategoryDao;
    }

    @Override
    public void deleteBrandCateById(Long cid) {
        brandCategoryDao.deleteBrandCateByid(cid);
    }
}