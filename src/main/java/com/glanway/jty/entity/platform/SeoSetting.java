package com.glanway.jty.entity.platform;


import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.dictionary.Dictionary;

/**
 * SEO设置 实体类
 * @author Songzhe
 *
 */
public class SeoSetting extends BaseEntity {


	private static final long serialVersionUID = 1L;

	/**
	 * 路径
	 */
	private String url;

	/**
	 * SEO标题
	 */
	private String title;
	
	/**
	 * SEO关键字
	 */
	private String keyWords;
	
	/**
	 * SEO描述
	 */
	private String content;
	
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
	
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
