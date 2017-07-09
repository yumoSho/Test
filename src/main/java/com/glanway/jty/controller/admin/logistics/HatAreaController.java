package com.glanway.jty.controller.admin.logistics;

import com.glanway.jty.common.Constants;
import com.glanway.jty.entity.logistics.HatArea;
import com.glanway.jty.service.logistics.HatAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/** 
* @文件名: HatAreaController.java
* @功能描述: 区县信息控制层
* @author SunF
* @date 2016年4月7日 下午3:48:24 
*  
*/
@Controller
@RequestMapping("/hatArea")
public class HatAreaController {

    @Autowired
    private HatAreaService hatAreaService;

    /** 
    * @功能描述: 异步，根据所属城市编号获取区县信息集合
    * @param superCode 所属城市编号
    * @return       
    */
    @RequestMapping("ajaxListAreaBySuperCode")
    @ResponseBody
    public List<HatArea> ajaxListAreaBySuperCode(String superCode){
        if(!StringUtils.hasText(superCode) || Constants.STRING_NULL.equals(superCode)){
            return new ArrayList<HatArea>();
        }
        return hatAreaService.listAreaBySuperCode(superCode);
    }
}
