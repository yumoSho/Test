package com.glanway.jty.service.product;


import com.glanway.jty.entity.product.Brand;
import com.glanway.jty.entity.product.Label;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * 标签service
 */
public interface LabelService extends BaseService<Label,Long> {



    /**
     * <p>名称：brandNextId</p>
     * <p>描述：获取下个ID值</p>
     * @author：LiuJC
     * @return
     */
    Long labelNextId();

}
