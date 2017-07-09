package com.glanway.jty.vo;

/**
 * @author tianxuan
 * @Time 2016/4/19
 */
public class RegisterVo {

    private String memberName;//会员名

    private String password;//密码

    private String confirmPassword;//重复密码

    private String phone;//手机号

    private String iCodes;//图片验证码

    private String pCodes;//手机验证码

    private String email;//用户邮箱

    private Integer regType;//注册类型  1、手机 2、邮箱

    private String token; //放重复提交token

    private Boolean activated;  //是否激活

    /**
     * 推荐码，格式为YJBB+Long.toHexString((ID*3)+9)
     * ID=Long.parseLong(recommendedCode)/2 -3
     */
    private String recommendedCode;//推荐码 

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getICodes() {
        return iCodes;
    }

    public void setICodes(String iCodes) {
        this.iCodes = iCodes;
    }

    public String getPCodes() {
        return pCodes;
    }

    public void setPCodes(String pCodes) {
        this.pCodes = pCodes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

	public String getRecommendedCode() {
		return recommendedCode;
	}

	public void setRecommendedCode(String recommendedCode) {
		this.recommendedCode = recommendedCode;
	}
    
}
