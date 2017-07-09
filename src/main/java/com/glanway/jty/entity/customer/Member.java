package com.glanway.jty.entity.customer;

import com.glanway.jty.entity.personalcenter.DeliveryAddress;
import org.apache.hadoop.classification.InterfaceAudience;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 前台会员实体
 *  @author  tianxuan
 *  @Time     2016/4/1
 *  @version 1.0
 */
public class Member {
    public static final int STATUS_NORMAL = 1;//正常
    public static final int STATUS_FREEZE = 2;//冻结

    public static final int REG_FROM_PC  = 1; //pc
    public static final int REG_FROM_MOBILE  = 2; //手机
    public static final int REG_FROM_QQ  = 3; //QQ
    public static final int REG_FROM_WE_CHAT  = 4; //微信
    public static final int REG_FROM_WEIBO  = 5; //微博
    public static final int CODE_LENGHT  = 7; //会员编号长度

    public static final int ACTIVATED_TRUE  = 1; //会员编号长度
    public static final int ACTIVATED_FALSE  = 0; //会员编号长度

    private Long id;

    private String memberName;//会员名

    private  String password;//密码

    private String memberCode;//会员编号

    private String phone;//手机

    private String email;//邮箱

    private String idNo;//身份证号

    private String realName;//真实姓名

    private Integer sex;//性别

    private String headImage;//头像

    private String address;

    private Integer registerFrom;//注册平台 1：pc 2:手机

    private Integer regType;//注册类型 1、手机 2、邮箱

    private Integer status;//状态 1：正常2：冻结

    private Date lastLoginTime;//最后登录时间

    private String lastLoginIp;//最后登录ip

    private String isp;//最后登录的 isp 信息 如 陕西省 西安市 信息

    private Date registerDate;//注册时间

    private Date lastModifiedDate;//最后修改时间

    private Long lastModifiedBy;//最后修改人

    private Boolean deleted;//是否删除

    private DeliveryAddress deliveryAddress;//收货地址，作为查询的载体

    /**@Fields loginCount : 登录次数 */
    private Integer loginCount;

    /**@Fields cardImg : 身份证照片正面 */
    private String cardImg;

    /**@Fields cardImgTwo : 身份证照片反面 */
    private String cardImgTwo;

    /**@Fields birthdate : 生日 */
    private String birthdate;

    /**@Fields activated : 是否激活 */
    private Boolean activated;

    /**@Fields provinceId : 省ID */
    private Long provinceId;

    /**@Fields recommendedId : 推荐人ID */
    private Long recommendedId;
    
    /**@Fields balance : 账户余额 */
    private BigDecimal balance;

    private  String gradeName;

    private Long gradeId;
    
    public Member(){
        this.status = STATUS_NORMAL;
        this.deleted = Boolean.FALSE;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Integer getRegisterFrom() {
        return registerFrom;
    }

    public void setRegisterFrom(Integer registerFrom) {
        this.registerFrom = registerFrom;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }


    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }


    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public String getCardImgTwo() {
        return cardImgTwo;
    }

    public void setCardImgTwo(String cardImgTwo) {
        this.cardImgTwo = cardImgTwo;
    }

    public Integer getRegType() {
        return regType;
    }

    public void setRegType(Integer regType) {
        this.regType = regType;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
	public Long getRecommendedId() {
		return recommendedId;
	}
	public void setRecommendedId(Long recommendedId) {
		this.recommendedId = recommendedId;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
