package com.glanway.jty.utils;



import com.glanway.jty.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;

/** 
* @文件名: AdminUserUtil.java
* @功能描述: 在线用户获取工具类
* @author 
* @date 2016年4月1日 下午3:18:51 
*  
*/
@Component("adminUserUtil")
public class AdminUserUtil {

    @Autowired
    private CacheUtil cacheUtil;

    /**
     * 获取当前用户ID
     * @return
     */
    public String getCurrentUser(){
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        Object cacheValue = cacheUtil.getCacheValue(Constants.AUTHORIZE_PREFIX_CURRENTUSER + sessionId);
        if(null==cacheValue){
            return null;
        }
        return cacheValue.toString();
    }

    /** 
    * @功能描述: 获取当前用户id
    * @说明：本方法仅供以Long类型做为主键的项目，否则请勿使用
    * @return       
    */
    public Long getCurrentUserId(){
    	String userId = this.getCurrentUser();
    	try {
    		return Long.valueOf(userId);
		} catch (NumberFormatException e) {
			return null;
		}
    }
    
    /**
     * 将当前用户放入缓存
     * @param value
     */
    public void setCurrentUser(String value){
        if(StringUtils.isEmpty(value)){
            return;
        }
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        cacheUtil.setCacheValue(Constants.AUTHORIZE_PREFIX_CURRENTUSER + sessionId, value);
    }

    /**
     * 将当前用户信息从缓存中清空
     */
    public void destroyCurrentUser(){
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        cacheUtil.removeCacheValue(Constants.AUTHORIZE_PREFIX_CURRENTUSER + sessionId);
    }
}
