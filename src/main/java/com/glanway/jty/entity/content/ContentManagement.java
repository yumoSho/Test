package com.glanway.jty.entity.content;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.dictionary.Dictionary;

/**
 * <p>名称: 内容管理实体</p>
 * <p>说明: 投诉与建议实体</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：SongZhe
 * @date：2016/8/15
 * @version: 1.0
 */
public class ContentManagement extends BaseEntity {

	/**
	 * 数据字典 得到ID和name
	 */
	private Dictionary dictionary;

	private String image;
	/**
	 * 内容
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

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
