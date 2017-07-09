package com.glanway.jty.service.marketing;


import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.marketing.CategoryGoodsCommend;
import com.glanway.jty.service.BaseService;

public interface CategoryGoodsCommendService extends BaseService<CategoryGoodsCommend, Long> {

    CategoryGoodsCommend  findDetail(Long id);

    Page<CategoryGoodsCommend> findPage2(Filters filters, Pageable pageable);

}
