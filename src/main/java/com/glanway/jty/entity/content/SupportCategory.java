package com.glanway.jty.entity.content;



import com.glanway.jty.entity.BaseEntity;

import java.util.List;

/**
 * 帮助中心分类 实体类
 * @author Songzhe
 *
 */
public class SupportCategory extends BaseEntity {


	private static final long serialVersionUID = 1L;

	/**
	 * 分类名称
	 */
	private String name;
	
	
	 /**
     * 父级分类
     * @return
     */
    private SupportCategory parent;
    
    private List<SupportCategory> children;
    
	/**
	 * 是否删除
	 */
	private int deleted;
	/**
	 * 排序编号
	 */
	private String sortNum;
	
	/**
	 * 帮助中心内容集合
	 */
	private List<SupportContent> supportContents;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SupportCategory getParent() {
		return parent;
	}
	public void setParent(SupportCategory parent) {
		this.parent = parent;
	}
	public List<SupportCategory> getChildren() {
		return children;
	}
	public void setChildren(List<SupportCategory> children) {
		this.children = children;
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
	public List<SupportContent> getSupportContents() {
		return supportContents;
	}
	public void setSupportContents(List<SupportContent> supportContents) {
		this.supportContents = supportContents;
	}

}
