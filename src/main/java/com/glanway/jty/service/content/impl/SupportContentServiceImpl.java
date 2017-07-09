package com.glanway.jty.service.content.impl;

import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.content.SupportContentDao;
import com.glanway.jty.entity.content.SupportContent;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.content.SupportContentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SupportContentServiceImpl extends BaseServiceImpl<SupportContent,Long> implements SupportContentService{

	Logger logger = Logger.getLogger(SupportContentServiceImpl.class);
	
	@Autowired
	private SupportContentDao supportContentDao;
	
	@Override
	public void save(SupportContent supportContent) {
		// TODO Auto-generated method stub

		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
//			contentManagement.setCreateByUsername(currentUser.getUserName());
			supportContent.setCreatedBy(currentUser.getId());
			supportContent.setLastModifiedBy(currentUser.getId());
		}
		Date createDate = new Date();
		supportContent.setCreatedDate(createDate);
		supportContent.setLastModifiedDate(createDate);
		supportContent.setDeleted(Constants.SYS_DELETED_FALSE);
		
		supportContentDao.save(supportContent);
	}

	@Override
	public void update(SupportContent supportContent) {
		// TODO Auto-generated method stub
		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			supportContent.setLastModifiedBy(currentUser.getId());
		}
		supportContent.setLastModifiedDate(new Date());
		supportContentDao.update(supportContent);
		
	}
	
	
	@Override
	public boolean batchDeleteSupportContent(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			supportContentDao.delete(id);
		}
		return true;
	}


	@Override
	public List<SupportContent> findAllByBankId() {
		// TODO Auto-generated method stub
		return supportContentDao.findAllByBankId();
	}

	
	
	
}
