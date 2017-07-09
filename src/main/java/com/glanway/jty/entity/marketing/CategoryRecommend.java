package com.glanway.jty.entity.marketing;

import com.glanway.jty.entity.BaseEntity;

/**
 * 分类推荐实体类
 * @author songzhe
 *
 */
public class CategoryRecommend extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 标签名称
	 */
	private String title;
	
	/**
	 * 链接地址
	 */
	private String url;
	
	/**
	 * 排序值
	 */
	private String sort;

	
	/**
	 * 是否删除
	 */
	private int deleted;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
}
