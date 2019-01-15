package com.megazone.devops.comment.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.megazone.devops.common.entity.BaseModel;

/**
 * @author sudden(sch0718@naver.com)
 */
@Entity
@Table(name = "SVC_CMT_BASIC")
public class Comment extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4290968082397907670L;

	@Column(name = "GROUP_UID", updatable = false)
	Long groupId;

	/**
	 * 부모 글 ID
	 */
	@Column(name = "PARENT_UID", updatable = false)
	Long parentId;

	/**
	 * Reply Level
	 */
	@Column(name = "REPLY_LEVEL", updatable = false)
	int level;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARENT_UID")
	List<Comment> children;

	/**
	 * 내용 요약
	 */
	@Column(name = "SUMMARY_CONTENT")
	String summaryContent;

	/**
	 * 내용
	 */
	@Column(name = "CONTENT")
	@Lob
	String content;

	@Column(name = "LIKE_CNT")
	Long likeCount = 0L;

	@Column(name = "RECOMMEND_CNT")
	Long recommendCount = 0L;

	@Column(name = "OPPOSE_CNT")
	Long opposeCount = 0L;

	@Column(name = "AVG_POINT")
	Double averagePoint = 0D;

	@Column(name = "IS_BLIND")
	boolean isBlid;

	/**
	 * @return the averagePoint
	 */
	public Double getAveragePoint() {
		return averagePoint;
	}

	/**
	 * @return the children
	 */
	public List<Comment> getChildren() {
		return children;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return the likeCount
	 */
	public Long getLikeCount() {
		return likeCount;
	}

	/**
	 * @return the opposeCount
	 */
	public Long getOpposeCount() {
		return opposeCount;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @return the recommendCount
	 */
	public Long getRecommendCount() {
		return recommendCount;
	}

	/**
	 * @return the summaryContent
	 */
	public String getSummaryContent() {
		return summaryContent;
	}

	/**
	 * @return the isBlid
	 */
	public boolean isBlid() {
		return isBlid;
	}

	/**
	 * @param averagePoint the averagePoint to set
	 */
	public void setAveragePoint(Double averagePoint) {
		this.averagePoint = averagePoint;
	}

	/**
	 * @param isBlid the isBlid to set
	 */
	public void setBlid(boolean isBlid) {
		this.isBlid = isBlid;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Comment> children) {
		this.children = children;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	/**
	 * @param opposeCount the opposeCount to set
	 */
	public void setOpposeCount(Long opposeCount) {
		this.opposeCount = opposeCount;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @param recommendCount the recommendCount to set
	 */
	public void setRecommendCount(Long recommendCount) {
		this.recommendCount = recommendCount;
	}

	/**
	 * @param summaryContent the summaryContent to set
	 */
	public void setSummaryContent(String summaryContent) {
		this.summaryContent = summaryContent;
	}

}
