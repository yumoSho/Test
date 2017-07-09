package com.glanway.jty.dao.content;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.content.CommonPro;

import java.util.List;


/**
 * 帮助中心内容管理DAO类
 * @author songzhe
 *
 */
public interface CommonProDao extends BaseDao<CommonPro,Long> {

	

	/**
	 * 查询所有内容
	 * @return
	 */
	public List<CommonPro> findAllByBankId();

}
