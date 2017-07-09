package com.glanway.jty.dao.content;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.content.SupportCategory;

import java.util.List;

/**
 * 帮助中心类别管理DAO类
 * @author songzhe
 *
 */
public interface SupportCategoryDao extends BaseDao<SupportCategory,Long> {

	/**
	 * 根据父级拿到所有的帮助类别
	 * @param supportCategory
	 * @return
     */
	 List<SupportCategory> findByParentId(SupportCategory supportCategory);

	/**
	 * 更新某分类下的所有分类
	 * @param supportCategory
     */
	 void updateByParentId(SupportCategory supportCategory);

	/**
	 * 查询所有分类
	 * @return
     */
	 List<SupportCategory> findAllByBankId();

	/**
	 * 查询分类及其子分类(Inner Join)
	 * @return
     */
	 List<SupportCategory> findAllCategoryAndContentsByBankId();

	/**
	 * 查询全部分类及其包含内容(left join)
	 * @return
     */
	List<SupportCategory> findFullCategoryAndContentsByBankId();
}
