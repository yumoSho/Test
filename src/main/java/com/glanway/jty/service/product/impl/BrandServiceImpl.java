package com.glanway.jty.service.product.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.dao.product.BrandDao;
import com.glanway.jty.entity.product.Brand;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.BrandService;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 品牌service实现类
 */
@Service
@Transactional
public class BrandServiceImpl extends BaseServiceImpl<Brand,Long> implements BrandService {
	Logger logger = Logger.getLogger(BrandServiceImpl.class);

	@Autowired
    private BrandDao brandDao;

    @Autowired
    public void setBrandDao(BrandDao brandDao) {
        this.brandDao = brandDao;
        super.setCrudDao(brandDao);
    }

    /**
     * <p>名称：getEnableBrand</p>
     * <p>描述：获取所有已开启的品牌列表</p>
     * @author：LiuJC
     * @return List<Brand> 品牌集合;
     */
    public List<Brand> getEnableBrand(){
        Filters filters = Filters.create();
        filters.eq("enabled",1);
        List<Brand> enabledBrands = this.findMany(filters, (PageRequest) null);
        return  enabledBrands;
    }

    /**
     * <p>名称：brandNextId</p>
     * <p>描述：获取下一个品牌编号</p>
     * @author：LiuJC
     * @return
     */
    @Override
    public Long brandNextId() {
        return brandDao.brandNextId();
    }

    /**
     * <p>名称：save</p>
     * <p>描述：保存</p>
     * @author：LiuJC
     * @param brand
     */
    @Override
    public void save(Brand brand) {
        brand.setBrandCode(getBrandCode());
        super.save(brand);
    }

    /**
     * <p>名称：</p>
     * <p>描述：获取品牌code</p>
     * @author：LiuJC
     * @return
     */
    private String getBrandCode(){
        Long nextId = brandNextId();
        nextId=null==nextId?0:nextId;
        return "B"+(nextId+1002);
    }

    /**
     * <p>名称：</p>
     * <p>描述：获取推荐品牌</p>
     * @author：LiuJC
     * @param  offset
     * @param  maxResult
     * @param  topCategoryId 顶级分类Id
     * @return
     */
    @Override
    public List<Brand> findTopBrandRecommend(Integer offset,Integer maxResult,Long topCategoryId) {
        HashMap<String, Object> findFilter = Maps.newHashMap();
        findFilter.put("_offset",offset);
        findFilter.put("_maxResults",maxResult);
        findFilter.put("categoryId",topCategoryId);
        return brandDao.findTopBrandRecommend(findFilter);
    }
}