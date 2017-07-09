package com.glanway.jty.service.marketing.impl;

import com.glanway.jty.dao.marketing.ProductAccessoryDetailDao;
import com.glanway.jty.entity.marketing.ProductAccessoryDetail;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.Product;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.marketing.ProductAccessoryDetailService;
import com.glanway.jty.utils.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 2014-07-25 09:11:01
 *
 * @author crud generated
 */
@Service
@Transactional
public class ProductAccessoryDetailServiceImpl extends BaseServiceImpl<ProductAccessoryDetail, Long> implements ProductAccessoryDetailService {

    ProductAccessoryDetailDao productAccessoryDetailDao;

    @Autowired
    public void setProductAccessoryDetailDao(ProductAccessoryDetailDao productAccessoryDetailDao){
        this.productAccessoryDetailDao = productAccessoryDetailDao;
        super.setCrudDao(productAccessoryDetailDao);
    }

    @Override
    public void saveProductAccessoryDetail(String accessoryId, String goodsIds, String productIds) {
        String[] goodsIdArray = ArrayUtil.stringToArray(goodsIds);
        String[] productIdArray = ArrayUtil.stringToArray(productIds);
        ProductAccessoryDetail productAccessoryDetail;
        Goods goods;
        Product product;
        for (int i = 0; i <goodsIdArray.length ; i++) {
            productAccessoryDetail = new ProductAccessoryDetail();
            productAccessoryDetail.setAccessoryId(accessoryId);
            productAccessoryDetail.setDeleted(Boolean.FALSE);
            goods = new Goods();
            goods.setId(Long.parseLong(goodsIdArray[i]));
            product = new Product();
            product.setId(Long.parseLong(productIdArray[i]));
            productAccessoryDetail.setGoods(goods);
            productAccessoryDetail.setProduct(product);
            save(productAccessoryDetail);
        }
    }

    @Override
    public void deleteByAccessoryId(String accessoryId) {
        productAccessoryDetailDao.deleteByAccessoryId(accessoryId);
    }
}
