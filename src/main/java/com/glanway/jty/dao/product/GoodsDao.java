package com.glanway.jty.dao.product;

import com.glanway.gone.dao.MyBatisMapper;
import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.Goods;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 商品DAO
 */
@MyBatisMapper
public interface GoodsDao extends BaseDao<Goods, Long> {
    /**
     * 通过商品id 删除规格值
     * @param goodsId
     */
    void deleteGoodsSpecValues(Long goodsId);

    /**
     * 通过产品id 删除商品
     * @param pid
     */
    void deleteProductGoods(Long pid);

    /**
     * 查询产品详情
     * @param map
     * @return
     */
    List<Goods>findGoodsDetail(Map<String, Object> map);
    /**
     * 获取商品库存*
     */
    Integer getStock(Long goodsId);

    /*更新商品库存*/
    void updateStock(Map<String, Object> map);

    /*更新商品销量*/
    void updateSaleNum(Map<String, Object> map);

    /*更新商品真实销量*/
    void updateRealSaleNum(Map<String, Object> map);

    /*获取商品的销量 、真实销量、 库存*/
    Goods getSaleNumAndStock(Long goodsId);

    /*库存警戒查询*/
    int alertStockCount(Map<String, Object> map);

    /*库存警戒查询*/
    List<Goods> alertStockFindMany(Map<String, Object> map);



    int findNewGoodsCount(Map<String, Object> map);

    /**
     * <p>名称：getGoodsByOrderDetailNoCommentedCount</p>
     * <p>描述：统计未评论商品</p>
     * @author：LiuJC
     * @param map
     * @return
     */
    int getGoodsByOrderDetailNoCommentedCount(Map<String, Object> map);

    /**
     * <p>名称：getGoodsByOrderDetailNoCommented</p>
     * <p>描述：未评论商品</p>
     * @author：LiuJC
     * @param map
     * @return
     */
    List<Goods> getGoodsByOrderDetailNoCommented(Map<String, Object> map);

    /*用于精选新品 活动后台*/
    List<Goods> findGoodsList(Map<String,Object> map);

    /*计算商品评分*/
    double calcsCore(Long id);

    List<Goods> selectGoodsByCategoryId(Map<String,Object> map);

    /**
     *  用于精选新品 活动后台
     */
    int findGoodsListCount(Map<String, Object> map);
}