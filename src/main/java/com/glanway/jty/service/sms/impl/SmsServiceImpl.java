package com.glanway.jty.service.sms.impl;

import com.glanway.jty.service.sms.AccessToken;
import com.glanway.jty.service.sms.SmsService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * 简单的短信服务
 */
public class SmsServiceImpl extends SmsSupport implements SmsService {
    private Producer producer;
    private long captchaTimeout = 3 * 60;

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getChallengeFor(String id) {
        return null != id ? this.doCreateToken(id) : null;
    }

    protected BufferedImage doCreateToken(String id) {
        HttpSession session = (HttpSession) RequestContextHolder.getRequestAttributes().resolveReference("session");
        if (null == session) {
            return null;
        }
        String text = producer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY + id, text);
        session.setAttribute(Constants.KAPTCHA_SESSION_DATE + id, new Date().getTime());
        return producer.createImage(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateToken(String id, String accessCode) {
        HttpSession session = (HttpSession) RequestContextHolder.getRequestAttributes().resolveReference("session");
        if (null == session) {
            return false;
        }
        String text = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY + id);
        Long generateTime = (Long) session.getAttribute(Constants.KAPTCHA_SESSION_DATE + id);
        if (null == text || null == generateTime || new Date().getTime() - generateTime > captchaTimeout * 1000) {
            return false;
        }
        return text.equalsIgnoreCase(accessCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateSms(String smsCode, HttpSession session) {
        String registerVerifyCode = (String) session.getAttribute("registerVerifyCode");
        Long registerVerifyDate = (Long) session.getAttribute("registerVerifyDate");
        if (StringUtils.hasText(registerVerifyCode)
                && registerVerifyDate != null) {
            Long currentDate = new Date().getTime();
            Long result = (currentDate - registerVerifyDate) / 1000;

            if (registerVerifyCode.equals(smsCode) && result < 400) {
                return true;
            }
        }
        return false;
    }

    protected void cleanupToken(String id) {
        HttpSession session = (HttpSession) RequestContextHolder.getRequestAttributes().resolveReference("session");
        if (null != session) {
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY + id);
            session.removeAttribute(Constants.KAPTCHA_SESSION_DATE + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void assertTokenMatch(AccessToken token) {
        if (null == token) {
            throw new NullPointerException("token is null");
        }
        String id = token.getId();
        String accessCode = token.getAccessCode();
        try {
            if (!validateToken(id, accessCode)) {
                throw new IllegalStateException("ILLEGAL_TOKEN");
            }
            // TODO 这里为了兼容当前前台因此只在验证后删除，正常来讲应该只要验证就删除
            cleanupToken(id);
        } finally {
            // cleanupToken(id);
        }
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public long getCaptchaTimeout() {
        return captchaTimeout;
    }

    public void setCaptchaTimeout(long captchaTimeout) {
        this.captchaTimeout = captchaTimeout;
    }
}
