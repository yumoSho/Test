/*
 * Copyright (c) 2005, 2014  glanway.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.glanway.jty.common;

/**
 * @author  zuoyang
 * @date 创建时间：2015年10月26日 上午11:07:10 
 * @version 1.0  
 * @since  
 */
public interface Constants {
	/**
	 * @Fields STRING_TASBLE : 制表符
	 */
	String STRING_TASBLE = "|";

	/**
	 * @Fields STRING_EMPTY : 空字符串
	 */
	String STRING_EMPTY = "";

	/**
	 * @Fields STRING_COMMA : 逗号
	 */
	String STRING_COMMA = ",";

	/**
	 * @Fields STRING_SEPARATOR : 连接符
	 */
	String STRING_SEPARATOR = "-";

	/**
	 * @Fields SYS_DELETED_FALSE : 删除标记 - 未删除
	 */
	int SYS_DELETED_FALSE = 0;

	/**
	 * @Fields DEFAULT_HEAD_IMAGE : head图标
	 */
	String DEFAULT_HEAD_IMAGE = "storage/images/head.png";

	/**
	 * @Fields AUTHORIZE_PREFIX_PERMISSION : 缓存权限前缀
	 */
	String AUTHORIZE_PREFIX_PERMISSION = "permission_";

	/**
	 * @Fields AUTHORIZE_PREFIX_CURRENTUSER :缓存用户前缀
	 */
	String AUTHORIZE_PREFIX_CURRENTUSER = "currentUser_";

	/**@Fields WEBSITE_CONF : 网站设置配置文件名称 */
	String WEBSITE_CONF = "websiteConf";

	/**@Fields WEBSITE_CONF : 注册协议配置文件名 */
	String REG_CONF = "regProtocol";

	/**@Fields WEBSITE_CONF : 返利协议配置文件名 */
	String RT_MONEY_CONF = "rtMoneyConf";

	String DT_NRGL  = "NRGL";   //内容管理


	/**
	 * 	会员编号前缀
	 */
	String SH = "SH";


	/**
	 * 手机验证码位数
	 */
	int PHONE_VCODE_LEN = 6;

	/*********************mail begin*******************/
	/**
	 * 邮件 AES 加密 秘钥
	 */
	String  MAIL_AES_KEY = "Glanway_16_jty_mail";
	/*********************mail end*******************/
	/*********************session key 开始*******************/

	/*********************订单状态 编码 开始*******************/
	/**@Fields ORDER_EXPIRATION_ACCESSORY : 订单到期时间 */
	int ORDER_EXPIRATION_ACCESSORY = 24;

	public static final  String ORDER_CODE_PREFIX = "DD";
	public static final  String ORDER_GROUP_PREFIX = "GP";
	int ORDER_STATUS_DZF  = 1;//待支付
	int ORDER_STATUS_YZF  = 2;//已支付
	int ORDER_STATUS_DFH  = 3;//待发货
	int ORDER_STATUS_DSH  = 4;//待收货
	int ORDER_STATUS_YSH  = 5;//已确认收货
	int ORDER_STATUS_YWC  = 6;//交易完成
	int ORDER_STATUS_YQX  = 7;//交易取消
	int ORDER_STATUS_THH  = 8;//退换货处理中
	int ORDER_STATUS_WTQH  = 9;//问题、缺货
	/**
	 * @Fields ORDER_FINISH_DAY
	 * 天数：用于规定收货后自动完成订单的天数
	 */
	int ORDER_FINISH_DAY  = 7;

	/**
	 * @Fields ORDER_CANCLE_DAY
	 * 天数：用于规定生成订单后不支付订单 自动取消订单的天数
	 */
	int ORDER_CANCLE_DAY  = 3;

	/*********************订单状态 编码 结束*******************/
	/*********************订单 其它 开始*******************/
	/**@Fields ORDER_FROM_LJGM : 订单 来自立即 购物车） */
	int ORDER_FROM_LJGM = 1;
	/**@Fields ORDER_FROM_GWC : 订单 来自立即 购物车） */
	int ORDER_FROM_GWC = 2;
	/**@Fields ORDER_FROM_PJ : 订单 来自 配件） */
	int ORDER_FROM_PJ = 3;
	/**@Fields ORDER_FROM_TC : 订单 来自 套餐） */
	int ORDER_FROM_TC = 4;
/*********************订单 其它 结束*******************/
int MYCENTER_MYTRAD  = 1;//我的交易
	int MYCENTER_MYTRAD_MYORDER  = 4;//我的订单
	int MYCENTER_MYTRAD_MYRETURN  = 5;//退换货记录


	/**
	 * @Fields STRING_NULL : 字符串：null
	 */
	String STRING_NULL = "null";

	String DT_FBYM  = "FBYMMC"; //轮播图页面
	String DT_FBYM_INDEX  = "index";//首页
	String DT_FBYM_XSQG  = "xsqg";//限时抢购-正在进行
	String DT_FBYM_XSQG_HOPE  = "xsqghope";//限时抢购-即将开放

	String DT_YMMC  = "YMMC";   //页面名称
	String DT_YMMC_INDEX  = "index";   //首页
	String DT_YMMC_CART  = "cart";   //购物车


	String DT_GGPOS  = "GGPOS"; //广告位置
	String DT_GGPOS_ITOP  = "itop";//首页 头部
	String DT_GGPOS_ITOPBANNER = "itopbanner";// 首页  banner 左侧
	String DT_GGPOS_IMIDDLE  = "imiddle";//首页中间
	String DT_GGPOS_IFOOT  = "ifoot";//首页中间
	String DT_GGPOS_HDLB = "hdlb";//活动列表页

	/*********************数据字典 编码 开始*******************/
	/**@Fields ADMIN_USER : 后台登录用户 的session key */
	String ADMIN_USER = "user";
	/**
	 * @Fields DICTIONARY_BASE_SUPER_ID : 数据字典最高级别父类ID
	 */
	Long DICTIONARY_BASE_SUPER_ID = 10000L;

	String DT_YHLX  = "YHLX";   //用户类型
	String DT_ZCPT  = "ZCPT";   //注册平台
	String DT_ZCPT_PC  = "1";   //PC
	String DT_ZCPT_MOBILE  = "2";   //手机

	String DT_HYZT  = "HYZT";   //会员状态

	String DT_WLGS = "WLGS";//物流公司
	String DT_SHBZ = "SHBZ";//售后保障
	/*********************数据字典 编码 结束*******************/

	/*********************cookie name begin*******************/
	String COOKIE_AUTO_LOGIN = "mal";
	/**@Fields COOKIE_MAX_AGE : 自动登录的最大生存周期 */
	int COOKIE_MAX_AGE = 24 * 3600 * 7;  //cookie的最大生存时间
	/**
	 * 用户登录信息cookie加密秘钥
	 */
	String  COOKIE_AES_KEY = "Glanway_16_hg_mal";

	/**@Fields CART_COOKIE_NAME : 购物车cookie的存储 */
	String CART_COOKIE_NAME = "con";

	/**@Fields CART_AES_KEY : 购物车 cookie 加密秘钥 */
	String CART_AES_KEY = "Glanway_16_hg_cart";

	/**
	 * 银行缓存的key
	 */
	String CACHE_NAME_BANK = "customer.bank";
	/**
	 * 用户登录错误的缓存的key
	 */
	String CACHE_MEMBER_ERRROR = "member.error";
	/*********************cookie name end*******************/


	/*********************session key 开始*******************/

	/**
	 * 手机验证码 key
	 */
	String PHONE_VCODE_SESSION_KEY = "PHONE_VCODE_SESSION_KEY";
	/**
	 * 发送手机验证码的时间 key
	 */
	String PHONE_VCODE_SESSION_DATE = "PHONE_VCODE_SESSION_DATE";
	/**
	 * 手机号 key
	 */
	String PHONE_NUM = "PHONE_NUM";

	/**
	 * 会员在session中的存储
	 */
	String MEMBER = "member";
	/**
	 * 后台用户在session中的存储
	 */
	String USER = "user";

	/**
	 * 修改密码 邮箱
	 */
	String FIND_MAIL = "find_mail_";


	/**
	 * 修改密码 邮箱
	 */
	String FIND_MOBILE = "find_mail_";


	/**
	 * 是否能通过手机修改密码
	 */
	String  CAN_CHANGE_BY_PHONE = "canChangeByPhone_";

	/**
	 * 是否能通过邮箱修改密码
	 */
	String  CAN_CHANGE_BY_MAIL = "canChangeByMail_";

	/**@Fields PAY_ORDER_GROUP_CODE : 当前需要支付的 订单组编号 */
	String PAY_ORDER_GROUP_CODE = "pay_code_";

	/**@Fields PAY_ORDER_TOTAL_PRICE : 当前需要支付的 订单组编号 */
	String PAY_ORDER_TOTAL_PRICE = "pay_totalPrice_";
	/*********************session key end*******************/

	int SHEET_MAX_COUNT = 3000;   //excel一个sheet页最大行数

	String ALL_PROVINCE = "allProvince";// 所有省

	String COOKIE_STRING = "provinceId";


	/******************商品购买来源********************************/
	Integer GOODS_FROM_HY = 1;  //会员折扣
	Integer GOODS_FROM_HD = 2; //活动折扣
	Integer GOODS_FROM_XSQG = 3;//限时抢购
	Integer GOODS_FROM_TC = 4;//套餐



	/******************商品购买end********************************/


	/*---------------------配送时间--------------------------*/
	String PeiSongShiJian = "PSSJ";
	String PeiSongShiJian_1 = "PSSJ-1";
	String PeiSongShiJian_2 = "PSSJ-2";
	String PeiSongShiJian_3 = "PSSJ-3";

}
