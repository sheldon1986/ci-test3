package com.megazone.devops.comment.controller;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.megazone.devops.comment.entity.Comment;
import com.megazone.devops.comment.entity.CommentGroup;
import com.megazone.devops.comment.entity.CommentSearchForm;
import com.megazone.devops.comment.service.CommentGroupService;
import com.megazone.devops.comment.service.CommentService;

/**
 * @author sudden(sch0718@naver.com)
 */
@RestController
@RequestMapping(CommentController.REQUEST_MAPPING_PREFIX)
public class CommentController {

	public static final String REQUEST_MAPPING_PREFIX = "/comment";

	private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	CommentService service;

	@Autowired
	CommentGroupService groupService;

	@PostMapping
	public Comment create(@RequestBody Comment t) {
		LOG.debug("create: {}", t.toString());
		return service.create(t);
	}

	@GetMapping("/{id}")
	public Comment get(@PathVariable final long id) {
		LOG.debug("get", id);
		return service.get(id);
	}

	@GetMapping
	public Page<Comment> getPages(final CommentSearchForm searchForm) {
		LOG.debug("searchForm: {}", searchForm.toString());
		Assert.isTrue(searchForm.getGroupId() != null || searchForm.getLinkKey() != null, "Either a group ID or a parent ID must exist.");
		CommentGroup group = null;
		if (searchForm.getGroupId() != null) group = groupService.get(searchForm.getGroupId());
		if (group == null && searchForm.getLinkKey() != null) {
			group = groupService.getByLinkKey(searchForm.getLinkKey());
			searchForm.setGroupId(group.getId());
		}
		
		searchForm.setPageable(generatePageable(group, searchForm));
		return service.getList(searchForm);
	}

	private Pageable generatePageable(final CommentGroup group, final CommentSearchForm searchForm) {
		Pageable pageable = searchForm.getPageable();
		return PageRequest.of(pageable.getPageNumber(), group.getDefaultPageSize(), group.getDefaultSort());
	}

	@PutMapping("/{id}")
	public Comment modify(@PathVariable Long id, @RequestBody Comment t) {
		t.setId(id);
		LOG.debug("modify: {}", t.toString());
		return service.modify(t);
	}

	@DeleteMapping("/{ids}")
	public String remove(@PathVariable final String ids, final boolean isPhysical) {
		LOG.debug("remove: {}", ids.toString());
		new StringTokenizer(ids, ",").getTokenList().stream().map(item -> NumberUtils.toLong(item)).forEach(action -> {
			if (isPhysical) {
				service.removeByPhysical(action);
			} else {
				service.remove(action);
			}
		});
		return ids;
	}
}
