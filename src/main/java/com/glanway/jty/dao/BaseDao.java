package com.glanway.jty.dao;

import java.io.Serializable;

import com.glanway.gone.dao.CrudDao;

/**
 * 基础 Dao
 *
 * @author vacoor
 */
public interface BaseDao<E, ID extends Serializable> extends CrudDao<E, ID> {
    String OFFSET_PROP = "_offset";
    String MAX_RESULTS_PROP = "_maxResults";
    String FILTERS_PROP = "_filters";
    String SORT_PROP = "_sort";
    String PROVINCE ="_province";

    void delete(ID id);
}
