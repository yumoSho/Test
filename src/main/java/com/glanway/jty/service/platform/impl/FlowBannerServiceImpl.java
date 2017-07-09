package com.glanway.jty.service.platform.impl;

import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.platform.FlowBannerDao;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.entity.platform.FlowBanner;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.platform.FlowBannerService;
import com.glanway.jty.utils.UserAgent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FlowBannerServiceImpl extends BaseServiceImpl<FlowBanner,Long> implements FlowBannerService {

	Logger logger = Logger.getLogger(FlowBannerServiceImpl.class);
	
	@Autowired
	private FlowBannerDao flowBannerDao;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Override
	public void save(FlowBanner flowBanner) {
		// TODO Auto-generated method stub

		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			flowBanner.setCreatedBy(currentUser.getId());
			flowBanner.setLastModifiedBy(currentUser.getId());
		}
		Date createDate = new Date();
		flowBanner.setCreatedDate(createDate);
		flowBanner.setLastModifiedDate(createDate);
		flowBanner.setDeleted(Constants.SYS_DELETED_FALSE);
		if(null == flowBanner.getIsShow()){
			flowBanner.setIsShow(2);
		}
		flowBannerDao.save(flowBanner);
	}

	@Override
	public void update(FlowBanner seoSetting) {
		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			seoSetting.setLastModifiedBy(currentUser.getId());
		}
		seoSetting.setLastModifiedDate(new Date());
		if(null == seoSetting.getIsShow()){
			seoSetting.setIsShow(2);
		}
		flowBannerDao.update(seoSetting);
		
	}
	
	
	@Override
	public boolean batchDeleteFlowBanner(Long[] ids) {
		for (Long id : ids) {
			flowBannerDao.delete(id);
		}
		return true;
	}


	@Override
	public List<FlowBanner> findAllByBankId(FlowBanner flowBanner,UserAgent ua) {
		String dicCode = "";
		if(ua.isMobile()){//手机
			dicCode = Constants.DT_ZCPT_MOBILE;
		}else{//pc
			dicCode = Constants.DT_ZCPT_PC;
		}
		Dictionary dictionary = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_ZCPT, dicCode);
		return flowBannerDao.findAllByBankIdAndPageId(flowBanner.getPageDictionary().getId(),dictionary.getId());
	}

	@Override
	public List<FlowBanner> findAllByBankIdMobile(FlowBanner flowBanner) {
		String dicCode = Constants.DT_ZCPT_MOBILE;;
		Dictionary dictionary = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_ZCPT, dicCode);
		return flowBannerDao.findAllByBankIdAndPageId(flowBanner.getPageDictionary().getId(),dictionary.getId());
	}
}
