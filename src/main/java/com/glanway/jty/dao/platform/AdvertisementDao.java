package com.glanway.jty.dao.platform;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.platform.Advertisement;

import java.util.List;

/**
 * 广告管理Dao层
 * @author Songzhe
 *
 */
public interface AdvertisementDao extends BaseDao<Advertisement, Long> {
	
	List<Advertisement> findAll();
	List<Advertisement> findAllByBankId(Advertisement advertisement);
}
