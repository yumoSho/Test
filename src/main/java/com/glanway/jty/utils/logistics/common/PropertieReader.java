package com.glanway.jty.utils.logistics.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/** 
* @文件名: PropertieReader.java
* @功能描述: Prepertie文件阅读器
* @author SunF
* @date 2016年1月25日 下午2:27:29 
*  
*/
public class PropertieReader {
	private static Logger logger = Logger.getLogger(PropertieReader.class);
	
	public PropertieReader(){}
	
	/**
	 * 读取 Propertie 配置文件
	 * @param FilePath 文件路径
	 * @param FileName 文件名称
	 * @return  Properties 或者 NULL
	 */
	public Properties load(String FilePath, String FileName){
		
		if(StringUtils.isEmpty(FilePath)){
			logger.error("检测到配置文件路径为空！");
			return null;
		}
		if(StringUtils.isEmpty(FileName)){
			logger.error("配置文件名称为空");
			return null;
		}
		
		String resource = null;
		if(FilePath.endsWith("/")){
			resource = FilePath + FileName;
		}else{
			resource = FilePath + "/" + FileName;
		}
		
		Properties properties = new Properties();
		InputStream inputStream = null;
		try{
			logger.info("准备读取Propertie文件: " + resource);
			inputStream = PropertieReader.class.getClassLoader().getResourceAsStream(resource);
			properties.load(inputStream);
			if(null == properties || properties.isEmpty()){
				logger.info("Propertie文件[" + resource + "] 初始化失败 。 ");
				return null;
			}else{
				logger.info("Propertie文件[" + resource + "] 初始化成功。 ");
				return properties;
			}
		}catch (Exception ex){
			logger.error("Properties File[" + resource + "]加载错误：文件名重复或文件未找到。错误信息： " + ex.getCause());
			return null;
		}
		
	}
	
}
