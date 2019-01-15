package com.megazone.devops.comment.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thymeleaf.util.StringUtils;

import com.megazone.devops.comment.entity.CommentGroup;
import com.megazone.devops.comment.repository.CommentGroupRepository;
import com.megazone.devops.comment.service.CommentGroupService;
import com.megazone.devops.common.entity.SearchForm;
import com.megazone.devops.common.web.service.impl.AbstractBaseCrudService;

/**
 * @author sudden(sch0718@naver.com)
 */
@Service("CommentGroupService")
public class CommentGroupServiceImpl extends
		AbstractBaseCrudService<CommentGroup, SearchForm, CommentGroupRepository, Long> implements CommentGroupService {

	public static final Logger LOG = LoggerFactory.getLogger(CommentGroupServiceImpl.class);
	
	@Override
	@Autowired
	public void setRepository(CommentGroupRepository repository) {
		super.setRepository(repository);
	}

	@Override
	public CommentGroup getByLinkKey(String linkKey) {
		return repository.findByLinkKey(linkKey);
	}

	@Override
	public CommentGroup create(CommentGroup t) {
		boolean isnotUnique = hasLinkKey(t.getLinkKey());
		LOG.debug("isnotUnique: {}", isnotUnique);
		Assert.state(!isnotUnique, "Link Key must be unique.");
		return super.create(t);
	}

	@Override
	public CommentGroup modify(CommentGroup t) {
		boolean isnotUnique = hasLinkKey(t.getLinkKey());
		LOG.debug("isnotUnique: {}", isnotUnique);
		if (isnotUnique) {
			isnotUnique = !StringUtils.equals(t.getLinkKey(), get(t.getId()).getLinkKey());
		}
		Assert.state(!isnotUnique, "Link Key must be unique.");
		return super.modify(t);
	}

	@Override
	public CommentGroup modify(CommentGroup t, String currentUserId) {
		boolean isnotUnique = hasLinkKey(t.getLinkKey());
		LOG.debug("isnotUnique: {}", isnotUnique);
		Assert.state(!isnotUnique, "Link Key must be unique.");
		return super.modify(t, currentUserId);
	}

	@Override
	public boolean hasLinkKey(String linkKey) {
		return repository.existsByLinkKeyAndIsDeletedFalse(linkKey);
	}
	
	
}
