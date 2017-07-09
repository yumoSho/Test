package com.glanway.jty.service.content;


import com.glanway.jty.entity.content.News;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * @文件名: NewsService
 * @功能描述: 资讯管理类控制器
 * @author songzhe
 *
 */
public interface NewsService extends BaseService<News, Long>{

	String DELETED = "deleted";
	/** 
	* @功能描述: 批量删除资讯信息
	* @param ids
	* @return       
	*/
	boolean batchDeleteNews(Long[] ids);
	
	
	
	/**
	 * 根据银行编号，查询其所有资讯记录
	 * @param bankId
	 * @return
	 */
	public List<News> findAllByBankId(Long bankId);

	/**
	 * 累加资讯点击量
	 * @param
	 */
    public void hitAccumulate(News news);
}
