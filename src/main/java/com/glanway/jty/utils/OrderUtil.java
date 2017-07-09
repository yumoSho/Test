package com.glanway.jty.utils;

/**
 * 订单工具类
 * @author songzhe
 *
 */
public class OrderUtil {

	/**
	 * 将数据库存的 订单status转成相应的状态值
	 * @param key
	 * @return
	 */
	public static String getOrderStatusValue(Integer key){
		
		if(key==null){
			return "";
		}
		String statusValue = "";
			switch (key) {
	        case 1:
	        	statusValue = "待支付";
	            break;
	        case 2:
	        	statusValue = "已支付";
	            break;
	        case 3:
	        	statusValue = "待发货";
	            break;
	        case 4:
	        	statusValue = "待收货";
	            break;
	        case 5:
	        	statusValue = "已确认收货";
	            break;
	        case 6:
	        	statusValue = "交易完成";
	            break;
	        case 7:
	        	statusValue = "交易取消";
	            break;
	        case 8:
	        	statusValue = "退换货处理中";
	            break;
	        case 9:
	        	statusValue = "问题/缺货";
	            break;
	        default:
	        	statusValue = "";
	
	    }
		return statusValue;
	}
}
