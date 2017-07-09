package com.glanway.jty.service.content.impl;

import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.content.NewsDao;
import com.glanway.jty.entity.content.News;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.content.NewsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class NewsServiceImpl extends BaseServiceImpl<News,Long> implements NewsService{

	Logger logger = Logger.getLogger(NewsServiceImpl.class);
	
	@Autowired
	private NewsDao newsDao;
	
	@Override
	public void save(News news) {
		// TODO Auto-generated method stub

		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
//			contentManagement.setCreateByUsername(currentUser.getUserName());
			news.setCreatedBy(currentUser.getId());
		}
		news.setCreatedDate(new Date());
		news.setDeleted(Constants.SYS_DELETED_FALSE);
		news.setHitNum(0);
		newsDao.save(news);
	}

	@Override
	public void update(News news) {
		// TODO Auto-generated method stub
		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			news.setLastModifiedBy(currentUser.getId());
		}
		news.setLastModifiedDate(new Date());
		newsDao.update(news);
		
	}
	
	
	@Override
	public boolean batchDeleteNews(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			newsDao.delete(id);
		}
		return true;
	}

	

	@Override
	public List<News> findAllByBankId(Long bankId) {
		// TODO Auto-generated method stub
		return newsDao.findAllByBankId(bankId);
	}

	@Override
	public void hitAccumulate(News news) {
		// TODO Auto-generated method stub
		
		int hitNum = news.getHitNum();
		news.setHitNum(hitNum+1);
		newsDao.update(news);
	}

	
	
}
