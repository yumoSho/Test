package com.glanway.jty.dao.product;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.SpecValue;

import java.util.List;
import java.util.Map;

/**
 * 规格值dao
 */
public interface SpecValueDao extends BaseDao<SpecValue, Long> {

    void deleteSpecValue(Long sid);

    void saveGoodsSpecValue(Map<String, Object> paramsMap);

    SpecValue specValueBean(Long id);

    List<SpecValue> findSpecValByspId(Long specId);

    Long isNot(Map<String, Object> map);

    void deleteGoodsSpecValue(Long gid);

    /**
     * 规格值是否已被商品引用
     * @param specValueId
     * @return
     */
    Boolean specValueHaveBeenUsedForGoods(Long specValueId);
}