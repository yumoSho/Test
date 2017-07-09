package com.glanway.jty.controller.admin.logistics;

import com.glanway.jty.common.Constants;
import com.glanway.jty.entity.logistics.HatCity;
import com.glanway.jty.service.logistics.HatCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/** 
* @文件名: HatCityController.java
* @功能描述: 城市信息控制器
* @author SunF
* @date 2016年4月7日 下午3:59:44 
*  
*/
@Controller
@RequestMapping("/hatCity")
public class HatCityController {

    @Autowired
    private HatCityService hatCityService;

    /** 
    * @功能描述: 根据所属省份编号获取城市信息集合
    * @param superCode 所属省份编号
    * @return       
    */
    @ResponseBody
    @RequestMapping("ajaxListCityBySuperCode")
    public List<HatCity> ajaxListCityBySuperCode(String superCode){
        if(!StringUtils.hasText(superCode) || Constants.STRING_NULL.equals(superCode)){
            return new ArrayList<HatCity>();
        }
        return hatCityService.listCityBySuperCode(superCode);
    }
}
