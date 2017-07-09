package com.glanway.jty.dao.content;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.content.SupportContent;

import java.util.List;


/**
 * 帮助中心内容管理DAO类
 * @author songzhe
 *
 */
public interface SupportContentDao extends BaseDao<SupportContent,Long> {

	

	/**
	 * 查询所有内容
	 * @return
	 */
	public List<SupportContent> findAllByBankId();

	/**
	 * <p>名称：根据分类id查询所有的内容列表</p>
	 * <p>描述：</p>
	 * @author：tianxuan
	 * @param cId
	 * @return
     */
	List<SupportContent> findListByCategoryId(Long cId);

}
