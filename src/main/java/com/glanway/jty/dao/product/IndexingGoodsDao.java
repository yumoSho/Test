package com.glanway.jty.dao.product;

import com.glanway.jty.dto.IndexedGoods;

import java.util.List;
import java.util.Map;

/**
 */
public interface IndexingGoodsDao {
    String OFFSET = "offset";
    String MAX_RESULTS = "maxResults";

    IndexedGoods find(Long id);

    int count();

    List<IndexedGoods> findMany(Map<String, Object> paramsMap);

}
