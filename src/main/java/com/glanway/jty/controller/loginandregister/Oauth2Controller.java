package com.glanway.jty.controller.loginandregister;

import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.controller.cart.CookiesCartController;
import com.glanway.jty.entity.cart.Cart;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.customer.OauthBind;
import com.glanway.jty.service.cart.CartService;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.customer.OauthBindService;
import com.glanway.jty.utils.*;
import org.ponly.oauth2.Oauth2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
 * 第三方登录Controller
 *
 * @author SongZhe
 * @Time 2016/10/9
 */
@Controller
@RequestMapping("/oauth")
public class Oauth2Controller extends BaseController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private OauthBindService oauthBindService;

    @RequestMapping("{channel}/callback")
    public String callback(@PathVariable("channel") String channel, HttpServletRequest request, HttpSession session) {
        String openid = Oauth2Utils.getOpenid(request);
        String nickname = Oauth2Utils.getNickname(request);
        String avatarUrl = Oauth2Utils.getAvatarUrl(request);
        if (null == openid) {
            // 重新认证
            return "redirect:/oauth/" + channel;
        }else{
            Member member = (Member)session.getAttribute(Constants.MEMBER);
            if(null != member){
                //判断当前用户是否已经绑定此openid
                // 已绑定且是此用户跳转登录成功
                // 绑定账号不是此用户，登录失败
                // 未绑定Q直接绑定并返回成功
                OauthBind oauth = oauthBindService.findOne("memberId", member.getId());
                if(null==oauth){
                    //当前用户已经登录，但没有绑定到当前登录信息
                }else{
                    if(oauth.getOpenid().equals(openid)){
                        //当前已登录用户就是已经绑定的本次登录用户     直接返回首页
                        return "redirect:/";
                    }else{
                        //当前已经登录的用户已经绑定第三方登录但不是正在登录的第三方信息
                    }
                }
            }else{
                //注册默认用户  或跳转到绑定用户界面    现在直接做生成默认账户并绑定处理
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("openid", openid);
                OauthBind oauthB = oauthBindService.findOne(params);
                if(null!=oauthB){  //已经授权登录过
                    Member memberInfo = new Member();
                    memberInfo.setId(oauthB.getMemberId());
                    memberInfo = memberService.find(memberInfo.getId());
                    memberInfo.setLastLoginTime(new Date());
                    memberService.update(memberInfo);
                    session.setAttribute(Constants.MEMBER, memberInfo);
                    session.setAttribute("userType", memberInfo.getRegisterFrom());
                }else{
                    //first 注册默认账户
                    Member defMember = new Member();
                    defMember.setMemberName(nickname);
                    defMember.setHeadImage(avatarUrl);
                    defMember.setRegisterDate(new Date());
                    defMember.setActivated(Boolean.TRUE);
                    defMember.setDeleted(Boolean.FALSE);
                    defMember.setStatus(1);
                    defMember.setLastLoginTime(new Date());
                    if("qq".equals(channel)){
                        defMember.setRegisterFrom(Member.REG_FROM_QQ);
                    }else if("wechat".equals(channel)){
                        defMember.setRegisterFrom(Member.REG_FROM_WE_CHAT);
                    }else if("weibo".equals(channel)){
                        defMember.setRegisterFrom(Member.REG_FROM_WEIBO);
                    }
                    memberService.save(defMember);

                    //绑定认证
                    OauthBind oauthBind = new OauthBind();
                    oauthBind.setMemberId(defMember.getId());
                    oauthBind.setChannel(channel);
                    oauthBind.setOpenid(openid);
                    oauthBind.setCreatedDate(new Date());
                    oauthBindService.save(oauthBind);
                    session.setAttribute(Constants.MEMBER, defMember);
                    session.setAttribute("userType", defMember.getRegisterFrom());
                }
            }
        }
        return "redirect:/";
    }
}
