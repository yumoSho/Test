package com.glanway.jty.entity.content;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.utils.HtmlUtil;

/**
 * 新闻 实体类
 * @author Songzhe
 *
 */
public class News extends BaseEntity{


	private static final long serialVersionUID = 1L;

	/**
	 * 公告标题
	 */
	private String title;
	
	/**
	 * 资讯图片路径
	 */
	private String image;
	
	/**
	 * 排序
	 */
	private int sortNum;
	/**
	 * 公告内容
	 */
	private String content;
	
	/**
	 * 是否删除
	 */
	private int deleted;

	/**
	 * 点击量
	 */
	private Integer hitNum;
	
	/**
	 * 是否显示
	 */
	private Boolean isShow;
	/**
	 * 所属银行
	 */
	private Boolean isHot;
	
	/**
	 * 资讯内容过滤后的文本
	 * 仅用于前台数据显示
	 */
	private String shotContent;

	/**
	 * 标题Id
	 */
	private Long newsTypeId;

	/**
	 * 标题名称
	 */
	private String newsTypeName;

	private String nType;
	
	public void setShotContent(String shotContent) {
		this.shotContent = shotContent;
	}

	public String getShotContent() {
		return HtmlUtil.removeArticleHtml(this.content,100);
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean IsShow) {
		isShow = IsShow;
	}

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
	
	public Integer getHitNum() {
		return hitNum;
	}
	public void setHitNum(Integer hitNum) {
		this.hitNum = hitNum;
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public Long getNewsTypeId() {
		return newsTypeId;
	}

	public void setNewsTypeId(Long newsTypeId) {
		this.newsTypeId = newsTypeId;
	}

	public String getNewsTypeName() {
		return newsTypeName;
	}

	public void setNewsTypeName(String newsTypeName) {
		this.newsTypeName = newsTypeName;
	}

	public String getnType() {
		return nType;
	}

	public void setnType(String nType) {
		this.nType = nType;
	}
}
