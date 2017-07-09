package com.glanway.jty.entity.content;


import com.glanway.jty.entity.BaseEntity;

public class SupportContent extends BaseEntity {

	/**
	 * 帮助中心内容标题
	 */
	private String title;
	
	/**
	 * 帮助中心内容 内容
	 */
	private String content;
	
	/**
	 * 所属帮助中心分类
	 */
	private SupportCategory supportCategory;
	/**
	 * 是否删除
	 */
	private int deleted;
	
	/**
	 * 排序编号
	 */
	private String sortNum;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public SupportCategory getSupportCategory() {
		return supportCategory;
	}

	public void setSupportCategory(SupportCategory supportCategory) {
		this.supportCategory = supportCategory;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public String getSortNum() {
		return sortNum;
	}

	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}
	
	
}
