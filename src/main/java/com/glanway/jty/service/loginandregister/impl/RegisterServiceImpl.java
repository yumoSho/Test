package com.glanway.jty.service.loginandregister.impl;

import com.glanway.jty.common.Constants;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.loginandregister.RegisterService;
import com.glanway.jty.utils.IPUtil;
import com.glanway.jty.utils.MemberUtil;
import com.glanway.jty.utils.NumberUtil;
import com.glanway.jty.utils.PassWordSaltUtil;
import com.glanway.jty.utils.UserAgent;
import com.glanway.jty.vo.RegisterVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 注册service的
 * @author tianxuan
 * @Time 2016/4/19
 */
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private PassWordSaltUtil passWordSaltUtil;

    /**
     * 注册用户并登陆
     * @param rv
     */
    @Override
    public void register(RegisterVo rv, HttpServletRequest request) throws Exception {
        /*密码加盐*/
        Date currentDate = (passWordSaltUtil.getFormatDate(new Date()));
        rv.setPassword(passWordSaltUtil.passWordSalt(rv.getPassword(), currentDate));

        Member member = new Member();
        BeanUtils.copyProperties(member,rv);
        member.setRegisterDate(currentDate);

        /*设置注册来源*/
        UserAgent ua = UserAgent.parse(request);
        member.setRegisterFrom(ua.isMobile() ? Member.REG_FROM_MOBILE : Member.REG_FROM_PC);
        
        //绑定推荐人
        if(null != rv.getRecommendedCode() && rv.getRecommendedCode().length() > 0){
        	member.setRecommendedId(MemberUtil.decodeRecommendedCode(rv.getRecommendedCode()));
        }
        
        synchronized(this){
            Integer count = memberService.maxMemberCode();//当前会员注册数
            count = (null != count) ? count : 0;
            String thisCount =  NumberUtil.zeroFill(++count, Member.CODE_LENGHT);
            String memberCode = Constants.SH + thisCount;
            member.setMemberCode(memberCode);


        /*最后登录ip和时间*/
            String loginIp = IPUtil.getIp(request);
            member.setLastLoginIp(loginIp);
            member.setLoginCount(1);
            member.setLastLoginTime(currentDate);

            memberService.save(member);//保存
            member.setPassword(null);
            request.getSession().setAttribute(Constants.MEMBER,member);
        }
    }
}
