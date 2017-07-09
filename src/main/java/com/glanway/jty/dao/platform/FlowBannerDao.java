package com.glanway.jty.dao.platform;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.platform.FlowBanner;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 轮播图管理DAO类
 * @author songzhe
 *
 */
public interface FlowBannerDao extends BaseDao<FlowBanner,Long> {
	
	

	/**
	 * 查询指定银行下的所有内容
	 * @param bankId 银行编号 
	 * @param dicPageId 数据字典中的轮播图页面的编号
	 * @param dicCodePlatId 数据字典中的轮播图平台设备编号
	 * @return
	 */
	public List<FlowBanner> findAllByBankIdAndPageId(@Param("dicPageId") Long dicPageId, @Param("dicCodePlatId") Long dicCodePlatId);

	/**
	 * 查找 页面编号，平台编号，银行编号与参数相等的记录
	 * @param flowBanner
	 * @return
	 */
	public FlowBanner findByDicPageIdAndDicplatIdAndBankId(FlowBanner flowBanner);
}
