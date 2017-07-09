package com.glanway.jty.utils.logistics;

import com.glanway.jty.utils.logistics.common.DomainConstants;
import com.glanway.jty.utils.logistics.trans.ILogisticsTrans;
import com.glanway.jty.utils.logistics.trans.impl.KuaiDi100Trans;
import com.glanway.jty.utils.logistics.trans.impl.ShunFengTrans;
import org.apache.log4j.Logger;

/** 
* @文件名: LogisticsTransFactory.java
* @功能描述: 物流业务处理工厂 
* @author SunF
* @date 2016年4月20日 下午2:02:08 
*  
*/
public class LogisticsTransFactory {
	private static Logger logger = Logger.getLogger(LogisticsTransFactory.class);
	private LogisticsTransFactory(){}
	
	/** 
	* @功能描述: 根据渠道号处理获取物流信息处理类
	* @param channel 
	* @return       
	*/
	public static ILogisticsTrans getInstance(String channel){
		if(DomainConstants.LOGISTICS_CHANNEL_CODE_SHUNFENG.equals(channel)){
			logger.info("准备调用顺丰渠道物流服务类");
			return new ShunFengTrans();
		}
		else if(DomainConstants.LOGISTICS_CHANNEL_CODE_KUAIDI100.equals(channel)){
			logger.info("准备调用快递100渠道物流服务类");
			return new KuaiDi100Trans();
		}
		else{
			logger.error("未找到可用的渠道服务");
			return null;
		}
		
	}
	
}
