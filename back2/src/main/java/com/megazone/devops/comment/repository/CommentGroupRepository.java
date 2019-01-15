package com.megazone.devops.comment.repository;

import com.megazone.devops.comment.entity.CommentGroup;
import com.megazone.devops.common.repository.BaseCrudRepository;

/**
 * @author sudden(sch0718@naver.com)
 */
public interface CommentGroupRepository extends BaseCrudRepository<CommentGroup, Long> {

	CommentGroup findByLinkKey(final String linkKey);
	
	boolean existsByLinkKeyAndIsDeletedFalse(final String linkKey);
}
