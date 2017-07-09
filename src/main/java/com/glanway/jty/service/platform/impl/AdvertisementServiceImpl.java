package com.glanway.jty.service.platform.impl;


import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.platform.AdvertisementDao;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.entity.platform.Advertisement;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.platform.AdvertisementService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdvertisementServiceImpl extends BaseServiceImpl<Advertisement, Long> implements AdvertisementService {

	Logger logger = Logger.getLogger(AdvertisementServiceImpl.class);

	@Autowired
	private AdvertisementDao advertisementDao;
	
	@Override
	public void save(Advertisement advertisement) {
		// TODO Auto-generated method stub

		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			advertisement.setCreateByUsername(currentUser.getUserName());
			advertisement.setCreatedBy(currentUser.getId());
		}
		advertisement.setCreatedDate(new Date());
		advertisement.setDeleted(Constants.SYS_DELETED_FALSE);
		if(advertisement.getIsShow() == null){
			advertisement.setIsShow(2);
		}
		advertisementDao.save(advertisement);
	}

	@Override
	public void update(Advertisement advertisement) {
		// TODO Auto-generated method stub
		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			advertisement.setLastModifiedBy(currentUser.getId());
		}
		if(advertisement.getIsShow() == null){
			advertisement.setIsShow(2);
		}
		advertisement.setLastModifiedDate(new Date());
		advertisementDao.update(advertisement);
		
	}

	@Override
	public boolean batchDeleteAdvertisement(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			advertisementDao.delete(id);
		}
		return true;
	}

	@Override
	public List<Advertisement> findAllByBankId(Advertisement advertisement) {
		// TODO Auto-generated method stub
		return advertisementDao.findAllByBankId(advertisement);
	}

}
