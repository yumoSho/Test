package com.glanway.jty.service.sms;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * 短信发送服务
 */
public interface SmsService {

    /**
     * 获取 发送 Token 的验证码
     *
     * @param id 验证码 ID
     * @return 验证码图片
     */
    BufferedImage getChallengeFor(String id);

    /**
     * 预验证 验证码
     *
     * @param id         验证码 ID
     * @param accessCode 验证码内容
     * @return 是否正确
     */
    boolean validateToken(String id, String accessCode);

    /**
     * 使用给定的 Token 发送短信
     *
     * @param token   token 包含 id(验证码id), clientId(一般是 ip), accessCode (验证码内容)
     * @param mobile  手机号码
     * @param content 内容
     */
    boolean send(AccessToken token, String mobile, String content);

    /**
     * 验证手机短信验证码
     *
     * @param smsCode   手机短信验证码
     * @param session
     */
    boolean validateSms(String smsCode, HttpSession session);
}
