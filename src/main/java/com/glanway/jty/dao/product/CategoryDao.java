package com.glanway.jty.dao.product;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.Category;

import java.util.List;
import java.util.Map;

/**
 * 分类DAO
 */
public interface CategoryDao extends BaseDao<Category, Long> {

    /**
     * 树查询
     * @return List<Category>
     */
    List<Category> findTree();

    /**
     * 分类是否被引用
     * @param cid
     * @return
     */
    Boolean categoryHaveBeenUsedForGoods(Long cid);

    /**
     * 获取下个ID值
     * @return
     */
    Long categoryNextId();

    /**
     * 分类有子树
     */
    Boolean categoryHaveChild(Long cid);

    /**
     * 顶级分类前7个
     * @return
     */
    List<Category> topSeven(Map<String, Object> map);



}