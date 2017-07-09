package com.glanway.jty.dao.platform;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.platform.SeoSetting;

import java.util.List;
import java.util.Map;


/**
 * 帮助中心内容管理DAO类
 * @author songzhe
 *
 */
public interface SeoSettingDao extends BaseDao<SeoSetting,Long> {
	
	

	/**
	 * 查询指定银行下的所有内容
	 * @param bankId
	 * @return
	 */
	public List<SeoSetting> findAllByBankId(Long bankId);

	/**
	 * 查找 页面编号，平台编号，银行编号与参数相等的记录
	 * @param seoSetting
	 * @return
	 */
	public SeoSetting findByDicPageIdAndDicplatIdAndBankId(SeoSetting seoSetting);

	List<SeoSetting> findList(Map param);
}
