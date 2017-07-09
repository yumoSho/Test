package com.glanway.jty.service.product;



import com.glanway.jty.entity.product.Spec;
import com.glanway.jty.entity.product.SpecValue;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * 规格service
 */
public interface SpecService extends BaseService<Spec,Long> {

    /**
     *
     * <p>名称：saveSpecValue</p>
     * <p>描述：保存规格值</p>
     * @author：LiuJC
     * @param spv
     */
	void saveSpecValue(SpecValue spv);

    /**
     *
     * <p>名称：updateSpecValue</p>
     * <p>描述：更新规格值</p>
     * @author：LiuJC
     * @param spv
     */
	void updateSpecValue(SpecValue spv);

    /**
     * <p>名称：findSpecValById</p>
     * <p>描述：通过规格id 查找规格值集合</p>
     * @author：LiuJC
     * @param specId
     * @return
     */
	List<SpecValue> findSpecValById(Long specId);

    /**
     *
     * <p>名称：specHaveBeenUsedForGoods</p>
     * <p>描述：此规格是否已被商品使用</p>
     * @author：LiuJC
     * @param specId
     * @return
     */
    Boolean specHaveBeenUsedForGoods(Long specId);

    /**
     *
     * <p>名称：specNextId</p>
     * <p>描述：获取下个ID值</p>
     * @author：LiuJC
     * @return
     */
    Long specNextId();

    /**
     * <p>名称：findSpecAndValuesByProductId</p>
     * <p>描述：t通过产品id查询规格及规格值</p>
     * @author：LiuJC
     * @param pid
     * @return
     */
    List<Spec> findSpecAndValuesByProductId(Long pid);

}
