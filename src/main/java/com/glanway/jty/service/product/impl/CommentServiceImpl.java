package com.glanway.jty.service.product.impl;

import com.glanway.jty.dao.product.CommentDao;
import com.glanway.jty.entity.product.Comment;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.order.OrderDetailService;
import com.glanway.jty.service.product.CommentService;
import com.glanway.jty.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 评论serviceImpl
 */
@Service
@Transactional
public class CommentServiceImpl extends BaseServiceImpl<Comment, Long> implements CommentService{
	private CommentDao commentDao;
	
	@Autowired
	public void setCommentDao(CommentDao commentDao){
		this.commentDao = commentDao;
		setCrudDao(commentDao);
	}
    @Autowired
    private MemberService memberService;
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * <p>名称：getCommentById</p>
     * <p>描述：获取评论信息</p>
     * @author：LiuJC
     * @param id
     * @return
     */
	@Override
	public Comment getCommentById(Long id) {
		return commentDao.getCommentById(id);
	}

    /**
     * <p>名称：getCommentByPage</p>
     * <p>描述：评论分页查询</p>
     * @author：LiuJC
     * @param map
     * @return
     */
	@Override
	public List<Comment> getCommentByPage(Map<String, Object> map) {
		Page pager = (Page) map.get("pager");
		Integer _offset = (pager.getCurPage() - 1) * pager.getPageSize();
		Integer _maxResults = pager.getPageSize();
		map.put("_offset", _offset);
		map.put("_maxResults", _maxResults);
		List<Comment> comments = commentDao.findMany(map);
		return comments;
	}

    /**
     * <p>名称：getComments</p>
     * <p>描述：获取评论信息  </p>
     * @author：LiuJC
     * @param page
     * @param GoodsId
     * @return
     */
    //todo 有待验证
    @Override
    public List<Comment> getComments(Page page, Long GoodsId) {
             if(page.getCurPage()<1)
                 page.setCurPage(1);
        if(page.getPageSize()<1)
            page.setPageSize(5);
        int count = this.count("orderItemId", GoodsId);
        page.setTotalCount(count);
        if(count<1)
            return null;
        page.setTotalPage((count + page.getPageSize() - 1) / page.getPageSize());
        if(page.getCurPage()>page.getTotalPage())
            page.setCurPage(page.getTotalPage());
        page.setBigenPage();
        page.setEndPage();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("pager", page);
        map.put("orderItemId", GoodsId);

        List<Comment> comments = this.getCommentByPage(map);
        if(null!=comments&&comments.size()>0) {
            for (Comment comment : comments) {
                List<String> blackStarList = new ArrayList<String>();
                List<String> whiteStarList = new ArrayList<String>();
                List<String> photos = new ArrayList<String>();
                if(null!=comment.getPhotos()&&comment.getPhotos().trim().length()>0)
                photos = Arrays.asList(comment.getPhotos().split(","));
                Long grade = null == comment.getGrade() ? 0L : comment.getGrade() + 1;
                //判断星级
                if (grade > 5) {
                    grade = grade - 5;
                }
                for (int i = 0; i < grade; i++) {
                    blackStarList.add(grade.toString());
                }
                for (int i = 0; i < 5 - grade; i++) {
                    whiteStarList.add(grade.toString());
                }
                comment.setPhotoList(photos);
                /*comment.setBlackStar(blackStarList);
                comment.setWhiteStar(whiteStarList);*/
            }
        }

        return comments;
    }

    /**
     * <p>名称：</p>
     * <p>描述：保存评论信息</p>
     * @author：LiuJC
     * @param comment
     */
    @Override
    public void saveComment(Comment comment) {
        comment.setCommentTime(new Date());
        comment.setCreatedDate(new Date());
        comment.setLastModifiedDate(new Date());
        commentDao.save(comment);
        //修改订单详情的状态
        orderDetailService.updateCommented(comment.getOrderDetail().getId());
    }
}
