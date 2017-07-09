package com.glanway.jty.interceptor;

import com.glanway.jty.common.Constants;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.service.customer.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 前台登录的验证
 *
 * @author tianxuan
 * @Time 2016/4/21
 */
public class WebInterceptor implements HandlerInterceptor {

    @Autowired
    private MemberService memberService;

    /**
     * 拦截前台页面，如果设保存密码则 自动登录
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean rtFlag = true;
        Member member = (Member) request.getSession().getAttribute(Constants.MEMBER);
        String requestURI = request.getRequestURI();
        boolean isAjax = ("XMLHttpRequest".equals(request.getHeader("X-Requested-with"))) ? true : false;
        if (null == member && !requestURI.contains("/cookieCart")) {
            StringBuffer requestURL = request.getRequestURL().append("%3F");
            Map<String,String[]> paramsMap = request.getParameterMap();
            for(String key : paramsMap.keySet()){
                requestURL.append(key).append("%3D").append(request.getParameter(key)).append("%26");
            }
            if(!isAjax){
                response.sendRedirect(request.getContextPath() + "/login?redirectURL=" + requestURL.toString());
            }
            rtFlag = false;
        }else if(null != member && requestURI.contains("/cookieCart")){
            /*如果登陆后访问 操作cookie的方法就跳转到 登陆后的购物车页面 */
            if(!isAjax){
                response.sendRedirect(request.getContextPath() + "/cart");
            }
            rtFlag = false;
        }
        return rtFlag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
