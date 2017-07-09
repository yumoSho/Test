package com.glanway.jty.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class SerialNum {

	/**
	 * 产生流水号工具类
	 * 
	 * @version V1.0
	 * @date: 
	 */

	private static String count = "000";
	private static String dateValue = "20150101000000000";

	/**
	 * 产生流水号
	 */
	public synchronized static String getMoveOrderNo(String receiptType) {
        long no = 0;
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String now = sf.format(new Date());
        no = Long.parseLong(now);
        if (!(String.valueOf(now.substring(0, 8))).equals(dateValue.substring(0, 8))) {
            count = "000000";
            dateValue = String.valueOf(no);
        }
        String num = String.valueOf(no);
        num += getNo(count);
        num = receiptType + num;
        Random random = new Random();
        int r = 10;
        do {
            r = random.nextInt(100);
        } while (r < 10 || r > 99);
        num += r;
        return num;
	}

	/**
	 * 获取撤展单序列号
	 */
	public synchronized static String getMoveOrderNo(String receipt,String serialNum) {
		String nyr = StringUtils.substring(serialNum, 2, 10); // 获取年月日字符串
		String countV = StringUtils.substring(serialNum, 10); // 获取流水号
		if (Integer.valueOf(countV) > Integer.valueOf(count)) {
			dateValue = nyr;
			count = String.valueOf(countV);
		}
		return getMoveOrderNo(receipt);
	}

	/**
	 * 返回当天的订单数+1
	 */
	public static String getNo(String s) {
		String rs = s;
		int i = Integer.parseInt(rs);
		i += 1;
		rs = "" + i;
		for (int j = rs.length(); j < 3; j++) {
			rs = "0" + rs;
		}
		count = rs;
		return rs;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			//System.out.println(getMoveOrderNo(Constants.RECEIPT_TYPE_CG));
		}
	}

}
