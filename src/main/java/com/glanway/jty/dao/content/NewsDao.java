package com.glanway.jty.dao.content;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.content.News;

import java.util.List;


/**
 * 资讯管理DAO类
 * @author songzhe
 *
 */
public interface NewsDao extends BaseDao<News,Long>{

	

	/**
	 * 查询指定银行下的所有资讯
	 * @param bankId
	 * @return
	 */
	public List<News> findAllByBankId(Long bankId);


	/**
	 * 查询某分类的所有咨询
	 * @param typeId
	 * @return
     */
	List<News> findAllByTypeId(Long typeId);

}
