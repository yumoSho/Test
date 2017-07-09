package com.glanway.jty.controller.admin.marketing;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.entity.marketing.ProductPackage;
import com.glanway.jty.entity.marketing.ProductPackageDetail;
import com.glanway.jty.service.marketing.ProductPackagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/admin/packages")
public class AdminPackagesController {

    @Autowired
    ProductPackagesService productPackagesService;

    @RequestMapping("add")
    public String add(){
        return "admin/packages/add";
    }

    @RequestMapping("index")
    public String list(){
        return "admin/packages/index";
    }

    @RequestMapping("savePackages")
    @ResponseBody
    public Message savePackages(ProductPackage productPackage, String goodsIds, String productIds){
        if(null!=productPackage && null !=productPackage.getId()){
            productPackage.setType(ProductPackage.PACKAGE_TYPE_PACKAGE);
            productPackagesService.updatePackages(productPackage, goodsIds, productIds);
            return Message.success();
        }else if(null != productPackage && null == productPackage.getId()){
            productPackage.setType(ProductPackage.PACKAGE_TYPE_PACKAGE);
            String packagesId = productPackagesService.savePackages(productPackage, goodsIds, productIds);
            return Message.success();
        }else{
            return Message.fail("信息不完善");
        }
    }





    @RequestMapping("delete")
    @ResponseBody
    public Message deletePackagesByIds(@RequestParam("id") Long[] packagesIds){
        boolean result = productPackagesService.deletePackagesByIds(packagesIds);
        if(result){
            return Message.success();
        }else{
            return Message.fail("删除失败");
        }

    }

    @ResponseBody
    @RequestMapping("list")
    public Page<ProductPackage> list(Filters filters,Pageable pageable){
        filters.eq("type",0);
        Page<ProductPackage> productPackages=productPackagesService.listAllPryGoods(filters, pageable);
        for(ProductPackage productPackage:productPackages.getData()){
          for(ProductPackageDetail productPackageDetail:productPackage.getPackageDetails()){
              if(productPackageDetail.getGoods().getTitle().length()>4){
                  productPackageDetail.getGoods().setTitle(productPackageDetail.getGoods().getTitle().substring(0,4)+"...");
              }
          }
        }
        return productPackages;
    }

    @RequestMapping("edit/{id}")
    public ModelAndView edit(@PathVariable("id")Long id) {
        ModelAndView mav = new ModelAndView();
        ProductPackage productPackage = productPackagesService.getPackageById(id);
        List<ProductPackageDetail> packageDetails = productPackage.getPackageDetails();

        String goodsIds = "";
        String ids = "";
        int len = packageDetails.size();
        for (int i = 0; i < packageDetails.size(); i++) {

                goodsIds += packageDetails.get(i).getGoods().getId();
                ids += packageDetails.get(i).getId();
                if(i<len-1){
                    goodsIds += ",";
                    ids += ",";
                }

        }
        mav.addObject("ids", ids);
        mav.addObject("goodsIds", goodsIds);
        mav.addObject("productPackage", productPackage);
        mav.setViewName("admin/packages/edit");
        return mav;
    }


}
