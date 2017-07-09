package com.glanway.jty.service.product.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.dao.product.BrandDao;
import com.glanway.jty.dao.product.LabelDao;
import com.glanway.jty.entity.product.Brand;
import com.glanway.jty.entity.product.Label;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.BrandService;
import com.glanway.jty.service.product.LabelService;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 品牌service实现类
 */
@Service
@Transactional
public class LabelServiceImpl extends BaseServiceImpl<Label,Long> implements LabelService {
	Logger logger = Logger.getLogger(LabelServiceImpl.class);

	@Autowired
    private LabelDao labelDao;

    @Autowired
    public void setBrandDao(LabelDao labelDao) {
        this.labelDao = labelDao;
        super.setCrudDao(labelDao);
    }


    /**
     * <p>名称：brandNextId</p>
     * <p>描述：获取下一个品牌编号</p>
     * @author：LiuJC
     * @return
     */
    @Override
    public Long labelNextId() {
        return labelDao.labelNextId();
    }

    /**
     * <p>名称：save</p>
     * <p>描述：保存</p>
     * @author：LiuJC
     * @param label
     */
    @Override
    public void save(Label label) {
        label.setLabelCode(getLabelCode());
        super.save(label);
    }

    /**
     * <p>名称：</p>
     * <p>描述：获取品牌code</p>
     * @author：LiuJC
     * @return
     */
    private String getLabelCode(){
        Long nextId = labelNextId();
        nextId=null==nextId?0:nextId;
        return "L"+(nextId+1002);
    }


}