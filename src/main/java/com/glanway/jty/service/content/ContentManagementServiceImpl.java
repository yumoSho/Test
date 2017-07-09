package com.glanway.jty.service.content;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.content.ContentManagementDao;
import com.glanway.jty.entity.content.ContentManagement;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.service.BaseServiceImpl;





@Service
public class ContentManagementServiceImpl extends BaseServiceImpl<ContentManagement,Long> implements ContentManagementService{

	Logger logger = Logger.getLogger(ContentManagementServiceImpl.class);
	
	@Autowired
	private ContentManagementDao contentManagementDao;
	
	@Override
	public void save(ContentManagement contentManagement) {
		// TODO Auto-generated method stub

		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
//			contentManagement.setCreateByUsername(currentUser.getUserName());
			contentManagement.setCreatedBy(currentUser.getId());
		}
		contentManagement.setCreatedDate(new Date());
		contentManagement.setDeleted(Constants.SYS_DELETED_FALSE);
		contentManagementDao.save(contentManagement);
	}

	@Override
	public void update(ContentManagement contentManagement) {
		// TODO Auto-generated method stub
		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			contentManagement.setLastModifiedBy(currentUser.getId());
		}
		contentManagement.setLastModifiedDate(new Date());
		contentManagementDao.update(contentManagement);
		
	}
	
	
	@Override
	public boolean batchDeleteLink(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			contentManagementDao.delete(id);
		}
		return true;
	}

	@Override
	public Map<String, Boolean> checkIsTitleAndBankExists(ContentManagement contentManagement) {
		// TODO Auto-generated method stub
		int count = contentManagementDao.countByDicIdAndBankId(contentManagement);
		Map<String, Boolean> resultMaps = new HashMap<String,Boolean>();
		if(count > 0){//已存在
			resultMaps.put("isExist", true);
		}else{//不存在
			resultMaps.put("isExist", false);
		}
		return resultMaps;
	}

	@Override
	public List<ContentManagement> findManyByBankId(ContentManagement contentManagement) {
		// TODO Auto-generated method stub
		return contentManagementDao.findManyByBankId(contentManagement);
	}
	
}
