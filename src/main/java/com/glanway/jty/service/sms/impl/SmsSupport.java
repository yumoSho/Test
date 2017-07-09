package com.glanway.jty.service.sms.impl;


import com.glanway.jty.service.sms.AccessToken;
import com.glanway.jty.service.sms.SmsService;
import com.glanway.jty.utils.sms.SmsSender;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 */
public abstract class SmsSupport implements SmsService {
    private static final String DEFAULT_SMS_CACHE_NAME = SmsSupport.class.getName() + ".SMS_CACHE";
    private static final String PREFIX = "SMS_";
    private static final Logger LOG = LoggerFactory.getLogger(SmsSupport.class);
    private long limitForDay;
    private long limitForMobile;
    private long frequencyMobile = 6 * 1000;
    private long limitForIP = 300;
    private long frequencyIP;

    private CacheManager cacheManager;
    private String smsCacheName = DEFAULT_SMS_CACHE_NAME;
    private SmsSender smsSender;

    @Override
    public boolean send(AccessToken token, String mobile, String content) {
//        assertTokenMatch(token);
        String clientId = token.getClientId();
        return doCheckAndSend(clientId, mobile, content);
    }

    protected abstract void assertTokenMatch(AccessToken token);

    protected boolean doCheckAndSend(String clientId, String mobile, String content) {
        final Cache cache = getRequiredSmsCache();
        final Date date = new Date();
        final long now = date.getTime();

        String key = PREFIX + new SimpleDateFormat("yyyyMMdd").format(date);
        Long total = getCachedValue(key);
        // 今天还没有数据, 则清空今日之前数据
        if (null == total) {
            cache.flush();
            total = 0L;
        }

        // 0. 所有用户总量
        assertTrue(!isTotalLimitEnabled() || total < limitForDay, "接口限制");

        ItemInfo mobileInfo = getCachedValue(mobile);
        mobileInfo = null != mobileInfo ? mobileInfo : new ItemInfo(0, -1);

        // 1. 同一手机号码频次限制
        assertTrue(!isMobileFrequencyEnabled() || now - mobileInfo.lastAccessTime > frequencyMobile, "在60s内只能发送1条， 请稍等片刻.");

        // 2. 同一手机号码 总量/天 限制
        assertTrue(!isMobileLimitEnabled() || mobileInfo.accessCount < limitForMobile, "系统检测到恶意发送短信, 禁止发送");

        ItemInfo clientInfo = null;
        clientId = "".equals(clientId) ? null : clientId;
        if (null != clientId) {
            clientInfo = getCachedValue(clientId);
            clientInfo = null != clientInfo ? clientInfo : new ItemInfo(0, -1);

            // 3. 同一 IP 频次
            assertTrue(!isAddressFrequencyEnabled() || now - clientInfo.lastAccessTime > frequencyIP, "所在IP发送频率太高， 请稍等片刻.");

            // 4. 同一 IP 总量
            assertTrue(!isAddressLimitEnabled() || clientInfo.accessCount < limitForIP, "系统检测到您的IP存在恶意发送短信行为，禁止发送");
        }

        boolean success = doSendSms(mobile, content);

        mobileInfo.accessCount++;
        mobileInfo.lastAccessTime = now;
        cache.put(new Element(mobile, mobileInfo));

        if (null != clientInfo) {
            clientInfo.accessCount++;
            clientInfo.lastAccessTime = now;
            cache.put(new Element(clientId, clientInfo));
        }

        total++;
        cache.put(new Element(key, total));

        if (LOG.isInfoEnabled()) {
            LOG.info("发送短信 '{}' -> {} {}, 该手机今日共发送{}条, IP({})今日共发送{}条, 服务器总计发送{}条",
                    mobile, content, success ? "成功" : "失败", mobileInfo.accessCount, clientId,
                    null != clientInfo ? clientInfo.accessCount : null, total
            );
        }
        return success;
    }

    protected boolean doSendSms(String mobile, String content) {
        SmsSender smsSender = getSmsSender();
        if (null == smsSender) {
            throw new IllegalStateException("No SmsSender found: no SmsSender configure?");
        }
        return 0 == smsSender.sendSms(mobile, content);
    }

    /* ********************************************************************
     *
     * ********************************************************************/

    private void assertTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    private boolean isMobileFrequencyEnabled() {
        return 0 < frequencyMobile;
    }

    private boolean isMobileLimitEnabled() {
        return 0 < limitForMobile;
    }

    private boolean isAddressFrequencyEnabled() {
        return 0 < frequencyIP;
    }

    protected boolean isAddressLimitEnabled() {
        return 0 < limitForIP;
    }

    protected boolean isTotalLimitEnabled() {
        return 0 < limitForDay;
    }

    @SuppressWarnings("unchecked")
    private <V> V getCachedValue(String key) {
        Element element = getRequiredSmsCache().get(key);
        return (V) (null != element ? element.getObjectValue() : null);
    }

    protected Cache getRequiredSmsCache() {
        CacheManager mgr = getRequiredCacheManager();
        Cache cache = mgr.getCache(smsCacheName);
        if (null == cache) {
            throw new IllegalStateException("No Cache(" + smsCacheName + ") found: no Cache configure?");
        }
        return cache;
    }

    private CacheManager getRequiredCacheManager() {
        CacheManager mgr = getCacheManager();
        if (null == mgr) {
            throw new IllegalStateException("No CacheManager found: no CacheManager configure?");
        }
        return mgr;
    }
    /* ******************************************************************
     *
     * ******************************************************************/

    public long getLimitForDay() {
        return limitForDay;
    }

    public void setLimitForDay(long limitForDay) {
        this.limitForDay = limitForDay;
    }

    public long getLimitForMobile() {
        return limitForMobile;
    }

    public void setLimitForMobile(long limitForMobile) {
        this.limitForMobile = limitForMobile;
    }

    public long getFrequencyMobile() {
        return frequencyMobile;
    }

    public void setFrequencyMobile(long frequencyMobile) {
        this.frequencyMobile = frequencyMobile;
    }

    public long getLimitForIP() {
        return limitForIP;
    }

    public void setLimitForIP(long limitForIP) {
        this.limitForIP = limitForIP;
    }

    public long getFrequencyIP() {
        return frequencyIP;
    }

    public void setFrequencyIP(long frequencyIP) {
        this.frequencyIP = frequencyIP;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public String getSmsCacheName() {
        return smsCacheName;
    }

    public void setSmsCacheName(String smsCacheName) {
        this.smsCacheName = smsCacheName;
    }

    public SmsSender getSmsSender() {
        return smsSender;
    }

    public void setSmsSender(SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    private static class ItemInfo implements Serializable {
        private long lastAccessTime;
        private long accessCount;

        public ItemInfo(long accessCount, long lastAccessTime) {
            this.lastAccessTime = lastAccessTime;
            this.accessCount = accessCount;
        }
    }
}
