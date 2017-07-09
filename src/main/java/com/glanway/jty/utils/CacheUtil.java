package com.glanway.jty.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by ASUS on 2014/12/25.
 */
@Component("cacheUtil")
@Scope("prototype")
public class CacheUtil {

    private CacheManager cacheManager;

    private Cache cache;

    @Autowired
    public void setCacheManager(CacheManager cacheManager){
        this.cacheManager = cacheManager;
        this.cache = this.cacheManager.getCache("authz.cache");
    }

    /**
     * 设置要使用的cache
     * @param cacheName
     * @author tianxuan
     */
    public void setCache(String cacheName){
        this.cache = this.cacheManager.getCache(cacheName);
    }

    public  void setCacheValue(String key, Object value) {
        if (null == value) {
            cache.evict(key);
        } else {
            cache.put(key, value);
        }
    }


    public Object getCacheValue(String key){
        if(!StringUtils.hasText(key)){
            return null;
        }
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if(StringUtils.isEmpty(valueWrapper)){
            return null;
        }
        return valueWrapper.get();
    }

    public void removeCacheValue(String key){
        if(!StringUtils.hasText(key)){
            return;
        }

        cache.evict(key);
    }
}
