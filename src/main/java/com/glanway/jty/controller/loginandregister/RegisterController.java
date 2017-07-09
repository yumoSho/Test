package com.glanway.jty.controller.loginandregister;

import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.loginandregister.RegisterService;
import com.glanway.jty.service.sms.AccessToken;
import com.glanway.jty.service.sms.SmsService;
import com.glanway.jty.utils.*;
import com.glanway.jty.utils.mail.Mail;
import com.glanway.jty.utils.mail.MailUtil;
import com.glanway.jty.vo.RegisterVo;
import com.google.code.kaptcha.Constants;
import com.google.common.collect.Maps;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ponly.web.util.WebUtils;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 会员注册Controller
 *
 * @author tianxuan
 * @Time 2016/4/15
 */
@Controller
@RequestMapping("/reg")
public class RegisterController extends BaseController{

    private static Log logger = LogFactory.getLog(RegisterController.class);
    private static final int FAIL_CODE_1 = 1;//手机号错误
    private static final int FAIL_CODE_2 = 2;//图片验证码错误
    private static final int FAIL_CODE_3 = 3;//手机验证码错误
    private static final int FAIL_CODE_4 = 4;//短信发送操作频繁
    private static final int FAIL_CODE_5 = 5;//用户名已存在
    @Autowired
    private MemberService memberService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private DictionaryService dictionaryService;
    /**
     * 跳转到注册页面
     *
     * @return 注册页
     */
    @RequestMapping
    public String toRegister(ModelMap  modelMap,String recommendedCode) {
        String path = "loginAndRegister/regist";
        if(this.isMobile){
            path = "mobile/" + path;
        }else {
            path = "pc/" + path;
        }
        
        if(recommendedCode  !=null && recommendedCode.length()>0){
        	modelMap.put("recommendedCode", recommendedCode);
        }

        //注册协议
        Map<String,String> param = Maps.newConcurrentMap();
        param.put("dicCode","DT_YHZCXY");
        Dictionary _dictionary = dictionaryService.findOne(param);
       /* String content = _dictionary.getContent();
        content.replaceAll("<[p|P][^>]*>","").replaceAll("<[p|P][^>]*>","<br/>");
        _dictionary.setContent(content);*/
        modelMap.put("dictionary",_dictionary);
        return path;
    }

    @RequestMapping("toRegisterByMail")
    public String toRegisterByMail(ModelMap  modelMap,String recommendedCode) {
        String path = "mobile/loginAndRegister/registEmail";
        if(recommendedCode  !=null && recommendedCode.length()>0){
            modelMap.put("recommendedCode", recommendedCode);
        }
        return path;
    }

    /**
     * 验证会员名的唯一性
     *
     * @param memberName 会员名
     * @return Message
     */
    @RequestMapping("verifyMemberName")
    @ResponseBody
    public Message verifyMemberName(String memberName) {
        boolean isExist = memberService.nameIsExist(memberName);
        return isExist ? Message.fail("用户名已注册") : Message.success();
    }

    /**
     * 验证手机号的唯一性
     * @param phone 手机号
     * @return Message
     */
    @RequestMapping("verifyPhone")
    @ResponseBody
    public Message verifyPhone(String phone) {
        boolean isExist;
        try {
            isExist = memberService.phoneIsExist(phone);
        } catch (Exception e) {
            /*当出现系统异常时，默认验证成功*/
            logger.info("验证会员注册手机号唯一性时报错");
            e.printStackTrace();
            isExist = false;
        }
        return isExist ? Message.fail("手机号已注册") : Message.success();
    }

    /**
     * 验证邮箱的唯一性
     * @param mail
     * @return
     */
    @RequestMapping("verifyMail")
    @ResponseBody
    public Message verifyMail(String mail) {
        boolean isExist;
        try {
            isExist = memberService.emailIsExist(mail);
        } catch (Exception e) {
            /*当出现系统异常时，默认验证成功*/
            logger.info("验证会员注册邮箱唯一性时报错");
            e.printStackTrace();
            isExist = false;
        }
        return isExist ? Message.fail("邮箱已注册") : Message.success();
    }

    /**
     * 发送手机验证码
     * @param phone 手机号
     * @param iCode 图片验证码
     * @return Message
     */
    @RequestMapping("sendPhoneVCode")
    @ResponseBody
    public Message sendPhoneVCode(String phone, String iCode, HttpServletRequest request, HttpSession session) throws Exception {
        Message message = Message.success();
        /*从session中获取图片验证码*/
        String kapachaCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        Long sendTime = (Long) session.getAttribute(Constants.KAPTCHA_SESSION_DATE);

        /*从session中获取图片验证码*/
        String phoneCode = (String) session.getAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_KEY);
        Long phoneSendTime = (Long) session.getAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_DATE);

        if (memberService.phoneIsExist(phone)) {
            /*判断手机号是否存在*/
            message = Message.fail("手机号已被绑定",FAIL_CODE_1);
        } else if (null == sendTime || (System.currentTimeMillis() - sendTime) > (15 * 60 * 1000) || null == iCode || !kapachaCode.toUpperCase().equals(iCode.trim().toUpperCase())) {
            /*校验图片验证码*/
            message = Message.fail("验证码过期或有误",FAIL_CODE_2);
        } else if (null != phoneCode && (System.currentTimeMillis() - phoneSendTime) < (60 * 1000)) {
            /*确保两分钟发一次*/
            message = Message.fail( "您操作频繁，请稍后再试",FAIL_CODE_4);
        } else {
            /*发送手机注册码*/

            String pCode = String.valueOf(NumberUtil.getRandomInt(com.glanway.jty.common.Constants.PHONE_VCODE_LEN)); //生成手机验证码
            String websiteName = PropertiesUtil.readValue("name");
            String content = "【"+websiteName+"】您正在注册" + websiteName + "会员，验证码" + pCode + ",请在15分钟内按页面提示提交验证码，请勿将验证码泄露于他人。";
          //  String content = "【有机伯伯】您的验证码是"+ pCode +"。如非本人操作，请忽略本短信";

            String clientIp = IPUtil.getIp(request);
            AccessToken token = new AccessToken(UUID.randomUUID().toString(), clientIp, pCode);
            Boolean isSuccess = smsService.send(token, phone, content);
            if (null == isSuccess || !isSuccess) {
                message = Message.fail("服务器忙，请稍后再试！");
            }
            /*从session中清除图片验证码*/
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
            session.removeAttribute(Constants.KAPTCHA_SESSION_DATE);

            /*短信验证信息放入session*/
            session.setAttribute(com.glanway.jty.common.Constants.PHONE_NUM, phone.trim());//手机号放入session
            session.setAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_KEY, pCode);//将手机验证码存放到session中
            session.setAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_DATE, System.currentTimeMillis());  //发送时间存放到session中
        }
        return message;
    }

    /**
     * 手机注册
     */
    @RequestMapping("register")
    @ResponseBody
    public Message register(RegisterVo rv,HttpServletRequest request,HttpSession session) throws Exception {
        Message message = Message.success();
        Long currentTime = System.currentTimeMillis();

        /*手机验证码相关*/
        String phoneCode = (String) session.getAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_KEY);
        Long phoneSendTime = (Long) session.getAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_DATE);
        String phone = (String) session.getAttribute(com.glanway.jty.common.Constants.PHONE_NUM);//获取手机号
        String pCode = StringUtil.trim(rv.getPCodes());//短信验证码
        if(null == rv.getMemberName() || rv.getMemberName().isEmpty()) {
            message = Message.fail("数据错误",99);
        } else if (memberService.phoneIsExist(rv.getPhone())) {
            /*判断手机号是否存在*/
            message = Message.fail("手机号已被绑定",FAIL_CODE_1);
        } else if (memberService.nameIsExist(rv.getMemberName())) {
            /*判断会员名是否重复*/
            message = Message.fail("用户名已存在",FAIL_CODE_5);
        } else if(null == phone || null == phoneCode || null == phoneSendTime){
             /*判断是否获取了验证码*/
            message = Message.fail( "请获取短信验证码",FAIL_CODE_3);
        }else if (!phone.equals(StringUtil.trim(rv.getPhone()))) {
            /*判断手机号是否与验证时匹配*/
            message = Message.fail( "手机号与验证时不符",FAIL_CODE_1);
        } else if (null == phoneCode && (currentTime - phoneSendTime) > (15 * 60 * 1000) || null == pCode || !phoneCode.toUpperCase().equals(pCode.toUpperCase())) {
            /*校验短信验证码*/
            message = Message.fail("验证码过期或有误",FAIL_CODE_3);
        } else {
            /*进行注册并登陆*/
            rv.setActivated(Boolean.TRUE);
            registerService.register(rv,request);
         /*从session中清除短信验证码*/
            session.removeAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_KEY);
            session.removeAttribute(com.glanway.jty.common.Constants.PHONE_VCODE_SESSION_DATE);
            session.removeAttribute(com.glanway.jty.common.Constants.PHONE_NUM);
        }
        return message;
    }

    /**
     * 邮箱注册
     */
    @RequestMapping("registerByMail")
    @ResponseBody
    public Message registerByMail(RegisterVo rv,HttpServletRequest request,HttpSession session) throws Exception {
        Message message = Message.success();
        /*从session中获取图片验证码*/
        String kapachaCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        Long sendTime = (Long) session.getAttribute(Constants.KAPTCHA_SESSION_DATE);
        String iCode = rv.getICodes();
        String mail = rv.getEmail();
        if (null == sendTime || (System.currentTimeMillis() - sendTime) > (15 * 60 * 1000) || null == iCode || !kapachaCode.toUpperCase().equals(iCode.trim().toUpperCase())) {
            /*校验图片验证码*/
            message = Message.fail("验证码过期或有误", FAIL_CODE_2);
        }else if(null == rv.getMemberName() || rv.getMemberName().isEmpty() || null == mail || mail.isEmpty()) {
            message = Message.fail("数据错误",99);
        } else if (memberService.emailIsExist(rv.getEmail())) {
            /*判断邮箱是否存在*/
            message = Message.fail("邮箱号已被绑定",FAIL_CODE_1);
        } else if (memberService.nameIsExist(rv.getMemberName())) {
            /*判断会员名是否重复*/
            message = Message.fail("用户名已存在",FAIL_CODE_5);
        }  else {
            /*进行注册并登陆*/
            rv.setActivated(Boolean.FALSE);
            registerService.register(rv,request);
            sendMail(mail,request);
        }
            /*从session中清除图片验证码*/
        session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        session.removeAttribute(Constants.KAPTCHA_SESSION_DATE);
        return message;
    }



    /**
     * 注册成功
     * @return
     */
    @RequestMapping("success")
    public String regOk(HttpSession session){
        String rtPath = "pc/loginAndRegister/regOk";
        Member member = (Member) session.getAttribute(com.glanway.jty.common.Constants.MEMBER);
        if(null == member){
            rtPath = "redirect:/reg";
        }
        return rtPath;
    }

    /**
     * 邮箱注册成功
     * @return
     */
    @RequestMapping("emailActivate")
    public String emailActivate(){
        String rtPath = "/loginAndRegister/emailActivate";
        if(this.isMobile){
            rtPath = "mobile" + rtPath;
        } else {
            rtPath = "pc" + rtPath;
        }
        return rtPath;
    }

    /**
     * 激活账号
     * @return
     */
    @RequestMapping("active")
    public String active(String token){
        Long mailSendTime = null;
        String email = null;
        if (null != token) {
            token = AESUtil.decrypt(token, com.glanway.jty.common.Constants.MAIL_AES_KEY);
            String[] strs = null != token ? token.split("_jty_") : new String[]{};
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
        if (null == token || null == email) {
            return "redirect:/reg";
        }
        Map<String,Object> param = new HashMap<>();
        param.put("email",email);
        memberService.active(param);
        return "redirect:/login";
    }

    /**
     * 发送账号激活邮件
     *
     * @returnid
     */
    private void sendMail(String mail, HttpServletRequest request) throws IOException, MessagingException {
        String subject = "【金佃园】-账号激活";
        String dns = IPUtil.getDNS(request);
        String token = mail + "_jty_" + System.currentTimeMillis();
        String url = dns + "/reg/active?token=" + AESUtil.encrypt(token, com.glanway.jty.common.Constants.MAIL_AES_KEY);
        String content = "请点击以下链接设置激活账号：<br/>";
        content += "<a href=\"" + url + "\">点击此链接激活账号</a><br/><br/>";
//        content += "提示:该链接将在30分钟后失效，请及时操作";
        Mail sendMail = new Mail(mail, subject, content);
        Session mailSession = MailUtil.createSession();
        MailUtil.send(mailSession, sendMail);
    }

 
    @RequestMapping("generateRecommendedCode")
    @ResponseBody
    public Map<String,String> generateRecommendedCode(HttpServletRequest request,HttpSession session){
    	Map<String,String> resultMap = Maps.newConcurrentMap();
    	String url = WebUtils.getApplicationUrl(request);
    	Member user = (Member) session.getAttribute(com.glanway.jty.common.Constants.MEMBER);
        if (null == user) {
        	resultMap.put("result","未登录");
        	resultMap.put("url",url+"/reg");
            return resultMap;
        }
        String recommendedCode = MemberUtil.generateRecommendedCode(user.getId());
        resultMap.put("result", url+"/reg?recommendedCode="+recommendedCode);
        resultMap.put("url",url+"/reg?recommendedCode="+recommendedCode);
        return resultMap;
    }

    /**
     * 跳转到注册协议（手机端）
     * @return
     */
    @RequestMapping("registDeal")
    public String toRegisterDeal(){
        return "mobile/loginAndRegister/registDeal";
    }
}
