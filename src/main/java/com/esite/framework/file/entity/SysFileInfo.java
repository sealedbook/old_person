package com.esite.framework.file.entity;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="sys_file_info")
public class SysFileInfo {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private String id;

	@Column(name="file_key")
	private String fileKey;

	@Lob
	@Column(name="file_content")
	private byte[] content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SysFileInfo{");
		sb.append("id='").append(id).append('\'');
		sb.append(", fileKey='").append(fileKey).append('\'');
		sb.append(", content=").append(Arrays.toString(content));
		sb.append('}');
		return sb.toString();
	}
}
