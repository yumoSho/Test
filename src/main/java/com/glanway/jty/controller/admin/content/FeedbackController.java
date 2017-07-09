package com.glanway.jty.controller.admin.content;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.content.Feedback;
import com.glanway.jty.entity.content.Feedback;
import com.glanway.jty.entity.content.NewsType;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.service.content.FeedbackService;
import com.glanway.jty.service.customer.MemberService;
import com.google.common.collect.Maps;
import com.glanway.jty.service.content.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
* @文件名: FeedbackController.java
* @功能描述: 用户反馈类控制器
* @author songzhe
*  
*/
@Controller
@RequestMapping("/admin/feedback")
public class FeedbackController extends BaseController{

	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private MemberService memberService;
	
	/**
	 * 帮用户反馈列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		

		return "admin/feedback/index";
	}
	
	/**
	 * 异步请求用户反馈信息列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<Feedback> list(@Qualifier("tu.")Filters filters, Pageable pageable) {
		Page<Feedback> list = feedbackService.findPage(filters, pageable); //SQL ： findMany
		return list;
	}

	
	/** 
	* @功能描述: 添加用户反馈内容管理信息
	* @param
	* @return       
	*/
	@RequestMapping("save")
	public String save(Feedback supportContent) {
		feedbackService.save(supportContent);
		return "redirect:/admin/feedback/index";
	}
	
	
	
	/**
	 * 删除用户反馈内容管理
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Message delete(@RequestParam("id") Long[] ids) {
		feedbackService.delete(ids);
	    return Message.success();
	}


	/**
	 * @功能描述: 查看反馈信息
	 * @return
	 */
	@RequestMapping("show")
	public ModelAndView edit(Long id) {
		ModelAndView mav = new ModelAndView();
		Feedback feedback = feedbackService.find(id);
		if(!feedback.getFlag()){
			feedback.setFlag(true);
			feedbackService.update(feedback);  //标记为已查看
		}
		mav.addObject("feedback", feedback);
		mav.setViewName("/admin/feedback/show");
		return mav;
	}


	
}
