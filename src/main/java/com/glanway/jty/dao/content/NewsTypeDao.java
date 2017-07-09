package com.glanway.jty.dao.content;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.content.News;
import com.glanway.jty.entity.content.NewsType;

import java.util.List;


/**
 * 资讯管理 分类DAO类
 * @author songzhe
 *
 */
public interface NewsTypeDao extends BaseDao<NewsType,Long>{

	

	/**
	 * 查询指定银行下的所有资讯
	 * @param
	 * @return
	 */
	 List<NewsType> findAllByBankId();
;
}
