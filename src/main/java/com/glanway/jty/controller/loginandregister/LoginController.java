package com.glanway.jty.controller.loginandregister;

import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.controller.cart.CookiesCartController;
import com.glanway.jty.entity.cart.Cart;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.service.cart.CartService;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前会员登录Controller
 *
 * @author tianxuan
 * @Time 2016/4/15
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
    @Autowired
    private PassWordSaltUtil passWordSaltUtil;

    @Autowired
    private MemberService memberService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private CartService cartService;


    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping
    public String toLogin(ModelMap map,HttpServletRequest request) {
        String ip = IPUtil.getIp(request);//登录ip
        cacheUtil.setCache(Constants.CACHE_MEMBER_ERRROR);
        Integer errNum = (Integer) cacheUtil.getCacheValue(ip);
        errNum = null == errNum ? 0 : errNum;//登录错误数量
        map.put("errNum", errNum);
        String url  = "pc/loginAndRegister/login";
        if(this.isMobile){
            url = "mobile/loginAndRegister/login";
        }
        return url;
    }

    @RequestMapping("loginFrame")
    public String loginFrame(ModelMap map,HttpServletRequest request) {
        String ip = IPUtil.getIp(request);//登录ip
        cacheUtil.setCache(Constants.CACHE_MEMBER_ERRROR);
        Integer errNum = (Integer) cacheUtil.getCacheValue(ip);
        errNum = null == errNum ? 0 : errNum;//登录错误数量
        map.put("errNum", errNum);
        String path = "pc/loginAndRegister/loginFrame";
        if(this.isMobile){
            path = "mobile/loginAndRegister/login";
        }
        return path;
    }

    /**
     * 登录
     *
     * @param loginKey 会员名
     * @param password   密码
     * @param vcode      图片验证码
     * @return Message
     */
    @RequestMapping("login")
    @ResponseBody
    public Message login(String loginKey, String password, String vcode, Boolean remember, HttpServletRequest request,
                         HttpServletResponse response, HttpSession session) {
        Message message = Message.success();
        String ip = IPUtil.getIp(request);//登录ip
        /*从session中获取图片验证码*/
        String kapachaCode = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        Long sendTime = (Long) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_DATE);

        cacheUtil.setCache(Constants.CACHE_MEMBER_ERRROR);
        Integer errNum = (Integer) cacheUtil.getCacheValue(ip);
        errNum = (null == errNum) ? 0 : errNum;//登录错误数量
        Long currentTime = System.currentTimeMillis();

        if (!StringUtil.notEmpty(loginKey) || !StringUtil.notEmpty(password)) {
            cacheUtil.setCacheValue(ip, ++errNum);
            message = Message.fail("请输入账号和密码", errNum);
        }/* else if ((errNum > 1) && (!StringUtil.notEmpty(vcode) || (currentTime - sendTime) > (15 * 60 * 1000) || null == kapachaCode || !kapachaCode.toUpperCase().equals(vcode.trim().toUpperCase()))) {
             *//*登录错误两次后就要校验验证码*//*
            cacheUtil.setCacheValue(ip, ++errNum);
            message = Message.fail("验证码过期或有误", errNum);
        }*/ else {
            loginKey = loginKey.trim();
            Member member;
            Map param = new HashMap<String,Object>();
            if (RegexUtil.isMobile(loginKey)) {
                param.put("phone", loginKey);
                member = memberService.findOne(param);
            }else if(RegexUtil.isMail(loginKey)){
                param.put("email", loginKey);
                member = memberService.findOne(param);
            } else {
                param.put("memberName", loginKey);
                member = memberService.findOne(param);
            }
            if (null != member && member.getStatus() == Member.STATUS_FREEZE) {
                cacheUtil.setCacheValue(ip, ++errNum);
                message = Message.fail("账号已冻结，请联系管理员", errNum);
            } else if(null != member && !member.getActivated()){
                message = Message.fail("账号未激活，请激活后登录", errNum);
            }else if (null == member || !passWordSaltUtil.equalPassword(member.getPassword(), password, member.getRegisterDate())) {
                cacheUtil.setCacheValue(ip, ++errNum);
                message = Message.fail("用户名或密码错误", errNum);
            } else {
                member.setLastLoginIp(ip);
                member.setLastLoginTime(new Date());
                Integer loginCount = member.getLoginCount();
                loginCount = (null == loginCount ? 0 : loginCount);
                member.setLoginCount(loginCount + 1);
                memberService.updateLoginTimeAndIp(member);

                /*会员信息 写入cookie*/
                String token = memberToAES(member.getMemberName(), member.getPassword());
                Cookie cookie = new Cookie(Constants.COOKIE_AUTO_LOGIN, token);
                if (null != remember && remember) {
                    cookie.setMaxAge(Constants.COOKIE_MAX_AGE);  //7天
                } else {
                    cookie.setMaxAge(0);  //删除cookie
                }
                cookie.setPath("/");
                response.addCookie(cookie);

                member.setPassword(null);
                session.setAttribute(Constants.MEMBER, member);//将会员放入session

//                合并cookie购物车 到 数据库 todo
                List<Cart> cartList = CookiesCartController.findCartList(request);
                cartService.saveCartBatch(cartList,member.getId());
                CookiesCartController.deleteCart(request,response);//清空本地cookie购物车
            }
        }
             /*从session中清除图片验证码*/
       /* session.removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        session.removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_DATE);*/
        return message;
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping("out")
    public String loginOut(HttpSession session,HttpServletResponse response){
        session.removeAttribute(Constants.MEMBER);
        Cookie cookie = new Cookie(Constants.COOKIE_AUTO_LOGIN, null);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

    private String memberToAES(String memberName, String pwd) {
        Long currentTime = System.currentTimeMillis();
        String content = memberName + "_" + pwd + "_" + currentTime;
        return AESUtil.encrypt(content, Constants.COOKIE_AES_KEY);
    }
}
