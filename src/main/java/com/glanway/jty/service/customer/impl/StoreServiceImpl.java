package com.glanway.jty.service.customer.impl;

import com.glanway.gone.dao.CrudDao;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.dao.customer.StoreDao;
import com.glanway.jty.dao.product.LabelDao;
import com.glanway.jty.entity.customer.Store;
import com.glanway.jty.entity.product.Label;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.customer.StoreService;
import com.glanway.jty.utils.AdminUserUtil;
import com.glanway.jty.utils.NumberUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 功能：</b>Bank Service
 * 日期：2016-04-05
 */
@Service("bankService")
@Transactional
public class StoreServiceImpl extends BaseServiceImpl<Store, Long> implements StoreService {

    @Autowired
    private StoreDao storeDao;
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AdminUserUtil adminUserUtil;

    /**
     * 保存
     *
     * @param
     */
    @Override
    public void saveStore(Store store, String[] labels) throws CustomException {
        String bankCode;
        Long userId  = adminUserUtil.getCurrentUserId();
        synchronized (this){
            /*设置店铺编号*/
            Integer count = storeDao.count(null);
            count = null != count ? count : 0;
             bankCode =Store.PREFIX + NumberUtil.zeroFill(++count, Store.CODE_LENGTH);
            store.setCode(bankCode);//设置银行编号

          /*设置创建人*/
            store.setLastModifiedBy(userId);
            store.setCreatedBy(userId);
            storeDao.save(store);
        }

        /* 保存标签 2016/08/20 SongZhe 由于属性不明确，暂时注释 */
     /*   labelDao.deleteAll(store.getId());
        for(String lab : labels){
            Label label = new Label();
            label.setName(lab);
            label.setStoreId(store.getId());
            label.setDeleted(false);
            labelDao.save(label);
        }*/
    }

    @Override
    public void updateStore(Store store, String[] labels) throws CustomException {
        storeDao.update(store);
           /* 保存标签  2016/08/20 SongZhe 由于属性不明确，暂时注释*/
        /*labelDao.deleteAll(store.getId());
        for(String lab : labels){
            Label label = new Label();
            label.setName(lab);
            label.setStoreId(store.getId());
            label.setDeleted(false);
            labelDao.save(label);
        }*/
    }

    /**
     * 根据map内容判断是否有存在
     * @param param
     * @return
     */
    @Override
    public Boolean isExits(Map param) {
       Integer count = storeDao.isExist(param);
        return (null != count && count > 0) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public List<Store> findToExport(Filters filters, Pageable pageable) {
        Map<String, Object> params = FiltersAndPageToMap(filters, pageable.getSort());
        Integer count = storeDao.count(params);
        List<Store> list = (List<Store>) ((null != count && count > 0) ? storeDao.findMany(params) : Collections.emptyList());
        return list;
    }

    /**
     * 根据名称查询 商铺
     * @param offset
     * @param size
     * @param keyword
     * @return
     */
    @Override
    public List<Store> findByName(int offset, int size, String keyword) {
        Map<String, Object> paramsMap = createParamsMap();
        paramsMap.put(CrudDao.OFFSET_PROP, offset);
        paramsMap.put(CrudDao.MAX_RESULTS_PROP, size);
        paramsMap.put("name","%"+ keyword+"%");
        return storeDao.findByName(paramsMap);
    }

    @Override
    public List<Store> findListForMobile() {
        return storeDao.findListForMobile();
    }
}


