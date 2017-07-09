package com.glanway.jty.utils.sms;


import com.glanway.jty.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于云片短信平台
 */

public class YunPianSmsSenderImpl implements SmsSender {
	
    private static final Logger LOG = LoggerFactory.getLogger(YunPianSmsSenderImpl.class);

	//智能匹配模板发送接口的http地址
	private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";

	@Value("${sms.yunpian.apikey}")
	private String apikey;

	/**
	 * 	单次 自动匹配模板短信发送
	 * @param mobile 手机号
	 * @param content	发送内容
	 * @return
     */
    @Override
    public int sendSms(String mobile, String content) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("text", content);
		params.put("mobile", mobile);
		String result = HttpUtils.URLPost(URI_SEND_SMS, params, null);
		Matcher matcher = Pattern.compile("\"code\":(-?[0-9]*)").matcher(result);
		String code = null;
		if (matcher.find()) {
			code = matcher.group(1);
		}
		return null != code ? Integer.parseInt(code) : -99;
    }

	@Override
	public int sendSms(String[] mobiles, String content) {
		return 0;
	}
}
