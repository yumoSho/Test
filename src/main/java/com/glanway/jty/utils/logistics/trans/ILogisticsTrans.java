package com.glanway.jty.utils.logistics.trans;

import com.glanway.jty.utils.logistics.base.QueryTxnRequest;
import com.glanway.jty.utils.logistics.base.QueryTxnResult;

/** 
* @文件名: ILogisticsTrans.java
* @功能描述: 业务处理公共接口
* @author SunF
* @说明：第一版为仅提供路由查询接口
* @date 2016年4月20日 下午2:06:24 
*  
*/
public interface ILogisticsTrans {
	
	/** 
	* @功能描述: 查询路由信息
	* @param dataBean 介入公司对应的API数据模型
	* @return       
	*/
	public QueryTxnResult queryRouteInfo(QueryTxnRequest queryTxnRequest);

}
