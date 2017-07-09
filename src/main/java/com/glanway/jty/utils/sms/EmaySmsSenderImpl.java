package com.glanway.jty.utils.sms;

import cn.emay.sdk.client.api.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * 基于 亿美软通短信平台SDK4.3.0
 */

public class EmaySmsSenderImpl implements SmsSender {
	
    private static final Logger LOG = LoggerFactory.getLogger(EmaySmsSenderImpl.class);

	@Value("${sms.softwareSerialNo}")
	private String softwareSerialNo;//软件序列号,请通过亿美销售人员获取
	
	@Value("${sms.key}")
	private String key;//序列号首次激活时自己设定
  	
  	public static String password="338175";// 密码,请通过亿美销售人员获取
  	
  	/**
	 * 发送短信、可以发送定时和即时短信
	 * sendSMS(String[] mobiles,String smsContent, String addSerial, int smsPriority)
	 * 1、mobiles 手机数组长度不能超过1000
	 * 2、smsContent 最多500个汉字或1000个纯英文、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内 
	 * 3、addSerial 附加码(长度小于15的字符串) 用户可通过附加码自定义短信类别,或添加自定义主叫号码( 联系亿美索取主叫号码列表)
	 * 4、优先级范围1~5，数值越高优先级越高(相对于同一序列号)
	 * 5、其它短信发送请参考使用手册自己尝试使用
	 */
    @Override
    public int sendSms(String mobile, String content) {//
    	String[] mobileString = new String[1];
    	mobileString[0] = mobile;
    	int i = 1;
    	try {
    		Client sdkclient = new Client(softwareSerialNo,key);
			i = sdkclient.sendSMS(mobileString, content, 5);//带扩展码
			System.out.println(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
        //return sendSms(mobile.split("\\s*,\\s*"), content);
        return i;
    }

	@Override
	public int sendSms(String[] mobiles, String content) {
		return 0;
	}
}
