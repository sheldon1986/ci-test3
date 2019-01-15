package com.megazone.devops.comment.service;

import com.megazone.devops.comment.entity.CommentGroup;
import com.megazone.devops.common.entity.SearchForm;
import com.megazone.devops.common.service.BaseCrudService;

/**
 * @author sudden(sch0718@naver.com)
 */
public interface CommentGroupService extends BaseCrudService<CommentGroup, SearchForm, Long> {

	/**
	 * Link Key를 이용하여 댓글 그룹을 리턴한다.
	 * @param linkKey
	 * @return
	 */
	public CommentGroup getByLinkKey(final String linkKey);
	
	/**
	 * 
	 * @param linkKey
	 * @return
	 */
	public boolean hasLinkKey(final String linkKey);
}
