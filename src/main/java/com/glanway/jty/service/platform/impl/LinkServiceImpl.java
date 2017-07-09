package com.glanway.jty.service.platform.impl;


import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.platform.LinkDao;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.entity.platform.Link;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.platform.LinkService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LinkServiceImpl extends BaseServiceImpl<Link, Long> implements LinkService {

	Logger logger = Logger.getLogger(LinkServiceImpl.class);

	@Autowired
	private LinkDao linkDao;
	
	@Override
	public void save(Link link) {
		// TODO Auto-generated method stub

		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			link.setCreateByUsername(currentUser.getUserName());
			link.setCreatedBy(currentUser.getId());
		}
		link.setCreatedDate(new Date());
		link.setDeleted(Constants.SYS_DELETED_FALSE);
		linkDao.save(link);
	}

	@Override
	public void update(Link link) {
		// TODO Auto-generated method stub
		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			link.setLastModifiedBy(currentUser.getId());
		}
		link.setLastModifiedDate(new Date());
		linkDao.update(link);
		
	}

	@Override
	public boolean batchDeleteLink(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			linkDao.delete(id);
		}
		return true;
	}

	@Override
	public List<Link> findAllByBankId() {
		// TODO Auto-generated method stub
		return linkDao.findAllByBankId();
	}

}
