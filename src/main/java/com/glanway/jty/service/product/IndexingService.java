package com.glanway.jty.service.product;

/**
 * 商品索引
 */
public interface IndexingService {

    /**
     * 更新当前索引
     * @param id
     * @param delete
     */
    void updateIndex(String id, boolean delete);

    /**
     * 更新所有索引
     */
    void rebuildIndex();

    /**
     * 删除所有索引
     */
    void truncate();

}
