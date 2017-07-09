package com.glanway.jty.service.content;


import com.glanway.jty.entity.content.SupportCategory;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * <p>名称: 帮助中心分类serivce</p>
 * <p>说明: 帮助中心分类serivce</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/6/2416:22
 * @version: 1.0
 */
public interface SupportCategoryService extends BaseService<SupportCategory, Long> {

	String DELETED = "deleted";
	/** 
	* @功能描述: 批量删除帮助中心类别管理信息
	* @param ids
	* @return       
	*/
	boolean batchDeleteSupportCategory(Long[] ids);
	
	
	
	/**
	 * 通过parentId查询帮助分类
	 * @param supportCategory
	 * @return
	 */
	public List<SupportCategory> findByParentId(SupportCategory supportCategory);
	
	/**
	 * 通过银行编号查询其对应的所有帮助分类
	 * @param id
	 * @return
	 */
	public List<SupportCategory> findAllByBankId();

	/**
	 * 查询全部分类及其包含内容(inner join)
	 * @return
     */
	public List<SupportCategory> findAllCategoryAndContentsByBankId();

	/**
	 * 查询全部分类及其包含内容(left join)
	 * @return
	 */
	List<SupportCategory> findFullCategoryAndContentsByBankId();
}
