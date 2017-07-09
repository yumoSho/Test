package com.glanway.jty.service.marketing.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import com.glanway.jty.dao.marketing.ProductPackagesDao;
import com.glanway.jty.dao.product.ProductDao;
import com.glanway.jty.entity.marketing.ProductPackage;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.marketing.ProductPackagesDetailService;
import com.glanway.jty.service.marketing.ProductPackagesService;
import com.glanway.jty.service.product.GoodsService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ProductPackagesServiceImpl extends BaseServiceImpl<ProductPackage, Long> implements ProductPackagesService{
    @Autowired
    private ProductPackagesDao productPackagesDao;

    @Autowired
    private ProductPackagesDetailService productPackagesDetailService;
    @Autowired
    private GoodsService goodsService;



    @Override
    @Transactional
    public String savePackages(ProductPackage productPackage, String goodsIds, String productIds) {
        productPackage.setDeleted(Boolean.FALSE);
        productPackage.setEnable(Boolean.TRUE);
        save(productPackage);
        Long packageId = productPackage.getId();
        productPackagesDetailService.saveProductPackagesDetail(packageId, goodsIds, productIds);
        return productPackage.getId().toString();
    }

    @Override
    @Transactional
    public String updatePackages(ProductPackage productPackage, String goodsIds, String productIds) {
        update(productPackage);
        Long packageId = productPackage.getId();
        productPackagesDetailService.deleteByPackagesId(packageId);
        productPackagesDetailService.saveProductPackagesDetail(packageId, goodsIds, productIds);
        return productPackage.getId().toString();
    }

    @Override
    @Transactional
    public boolean deletePackages(Long packagesID) {
        if(null == packagesID){
            return false;
        }
        ProductPackage productPackage = new ProductPackage();
        productPackage.setId(packagesID);
        delete(productPackage);
        productPackagesDetailService.deleteByPackagesId(packagesID);
        return true;
    }

    @Override
    @Transactional
    public boolean deletePackagesByIds(Long[] packageIds) {
        if(null == packageIds || 0== packageIds.length){
            return false;
        }
        for(Long id : packageIds){
            deletePackages(id);
        }
        return true;
    }

    @Override
    public Page<ProductPackage> listAllPryGoods(Filters baseFilters,    /* 产品条件过滤器 */
                                                                                    Pageable pageable) {

        baseFilters = new IterateNamingTransformFilters(baseFilters);

        Map<String, Object> paramsMap = createParamsMap();
        if (null != baseFilters) {
            paramsMap.put(ProductDao.PRODUCT_FILTERS_PROP, baseFilters);
        }

        if (null != pageable) {
            paramsMap.put(ProductDao.OFFSET_PROP, pageable.getOffset());
            paramsMap.put(ProductDao.MAX_RESULTS_PROP, pageable.getPageSize());

            Sort sort = pageable.getSort();
            if (null != sort) {
                paramsMap.put(ProductDao.SORT_PROP, new IterateNamingTransformSort(sort));
            }
        }

        int total = count(paramsMap);
        List<ProductPackage> data = total > 0 ? productPackagesDao.listAllPryGoods(paramsMap) : Collections.<ProductPackage>emptyList();

        return new SimplePage<ProductPackage>(pageable, data, total);
    }

    @Override
    public ProductPackage getPackageById(Long id) {
        return productPackagesDao.getPackageById(id);
    }

    @Override
    public List<ProductPackage> findProductPackageByProductGoodsId(Long goodsId, Integer type) {
        if(null == goodsId || 0l == goodsId || null == type){
            throw  new CustomException("数据错误");
        }
        HashMap<String, Object> findMap = Maps.newHashMap();
        findMap.put("goodsId",goodsId);
        findMap.put("type",type);
        return productPackagesDao.findProductPackageByProductGoodsId(findMap);
    }
}
