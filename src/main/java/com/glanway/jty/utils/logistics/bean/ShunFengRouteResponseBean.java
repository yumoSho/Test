package com.glanway.jty.utils.logistics.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/** 
* @文件名: SunfengRouteResponseBean.java
* @功能描述: 顺丰快递路由信息响应数据
* @author SunF
* @date 2016年4月19日 下午3:44:28 
*  
*/
@XStreamAlias("Response")
public class ShunFengRouteResponseBean {
	@XStreamAlias("Head")
	private String head;
	
	@XStreamAlias("Body")
	private ShunfengBody body;
	
	public static class ShunfengBody{
		/** 
		 * @Fields mailno : 顺丰运单号 
		 */ 
		private String mailno;
		/** 
		 * @Fields orderid : 客户订单号
		 */ 
		private String orderid;
		/** 
		 * @Fields routeInfoList : 路由节点数据
		 */ 
		@XStreamAlias("RouteResponse")
		private List<RouteInfo> routeInfoList;

		public List<RouteInfo> getRouteInfoList() {
			return routeInfoList;
		}

		public void setRouteInfoList(List<RouteInfo> routeInfoList) {
			this.routeInfoList = routeInfoList;
		}

		public String getMailno() {
			return mailno;
		}

		public void setMailno(String mailno) {
			this.mailno = mailno;
		}

		public String getOrderid() {
			return orderid;
		}

		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}

		@Override
		public String toString() {
			return "ShunfengBody [mailno=" + mailno + ", orderid=" + orderid + ", routeInfoList=" + routeInfoList + "]";
		}
		
	}
	
	@XStreamAlias("Route")
	public static class RouteInfo{
		/** 
		* @Fields accept_time : 路由节点发生的时间
		*/ 
		//@XStreamAlias("accept_time")
		private String accept_time;
		/** 
		* @Fields accept_address : 路由节点发生的地点 
		*/ 
		//@XStreamAlias("accept_address")
		private String accept_address;
		/** 
		* @Fields remark : 路由节点具体描述 
		*/ 
		private String remark;
		/** 
		* @Fields opcode : 路由节点操作码 
		*/ 
		private String opcode;
	
		public String getAccept_time() {
			return accept_time;
		}
		public void setAccept_time(String accept_time) {
			this.accept_time = accept_time;
		}
		public String getAccept_address() {
			return accept_address;
		}
		public void setAccept_address(String accept_address) {
			this.accept_address = accept_address;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getOpcode() {
			return opcode;
		}
		public void setOpcode(String opcode) {
			this.opcode = opcode;
		}
		@Override
		public String toString() {
			return "RouteInfo [accept_time=" + accept_time + ", accept_address=" + accept_address + ", remark=" + remark
					+ ", opcode=" + opcode + "]";
		}
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public ShunfengBody getBody() {
		return body;
	}

	public void setBody(ShunfengBody body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ShunFengRouteResponseBean [head=" + head + ", body=" + body + "]";
	}
	
}
