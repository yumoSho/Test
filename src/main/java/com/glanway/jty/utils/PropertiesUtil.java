package com.glanway.jty.utils;

import com.glanway.jty.exception.CustomException;

import java.io.*;
import java.util.Properties;

/**
 * 从config.properties  中拿值
 * @author tianxuan
 * @Time 2016/4/22
 */
public class PropertiesUtil {
    private static Properties config;

    static {
        config = new Properties();
    }

    /**
     * 获取 properties 文件
     * @return
     */
    public static Properties getConfig(){
        return  PropertiesUtil.getConfig(null);
    }

    /**
     * 获取 properties 文件
     * @return
     */
    public static Properties getConfig(String confName){
        confName = (null == confName || confName.isEmpty()) ? "config" : confName;
        try {
            FileInputStream in = new FileInputStream("/" + getWebInfAddress() + File.separator + confName + ".properties");
            config.load(in);
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return  config;
    }

    /**
     * 得到类路径
     * @return
     */
    private static String getWebInfAddress() {
        Class<PropertiesUtil> thisClass = PropertiesUtil.class;
        java.net.URL u = thisClass.getResource("");
        // str会得到这个函数所在类的路径
        String str = u.toString();
        // 截去一些前面6个无用的字符
        str = str.substring(6, str.length());
        // 将%20换成空格（如果文件夹的名称带有空格的话，会在取得的字符串上变成%20）
        str = str.replaceAll("%20", " ");
        // 查找“WEB-INF”在该字符串的位置
        int num = str.indexOf("classes");
        // 截取即可
        str = str.substring(0, num + "classes".length());
        return str;
    }

    /**
     * 从config.properties 读取值
     * @param key
     * @return
     */
    public static String readValue(String key) {
        String rtVal = null;
        try {
            rtVal = config.getProperty(key);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return rtVal;
    }

    /**
     * 将properites 写出到配置文件
     * @param pro
     * @param confName
     * @param comments
     */
    public static void writeProperties(Properties pro, String confName,String comments){
        try {
            FileOutputStream out = new FileOutputStream("/" + getWebInfAddress() + File.separator + confName + ".properties");
            pro.store(out, comments);
        } catch (Exception e) {
            throw  new CustomException("写入网站配置信息失败");
        }
    }

}
