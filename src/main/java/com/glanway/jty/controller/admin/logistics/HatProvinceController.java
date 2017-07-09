package com.glanway.jty.controller.admin.logistics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
* @文件名: HatProvinceController.java
* @功能描述: 省份信息控制器 
* @author SunF
* @date 2016年4月7日 下午3:56:57 
*  
*/
@Controller
@RequestMapping("/hatProvince")
public class HatProvinceController {

    //@Autowired
    //private HatProvinceService hatProvinceService;
    //@Autowired
    //private HatAreaService hatAreaService;
    
   /* @ResponseBody
    @RequestMapping("listAllProvince")
    public List<HatProvince> listAllProvince(){
        return hatProvinceService.listAllProvince();
    }*/
    
    /*@ResponseBody
    @RequestMapping("selectHatProvince")
    @Deprecated
    public HatProvince selectHatProvince(String id){
        HatProvince hatProvince = hatProvinceService.listProvinceInfoByCode(id);
        if(hatProvince.getHatCities().get(0).getCityName().equals("市辖区")||hatProvince.getHatCities().get(0).getCityName().equals("市")||hatProvince.getHatCities().get(0).getCityName().equals("县")){
            List<HatArea> alist = new ArrayList<HatArea>();
            for(HatCity city:hatProvince.getHatCities()) {
                alist = hatAreaService.listAreaBySuperCode(city.getCityCode());
                city.setAreaList(alist);
            }
        }
        return hatProvince;
    }*/
}
