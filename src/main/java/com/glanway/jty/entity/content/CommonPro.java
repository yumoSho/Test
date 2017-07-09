package com.glanway.jty.entity.content;


import com.glanway.jty.entity.BaseEntity;

/**
 * 常见问题
 */
public class CommonPro extends BaseEntity {

	/**
	 * 常见问题
	 */
	private String title;
	
	/**
	 * 帮助中心内容 内容
	 */
	private String content;

	/**
	 * 是否删除
	 */
	private int deleted;
	
	/**
	 * 排序编号
	 */
	private String sortNum;

	/**
	 * 是否显示
	 */
	private Boolean display;

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

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}
}
