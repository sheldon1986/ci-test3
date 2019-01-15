package com.megazone.devops.comment.entity;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.megazone.devops.common.entity.BaseModel;

/**
 * @author sudden(sch0718@naver.com)
 */
@Entity
@Table(name = "SVC_CMT_GROUP")
public class CommentGroup extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8297531885599970362L;

	public static final char SORT_COLUMN_SEPARATOR = ';';

	/**
	 * 그룹 이름
	 */
	@Column(name = "NAME")
	@NotBlank
	private String name;

	/**
	 * 설명
	 */
	@Column(name = "DESCRIPTION")
	@Lob
	private String description;

	/**
	 * <pre>
	 * UID 외에 댓글 그룹을 연결 할 키(Key)
	 * ex) 게시글의 URL
	 * </pre>
	 */
	@Column(name = "LINK_KEY")
	@NotBlank
	private String linkKey;

	/**
	 * 기본 페이지당 게시물 갯수
	 */
	@Column(name = "PAGE_SIZE")
	private int defaultPageSize;

	/**
	 * 정렬 컬럼 및 방식, 세미콜론으로 구분(;)
	 */
	@Column(name = "SORT_COLUMNS")
	private String defaultSortColumns;

	/**
	 * Reply Level 제한(0: 무제한)
	 */
	@Column(name = "LEVEL_LIMIT")
	private int levelLimit;

	@Transient
	private Sort defaultSort;

	/**
	 * 좋아요 기능 사용 여부
	 */
	@Column(name = "USE_LIKE")
	private boolean useLike;

	/**
	 * 추천/반대 기능 사용 여부
	 */
	@Column(name = "USE_RECOMMEND")
	private boolean useRecommend;

	/**
	 * 점수(별점) 기능 사용 여부
	 */
	@Column(name = "USE_POINT")
	private boolean usePoint;

	/**
	 * 내용에 HTML 사용 여부
	 */
	@Column(name = "USE_HTML")
	private boolean useHtml;

	/**
	 * 내용에 Markdown 사용 여부
	 */
	@Column(name = "USE_MARKDOWN")
	private boolean useMarkdown;

	/**
	 * 삭제 제한 사용 여부(reply가 있을 경우 삭제 불가)
	 */
	@Column(name = "USE_DEL_RESTRICT")
	private boolean useDeleteRestrict;

	/**
	 * 글자 수 제한 타입(N: 사용 안함, B: Bytes, C: Characters)
	 */
	@Column(name = "CHAR_LIMIT_TYPE")
	private String characterLimitType;

	/**
	 * 제한 글자 수
	 */
	@Column(name = "CHAR_LIMIT_CNT")
	private int characterLimitCount;

	/**
	 * @return the characterLimitCount
	 */
	public int getCharacterLimitCount() {
		return characterLimitCount;
	}

	/**
	 * @return the characterLimitType
	 */
	public String getCharacterLimitType() {
		return characterLimitType;
	}

	/**
	 * @return the defaultPageSize
	 */
	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	/**
	 * @return the defaultSort
	 */
	public Sort getDefaultSort() {
		if (StringUtils.isBlank(getDefaultSortColumns()))
			return null;

		return Sort.by(Stream.of(StringUtils.split(getDefaultSortColumns(), SORT_COLUMN_SEPARATOR)).map(item -> {
			return new Order(Direction.valueOf(StringUtils.substringAfter(item, " ")),
					StringUtils.substringBefore(item, " "));
		}).collect(Collectors.toList()));
	}

	/**
	 * @return the defaultSortColumns
	 */
	public String getDefaultSortColumns() {
		return defaultSortColumns;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the levelLimit
	 */
	public int getLevelLimit() {
		return levelLimit;
	}

	/**
	 * @return the linkKey
	 */
	public String getLinkKey() {
		return linkKey;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the useDeleteRestrict
	 */
	public boolean isUseDeleteRestrict() {
		return useDeleteRestrict;
	}

	/**
	 * @return the useHtml
	 */
	public boolean isUseHtml() {
		return useHtml;
	}

	/**
	 * @return the useLike
	 */
	public boolean isUseLike() {
		return useLike;
	}

	/**
	 * @return the useMarkdown
	 */
	public boolean isUseMarkdown() {
		return useMarkdown;
	}

	/**
	 * @return the usePoint
	 */
	public boolean isUsePoint() {
		return usePoint;
	}

	/**
	 * @return the useRecommend
	 */
	public boolean isUseRecommend() {
		return useRecommend;
	}

	/**
	 * @param characterLimitCount the characterLimitCount to set
	 */
	public void setCharacterLimitCount(int characterLimitCount) {
		this.characterLimitCount = characterLimitCount;
	}

	/**
	 * @param characterLimitType the characterLimitType to set
	 */
	public void setCharacterLimitType(String characterLimitType) {
		this.characterLimitType = characterLimitType;
	}

	/**
	 * @param defaultPageSize the defaultPageSize to set
	 */
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	/**
	 * @param defaultSort the defaultSort to set
	 */
	public void setDefaultSort(Sort defaultSort) {
		this.defaultSort = defaultSort;
		if (defaultSort == null)
			return;

		setDefaultSortColumns(defaultSort.stream().map(item -> {
			return item.getProperty() + " " + item.getDirection().name();
		}).collect(Collectors.joining(SORT_COLUMN_SEPARATOR + "")));
	}

	/**
	 * @param defaultSortColumns the defaultSortColumns to set
	 */
	public void setDefaultSortColumns(String defaultSortColumns) {
		this.defaultSortColumns = defaultSortColumns;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param levelLimit the levelLimit to set
	 */
	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
	}

	/**
	 * @param linkKey the linkKey to set
	 */
	public void setLinkKey(String linkKey) {
		this.linkKey = linkKey;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param useDeleteRestrict the useDeleteRestrict to set
	 */
	public void setUseDeleteRestrict(boolean useDeleteRestrict) {
		this.useDeleteRestrict = useDeleteRestrict;
	}

	/**
	 * @param useHtml the useHtml to set
	 */
	public void setUseHtml(boolean useHtml) {
		this.useHtml = useHtml;
	}

	/**
	 * @param useLike the useLike to set
	 */
	public void setUseLike(boolean useLike) {
		this.useLike = useLike;
	}

	/**
	 * @param useMarkdown the useMarkdown to set
	 */
	public void setUseMarkdown(boolean useMarkdown) {
		this.useMarkdown = useMarkdown;
	}

	/**
	 * @param usePoint the usePoint to set
	 */
	public void setUsePoint(boolean usePoint) {
		this.usePoint = usePoint;
	}

	/**
	 * @param useRecommend the useRecommend to set
	 */
	public void setUseRecommend(boolean useRecommend) {
		this.useRecommend = useRecommend;
	}

}
