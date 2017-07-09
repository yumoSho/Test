package com.glanway.jty.service.marketing.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import com.glanway.jty.dao.marketing.ProductAccessoryDao;
import com.glanway.jty.dao.product.GoodsDao;
import com.glanway.jty.dao.product.ProductDao;
import com.glanway.jty.entity.marketing.ProductAccessory;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.marketing.ProductAccessoryDetailService;
import com.glanway.jty.service.marketing.ProductAccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductAccessoryServiceImpl extends BaseServiceImpl<ProductAccessory, Long> implements ProductAccessoryService {

    @Autowired
    private GoodsDao goodsDao;
    private ProductAccessoryDao productAccessoryDao;

    @Autowired
    public void setProductAccessoryDao(ProductAccessoryDao productAccessoryDao) {
        this.productAccessoryDao = productAccessoryDao;
        super.setCrudDao(productAccessoryDao);
    }

    @Autowired
    private ProductAccessoryDetailService productAccessoryDetailService;

    @Override
    @Transactional
    public String saveAccessory(ProductAccessory productAccessory, String goodsIds, String productIds) {
        productAccessory.setDeleted(Boolean.FALSE);
        productAccessory.setEnable(Boolean.FALSE);
        save(productAccessory);
        String accessoryId = productAccessory.getId();
        productAccessoryDetailService.saveProductAccessoryDetail(accessoryId, goodsIds, productIds);
        return productAccessory.getId();
    }

    @Override
    @Transactional
    public String updateAccessory(ProductAccessory productAccessory, String goodsIds, String productIds) {
        update(productAccessory);
        String accessoryId = productAccessory.getId();
        productAccessoryDetailService.deleteByAccessoryId(accessoryId);
        productAccessoryDetailService.saveProductAccessoryDetail(accessoryId, goodsIds, productIds);
        return productAccessory.getId();
    }

    @Override
    @Transactional
    public boolean deleteAccessory(String accessoryID) {
        if(!StringUtils.hasText(accessoryID)){
            return false;
        }
        ProductAccessory productAccessory = new ProductAccessory();
        productAccessory.setId(accessoryID);
        delete(productAccessory);
        productAccessoryDetailService.deleteByAccessoryId(accessoryID);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteAccessoryByIds(String[] accessoryIds) {
        if(StringUtils.isEmpty(accessoryIds)){
            return false;
        }
        for(String id : accessoryIds){
            deleteAccessory(id);
        }
        return true;
    }

    @Override
    public Page<ProductAccessory> listAllPryGoods(Filters baseFilters, Pageable pageable) {
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
        List<ProductAccessory> data = total > 0 ? productAccessoryDao.listAllPryGoods(paramsMap) : Collections.<ProductAccessory>emptyList();

        return new SimplePage<ProductAccessory>(pageable, data, total);
    }

    @Override
    public ProductAccessory getAccessoryById(String id) {
        return productAccessoryDao.getAccessoryById(id);
    }


    @Override
    public List<ProductAccessory> getAccessoryByPryGoodsId(String pryGoodsId) {
        return productAccessoryDao.getAccessoryByPryGoodsId(pryGoodsId);
    }


    @Override
    public boolean checkIfExist(String pryGoodsId){
        List<ProductAccessory> productAccessories = getAccessoryByPryGoodsId(pryGoodsId);
        if(productAccessories!=null && productAccessories.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public ProductAccessory getByPrimaryGoodsId(String websiteId, String goodsId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("websiteId", websiteId);
        params.put("goodsId", goodsId);

//        List<Goods> goods = productAccessoryDao.findManyGoodsByPrimaryGoodsId(params);

        List<ProductAccessory> accessory = productAccessoryDao.getAccessoryByPryGoodsId(goodsId);

        if (CollectionUtils.isEmpty(accessory)) return null;
        return accessory.get(0);
    }

    @Override
    public Boolean checkAccessory(String[] goodsIds, String promoteId) {
        ProductAccessory accessory = productAccessoryDao.getAccessoryById(promoteId);
        if (null != accessory) {
            /*TODO */ //List<Goods> goods = goodsDao.findManyByAccessoryId(promoteId);
        }
        return false;
    }


}
