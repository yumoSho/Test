package com.glanway.jty.utils.sms;

import cn.emay.sdk.client.api.Client;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class SingletonClient {

    public static String softwareSerialNo = "3SDK-BSO-0130-JHURN";//软件序列号,请通过亿美销售人员获取
  	public static String key = "918550";//序列号首次激活时自己设定
	
	private static Client client=null;
	private SingletonClient(){
	}
	public synchronized static Client getClient(String softwareSerialNo, String key){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public synchronized static Client getClient(){
		ResourceBundle bundle=PropertyResourceBundle.getBundle("config");
		if(client==null){
			try {
				client=new Client(bundle.getString("softwareSerialNo"),bundle.getString("key"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public static void main(String str[]){
		SingletonClient.getClient();
	}
}
