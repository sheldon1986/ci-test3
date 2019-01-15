package com.megazone.devops.comment.controller;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.megazone.devops.comment.entity.CommentGroup;
import com.megazone.devops.comment.service.CommentGroupService;
import com.megazone.devops.common.entity.SearchForm;

/**
 * @author sudden(sch0718@naver.com)
 */
@RestController
@RequestMapping(CommentGroupController.REQUEST_MAPPING_PREFIX)
public class CommentGroupController {

	public static final String REQUEST_MAPPING_PREFIX = "/group";

	private static final Logger LOG = LoggerFactory.getLogger(CommentGroupController.class);
	
	@Autowired
	CommentGroupService service;

	@PostMapping
	public CommentGroup create(@RequestBody CommentGroup t) {
		LOG.debug("create: {}", t.toString());
		return service.create(t);
	}

	@GetMapping("/{id}")
	public CommentGroup get(@PathVariable final long id) {
		return service.get(id);
	}

	@GetMapping
	public Page<CommentGroup> getPages(final SearchForm searchForm) {
		return service.getList(searchForm);
	}

	@PutMapping("/{id}")
	public CommentGroup modify(@PathVariable Long id, @RequestBody CommentGroup t) {
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
