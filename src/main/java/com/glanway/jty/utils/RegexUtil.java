package com.glanway.jty.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 *
 * @author tianxuan
 * @Time 2016/4/26
 */
public class RegexUtil {

    /**
     * 是否是手机号
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 是否是邮箱
     * @param str
     * @return
     */
    public static boolean isMail(String str) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
