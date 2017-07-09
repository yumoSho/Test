package com.glanway.jty.controller.admin.logistics;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.entity.logistics.SupplierArea;
import com.glanway.jty.service.logistics.HatProvinceService;
import com.glanway.jty.service.logistics.SupplierAreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @文件名: SupplierAreaController.java
* @功能描述: 物流信息管理 - 控制器 
* @author SunF
* @date 2016年4月5日 下午3:21:07 
*  
*/
@Controller
@RequestMapping("/admin/supplierArea")
public class SupplierAreaController extends BaseController{
	
	@Autowired
	private SupplierAreaService supplierAreaService;
	@Autowired
	private HatProvinceService hatProvinceService;
	
	@RequestMapping("index")
	public String index(){
		return "admin/supplierArea/index";
	}
	
	/**
	 * 供应商物流信息列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list")
	public Page<SupplierArea> list(@Qualifier("tu.")Filters filters, Pageable pageable) {
		//此处不需要包含城市列表信息
		Page<SupplierArea> list = supplierAreaService.findPageBySupplierId(filters, pageable);
		return list;
	}
	
	/** 
	* @功能描述: 进入供应商物流信息添加
	* @return       
	*/
	@RequestMapping("add")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView();  
		//判断用户类型，若用户为供应商则取出其信息
		List<HatProvince> provinceList = hatProvinceService.listALLProvinceAndCity();
		modelAndView.addObject("provinceList", provinceList);
		modelAndView.setViewName("admin/supplierArea/add");
		return modelAndView;
	}
	
	/** 
	* @功能描述: 供应商物流信息添加
	* @param
	* @return       
	*/
	@RequestMapping("save")
	public String save(SupplierArea supplierArea) {
		if(null != supplierArea ){
			supplierAreaService.saveInfo(supplierArea);
		}
		return "redirect:/admin/supplierArea/index";
	}

	/** 
	* @功能描述: 进入编辑页面
	* @param id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable(value="id") Long id) {
	    SupplierArea supplierArea = supplierAreaService.find(id);
	    //取全国城市信息列表
	    List<HatProvince> provinceList = hatProvinceService.listALLProvinceAndCity();
	    List<Long> cityList = supplierAreaService.listSupplierAreaCity(id);
	    String cityStr = StringUtils.join(cityList, Constants.STRING_SEPARATOR );

	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("supplierArea", supplierArea);
	    modelAndView.addObject("provinceList", provinceList);
	    modelAndView.addObject("cityStr", cityStr);
	    modelAndView.setViewName("admin/supplierArea/edit");
		return modelAndView;
	}

	/** 
	* @功能描述: 修改物流信息
	* @param supplierArea 物流信息
	* @return       
	*/
	@RequestMapping("update")
	public String update(SupplierArea supplierArea) {
		supplierAreaService.updateInfo(supplierArea);
		return "redirect:/admin/supplierArea/index";
	}
	
	/**
	 * 删除物流信息
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Map<String, Object> delete(@RequestParam("id") Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();  
		boolean result = supplierAreaService.batchDelete(ids);
		map.put("success", result);
	    return map;
	}
	
	/** 
	* @功能描述: 解决list越界问题
	* @说明： spring接收前台传入List时，超过256报IndexOutOfBoundsException
	* @位置：org.springframework.validation.DataBinder
	* @param binder       
	*/
	@InitBinder  
    protected void initBinder(WebDataBinder binder) {  
        binder.setAutoGrowNestedPaths(true);  
        binder.setAutoGrowCollectionLimit(512);  
    }  
	

}
