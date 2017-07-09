package com.glanway.jty.service.platform.impl;

import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.platform.SeoSettingDao;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.entity.platform.SeoSetting;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.platform.SeoSettingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeoSettingServiceImpl extends BaseServiceImpl<SeoSetting,Long> implements SeoSettingService{

	Logger logger = Logger.getLogger(SeoSettingServiceImpl.class);
	
	@Autowired
	private SeoSettingDao seoSettingDao;
	
	@Override
	public void save(SeoSetting seoSetting) {
		// TODO Auto-generated method stub

		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			seoSetting.setCreatedBy(currentUser.getId());
			seoSetting.setLastModifiedBy(currentUser.getId());
		}
		Date createDate = new Date();
		seoSetting.setCreatedDate(createDate);
		seoSetting.setLastModifiedDate(createDate);
		seoSetting.setDeleted(Constants.SYS_DELETED_FALSE);
		
		seoSettingDao.save(seoSetting);
	}

	@Override
	public void update(SeoSetting seoSetting) {
		// TODO Auto-generated method stub
		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			seoSetting.setLastModifiedBy(currentUser.getId());
		}
		seoSetting.setLastModifiedDate(new Date());
		seoSettingDao.update(seoSetting);
		
	}
	
	
	@Override
	public boolean batchDeleteSeoSetting(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			seoSettingDao.delete(id);
		}
		return true;
	}


	@Override
	public List<SeoSetting> findAllByBankId(Long bankId) {
		// TODO Auto-generated method stub
		return seoSettingDao.findAllByBankId(bankId);
	}

	@Override
	public Map<String, Boolean> checkIsPageNameAndPlatNameAndBankExists(SeoSetting seoSetting) {
		// TODO Auto-generated method stub
		SeoSetting _seoSetting = seoSettingDao.findByDicPageIdAndDicplatIdAndBankId(seoSetting);
		Map<String, Boolean> resultMaps = new HashMap<String,Boolean>();
		if(_seoSetting !=null){//已存在
			
			if(seoSetting.getId()==null){
				resultMaps.put("isExist", true);
			}
			else if(seoSetting.getId()!=null && seoSetting.getId()==_seoSetting.getId()){
				resultMaps.put("isExist", false);
			}else if(seoSetting.getId()!=null &&_seoSetting!=null && seoSetting.getId()!=_seoSetting.getId()){
				resultMaps.put("isExist", true);
			}
			else{
				resultMaps.put("isExist", false);
			}
		}else{//不存在
			resultMaps.put("isExist", false);
		}
		return resultMaps;
	}

	public List<SeoSetting> findByList(Map param){
		return seoSettingDao.findList(param);
	}
	
	
}
