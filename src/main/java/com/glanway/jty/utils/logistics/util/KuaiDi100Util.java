package com.glanway.jty.utils.logistics.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glanway.jty.utils.logistics.base.DomainRespBean;
import com.glanway.jty.utils.logistics.base.QueryTxnRequest;
import com.glanway.jty.utils.logistics.bean.KuaiDi100RequestBean;
import com.glanway.jty.utils.logistics.bean.KuaiDi100Result;
import com.glanway.jty.utils.logistics.bean.KuaiDi100ResultItem;
import com.glanway.jty.utils.logistics.common.DomainConstants;
import com.glanway.jty.utils.logistics.common.KuaiDi100Constants;

import java.util.*;

/** 
* @文件名: KuaiDi100Util.java
* @功能描述: 快递100处理工具类
* @author SunF
* @date 2016年4月19日 下午1:20:32 
*  
*/
public class KuaiDi100Util {

	/** 
	* @功能描述: 创建请求数据模型
	* @param queryTxnRequest
	* @return       
	*/
	public KuaiDi100RequestBean createRequestBean(QueryTxnRequest queryTxnRequest) {
		KuaiDi100RequestBean requestBean = new KuaiDi100RequestBean();
		/**此处需要如果要正常运行，需要添加将本系统物流公司编码转换为快递100制定编码的方法
		 * 因为暂不使用快递100所以省略*/
		requestBean.setCom(queryTxnRequest.getCompanyCode());
		
		requestBean.setNu(queryTxnRequest.getTrackingNumber());
		requestBean.setShow(KuaiDi100Constants.REQUEST_SHOW_TYPE_JSON);
		requestBean.setMuti(KuaiDi100Constants.REQUEST_MUTI_ONE);
		requestBean.setOrder(KuaiDi100Constants.REQUEST_ORDER_DESC);
		
		return requestBean;
	}

	/** 
	* @功能描述: 生成HTTP请求信息字符串
	* @param requestBean 请求数据源
	* @param customerId 客户id
	* @return       
	*/
	public HashMap<String, String> generateRequestParamMsg(KuaiDi100RequestBean requestBean, String customerId) {
		String paramJson = "{\"com\":\""+ requestBean.getCom() + "\",\"num\":\""+ requestBean.getNu() +"\"}";
		String key = requestBean.getId();
		String signStr = paramJson + key + customerId;
		String sign = MD5.encode(signStr);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("param", paramJson);
		params.put("sign", sign);
		params.put("customer",customerId);
		
		return params;
	}

	/** 
	* @功能描述: 根据参数列表组装请求数据格式
	* @param requestBean 数据源 
	* @param requestParam 参数列表
	* @return       
	*//*
	@java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
	private String reflexRequestData(Object reqBean, String[] array) {
		  if(array.length < 1){
	            throw new IllegalArgumentException("array is Empty");
	        }
			Class clazz = reqBean.getClass(); 
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<array.length; i++){
				String paramName = array[i];
				//get方法名称
				String methodName = "get" + paramName.substring(0, 1).toUpperCase()
										+ paramName.substring(1);
				try {
					//取值方法，组装请求参数
					Object value = clazz.getMethod(methodName).invoke(reqBean);
					value = value == null ? Constants.STRING_EMPTY : value;
					sb.append(Constants.STRING_AND).append(array[i])
					  .append(Constants.STRING_EQUALS).append(value.toString());
					
				} catch (Exception e) {
					return null;
				} 
			}
			String reqMsg = sb.substring(1, sb.length());
			return reqMsg;
	}*/

	/** 
	* @功能描述: 解析响应结果
	* @说明  因为暂时未申请所以无法得到正确响应结果，不知道如何解析，暂时搁置
	* @param requestMsgStr
	* @return       
	*/
	@SuppressWarnings("unchecked")
	public KuaiDi100Result parseResponseMsg(String requestMsgStr) {
		KuaiDi100Result result = JacksonHelper.fromJSON(requestMsgStr, KuaiDi100Result.class);
		
		if(KuaiDi100Constants.RESP_CODE_SUCCESS.equals(result.getStatus())){
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				
				//转换成map去其中的list
				Map<String, Object> maps = objectMapper.readValue(requestMsgStr, Map.class);
				List<LinkedHashMap<String, String>> resultInfoMap = (List<LinkedHashMap<String, String>>) maps.get("data");
				
				ArrayList<KuaiDi100ResultItem> data = new ArrayList<KuaiDi100ResultItem>();
				for(LinkedHashMap<String, String> map : resultInfoMap){
					KuaiDi100ResultItem ri = new KuaiDi100ResultItem();
					ri.setTime(map.get("time"));
					ri.setContext(map.get("context"));
					data.add(ri);
				}
				result.setData(data);
			} catch (Exception e) {
				return null;
			} 
		}
		return result;
	}

	/** 
	* @功能描述: 将快递100响应信息转换为基础信息模型
	* @param kuaidi100RespBean 快递100响应信息
	* @return       
	*/
	public DomainRespBean convertResponseBean(KuaiDi100Result resultBean) {
		List<KuaiDi100ResultItem> resultItemList = resultBean.getData();
		String trackingNo = resultBean.getNu();
		
		DomainRespBean respBean = new DomainRespBean();
		LinkedHashMap<String, String> routeInfoMap = new LinkedHashMap<String, String>();
		
		respBean.setTrackingNumber(trackingNo);
		for(KuaiDi100ResultItem routingMap : resultItemList){
			String time = routingMap.getTime();
			String context = routingMap.getContext();
			routeInfoMap.put(time, context);
		}
		respBean.setRouteInfoMap(routeInfoMap);
		
		/*快递公司编号暂时不保存，因为葒果项目只使用顺丰，
		 * 若以后需要保存编号，需要加入公司编号转换方法*/
		
		return respBean;
	}

	/** 
	* @功能描述: 查询请求
	* @param requestMsg HTTP请求信息
	* @return  响应结果字符串     
	*/
	@SuppressWarnings("static-access")
	public String queryLogisticsInfo(String url, HashMap<String, String> params) {
		String resp;
		try {
			resp = new HttpRequest().postData(url, params, DomainConstants.CHAR_ENCODING_UTF8).toString();
		} catch (Exception e) {
			resp = "";
		}
		return resp;
	}

}
