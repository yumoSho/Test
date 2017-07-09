package com.glanway.jty.dao.content;

import java.util.List;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.content.ContentManagement;




/**
 * 内容管理DAO类
 * @author songzhe
 *
 */
public interface ContentManagementDao extends BaseDao<ContentManagement,Long>{

	
	/**
	 * 查找除了本id所对应数据以外，数据字典标号和银行编号都相同的记录数
	 * @param contentManagement 内容对象
	 * @return
	 */
	public int countByDicIdAndBankId(ContentManagement contentManagement);
	
	/**
	 * 通过银行编号，查找其所对应的记录
	 * @param contentManagement 内容对象
	 * @return
	 */
	public List<ContentManagement> findManyByBankId(ContentManagement contentManagement);
}
