package com.glanway.jty.dao.product;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.Comment;

public interface CommentDao extends BaseDao<Comment, Long>{
	Comment getCommentById(Long id);
}
