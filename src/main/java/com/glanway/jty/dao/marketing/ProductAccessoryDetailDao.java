package com.glanway.jty.dao.marketing;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.marketing.ProductAccessoryDetail;

/**
 * Created on 2014-07-25 09:11:01
 *
 * @author crud generated
 */
public interface ProductAccessoryDetailDao extends BaseDao<ProductAccessoryDetail, Long> {

    /**
     * 通过配件的ID删除该配件下的所有配件详细
     *
     * @param accessoryId
     */
    public void deleteByAccessoryId(String accessoryId);

}