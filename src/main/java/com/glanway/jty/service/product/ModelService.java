package com.glanway.jty.service.product;



import com.glanway.jty.entity.product.Model;
import com.glanway.jty.service.BaseService;

/**
 * 模型Servive
 */
public interface ModelService extends BaseService<Model,Long> {
    /**
     * <p>名称：findDetail</p>
     * <p>描述：获取模型属性详细参数</p>
     * @author：LiuJC
     * @param id 模型id
     * @return model 模型详细信息
     */
    Model findDetail(Long id);

    /**
     *
     * <p>名称：isReferenced</p>
     * <p>描述：分类是否关联了模型</p>
     * @author：LiuJC
     * @param mid 模型ID
     * @return boolean 是否已和分类关联
     */
    boolean isReferenced(Long mid);

    /**
     *
     * <p>名称：deleteModelSpec</p>
     * <p>描述：通过模型规格的id 来删除</p>
     * @author：LiuJC
     * @param msId
     */
    void deleteModelSpec(Long msId);

    /**
     * <p>名称：findBaseModel</p>
     * <p>描述：基础属性查找</p>
     * @author：LiuJC
     * @return model 基础模型
     */
    Model findBaseModel();

    /**
     * <p>名称：saveBaseModel</p>
     * <p>描述：基础属性保存</p>
     * @author：LiuJC
     * @param m 模型
     */
    void saveBaseModel(Model m);

    /**
     * <p>名称：modelHaveBeenUsedForGoods</p>
     * <p>描述：是否被商品关联</p>
     * @author：LiuJC
     * @param modelId 模型
     */
    Boolean modelHaveBeenUsedForGoods(Long modelId);

    /**
     * <p>名称：modelSpecHaveBeenUsedForGoods</p>
     * <p>描述：某条模型规格值是否引用</p>
     * @author：LiuJC
     * @param modelSpecId
     */
    Boolean modelSpecHaveBeenUsedForGoods(Long modelSpecId);

    /**
     * <p>名称：modelNextId</p>
     * <p>描述：获取下个ID值</p>
     * @author：LiuJC
     * @return
     */
    Long modelNextId();


}
