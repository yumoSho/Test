package com.glanway.jty.controller;

import com.glanway.jty.common.json.ResponseModelBuilder;
import com.glanway.jty.service.sms.SmsService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("captcha")
public class CaptchaImageCreateController {
	
	 @Autowired
    private Producer captchaProducer = null;

    @Autowired
    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

	@Autowired
	private SmsService smsService;

    @RequestMapping("captchaImage")
	public ModelAndView handleRequest(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_DATE, new Date().getTime());
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 生成验证码图片 ，将UUID存入session作为验证码ID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("captchaImageWithSession")
	public ModelAndView captchaImage2(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");

		String id = UUID.randomUUID().toString();
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, id);

		BufferedImage img = smsService.getChallengeFor(id);
		if (null != img) {
			ImageIO.write(img, "JPG", response.getOutputStream());
		}
		return null;
	}

	@RequestMapping(value = { "/getCaptchaValue" })
	public @ResponseBody String getCaptchaValue( HttpServletRequest request) {

		HttpSession session=request.getSession();
		String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

		return ResponseModelBuilder.ok(null, code).toJSONString();
	}

	/**
	 * 验证注册 图片验证码
	 * @param session
	 * @param captcha
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("validateSigninCaptcha")
	public Boolean validateCaptcha(HttpSession session, String captcha) {
		String id = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		return smsService.validateToken(id, captcha);
	}

	/**
	 * 验证 图片验证码
	 * @param session
	 * @param verifyCode
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("validateCaptcha")
    public Boolean validateCaptcha(String verifyCode,HttpSession session){
    	
    	String registerVerifyCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
    	Long registerVerifyDate = (Long) session.getAttribute(Constants.KAPTCHA_SESSION_DATE);
    	
    	if(StringUtils.hasText(registerVerifyCode)
    			&&registerVerifyDate!=null){
    		Long currentDate = new Date().getTime();
    		Long result = (currentDate-registerVerifyDate)/1000;
    		
    		if(registerVerifyCode.equals(verifyCode)&&result<400){
    			 return true;   
    		}
    	}
        return false; 
    }

}