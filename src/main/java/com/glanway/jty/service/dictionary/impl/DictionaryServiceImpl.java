package com.glanway.jty.service.dictionary.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.dictionary.DictionaryDao;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/** 
* @文件名: DictionaryServiceImpl.java
* @功能描述: 数据字典业务实现 
* @author SunF
* @date 2016年3月31日 上午10:25:34 
*  
*/
@Service
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary, Long> implements DictionaryService {
	Logger logger = Logger.getLogger(DictionaryServiceImpl.class);
	
	@Autowired
	private DictionaryDao dictionaryDao;
	
	public List<Dictionary> findAll(){
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put(DELETED, Constants.SYS_DELETED_FALSE);
		return dictionaryDao.findMany(paramsMap);
	}

	@Override
	public List<Dictionary> findBySuperId(Long superId) {
		if(null == superId){
			return null;
		}
		List<Dictionary> dicList = dictionaryDao.findBySuperId(superId);
		return dicList;
	}

	@Override
	public List<Dictionary> findBySuperDicCode(String dicCode) {
		if(null == dicCode){
			return null;
		}
		List<Dictionary> dicList = dictionaryDao.findBySuperDicCode(dicCode);
		return dicList;
	}

	@Override
	public Dictionary findBySuperDicCodeAndSubCode(String supperCode, String subCode) {
		// TODO Auto-generated method stub
		if(subCode == null){
			return null;
		}
		
		List<Dictionary> dicList = findBySuperDicCode(supperCode);
		Dictionary subDic = null;
		for (Dictionary dictionary : dicList) {
			if(subCode.equals(dictionary.getDicCode())){
				subDic = dictionary;
				break;
			}
		}
		return subDic;
	}

	@Override
	public Page<Dictionary> listPage(Long superId, Pageable pageable) {
		List<Dictionary> data = null;
		int total = 0;
		
		if(null == superId){
			data = Collections.<Dictionary>emptyList();
		}else{
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put(SUPER_ID, superId);
			//paramsMap.put(DELETED, Constants.SYS_DELETED_FALSE);
			total = dictionaryDao.count(paramsMap);
			data = total > 0 ? dictionaryDao.listFindMany(paramsMap) : Collections.<Dictionary>emptyList();
		}
	    return new SimplePage<Dictionary>(pageable, data, total);
	}

	@Override
	@Deprecated
	public Page<Dictionary> listFindPage(Filters dicCodeFilter, Filters dicNamefilter, Pageable pageable) {
		dicCodeFilter = new IterateNamingTransformFilters(dicCodeFilter);
		dicNamefilter = new IterateNamingTransformFilters(dicNamefilter);
        Map<String, Object> paramsMap = super.createParamsMap();
        paramsMap = resolveFiltersToParamsMap(dicCodeFilter, paramsMap);
        paramsMap = resolveFiltersToParamsMap(dicNamefilter, paramsMap);
        
        int total = dictionaryDao.count(paramsMap);
        List<Dictionary> data = total > 0 ? dictionaryDao.findMany(paramsMap) : Collections.<Dictionary>emptyList();
        
        return new SimplePage<Dictionary>(pageable, data, total);
	}
	
	@Override
	public Page<Dictionary> listFindPage(Filters dicCodeFilter, Filters dicNamefilter, Pageable pageable, Long pid) {
        Map<String, Object> paramsMap = super.createParamsMapByFilters(dicCodeFilter, dicNamefilter);
        paramsMap.put(SUPER_ID, pid);
        int total = dictionaryDao.count(paramsMap);
        List<Dictionary> data = total > 0 ? dictionaryDao.findMany(paramsMap) : Collections.<Dictionary>emptyList();
        
        return new SimplePage<Dictionary>(pageable, data, total);
	}

	@Override
    public void save(Dictionary dictionary){
    	logger.info("保存字典文件：" + dictionary.toString());
        dictionary.setDeleted(Constants.SYS_DELETED_FALSE);
        dictionary.setCreatedDate(new Date());
        dictionary.setCreatedBy(super.getCurrentUserId());
        dictionaryDao.save(dictionary);
    }

	@Override
	public boolean batchDeleteDictionary(Long[] ids) {
		Map<String, Object> paramsMap = Maps.newHashMap();
		for(Long id :ids) {
			//查询是否含有子字典
			paramsMap.put(SUPER_ID, id);
			int num = dictionaryDao.count(paramsMap);
			if(num > 0){
				return false;
			}
	    }
		//开始标记删除
		for(Long id :ids) {
			dictionaryDao.delete(id);
		}
		return true;
		
	}

	@Override
	public boolean isExists(String dicCode, String superId, String id) {
		boolean isExists = true; //已存在
    	if(StringUtils.isEmpty(superId) || StringUtils.isEmpty(superId)){
    		isExists = true; //错误信息提示已存在，前端一验证
    	}else{
    		Map<String, Object> paramsMap = Maps.newHashMap();
    		//查询是否含有子字典
    		paramsMap.put(SUPER_ID, superId);
    		paramsMap.put(DIC_CODE, dicCode);
    		List<Dictionary> dicList = dictionaryDao.findMany(paramsMap);
    		//list长度为1时，通过ID判断是否编辑的本身
    		if(null != dicList && dicList.size() == 1){
    			Long dicId = dicList.get(0).getId();
    			if(dicId.toString().equals(id)){
    				isExists = false;  //通过
    			}else{
    				isExists = true; 
    			}
    		}else if(null != dicList && dicList.size() < 1){
    			isExists = false; //通过
    		}else{
    			isExists = true; 
    		}
    	}
		return isExists;
	}
	
}
