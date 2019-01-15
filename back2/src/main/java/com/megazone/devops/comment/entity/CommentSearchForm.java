package com.megazone.devops.comment.entity;

import com.megazone.devops.common.entity.SearchForm;

/**
 * @author sudden(sch0718@naver.com)
 */
public class CommentSearchForm extends SearchForm {

	private Long groupId;

	private Long parentId;

	private String linkKey;

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @return the linkKey
	 */
	public String getLinkKey() {
		return linkKey;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @param linkKey the linkKey to set
	 */
	public void setLinkKey(String linkKey) {
		this.linkKey = linkKey;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
