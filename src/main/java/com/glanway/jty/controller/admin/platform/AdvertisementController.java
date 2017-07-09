package com.glanway.jty.controller.admin.platform;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.common.Constants;
import com.glanway.jty.common.json.JSONUtil;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.entity.platform.Advertisement;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.platform.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
* @文件名: AdvertisementController.java
* @功能描述: 广告管理类控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/advertisement")
public class AdvertisementController extends BaseController{
	
	@Autowired
	private AdvertisementService advertisementService; 
	
	@Autowired
    private DictionaryService dictionaryService;


	/**
	 * <p>名称：</p>
	 * <p>描述：列表页</p>
	 * @author：tianxuan
	 * @param modelMap
	 * @return
     */
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		
		List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
		modelMap.put("advertisementTypes",JSONUtil.getJSONString(dictionaries).toString());
		return "admin/advertisement/index";
	}
	
	/**
	 * 异步请求节广告信息列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<Advertisement> list(@Qualifier("tu.")Filters filters, Pageable pageable) {
		Page<Advertisement> list = advertisementService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}
	
	/** 
	* @功能描述: 进入广告添加页
	* @return       
	*/
	@RequestMapping("add")
	public String add(ModelMap modelMap) {
		
		List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
		List<Dictionary> posList = dictionaryService.findBySuperDicCode(Constants.DT_GGPOS);

		modelMap.put("dictionaries_pt", dictionaries);
		modelMap.put("posList", posList);
		return "admin/advertisement/add";
	}
	
	/** 
	* @功能描述: 添加广告信息
	* @param
	* @return       
	*/
	@RequestMapping("save")
	public String save(Advertisement advertisement) {
		advertisementService.save(advertisement);
		return "redirect:/admin/advertisement/index";
	}
	
	/** 
	* @功能描述: 进入编辑页面
	* @param id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable(value="id") Long id) {
        ModelAndView mav = new ModelAndView();
        Advertisement  advertisement = advertisementService.find(id);
        List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
		List<Dictionary> posList = dictionaryService.findBySuperDicCode(Constants.DT_GGPOS);
        mav.addObject("dictionaries_pt", dictionaries);
        mav.addObject("advertisement", advertisement);
        mav.addObject("posList", posList);
        mav.setViewName("/admin/advertisement/edit");
		return mav;
	}
	
	/** 
	* @功能描述: 修改广告信息
	* @param advertisement 广告信息
	* @return       
	*/
	@RequestMapping("update")
	public String update(Advertisement advertisement) {
		Long userId = super.user.getId();
		advertisement.setLastModifiedBy(userId);
//		advertisement.setBankID(this.bankId);
		advertisementService.update(advertisement);
		return "redirect:/admin/advertisement/index";
	}
	
	/**
	 * 删除节假日
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Map<String, Object> delete(@RequestParam("id") Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();  
		boolean result = advertisementService.batchDeleteAdvertisement(ids);
		map.put("success", result);
	    return map;
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	
}
