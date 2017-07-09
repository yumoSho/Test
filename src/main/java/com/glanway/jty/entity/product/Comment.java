package com.glanway.jty.entity.product;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.order.OrderDetail;

import java.util.Date;
import java.util.List;

public class Comment extends BaseEntity{
	/*商品*/
	private Goods goods;
	/*星级*/
	private Long grade;
	/*品论内容*/
	private String content;
	/*晒图*/
	private String photos;
	/*评论时间*/
	private Date commentTime;
	/*回复*/
	private String reply;
	/*回复时间*/
	private Date replyTime;
	/*是否前台展示*/
	private Boolean visible = false;
	/*是否删除*/
	private Boolean deleted = false;
	/*会员*/
	private Member member;
	/*晒图集合*/
	private List<String> photoList;
	/*订单详情*/
	private OrderDetail orderDetail ;
	
	/**
	 * @return the photoList
	 */
	public List<String> getPhotoList() {
		return photoList;
	}
	/**
	 * @param photoList the photoList to set
	 */
	public void setPhotoList(List<String> photoList) {
		this.photoList = photoList;
	}
	/**
	 * @return the grade
	 */
	public Long getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(Long grade) {
		this.grade = grade;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the photos
	 */
	public String getPhotos() {
		return photos;
	}
	/**
	 * @param photos the photos to set
	 */
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	/**
	 * @return the commentTime
	 */
	public Date getCommentTime() {
		return commentTime;
	}
	/**
	 * @param commentTime the commentTime to set
	 */
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	/**
	 * @return the reply
	 */
	public String getReply() {
		return reply;
	}
	/**
	 * @param reply the reply to set
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}
	/**
	 * @return the replyTime
	 */
	public Date getReplyTime() {
		return replyTime;
	}
	/**
	 * @param replyTime the replyTime to set
	 */
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	/**
	 * @return the visible
	 */
	public Boolean getVisible() {
		return visible;
	}
	/**
	 * @param visible the visible to set
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return the member
	 */
	public Member getMember() {
		return member;
	}
	/**
	 * @param member the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
}
