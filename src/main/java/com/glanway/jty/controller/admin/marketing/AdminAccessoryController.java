package com.glanway.jty.controller.admin.marketing;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.marketing.ProductAccessory;
import com.glanway.jty.entity.marketing.ProductAccessoryDetail;
import com.glanway.jty.entity.product.ProductImg;
import com.glanway.jty.service.marketing.ProductAccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by ASUS on 2016/8/2.
 */
@RequestMapping("/admin/accessory")
@Controller
public class AdminAccessoryController {

    @Autowired
    ProductAccessoryService productAccessoryService;

    @RequestMapping("add")
    public String add(){
        return "admin/accessoy/add";
    }

    @RequestMapping("index")
    public String list(){
        return "admin/accessoy/index";
    }

    @RequestMapping("saveAccessory")
    @ResponseBody
    public String saveAccessory(ProductAccessory productAccessory, String goodsIds, String productIds){
        String accessoryId = productAccessoryService.saveAccessory(productAccessory, goodsIds, productIds);
        return accessoryId;
    }

    @RequestMapping("updateAccessory")
    @ResponseBody
    public String updateAccessory(ProductAccessory productAccessory, String goodsIds, String productIds){
        String accessoryId = productAccessoryService.updateAccessory(productAccessory, goodsIds, productIds);
        return accessoryId;
    }

    @RequestMapping("deleteAccessory")
    @ResponseBody
    public boolean deleteAccessory(String accessoryId){
        if(!StringUtils.hasText(accessoryId)){
            return false;
        }
        boolean result = productAccessoryService.deleteAccessory(accessoryId);
        return result;
    }

    @RequestMapping("deleteAccessoryByIds")
    @ResponseBody
    public boolean deleteAccessoryByIds(String ids){
        if(!StringUtils.hasText(ids)){
            return false;
        }
        String[] accessoryIds = ids.split(",");
        boolean result = productAccessoryService.deleteAccessoryByIds(accessoryIds);
        return result;
    }

    @ResponseBody
    @RequestMapping("list")
    public Page<ProductAccessory> list(
            @Qualifier("accessory.")
            Filters filters,
            Pageable pageable
    ){
        Page<ProductAccessory> productAccessories =productAccessoryService.listAllPryGoods(filters, pageable);
        for(ProductAccessory productAccessory:productAccessories.getData()){
            for(ProductAccessoryDetail productAccessoryDetail:productAccessory.getProductAccessoryDetails()){
                if(productAccessoryDetail.getGoods()!=null&&productAccessoryDetail.getGoods().getTitle()!=null&&productAccessoryDetail.getGoods().getTitle().length()>4){
                    productAccessoryDetail.getGoods().setTitle(productAccessoryDetail.getGoods().getTitle().substring(0,4)+"...");
                }
            }
        }
        return  productAccessories ;
    }

    @RequestMapping("edit")
    public ModelAndView edit(String id) {
        ModelAndView mav = new ModelAndView();
        ProductAccessory productAccessory = productAccessoryService.getAccessoryById(id);
        mav.addObject("productAccessory", productAccessory);
        mav.setViewName("admin/accessory/edit");
        return mav;
    }

    @ResponseBody
    @RequestMapping("getAccessoryById")
    public ProductAccessory getAccessoryById(String accessoryId){
        if(!StringUtils.hasText(accessoryId)){
            return null;
        }
        ProductAccessory productAccessory = productAccessoryService.getAccessoryById(accessoryId);
        List<ProductAccessoryDetail> productAccessoryDetails = productAccessory.getProductAccessoryDetails();
        for(int i=0; i < productAccessoryDetails.size(); i++){
            List<ProductImg> productImages = productAccessoryDetails.get(i).getGoods().getProduct().getProductImgs();
            if(productImages.size()==0){
                continue;
            }
            productImages = productImages.subList(0, 1);
            productAccessoryDetails.get(i).getGoods().getProduct().setProductImgs(productImages);
        }
        productAccessory.setProductAccessoryDetails(productAccessoryDetails);
        return productAccessory;
    }

    @ResponseBody
    @RequestMapping("checkIfExist")
    public boolean checkIfExist(String pryGoodsId){
        return productAccessoryService.checkIfExist(pryGoodsId);
    }

}
