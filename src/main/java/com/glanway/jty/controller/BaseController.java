package com.glanway.jty.controller;

import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.jty.common.Constants;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.utils.UserAgent;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/** 
* @文件名: BaseController.java
* @功能描述: 控制器公共基类 
* @author SunF
* @date 2016年3月31日 上午10:56:19 
*  
*/
public abstract class BaseController extends ControllerSupport{
	protected User user;//后台登录用户
	protected Member member;//前台登录用户

	/**@Fields isMobile : 是否移动端 */
	protected boolean isMobile;
	@ModelAttribute
	public void initParam(HttpServletRequest request, HttpSession session){
		user  = (User)session.getAttribute(Constants.ADMIN_USER);
		member = (Member)session.getAttribute(Constants.MEMBER);
		UserAgent ua = UserAgent.parse(request);
		this.isMobile = ua.isMobile();
	}

	/**
	 * 异常统一处理方法 ajax请求返回 Message.fail ,非ajax对象 返回 500
	 * @param request
	 * @param response
	 * @param e
	 * @return
     * @throws IOException
     */
	@ResponseBody
	@ExceptionHandler
	public Message handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
		e.printStackTrace();
		boolean isAjax = ("XMLHttpRequest".equals(request.getHeader("X-Requested-with"))) ? true : false;
		if (isAjax) {
			String message = null;
			if (e instanceof CustomException) {
				message = ((CustomException) e).getDescription();
			}
			return Message.fail((null != message && !message.isEmpty()) ? message : "系统异常");
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
	}
}
