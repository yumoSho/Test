package com.glanway.jty.service.product.impl;


import com.glanway.jty.dao.product.SpecValueDao;
import com.glanway.jty.entity.product.SpecValue;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.SpecValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created on 2014-07-25 09:11:01
 *
 * @author crud generated
 */
@Service
@Transactional
public class SpecValueServiceImpl extends BaseServiceImpl<SpecValue,Long> implements SpecValueService {

    private SpecValueDao specValueDao;

    @Autowired
    public void setShelfDao(SpecValueDao specValueDao) {
        this.specValueDao = specValueDao;
        super.setCrudDao(specValueDao);
    }

    /**
     * <p>名称：specValueBean</p>
     * <p>描述：规格值查询</p>
     * @author：LiuJC
     * @param id
     * @return
     */
    @Override
    public SpecValue specValueBean(Long id) {
        return specValueDao.specValueBean(id);
    }

    /**
     * <p>名称：saveGoodsSpecValue</p>
     * <p>描述：保存商品的规格值</p>
     * @author：LiuJC
     * @param paramsMap
     */
    @Override
    public void saveGoodsSpecValue(Map<String, Object> paramsMap) {
        specValueDao.saveGoodsSpecValue(paramsMap);
    }

    /**
     * <p>名称：specValueHaveBeenUsedForGoods</p>
     * <p>描述：规格值是否已被使用</p>
     * @author：LiuJC
     * @param specValueId
     * @return
     */
    @Override
    public Boolean specValueHaveBeenUsedForGoods(Long specValueId) {
        return specValueDao.specValueHaveBeenUsedForGoods(specValueId);
    }

    /**
     * <p>名称：delete</p>
     * <p>描述：删除</p>
     * @author：LiuJC
     * @param id
     */
    @Override
    public void delete(Long id) {
        Boolean flag = this.specValueHaveBeenUsedForGoods(id);
        if(null != flag && flag){
            throw new CustomException("此规格已被商品引用，规格值不能被删除");
        }else{
            super.delete(id);
        }
    }
}
