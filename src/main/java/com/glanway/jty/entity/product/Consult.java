package com.glanway.jty.entity.product;



import java.util.Date;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.customer.Member;

public class Consult extends BaseEntity{
	/*咨询问题*/
	private String question;
	/*咨询时间*/
	private Date askTime;
	/*咨询回复*/
	private String answer;
	/*咨询回复时间*/
	private Date replyTime;
	/*是否前台显示*/
	private Boolean visible = false;
	/*是否已删除*/
	private Boolean deleted = false;
	/*咨询商品*/
	private Goods goods;
	/*咨询会员*/
	private Member member;
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the askTime
	 */
	public Date getAskTime() {
		return askTime;
	}
	/**
	 * @param askTime the askTime to set
	 */
	public void setAskTime(Date askTime) {
		this.askTime = askTime;
	}
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
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
	 * @return the goods
	 */
	public Goods getGoods() {
		return goods;
	}
	/**
	 * @param goods the goods to set
	 */
	public void setGoods(Goods goods) {
		this.goods = goods;
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
	
}
