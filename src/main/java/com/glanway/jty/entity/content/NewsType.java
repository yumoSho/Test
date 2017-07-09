package com.glanway.jty.entity.content;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.utils.HtmlUtil;

/**
 * 新闻分类实体
 * @author Songzhe
 *
 */
public class NewsType {

	private Long id;

	private String name;//分类名称

	private int sortNum;//排序名称

	private Boolean deleted;//是否删除

	private Boolean activated;//是否激活

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}


	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
}
