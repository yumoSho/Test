package com.glanway.jty.controller.admin.alertstock;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.service.product.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *库存警戒
 */
@Controller
@RequestMapping("admin/goods/")
public class AlertStockController extends BaseController {

    @Autowired
    private GoodsService goodsService;
    @RequestMapping("index")
    public String index(){
        return "admin/alertstock/index";
    }

    @ResponseBody
    @RequestMapping("list")
    public Page<Goods> list(Filters filters,Pageable pageable){
        return goodsService.findPageAlertStock(filters,pageable);
    }
}
