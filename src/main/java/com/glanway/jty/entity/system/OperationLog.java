package com.glanway.jty.entity.system;

import com.glanway.jty.entity.perm.User;

import java.util.Date;

/**
 *系统操作日志类
 * @author tianxuan
 * @Time 2016/4/13
 */
public class OperationLog {
    public static final int SUCCESS = 1;  //操作成功
    public static final int ERROR = 2;   //操作失败
    private Long id;

    private String action;//行为


    private String otherFiled;//其他自定的内容  //本项目用作于操作登录名;

    private Integer operateResult;//结果  1:成功   2：失败

    private Date operateDate;//操做时间

    private Integer operateFrom;//操作平台

    private Integer operateType;//操作类型

    private User user;//操作人
    private Long userId;

    private String operateIp;//操作的ip

    public OperationLog(){}

    public OperationLog(String action, Integer operateType, Long userId) {
        this.action = action;
        this.operateType = operateType;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getOperateResult() {
        return operateResult;
    }

    public void setOperateResult(Integer operateResult) {
        this.operateResult = operateResult;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public Integer getOperateFrom() {
        return operateFrom;
    }

    public void setOperateFrom(Integer operateFrom) {
        this.operateFrom = operateFrom;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public String getOtherFiled() {
        return otherFiled;
    }

    public void setOtherFiled(String otherFiled) {
        this.otherFiled = otherFiled;
    }
}
