package com.glanway.jty.utils.sms;

/**
 * 短信发送接口
 */
public interface SmsSender {
	
	// 第一信息短信 接口
    /*boolean sendSms(String mobile, String content);*/

    int sendSms(String[] mobiles, String content);

	// 亿美软通短信发送接口
	int sendSms(String mobile, String content);

}
