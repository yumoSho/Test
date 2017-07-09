
package com.glanway.jty.entity.perm;

import java.util.Date;
import java.util.List;

import com.glanway.jty.entity.BaseEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class User extends BaseEntity {
	public static final String DEFAULT_PWD = "123456";
	public static final int TYPE_SUPPLIER = 3;
	public static final int TYPE_BANK = 2;
	public static final int TYPE_HG = 1;

	private static Log logger = LogFactory.getLog(User.class);
	/**
	 * 用户名
	 */
	private  String userName;
	/**
	 * 密码
	 */
	private  String password;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 是否冻结
	 */
	private Boolean isFreeze;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 用户类型
	 * 1：红果 2:银行 3：供应商'
	 */
	private Integer userType;


    /**
	 * 客户id
	 * 当用户时银行用户时，此处存放银行ID
	 * 当用户类型为供应商是，此处存放供应商ID
	 */
	private Long customerId;

	private String customerName;//客户名称

	/**
	 * 用户最后登录时间
	 */
	private Date lastLoginTime;

	private Boolean deleted;

	private String lastLoginIp;//最后登录Ip

	private String isp;//isp信息

	/**
	 * 该用户角色列表
	 */
	private List<Role> roles;


	public User(){}

	public User(String userName, String password,Boolean isFreeze, String remark, Integer userType, Long customerId,String customerName) {
		this.userName = userName;
		this.password = password;
		this.isFreeze = isFreeze;
		this.remark = remark;
		this.userType = userType;
		this.customerId = customerId;
		this.customerName = customerName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Boolean isFreeze) {
		this.isFreeze = isFreeze;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
