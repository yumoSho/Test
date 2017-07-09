package com.glanway.jty.utils.mail;

import java.io.File;

/**
 * 附件
 * @author tianxuan
 * @Time 2016/4/22
 */
public class AttachBean {
	private String cid;
	private File file;
	private String fileName;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public AttachBean() {

	}

	public AttachBean(File file, String fileName) {
		super();
		this.file = file;
		this.fileName = fileName;
	}
}
