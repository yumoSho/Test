/*
 * Copyright (c) 2014, by Besture All right reserved. 
 *
 */
package com.glanway.jty.service.product;


import com.glanway.jty.entity.product.SpecValue;
import com.glanway.jty.service.BaseService;

import java.util.Map;

/**
 * 规格值表
 */
public interface SpecValueService extends BaseService<SpecValue, Long> {
    /**
     * <p>名称：specValueBean</p>
     * <p>描述：规格值查询</p>
     * @author：LiuJC
     * @param id
     * @return
     */
    SpecValue specValueBean(Long id);

    /**
     * <p>名称：saveGoodsSpecValue</p>
     * <p>描述：保存商品的规格值</p>
     * @author：LiuJC
     * @param paramsMap
     */
    void saveGoodsSpecValue(Map<String, Object> paramsMap);


    /**
     * 规格值是否已被商品引用
     * @param specValueId
     * @return
     */
    Boolean specValueHaveBeenUsedForGoods(Long specValueId);
}
