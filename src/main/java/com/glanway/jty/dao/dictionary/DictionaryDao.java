package com.glanway.jty.dao.dictionary;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.dictionary.Dictionary;

import java.util.List;
import java.util.Map;

public interface DictionaryDao extends BaseDao<Dictionary, Long> {

	/** 
	* @功能描述: 获取字典列表
	* @param paramsMap 参数集合
	* @return       
	*/
	List<Dictionary> listFindMany(Map<String, Object> paramsMap);

	/** 
	* @功能描述: 根据父类id查询子类字典列表
	* @param superId 父类id
	* @return       
	*/
	List<Dictionary> findBySuperId(Long superId);

	/** 
	* @功能描述: 根据父类编码查询子类字典列表
	* @param dicCode 父字典code
	* @return       
	*/
	List<Dictionary> findBySuperDicCode(String dicCode);
	
}
