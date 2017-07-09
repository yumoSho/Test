package com.glanway.jty.controller.findpassword;

import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.sms.AccessToken;
import com.glanway.jty.service.sms.SmsService;
import com.glanway.jty.utils.*;
import com.glanway.jty.utils.mail.Mail;
import com.glanway.jty.utils.mail.MailUtil;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 会员找回密码Controller
 *
 * @author tianxuan
 * @Time 2016/4/22
 */
@Controller
@RequestMapping("/retrieve")
public class FindPasswordController extends BaseController {

    private static final int FAIL_CODE_1 = 1; //手机或者邮箱错误
    private static final int FAIL_CODE_2 = 2; //验证码错误
    private static final int FAIL_CODE_4 = 4;//短信发送操作频繁

    @Autowired
    private MemberService memberService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private CacheUtil cacheUtil;

    /**
     * 跳转到找回密码的第一页
     *
     * @return
     */
    @RequestMapping
    public String stepOne() {
        String path = "findPassword/findPWDstep1";
        if(this.isMobile){
            path = "mobile/" + path;
        } else {
            path = "pc/" + path;
        }
        return path;
    }

    /**
     * 跳转到找回密码的第一页（手机端）
     *
     * @return
     */
    @RequestMapping("main")
    public String main() {
        String path = "mobile/findPassword/main";
        return path;
    }

    /**
     * 手机找回（手机端）
     * @return
     */
    @RequestMapping("findByPhone")
    public String findByPhone() {
        String path = "mobile/findPassword/findByPhone";
        return path;
    }

    /**
     * 邮箱找回（手机端）
     * @return
     */
    @RequestMapping("findByMail")
    public String findByMail() {
        String path = "mobile/findPassword/findByMail";
        return path;
    }
    /**
     * 验证邮箱找回密码的第一步操作
     *
     * @param mail   邮箱
     * @param vcodes 验证码
     * @return
     */
    @RequestMapping("/validateStepOneByEmail")
    @ResponseBody
    public Message validateStepOneByEmail(String mail, String vcodes, HttpServletRequest request, HttpSession session) {
        Message message = Message.success();
        /*从session中获取图片验证码*/
        String kapachaCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        Long sendTime = (Long) session.getAttribute(Constants.KAPTCHA_SESSION_DATE);

        if (null == sendTime || (System.currentTimeMillis() - sendTime) > (15 * 60 * 1000) || null == vcodes || !kapachaCode.toUpperCase().equals(vcodes.trim().toUpperCase())) {
            /*校验图片验证码*/
            message = Message.fail("验证码过期或有误", FAIL_CODE_2);
        } else if (null != mail && !memberService.emailIsExist(mail)) {
            /*验证邮箱是否与预留邮箱一致*/
            message = Message.fail("该邮箱未注册", FAIL_CODE_1);
        } else {
               /*发送邮件*/
            try {
                sendMail(mail, request);
                session.setAttribute(com.glanway.jty.common.Constants.FIND_MAIL + mail, mail);
                //一封邮件只能一次修改使用，发出第二封邮件，第一封失效
                cacheUtil.setCacheValue(com.glanway.jty.common.Constants.CAN_CHANGE_BY_MAIL + mail,Boolean.TRUE);
            } catch (Exception e) {
                e.printStackTrace();
                message = Message.fail("邮件发送失败", FAIL_CODE_2);
            }
        }
        return message;
    }

    /**
     * 验证手机找回密码的第一步操作
     *
     * @param mobile 手机号
     * @param vcodes 验证码
     * @return
     */
        @RequestMapping("/validateStepOneByMobile")
    @ResponseBody
    public Message validateStepOneByMobile(String mobile, String vcodes, HttpServletRequest request, HttpSession session) {
            Message message = Message.success();
        /*从session中获取图片验证码*/
        String kapachaCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        Long sendTime = (Long) session.getAttribute(Constants.KAPTCHA_SESSION_DATE);

        if (null == sendTime || (System.currentTimeMillis() - sendTime) > (15 * 60 * 1000) || null == vcodes || !kapachaCode.toUpperCase().equals(vcodes.trim().toUpperCase())) {
            /*校验图片验证码*/
            message = Message.fail("验证码过期或有误", FAIL_CODE_2);
        } else if (null != mobile && !memberService.phoneIsExist(mobile)) {
            /*验证邮箱是否与预留邮箱一致*/
            message = Message.fail("该手机号未注册", FAIL_CODE_1);
        } else {
            session.setAttribute(com.glanway.jty.common.Constants.FIND_MOBILE + mobile, mobile.trim());//手机号放入session
        }
        return message;
    }

    /**
     * 跳转到找回密码的第二步的页面
     *@param key 手机号或邮箱
     * @param type 找回方式 1：邮箱 2：手机
     * @return
     */
    @RequestMapping("/step2")
    public String stepTwo(String key, Integer type, HttpSession session, ModelMap modelMap) {
        String rtPath = "findPassword/findPWDstep2Mail";
        if (2 == type) {
            rtPath = "findPassword/findPWDstep2Mobile";
        }
        String mail = (String) session.getAttribute(com.glanway.jty.common.Constants.FIND_MAIL + key);
        String phone = (String) session.getAttribute(com.glanway.jty.common.Constants.FIND_MOBILE + key);
        modelMap.put("email",mail);
        modelMap.put("phone",phone);
        /*如果是手机端*/
        if(this.isMobile){
            rtPath = "mobile/" + rtPath;
        } else {
            rtPath = "pc/" + rtPath;
        }
        /*如果为null 就跳到第一步骤*/
        if ((null == mail && type ==1) || (null == phone && type ==2)) {
            rtPath = "redirect:/retrieve";
        }
        return rtPath;
    }


    /**
     * 发送短信验证码
     * @return
     */
    @RequestMapping("/sendPhoneVCode")
    @ResponseBody
    public Message send(String phone, HttpServletRequest request, HttpSession session) {
        Message message = Message.success();
        /*拿到第一步验证通过的手机号*/
        String mobile = (String) session.getAttribute(com.glanway.jty.common.Constants.FIND_MOBILE + phone);

         /*从session中获取图片验证码*/
        String phoneCode = (String) session.getAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_KEY);
        Long phoneSendTime = (Long) session.getAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_DATE);

        if (null != phoneCode && (System.currentTimeMillis() - phoneSendTime) < (2 * 60 * 1000)) {
            /*确保两分钟发一次*/
            message = Message.fail("您操作频繁，请稍后再试", FAIL_CODE_4);
        } else if (null == mobile) {
            message = Message.fail("请在上一步进行手机号验证", FAIL_CODE_2);
        } /*else if (null == vcodes || null == phoneCode || !phoneCode.toUpperCase().equals(vcodes.trim().toUpperCase())) {
            message = Message.fail("验证码过期或有误", FAIL_CODE_2);
        }*/ else {
              /*发送手机注册码*/
            String pCode = String.valueOf(NumberUtil.getRandomInt(com.glanway.jty.common.Constants.PHONE_VCODE_LEN)); //生成手机验证码
           //TODO String content = "【" + this.websiteName + "】您正在修改" + this.websiteName + "密码，验证码" + pCode + ",15分钟内有效，请勿将验证码泄露于他人。";
            String content = "【有机伯伯】您的验证码是"+ pCode +"。如非本人操作，请忽略本短信";
            String clientIp = IPUtil.getIp(request);
            AccessToken token = new AccessToken(UUID.randomUUID().toString(), clientIp, pCode);
            Boolean isSuccess = smsService.send(token, mobile, content);
            if (null == isSuccess || !isSuccess) {
                message = Message.fail("服务器忙，请稍后再试！");
            }
            /*短信验证信息放入session*/
            session.setAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_KEY + "_" + mobile, pCode);//将手机验证码存放到session中
            session.setAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_DATE + "_" + mobile, System.currentTimeMillis());  //发送时间存放到session中
        }
        return message;
    }

    /**
     * 校验手机验证码
     *
     * @param pCode
     * @param session
     * @return
     */
    @RequestMapping("/validateCodes")
    @ResponseBody
    public Message validateCodes(String pCode, String phone, HttpSession session) {
        Message message = Message.success();
        /*手机验证码相关*/
        String phoneCode = (String) session.getAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_KEY + "_" + phone);
        Long phoneSendTime = (Long) session.getAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_DATE + "_" + phone);
        pCode = StringUtil.trim(pCode);//短信验证码
         if (null == phoneSendTime || (System.currentTimeMillis() - phoneSendTime) > (15 * 60 * 1000) || null == pCode || !phoneCode.toUpperCase().equals(pCode.toUpperCase())) {
            /*校验短信验证码*/
            message = Message.fail("验证码过期或有误", FAIL_CODE_2);
        } else {
            session.setAttribute(com.glanway.jty.common.Constants.CAN_CHANGE_BY_PHONE + phone, true);//标识通过验证，可以修改密码
        }
        return message;
    }

    /**
     * 跳转到设置新密码页面（手机修改）
     *
     * @return
     */
    @RequestMapping("phoneToStep3")
    public String phoneToStep3(String phone,HttpSession session,ModelMap modelMap) {
        String rtPath = "findPassword/findPWDstep3";
        if(this.isMobile) {
            rtPath = "mobile/" + rtPath;
        } else {
            rtPath = "pc/" + rtPath;
        }
        Boolean canChange = (Boolean) session.getAttribute(com.glanway.jty.common.Constants.CAN_CHANGE_BY_PHONE + phone);
        /*没有凭证 到修改密码的第一步去*/
        if (null == canChange || !canChange) {
            rtPath =  "redirect:/retrieve";
        }else{
            modelMap.put("key",phone);
        }
        return rtPath;
    }

    /**
     * 跳转到设置新密码页面（邮箱修改）
     *
     * @param token   验证码
     * @param session
     * @return
     */
    @RequestMapping("emailToStep3")
    public String emailToStep3(String token, HttpSession session,ModelMap modelMap) throws UnsupportedEncodingException {
        String rtPath = "findPassword/findPWDstep3";
        if(this.isMobile){
            rtPath = "mobile/" + rtPath;
        } else {
            rtPath = "pc/" + rtPath;
        }

        /*解析token*/
        Long mailSendTime = null;
        String email = null;
        if (null != token) {
            token = AESUtil.decrypt(token, com.glanway.jty.common.Constants.MAIL_AES_KEY);
            String[] strs = null != token ? token.split("_") : new String[]{};
            for (int i = 0; i < strs.length; i++) {
                switch (i) {
                    case 0:
                        email = strs[i];
                        break;
                    case 1:
                        mailSendTime = Long.valueOf(strs[i]);
                        break;
                }
            }
        }
        Boolean flag = (Boolean) cacheUtil.getCacheValue(com.glanway.jty.common.Constants.CAN_CHANGE_BY_MAIL + email);

        if (null == flag || !flag ||null == token || null == email || (null == mailSendTime || (System.currentTimeMillis() - mailSendTime) > (30 * 60 * 1000))) {
            /*验证码错误则返回修改密码第一页*/
            session.removeAttribute(com.glanway.jty.common.Constants.FIND_MAIL + email);
            rtPath = "redirect:/retrieve";
        } else {
            //标识可以通过邮箱进行密码修改
            session.setAttribute(com.glanway.jty.common.Constants.CAN_CHANGE_BY_MAIL + email, true);
            session.setAttribute(com.glanway.jty.common.Constants.FIND_MAIL + email, email);
            modelMap.put("key",email);
        }
        return rtPath;
    }

    /**
     * 修改秘密
     *
     * @param password 新密码
     * @param session
     * @return
     */
    @RequestMapping("change")
    public String changePassword(String password,String key ,HttpSession session,HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        String rtPath = "redirect:/retrieve/success";
        if (!StringUtil.notEmpty(password)) {
            throw new CustomException("密码为空");
        }
        //是否可以通过手机修改密码
        Boolean canChangeByPhone = (Boolean) session.getAttribute(com.glanway.jty.common.Constants.CAN_CHANGE_BY_PHONE + key);
        //是否可以通过邮箱修改密码
        Boolean canChangeByMail = (Boolean) session.getAttribute(com.glanway.jty.common.Constants.CAN_CHANGE_BY_MAIL + key);

        Map<String,Object> param = new HashMap<>();
        param.put("loginIp",IPUtil.getIp(request));
        param.put("lastLoginTime",new Date());
        Member member;
        if (null != canChangeByPhone && canChangeByPhone) {
            /*根据手机修改密码*/
            String phone = (String) session.getAttribute(com.glanway.jty.common.Constants.FIND_MOBILE + key);
            param.put("phone",phone);
           member = memberService.setPassword(param,password);
            session.removeAttribute(com.glanway.jty.common.Constants.CAN_CHANGE_BY_PHONE + key);
            session.removeAttribute(com.glanway.jty.common.Constants.FIND_MOBILE + key);
            /*从session中清除短信验证码*/
            session.removeAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_KEY +"_" + key);
            session.removeAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_DATE +"_" + key);
        } else if (null != canChangeByMail && canChangeByMail) {
            /*根据邮箱修改密码*/
            String email = (String) session.getAttribute(com.glanway.jty.common.Constants.FIND_MAIL + key);
            param.put("email",email);
            member =  memberService.setPassword(param,password);
            session.removeAttribute(com.glanway.jty.common.Constants.CAN_CHANGE_BY_MAIL + email);
            session.removeAttribute(com.glanway.jty.common.Constants.FIND_MAIL + email);
            cacheUtil.removeCacheValue(com.glanway.jty.common.Constants.CAN_CHANGE_BY_MAIL + email);
        } else {
            throw new CustomException("没有凭证，不能修改密码");
        }
        if(null != member){
            session.setAttribute(com.glanway.jty.common.Constants.MEMBER,member);
        }
        return rtPath;
    }

    /**
     * 跳转到修改成功的页面
     *
     * @return
     */
    @RequestMapping("success")
    public String changeOk() {
        String path = "findPassword/findPWDstep4";
        if(this.isMobile){
            path = "mobile/" + path;
        }else {
            path = "pc/" + path;
        }
        return path;
    }

    /**
     * 发送邮件
     *
     * @return
     */
    private void sendMail(String mail, HttpServletRequest request) throws IOException, MessagingException {
        String subject = "【金佃园】-重置密码";
        String dns = IPUtil.getDNS(request);
        String token = mail + "_" + System.currentTimeMillis();
        String url = dns + "/retrieve/emailToStep3?token=" + AESUtil.encrypt(token, com.glanway.jty.common.Constants.MAIL_AES_KEY);
        String content = "您正在进行重置密码操作，点击以下链接设置新密码：<br/>";
        content += "<a href=\"" + url + "\">点击此链接重置密码</a><br/><br/>";
        content += "提示:该链接将在30分钟后失效，请及时操作";
        Mail sendMail = new Mail(mail, subject, content);
        Session mailSession = MailUtil.createSession();
        MailUtil.send(mailSession, sendMail);
    }
}
