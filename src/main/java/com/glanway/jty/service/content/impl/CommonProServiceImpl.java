package com.glanway.jty.service.content.impl;

import com.glanway.jty.dao.content.CommonProDao;
import com.glanway.jty.entity.content.CommonPro;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.content.CommonProService;
import com.glanway.jty.service.content.SupportContentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommonProServiceImpl extends BaseServiceImpl<CommonPro,Long> implements CommonProService{

	Logger logger = Logger.getLogger(CommonProServiceImpl.class);
	
	@Autowired
	private CommonProDao commonProDao;
	
	
	@Override
	public boolean batchDeleteSupportContent(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			commonProDao.delete(id);
		}
		return true;
	}


	@Override
	public List<CommonPro> findAllByBankId() {
		// TODO Auto-generated method stub
		return commonProDao.findAllByBankId();
	}

	
	
	
}
