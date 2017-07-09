package com.glanway.jty.service.product;


import com.glanway.jty.entity.product.Comment;
import com.glanway.jty.service.BaseService;
import com.glanway.jty.utils.Page;

import java.util.List;
import java.util.Map;


public interface CommentService extends BaseService<Comment, Long>{
	Comment getCommentById(Long id);

	List<Comment> getCommentByPage(Map<String, Object> map);

    //获取商品评论
    List<Comment> getComments(Page page, Long GoodsId);

    //新增评价
    void saveComment(Comment comment);
}
