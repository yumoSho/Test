package com.glanway.jty.entity.platform;


import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.dictionary.Dictionary;

import java.util.Date;

/**
 * 轮播图实体类
 * @author Songzhe
 *
 */
public class FlowBanner extends BaseEntity {


	private static final long serialVersionUID = 1L;

	/**
	 * 轮播图标题
	 */
	private String title;
	
	/**
	 * 轮播图文件路径
	 */
	private String image;
	
	/**
	 * 图片广告链接地址
	 */
	private String url;
	
	/**
	 * 是否启用
	 */
	private Integer isShow;


	/**
	 * 是否删除
	 */
	private int deleted;

	/**
	 * 页面名称
	 */
	private Dictionary pageDictionary;
	
	/**
	 * 所属平台
	 */
	private Dictionary platformDictionary;
	
	private int sortNum;

	/**
	 * 有效期开始时间
	 */
	private Date beginDate;

	/**
	 * 有效期结束时间
	 */
	private Date endDate;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Dictionary getPageDictionary() {
		return pageDictionary;
	}
	public void setPageDictionary(Dictionary pageDictionary) {
		this.pageDictionary = pageDictionary;
	}
	public Dictionary getPlatformDictionary() {
		return platformDictionary;
	}
	public void setPlatformDictionary(Dictionary platformDictionary) {
		this.platformDictionary = platformDictionary;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
