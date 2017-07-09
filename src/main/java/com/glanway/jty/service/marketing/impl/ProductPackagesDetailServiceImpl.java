package com.glanway.jty.service.marketing.impl;

import com.glanway.jty.dao.marketing.ProductPackagesDetailDao;
import com.glanway.jty.entity.marketing.ProductPackageDetail;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.Product;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.marketing.ProductPackagesDetailService;
import com.glanway.jty.utils.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ASUS on 2014/11/7.
 */
@Service
@Transactional
public class ProductPackagesDetailServiceImpl extends BaseServiceImpl<ProductPackageDetail, Long> implements ProductPackagesDetailService{
    @Autowired
    private ProductPackagesDetailDao productPackagesDetailDao;



    /**
     * 保存套餐详细
     * @param packagesId
     * @param goodsIds
     * @param productIds
     */
    @Override
    public void saveProductPackagesDetail(Long packagesId, String goodsIds, String productIds) {
        String[] goodsIdArray = ArrayUtil.stringToArray(goodsIds);
        String[] productIdArray = ArrayUtil.stringToArray(productIds);
        ProductPackageDetail productPackageDetail;
        Goods goods;
        Product product;
        for (int i = 0; i <goodsIdArray.length ; i++) {
            productPackageDetail = new ProductPackageDetail();
            productPackageDetail.setPackageId(packagesId);
            productPackageDetail.setDeleted(Boolean.FALSE);
            goods = new Goods();
            goods.setId(Long.parseLong(goodsIdArray[i]));
            product = new Product();
            if(null!=productIds&&productIds.length()==goodsIds.length())
            product.setId(Long.parseLong(productIdArray[i]));
            productPackageDetail.setGoods(goods);
            productPackageDetail.setProduct(product);
            save(productPackageDetail);
        }
    }

    /**
     * 通过套餐ID删除该套餐下的所有套餐详细
     * @param packagesId
     */
    @Override
    public void deleteByPackagesId(Long packagesId) {
        productPackagesDetailDao.deleteByPackagesId(packagesId);
    }


}

