package com.glanway.jty.utils.logistics.util;

import com.glanway.jty.utils.HttpUtils;
import com.glanway.jty.utils.logistics.base.DomainRespBean;
import com.glanway.jty.utils.logistics.base.QueryTxnRequest;
import com.glanway.jty.utils.logistics.bean.ShunFengRouteRequestBean;
import com.glanway.jty.utils.logistics.bean.ShunFengRouteResponseBean;
import com.glanway.jty.utils.logistics.bean.ShunFengRouteResponseBean.RouteInfo;
import com.glanway.jty.utils.logistics.common.DomainConstants;
import com.glanway.jty.utils.logistics.common.ShunFengConstants;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.log4j.Logger;
import org.springframework.util.Base64Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** 
* @文件名: ShunFengUtil.java
* @功能描述: 顺丰处理工具类
* @author SunF
* @date 2016年4月20日 下午2:06:00 
*  
*/
public class ShunFengUtil {
	Logger logger = Logger.getLogger(ShunFengUtil.class);

	/** 
	* @功能描述: 将通用查询数据转换成顺丰数据模型
	* @param queryTxnRequest
	* @return       
	*/
	public ShunFengRouteRequestBean createRequestBean(QueryTxnRequest queryTxnRequest) {
		ShunFengRouteRequestBean requestBean = new ShunFengRouteRequestBean();
		requestBean.setTrackingNumber(queryTxnRequest.getTrackingNumber());
		requestBean.setTrackingType(ShunFengConstants.DEFAULT_REQUEST_TRACKING_TYPE);
		requestBean.setMethod_type(ShunFengConstants.DEFAULT_REQUEST_METHOD_TYPE);
		return requestBean;
	}

	/** 
	* @功能描述: 生成xml报文
	* @param requestBean 请求数据
	* @return       
	*/
	public String generateRequestParamMsg(ShunFengRouteRequestBean requestBean) {
		StringBuilder sb = new StringBuilder(ShunFengConstants.XML_HEAD);
		sb.append("<Head>BSPdevelop</Head>")
		.append("<Body><RouteRequest")
		.append(" tracking_type='").append(requestBean.getTrackingType())
		.append("' method_type='").append(requestBean.getMethod_type())
		.append("' tracking_number='").append(requestBean.getTrackingNumber())
		.append("'/></Body></Request>");
		return sb.toString();
	}

	/** 
	* @功能描述: 创建验证信息
	* @param checkword 用户密钥
	* @param requestXML 请求xml报文
	* @return       
	*/
	public String createVerifyCode(String checkword, String requestXML) {
		//编码规则 ： 1：连接   2：转MD5 3:转base64
		String signStr = requestXML + checkword;
		String md5Sign = MD5Util.MD5Encode(signStr, ShunFengConstants.CHAR_ENCODING_UTF8);
		String base64Sign = Base64Utils.encodeToString(md5Sign.getBytes());
		logger.info("签名密文完成：" + base64Sign);
		return base64Sign;
	}

	/** 
	* @功能描述: 解析顺丰网关响应报文
	* @param responseXml  响应报文
	* @return       
	*/
	@SuppressWarnings("rawtypes")
	public ShunFengRouteResponseBean parseResponseMsg(String responseXml) {
		/*例子		
		 <Response service="RouteService"> 
			<Head>OK</Head> 
			<Body> 
				<RouteResponse mailno="444003077898"> 
					<Route  accept_time="2015-01-04 10:11:26" 
							accept_address="深圳" remark="已收件" 
							opcode="50"/> 
					<Route accept_time="2015-01-05 17:41:50" 
						remark="此件签单返还的单号为 123638813180"
						opcode="922"/> 
				</RouteResponse> 
			</Body> 
		</Response>*/
		ShunFengRouteResponseBean respBean = null;
		try {
			XStream xs = new XStream(new DomDriver(DomainConstants.CHAR_ENCODING_UTF8, new XmlFriendlyNameCoder("-_", "_")));
			Class[] clazzArray = new Class[]{ShunFengRouteResponseBean.class, 
					RouteInfo.class,
					ShunFengRouteResponseBean.ShunfengBody.class};
			xs.processAnnotations(clazzArray); //批量添加注解
			xs.useAttributeFor(ShunFengRouteResponseBean.ShunfengBody.class, "mailno"); 
			xs.useAttributeFor(RouteInfo.class, "accept_time");
			xs.useAttributeFor(RouteInfo.class, "accept_address");
			xs.useAttributeFor(RouteInfo.class, "remark");
			xs.useAttributeFor(RouteInfo.class, "opcode");
			respBean = (ShunFengRouteResponseBean)xs.fromXML(responseXml);
		} catch (Exception e) {
			logger.error("收到错误响应报文，不解析直接返回错误");
			return null;
		}
		return respBean;
	}

	/** 
	* @功能描述: 发送网关通信
	* @param requestUrl 请求地址
	* @param requestXML 请求报文
	* @param verifyCode 请求验证码
	* @return       
	*/
	public String sendHttpPost(String requestUrl, String requestXML, String verifyCode) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(ShunFengConstants.HTTP_POST_PARMA_XML, requestXML);
		paramMap.put(ShunFengConstants.HTTP_POST_PARMA_VERIFYCODE, verifyCode);
		String response = HttpUtils.URLPost(requestUrl, paramMap, DomainConstants.CHAR_ENCODING_UTF8);
		logger.info("收到网关响应信息报文：" + response);
		return response;
	}

	/** 
	* @功能描述: 将顺丰响应模型转换为基础模型
	* @param shunfengRespBean
	* @return       
	*/
	public DomainRespBean convertResponseBean(ShunFengRouteResponseBean shunfengRespBean) {
		List<RouteInfo> RoutingInfoList = shunfengRespBean.getBody().getRouteInfoList();
		String trackingNo = shunfengRespBean.getBody().getMailno();
		
		DomainRespBean respBean = new DomainRespBean();
		LinkedHashMap<String, String> routeInfoMap = new LinkedHashMap<String, String>();
		
		respBean.setTrackingNumber(trackingNo);
		for(RouteInfo routingInfo : RoutingInfoList){
			String time = routingInfo.getAccept_time();
			String address = routingInfo.getAccept_address();
			String context = routingInfo.getRemark();
			routeInfoMap.put(time, address + " - " +context);
		}
		respBean.setRouteInfoMap(routeInfoMap);
		
		return respBean;
	}
	
	public static void main(String[] args){
		//正确报文
		String xml = "<Response service=\"RouteService\"> <Head>OK</Head> <Body> <RouteResponse mailno=\"444003077898\"> "
				+ "<Route  accept_time=\"2015-01-04 10:11:26\" accept_address=\"深圳\" remark=\"已收件\" opcode=\"50\"/> "
				+"	<Route accept_time=\"2015-01-05 17:41:50\" remark=\"此件签单返还的单号为 123638813180\" opcode=\"922\"/>"
				+ "</RouteResponse> </Body> </Response>";
		//错误报文（为了方便不解析）
		String errorXml = "<?xml version='1.0' encoding='UTF-8'?><Response><Head>ERR</Head><ERROR code=\"4001\">系统发生数据错误或运行时异常</ERROR></Response>";
		ShunFengUtil util = new ShunFengUtil();
		ShunFengRouteResponseBean respBean = util.parseResponseMsg(xml);
		System.out.println(respBean);
		
		ShunFengRouteResponseBean respBean2 = util.parseResponseMsg(errorXml);
		
		System.out.println(respBean2);
		
		//post请求测试
		/*String url = "http://bspoisp.sit.sf-express.com:11080/bsp-oisp/sfexpressService";
		String resp = util.sendHttpPost(url, xml, "000000");
		System.out.println(resp);*/
	}

}
