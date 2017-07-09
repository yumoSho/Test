/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 *
 * ProjectName: sh
 * FileName: AdminCommentController.java
 * PackageName: com.glanway.sh.controller.admin.product
 * Date: 2016/6/29 13:38
 **/
package com.glanway.jty.controller.admin.product;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.product.Comment;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.product.CommentService;
import com.glanway.jty.utils.HtmlCleaner;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/comment")
public class AdminCommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("index")
	public String index(){
		return "admin/comment/index";
	}

	/**
	 * <p>名称：list</p>
	 * <p>描述：列表页</p>
	 * @author：LiuJC
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Page<Comment> list(Filters filters, Pageable pageable){
		Page<Comment> page = commentService.findPage(filters, pageable);
		return page;
	}

	/**
	 * <p>名称：update</p>
	 * <p>描述：更新</p>
	 * @author：LiuJC
	 * @param comment
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public Map<String, Boolean> update(Comment comment){
		comment.setReplyTime(new Date(System.currentTimeMillis()));
		comment.setReply(HtmlCleaner.html(comment.getReply()));
		commentService.update(comment);
		Map<String, Boolean> map = Maps.newHashMap();
		map.put("success", true);
		return map;
	}

	/**
	 * <p>名称：edit</p>
	 * <p>描述：编辑</p>
	 * @author：LiuJC
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable(value="id") Long id, ModelMap map){
		Comment comment = commentService.getCommentById(id);
		List<String> photos = Arrays.asList(comment.getPhotos().split(","));
		Long grade = comment.getGrade() + 1;
		//判断星级
		if (grade > 5) {
			grade = grade - 5;
		}
		map.put("comment", comment);
		map.put("photos", photos);
		return "admin/comment/edit";
	}

	/**
	 * <p>名称：updateInEdit</p>
	 * <p>描述：编辑页更新</p>
	 * @author：LiuJC
	 * @param comment
	 * @return
	 */
	@RequestMapping("updateInEdit")
	public String updateInEdit(Comment comment){
		Comment comm = commentService.getCommentById(comment.getId());
		if (null == comm.getReply() || !comm.getReply().equals(comment.getReply())) {
			comment.setReplyTime(new Date(System.currentTimeMillis()));
		}
		comment.setReply(HtmlCleaner.html(comment.getReply()));
		commentService.update(comment);
		return "redirect:/admin/comment/index";
	}

	/**
	 * <p>名称：</p>
	 * <p>描述：删除品牌</p>
	 * @author：LiuJC
	 * @param ids 品牌ID数组
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Message delete(@RequestParam("id") Long[] ids) {
		if(null == ids && 0==ids.length){
			return Message.fail("没有有效的评论");
		}
		commentService.delete(ids);
		return Message.success();
	}
}
