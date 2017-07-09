package com.glanway.jty.service.customer.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.util.StringUtils;
import com.glanway.jty.dao.customer.MemberDao;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.utils.AdminUserUtil;
import com.glanway.jty.utils.PassWordSaltUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * </b>Member Service
 *
 * @author generator
 * @version 1.0
 * @Time 2016-04-12
 */
@Service("memberService")
@Transactional
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {
    @Autowired
    private MemberDao memberdao;
    @Autowired
    private PassWordSaltUtil passWordSaltUtil;
    @Autowired
    private AdminUserUtil adminUserUtil;

    /**
     * 更新会员信息
     *
     * @param member 会员
     */
    @Override
    public void update(Member member) {
        String password = member.getPassword();
        Member oldMember = memberdao.find(member.getId());
        if (StringUtils.hasLength(password)) {
            member.setPassword(passWordSaltUtil.passWordSalt(StringUtils.trim(password), oldMember.getRegisterDate()));
        }else{
            member.setPassword(null);
        }
        member.setLastModifiedBy(null == adminUserUtil.getCurrentUser() ? null : Long.valueOf(adminUserUtil.getCurrentUser()));
        memberdao.update(member);
    }

    /**
     * 跟新会员你的登录时间和登录ip
     *
     * @param member
     */
    public void updateLoginTimeAndIp(Member member) {
        if (null != member && null != member.getId()) {
            memberdao.update(member);
        }
    }

    /**
     * 会员名称是否存在
     *
     * @param memberName 会员名
     * @return true：存在 false ：不存在
     */
    @Override
    public boolean nameIsExist(String memberName) {
        boolean isExist = true;
        if (StringUtils.hasLength(memberName)) {
            Map<String,Object> param = new HashMap<>();
            param.put("memberName",memberName.trim());
            int count = memberdao.isExist(param);
            isExist = (1 > count) ? false : isExist;
        }
        return isExist;
    }

    /**
     * 手机号是否存在
     *
     * @param phone 手机号
     * @return true:存在 false 不存在
     */
    @Override
    public boolean phoneIsExist(String phone) {
        boolean isExist = true;
        if (StringUtils.hasLength(phone)) {
            Map<String,Object> param = new HashMap<>();
            param.put("phone",phone.trim());
            int count = memberdao.isExist(param);
            isExist = (1 > count) ? false : isExist;
        }
        return isExist;
    }

    /**
     * 邮箱是否存在
     * @param email 手机号
     * @return true:存在 false 不存在
     */
    @Override
    public boolean emailIsExist(String email) {
        boolean isExist = true;
        if (StringUtils.hasLength(email)) {
            Map<String,Object> param = new HashMap<>();
            param.put("email",email.trim());
            int count = memberdao.isExist(param);
            isExist = (1 > count) ? false : isExist;
        }
        return isExist;
    }

    /**
     * 根据参数判断其是否存在
     * @param param
     * @return
     */
    @Override
    public boolean isExist(Map param) {
        boolean isExist = true;
        if (null != param && !param.isEmpty()) {
            int count = memberdao.isExist(param);
            isExist = (1 > count) ? false : isExist;
        }
        return isExist;
    }

    /**
     * 得到某个银行的会员注册数量
     *
     * @param
     * @return
     */
    @Override
    public Integer maxMemberCode() {
        return memberdao.maxMemberCode();
    }

    /**
     * 根据条件得到一个用户
     * @param param
     * @return
     */
    @Override
    public Member findOne(Map param) {
        return memberdao.findOne(param);
    }

    /**
     * 根据
     * @param param 手机号 或者 邮箱
     * @param password
     */
    public Member setPassword(Map<String,Object> param,String password) {
        Member member = memberdao.findOne(param);
        if (StringUtils.hasLength(password) && null != member) {
            String pwd = passWordSaltUtil.passWordSalt(StringUtils.trim(password), member.getRegisterDate());
            param.put("password",pwd);
            memberdao.setPassword(param);
             /*登录*/
            param.remove("password");
            try {
                BeanUtils.populate(member,param);
            } catch (Exception e) {
                throw new CustomException("系统异常");
            }
            return member;
        }else{
            throw new CustomException("修改密码的条件不满足");
        }
    }

    public int memberNameRepeat(Long id,String memberName){
        return memberdao.memberNameRepeat(id,memberName);
    }

    /**
     * 验证原密码是否正确
     * 根据
     * @param param 手机号 或者 邮箱
     * @param password
     */
    public Member setPassword(Map<String,Object> param,String password,String  oldPassword) {
        Member member = memberdao.findOne(param);
        Boolean flag = passWordSaltUtil.equalPassword(member.getPassword(), oldPassword, member.getRegisterDate());
        if(!flag){
            throw  new CustomException("原密码错误");
        }
        if (StringUtils.hasLength(password) && null != member) {
            String pwd = passWordSaltUtil.passWordSalt(StringUtils.trim(password), member.getRegisterDate());
            param.put("password",pwd);
            memberdao.setPassword(param);
             /*登录*/
            param.remove("password");
            try {
                BeanUtils.populate(member,param);
            } catch (Exception e) {
                throw new CustomException("系统异常");
            }
            return member;
        }else{
            throw new CustomException("修改密码的条件不满足");
        }
    }

    @Override
    public List<Member> findToExport(Filters filters, Pageable pageable) {
        Map<String, Object> params = FiltersAndPageToMap(filters, pageable.getSort());
       Integer count = memberdao.count(params);
        List<Member> list = (List<Member>) ((null != count && count > 0) ? memberdao.findMany(params) : Collections.emptyList());
        return list;
    }

    /**
     * 验证原密码是否一致
     * 根据
     * @param param 手机号 或者 邮箱
     * @param password
     */
    public Boolean validatePasswordEqual(Map<String,Object> param,String password,String  oldPassword) {
        Member member = memberdao.findOne(param);
        Boolean flag = passWordSaltUtil.equalPassword(member.getPassword(), oldPassword, member.getRegisterDate());
        return flag;
    }

    @Override
    public void active(Map param) {
        memberdao.active(param);
    }
}
