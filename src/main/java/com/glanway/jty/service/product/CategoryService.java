package com.glanway.jty.service.product;


import com.glanway.jty.entity.product.Category;
import com.glanway.jty.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 分类Service
 */
public interface CategoryService extends BaseService<Category, Long> {

    /**
     * 树查询
     * @return 用于js tree;
     */
    List<Category> findCategoryTree();

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
     * 顶级分类前查询
     * @return
     */
    List<Category> topCategory(Integer offset, Integer maxResults);

    /**
     * 首页全部分类和品牌
     * @return
     */
    void  updateIndexCategoryAndBrand();

 /*   *//**
     * 查找热销产品分类
     *
     * @param bankId 银行ID
     * @return
     *//*
    List<TopCategoryIncludeGoodsDto> TopCategorySales(Long bankId, int anyOne) ;*/





    /**
     * <p>名称：getParmsList</p>.
     * <p>描述：并排分类树（名字不知道怎么起了）</p>
     * @author：LiuJC
     * @param websiteCategoryTree
     * @param longListMap
     * @date：2016/7/11 10:18
     * @return Map<Long, List<Long>>  Map<一级分类, List<所有子级分类>>
     */
    Map<Long, List<Long>> getParmsList(List<Category> websiteCategoryTree, Map<Long, List<Long>> longListMap);

    /**
     * <p>名称：getCategoryTree</p>
     * <p>描述：分类树 从缓存中取得</p>
     * @author：LiuJC
     * @return
     */
    public List<Category> getCategoryTree();
}



