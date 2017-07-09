package com.glanway.jty.utils.logistics.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.Set;

/** 
* @文件名: LogisticsConfig.java
* @功能描述:  物流信息配置文件加载类 
* @author SunF
* @date 2016年1月25日 下午2:28:02 
*  
*/
public class LogisticsConfig {
	
	/*Logger 日志器*/
	private static Logger logger = Logger.getLogger(LogisticsConfig.class);
	
	/**
	 * Properties文件读取标志
	 */
	public static boolean initFlag = false;
	/**
	 * Properties文件路径
	 */
	private static String PROPERTIES_FILE_PATH = "logistics";
	/**
	 * Properties文件名称
	 */
	private final static String propFileName = "logistics.properties";
	/**
	 * Properties内容
	 */
	private static Properties properties = new Properties();
	
	/**
	 * 是否初始化检查, 如没有初始化则执行初始化
	 * @return true|false
	 */
	private static boolean initCheck(){
		if(! initFlag){
			return loadPropertiesFile();
		}
		return initFlag;
	}
	
	/**
	 * 初始化处理，返回初始化结果标志
	 * @return true|false
	 */
	private static boolean loadPropertiesFile(){
		logger.info("++++++++++++++++++++ LogisticsConfig开始初始化++++++++++++++++++++");
		PropertieReader propReader = new PropertieReader();
		properties = propReader.load(PROPERTIES_FILE_PATH, propFileName);
		if(null == properties || properties.isEmpty()){
			logger.info("!!!!!!!!!!!!!!!!!!!! LogisticsConfig初始化失败 !!!!!!!!!!!!!!!!!!!!");
			initFlag = false;
		}else{
			logger.info("-------------------- LogisticsConfig初始化成功 --------------------");
			initFlag = true;
		}
		
		return initFlag;
	}
	
	/**
	 * 输出当前Properties内容
	 * @return 
	 */
	public static String list(){
		StringBuffer str = new StringBuffer("LogisticsConfig_Info").append("\n");
		str.append("size=[").append(properties.size()).append("]\n");
		Set<Object> keySet = properties.keySet();
		for (Object obj : keySet){
			String key = (String)obj;
			str.append(key + "=[" + properties.getProperty(key)).append("]\n");			
		}
		return str.toString();
	}
	
	/**
	 * 设置资源文件路径, 设置后重新执行初始化, 如初始化失败则还原文件路径
	 * @param resourcePath
	 */
	public static void setResourcePath(String resourcePath){
		String tmpStr = PROPERTIES_FILE_PATH;
		if(null == resourcePath || "".equals(resourcePath)){
			logger.info(" String[ResourcePath] is Null or Empty ");
		}else{
			PROPERTIES_FILE_PATH = resourcePath;
			if(! loadPropertiesFile()) PROPERTIES_FILE_PATH = tmpStr;
		}
	}
	
	/**
	 * 取得资源文件路径
	 */
	public static String getPropertiesFilePath(){
		initCheck();
		return PROPERTIES_FILE_PATH;
	}
	
	/**
	 * 判断参数Key是否存在
	 */
	public static boolean containsKey(String key){
		if(initCheck()){
			return properties.containsKey(key);
		}
		return false;
	}
	
	/**
	 * 取配置文件参数
	 */
	public static String getConfigValue(String configName){
		return getConfigValue(configName, "");
	}
	
	/** 
	* @功能描述:  取配置文件参数
	* @param configName 配置名称
	* @param defaultValue 默认值
	* @return       
	*/
	public static String getConfigValue(String configName, String defaultValue){
		if(StringUtils.isEmpty(configName)){
			return defaultValue;
		}
		String ConfigValue = "";
		if(containsKey(configName)){
			ConfigValue = properties.getProperty(configName);
			if(StringUtils.isEmpty(ConfigValue)){
				return defaultValue;
			}else{
				return ConfigValue;
			}
		}
		return defaultValue;
	}
	
	public static void main(String[] args){
		String c = LogisticsConfig.getConfigValue("KUAIDI100_REQUEST_URL");
		System.out.println(c);
		String b = LogisticsConfig.getConfigValue("KUAIDI100_REQUEST_ID");
		System.out.println(b);
	}
}
