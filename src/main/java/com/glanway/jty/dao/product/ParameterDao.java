/*
 * Copyright (c) 2014, by Besture All right reserved. 
 *
 */
package com.glanway.jty.dao.product;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.Parameter;

import java.util.List;

/**
 * 模型参数DAO
 */
public interface ParameterDao extends BaseDao<Parameter, Long> {
    /**
     * 通过模型ID 来查找模型参数信息
     * @param mid 模型id
     * @return List<Parameter> 参数数组
     */
    List<Parameter> findModelParameterDetail(Long mid);

    /**
     * 通过模型id 删除所有的模型参数
     * @param mid
     */
    void deleteModelParameters(Long mid);

    /**
     * 通过父参数id 删除所有子参数
     * @param pid
     */
    void deleteByParentId(Long pid);
}