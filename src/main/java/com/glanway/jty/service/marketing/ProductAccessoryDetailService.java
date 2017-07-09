package com.glanway.jty.service.marketing;


import com.glanway.jty.entity.marketing.ProductAccessoryDetail;
import com.glanway.jty.service.BaseService;

/**
 * Created on 2014-07-25 09:11:01
 *
 * @author crud generated
 */
public interface ProductAccessoryDetailService extends BaseService<ProductAccessoryDetail, Long> {
    /**
     * 保存配件详细数据
     * @param accessoryId
     * @param goodsIds
     * @param productIds
     */
    public void saveProductAccessoryDetail(String accessoryId, String goodsIds, String productIds);

    /**
     * 通过配件ID删除该配件下的所有配件详细数据
     * @param accessoryId
     */
    public void deleteByAccessoryId(String accessoryId);

}
