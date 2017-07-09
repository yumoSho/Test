package com.glanway.jty.service.content;

import java.util.List;
import java.util.Map;

import com.glanway.jty.entity.content.ContentManagement;
import com.glanway.jty.service.BaseService;




public interface ContentManagementService extends BaseService<ContentManagement, Long>{

	String DELETED = "deleted";
	/** 
	* @功能描述: 批量删除内容管理信息
	* @param ids
	* @return       
	*/
	boolean batchDeleteLink(Long[] ids);
	
	/** 
	* @功能描述: 验证标题名称和所属银行 是否存在
	* @param contentManagement
	* @return       
	*/
	public Map<String, Boolean> checkIsTitleAndBankExists(ContentManagement contentManagement);
	
	/** 
	* @功能描述: 查找指定银行的内容信息
	* @param contentManagement
	* @return       
	*/
	public List<ContentManagement> findManyByBankId(ContentManagement contentManagement);
}
