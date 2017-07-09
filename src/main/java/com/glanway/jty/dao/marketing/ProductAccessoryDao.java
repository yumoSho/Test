package com.glanway.jty.dao.marketing;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.marketing.ProductAccessory;
import com.glanway.jty.entity.product.Goods;

import java.util.List;
import java.util.Map;

/**
 * Created on 2014-07-25 09:11:01
 *
 * @author crud generated
 */
public interface ProductAccessoryDao extends BaseDao<ProductAccessory, Long> {
    public List<ProductAccessory> listAllPryGoods(Map<String, Object> params);

    public ProductAccessory getAccessoryById(String id);

    public List<ProductAccessory> getAccessoryByPryGoodsId(String pryGoodsId);

    List<Goods> findManyGoodsByPrimaryGoodsId(Map<String, Object> params);
}