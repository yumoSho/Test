package com.glanway.jty.service.product;

import java.util.Map;

/**
 * 货品搜索服务
 *
 * @beta
 * @author vacoor
 */
public interface GoodsSearchService {

    /**
     * 商品筛选说明:
     * <p/>
     * 属性筛选的入口是分类, 只有选择了分类才允许进行属性筛选
     * 1. 如果没有选择分类, 大多数做法是尝试获取最佳匹配分类z,(匹配度大于某个值?具体算法不清楚, 好像都会优先匹配分类)
     * 无法匹配最佳分类, 则只允许通过 category, brand, price 进行筛选
     * 2. 当存在具体分类(匹配到或给定)， 则在上面基础上允许通过商品属性筛选
     *
     * @param kw        搜索关键字
     * @param cat       商品分类
     * @param brand     商品品牌
     * @param sPrice    开始价格
     * @param ePrice    结束价格
     * @param provinceId 省id
     * @param ppaths    商品属性路径, 格式 属性key:属性值:key
     * @param offset    记录偏移量
     * @param rows      记录总数
     * @param orders    排序, 格式 field-dir, eg: price-desc
     */
    Map<String, Object> search(String kw, String cat, String brand, Long provinceId,Long labelId, Double sPrice, Double ePrice, String[] ppaths, int offset, int rows, String[] orders);

}
