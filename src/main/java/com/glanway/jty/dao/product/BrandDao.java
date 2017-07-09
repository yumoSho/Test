package com.glanway.jty.dao.product;

import com.glanway.gone.dao.MyBatisMapper;
import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.Brand;

import java.util.List;
import java.util.Map;


/**
 * 品牌DAO
 */
@MyBatisMapper
public interface BrandDao extends BaseDao<Brand, Long> {
    /**
     * 获取下个ID值
     * @return
     */
    Long brandNextId();

    /**
     * 获取顶级分类(及子分类) 所管理的品牌
     * @param  map 分类条件参数
     */
    List<Brand> findTopBrandRecommend(Map<String, Object> map);

}