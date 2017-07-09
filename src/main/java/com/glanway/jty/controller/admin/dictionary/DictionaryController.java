package com.glanway.jty.controller.admin.dictionary;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @文件名: DictionaryController.java
* @功能描述: 数据字典控制器 
* @author SunF
* @date 2016年3月31日 下午1:30:04 
*  
*/
@Controller
@RequestMapping("/admin/dictionary")
public class DictionaryController extends BaseController{
	@Autowired
	private  DictionaryService dictionaryService;
	
	@RequestMapping("index")
	public String index(){
		return "admin/dictionary/index";
	}
	
	@ResponseBody
	@RequestMapping("list")
	public Page<Dictionary> list(@Qualifier("tr.")Filters dicCodeFilter,
								 @Qualifier("tu.")Filters dicNamefilters,
								 Pageable pageable, String superId){
		Long pid = Constants.DICTIONARY_BASE_SUPER_ID;
		if(! StringUtils.isEmpty(superId)){
			pid = Long.valueOf(superId);
		}
		return dictionaryService.listFindPage(dicCodeFilter, dicNamefilters, pageable, pid);
	}
	
	/** 
	* @功能描述: 进入添加页
	* @return       
	*/
	@RequestMapping("add")
	public String add() {
		return "admin/dictionary/add";
	}
	
	/** 
	* @功能描述: 添加字典信息
	* @param dictionary
	* @return       
	*/
	@RequestMapping("save")
	public String save(Dictionary dictionary) {
		if(null != dictionary){
			dictionaryService.save(dictionary);
		}
		return "redirect:/admin/dictionary/index";
	}
	
	/** 
	* @功能描述: 进入编辑页面
	* @param id 父字典id
	* @return       
	*/
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable(value="id") Long id) {
        ModelAndView mav = new ModelAndView();
        Dictionary superDic = dictionaryService.find(id);
        mav.addObject("dictionary", superDic);
        mav.setViewName("admin/dictionary/edit");
		return mav;
	}
	
	/** 
	* @功能描述: 修改字典信息
	* @param dictionary 新字典信息
	* @return       
	*/
	@RequestMapping("update")
	public String update(Dictionary dictionary) {
		if(null != dictionary){
			Long userId = super.user.getId();
			dictionary.setLastModifiedBy(userId);
			dictionaryService.update(dictionary);
		}
		return "redirect:/admin/dictionary/index";
	}

	/**
	 * 删除字典
	 * @param
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();  
		boolean result = dictionaryService.batchDeleteDictionary(ids);
		map.put("success", result);
	    return map;
	}

	/** 
	* @功能描述: 请求字典树
	* @return       
	*/
	@ResponseBody
	@RequestMapping("ajaxDictionaryTree")
	public List<Map<String, Object>> ajaxloadDicTree(){
		List<Dictionary> diclist = dictionaryService.findAll();
		//创建一个最高级别的字典
		Map<String, Object> baseDic = Maps.newHashMap();
		baseDic.put("id", Constants.DICTIONARY_BASE_SUPER_ID);
		baseDic.put("name","全部");
		baseDic.put("open","true"); //默认打开
		
		List<Map<String, Object>> nodes = Lists.newArrayList();
		for(int i=0; i<diclist.size(); i++){
			Map<String, Object> node = Maps.newHashMap();
			node.put("id", diclist.get(i).getId());
			node.put("name", diclist.get(i).getDicName());
			node.put("pId", diclist.get(i).getSuperId());
			nodes.add(node);
		}
		nodes.add(baseDic);
		return nodes;
	}
	
    /** 
    * @功能描述: 检查指定字典下是否存在该编号
    * @param dicCode 待查字典编号
    * @param superId 指定的父字典id
    * @param id 字典id  添加时传空字符，编辑时传实际值
    * @return       
    */
    @ResponseBody
    @RequestMapping("ajaxCheckIsCodeExists")
    public Map<String, Boolean> ajaxCheckIsCodeExists(String dicCode, String superId, String id) {
    	Map<String, Boolean> resultMap = Maps.newHashMap();
    	boolean isExits = dictionaryService.isExists(dicCode, superId, id);
    	resultMap.put("isExists", isExits);
        return resultMap;
    }
	
}
