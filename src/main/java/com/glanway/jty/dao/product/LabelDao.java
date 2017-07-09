package com.glanway.jty.dao.product;

import com.glanway.gone.dao.MyBatisMapper;
import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.Brand;
import com.glanway.jty.entity.product.Label;

import java.util.List;
import java.util.Map;


/**
 * 标签DAO
 */
@MyBatisMapper
public interface LabelDao extends BaseDao<Label, Long> {
    /**
     * 获取下个ID值
     * @return
     */
    Long labelNextId();

}