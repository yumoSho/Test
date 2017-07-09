package com.glanway.jty.dao.platform;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.platform.Link;

import java.util.List;

/**
 * 链接管理Dao层
 * @author Songzhe
 *
 */
public interface LinkDao extends BaseDao<Link, Long> {

	List<Link> findAll();
	List<Link> findAllByBankId();
}
