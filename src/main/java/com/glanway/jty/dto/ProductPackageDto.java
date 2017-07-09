/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: jintianyuan
 * FileName: ProductPackageDto.java
 * PackageName: com.glanway.jty.dto
 * Date: 2016/8/269:45
 **/
package com.glanway.jty.dto;/**
 * Created by chao on 2016/8/26.
 */

import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>名称: </p>
 * <p>说明: 前台商品套餐的</p>
 * <p>修改记录：（2016/8/26 9:45 - LiuJC - 创建）</p>
 *
 * @author：tianxuan
 * @date：2016/8/269:45
 * @version: 1.0
 */
public class ProductPackageDto implements Serializable {
    /**
     * 主商品
     * */
    private Goods goods;
    /**
     * 主商品
     * */
    private Product product;
    /**
     * 主商品支持区域；
     * */
    private List<HatProvince> hatProvinceList;

    /**
     * 和主商品有关的配件
     */
    private List<Parts>  parts;
    /**
     * 和主商品有关的套餐
     */
    private List<Package> packages;


    /**
     * 配件
     */
    public class Parts{
        private Goods goods;
        private List<HatProvince> provinces;
    }

    /**
     * 配件套餐
     */
    public class Package{
        private List<Parts>  goods;
        private BigDecimal price;
    }

}


