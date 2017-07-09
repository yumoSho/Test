package com.glanway.jty.service.content;

import com.glanway.jty.entity.content.SupportContent;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * <p>名称: 帮助中心内容serivce</p>
 * <p>说明: 帮助中心内容serivce</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/6/2416:22
 * @version: 1.0
 */

public interface SupportContentService extends BaseService<SupportContent, Long>{

	String DELETED = "deleted";
	/** 
	* @功能描述: 批量删除帮助信息
	* @param ids
	* @return       
	*/
	boolean batchDeleteSupportContent(Long[] ids);
	
	
	
	/**
	 * 根据银行编号，查询其所有帮助信息记录
	 * @return
	 */
	public List<SupportContent> findAllByBankId();


}
