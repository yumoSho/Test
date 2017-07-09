package com.glanway.jty.utils;

import org.springframework.util.StringUtils;

import com.glanway.jty.common.Constants;

/** 
* @文件名: ArrayUtil.java
* @功能描述: 【字符串】数组操作工具类 
* @author 
* @date 2016年4月15日 下午3:23:43 
*  
*/
public class ArrayUtil {
	
	/** 
	* @功能描述: 将数组转换为字符串
	* @param strArray
	* @return       
	*/
	public static String arrayToString(String[] strArray){
		if(null == strArray || strArray.length < 1){
			return Constants.STRING_EMPTY;
		}
		StringBuffer result = new StringBuffer();
		for (int i=0; i<strArray.length; i++) {
			result.append(strArray[i]);
			if(i != strArray.length - 1){
				result.append(Constants.STRING_COMMA);
			}
		}
		return result.toString();
	}
	
	/** 
	* @功能描述: 字符串转数组
	* @param str 以逗号隔开的字符串
	* @return       
	*/
	public static String[] stringToArray(String str){
		if (!StringUtils.hasText(str)){
			return new String[0];
		}
		String[] result = str.split(Constants.STRING_COMMA);
		return result;
	}

    /** 
    * @功能描述: 判断数组中元素是否为空
    * @param array
    * @return       
    */
    public static boolean hasNotEmpty(String[] array) {
        if (null == array) {
            return false;
        }
        for (String s : array) {
            if (!StringUtils.isEmpty(s)) {
                return true;
            }
        }
        return false;
    }
    
}
