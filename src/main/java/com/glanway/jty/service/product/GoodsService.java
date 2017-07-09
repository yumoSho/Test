
package com.glanway.jty.service.product;


import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.service.BaseService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 商品service
 */
public interface GoodsService extends BaseService<Goods,Long> {

    /**
     * <p>名称：用于商品详情页面查找</p>.
     * <p>描述：用于商品详情页面查找</p>
     * @author：LiuJC
     * @param goodsId
     * @return
     */
    public Goods findGoodsDetail(Long goodsId);

    /**
     * <p>名称：商品查询</p>
     * <p>描述：商品查询</p>
     * @author：LiuJC
     * @param filters 过滤条件
     * @param pageable 分页条件
     * @return
     */
    Page<Goods> findGoodsMany(Filters filters, Pageable pageable);

    /**
     * <p>名称：查询库存</p>
     * <p>描述：查询库存</p>
     * @author：LiuJC
     * @param goodsId 商品id
     * @return
     */
    Integer getStock(Long goodsId);

    /*
    * <p>名称：更新商品库存</p>
    * <p>描述：更新商品库存</p>
    * @author：LiuJC
    * @param num 商品数量
    * @param goodsId 商品Id
    * @param flag  为true  或者为 null  为增加库存   为false 为减库存
    * */
    void updateStock(Integer num, Long goodsId, Boolean flag);

    /*
    * <p>名称：更新商品销量</p>
    * <p>描述：更新商品销量</p>
    * @author：LiuJC
    * @param num 商品数量
    * @param goodsId 商品Id
    * @param flag  为true  或者为 null  为增加销量   为false 为减销量
    **/
    void updateSaleNum(Integer num, Long goodsId, Boolean flag);

    /**
     * <p>名称：库存警戒查询</p>
     * <p>描述：库存警戒查询</p>
     * @author：LiuJC
     * @param baseFilters 过滤条件
     * @param pageable 分页条件
     * @return
     */
    Page<Goods> findPageAlertStock(Filters baseFilters, Pageable pageable);

    /**
     * <p>名称：getGoodsByOrderDetailNoCommentedCount</p>
     * <p>描述：统计未评论商品</p>
     * @author：LiuJC
     * @param filters
     * @param pageable
     * @return
     */
    Page<Goods> getGoodsByOrderDetailNoCommented(Filters filters, Pageable pageable);

    /**
     * <p>名称：findGoodsList</p>
     * <p>描述：用于后台精选新品 和活动管理</p>
     * @author：LiuJC
     * @param filters
     * @param pageable
     * @return
     */
    Page<Goods> findGoodsList(Filters filters, Pageable pageable);
    
    /**
    *
    * <p>名称：calcRealSalesPrice</p>
    * <p>描述：商品的价格计算有效期 </p>
    * @author：LiuJC
    * @param g 商品
    * @return
    */
   void calcRealSalesPrice(Goods g);

    /*计算评分*/
    double calcsCore(Long id);

    /**
     * 通过分类id 来查询商品
     */
    List<Goods> selectGoodsByCategoryId(Long categoryId,int maxResult,Long provinceId);

    void calcMemberPriceAtList(HttpSession session,List<Goods> goodses);

    void calcMemberPriceAtEveryOne(Goods goods,Integer goodsFrom,Long otherId,HttpSession session);
}
