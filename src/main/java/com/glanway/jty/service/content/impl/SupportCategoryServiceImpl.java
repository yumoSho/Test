package com.glanway.jty.service.content.impl;


import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.content.SupportCategoryDao;
import com.glanway.jty.dao.content.SupportContentDao;
import com.glanway.jty.entity.content.SupportCategory;
import com.glanway.jty.entity.content.SupportContent;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.content.SupportCategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>名称: 帮助中心分类serivce</p>
 * <p>说明: 帮助中心分类serivce</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/6/2416:22
 * @version: 1.0
 */
@Service
@Transactional
public class SupportCategoryServiceImpl extends BaseServiceImpl<SupportCategory,Long> implements SupportCategoryService {

	Logger logger = Logger.getLogger(SupportCategoryServiceImpl.class);
	
	@Autowired
	private SupportCategoryDao supportCategoryDao;
	@Autowired
	private SupportContentDao supportContentDao;

	@Override
	public void save(SupportCategory supportCategory) {

		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			supportCategory.setCreatedBy(currentUser.getId());
		}
		supportCategory.setCreatedDate(new Date());
		supportCategory.setDeleted(Constants.SYS_DELETED_FALSE);
		supportCategoryDao.save(supportCategory);
	}

	@Override
	public void update(SupportCategory supportCategory) {
		User currentUser = super.getCurrentUserBean();
		if(null != currentUser){
			supportCategory.setLastModifiedBy(currentUser.getId());
		}
		supportCategory.setLastModifiedDate(new Date());
		if(null == supportCategory.getParent()){//一级分类，需要更新其所有二级分类
			supportCategoryDao.update(supportCategory);
			supportCategoryDao.updateByParentId(supportCategory);
		}else{//二级分类，只用更新自己
			supportCategoryDao.update(supportCategory);
		}
	}
	
	
	@Override
	public boolean batchDeleteSupportCategory(Long[] ids) {
			for (Long id : ids) {
				
				List<SupportContent> scs = supportContentDao.findListByCategoryId(id);
				if(null != scs && scs.size() > 0) {
					throw new CustomException("删除失败,该类别已有子项");
				}else {//二级分类，只用删除自己
					supportCategoryDao.delete(id);
				}
			}
			return true;
	}

	@Override
	public List<SupportCategory> findByParentId(SupportCategory supportCategory) {
		return supportCategoryDao.findByParentId(supportCategory);
	}

	@Override
	public List<SupportCategory> findAllByBankId() {
		return supportCategoryDao.findAllByBankId();
	}

	@Override
	public List<SupportCategory> findAllCategoryAndContentsByBankId() {
		return supportCategoryDao.findAllCategoryAndContentsByBankId();
	}

	@Override
	public List<SupportCategory> findFullCategoryAndContentsByBankId() {
		return supportCategoryDao.findFullCategoryAndContentsByBankId();
	}
}
