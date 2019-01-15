package com.megazone.devops.comment.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.megazone.devops.comment.entity.Comment;
import com.megazone.devops.comment.entity.CommentGroup;
import com.megazone.devops.comment.entity.CommentSearchForm;
import com.megazone.devops.comment.repository.CommentRepository;
import com.megazone.devops.comment.service.CommentGroupService;
import com.megazone.devops.comment.service.CommentService;
import com.megazone.devops.common.util.LogMessageFormatter;
import com.megazone.devops.common.web.service.impl.AbstractBaseCrudService;

/**
 * @author sudden(sch0718@naver.com)
 */
@Service("CommentService")
@Transactional(readOnly = true)
public class CommentServiceImpl extends AbstractBaseCrudService<Comment, CommentSearchForm, CommentRepository, Long>
		implements CommentService {

	private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Autowired
	CommentGroupService groupService;

	@Override
	@Transactional
	public Comment create(Comment t) {
		if (t.getParentId() != null) {
			CommentGroup group = groupService.get(t.getGroupId());
			Comment parent = get(t.getParentId());
			int level = parent.getLevel() + 1;
			Assert.isTrue(level <= group.getLevelLimit(),
					LogMessageFormatter.getLogMessage("You can only reply to {} level.", group.getLevelLimit()));
			t.setLevel(level);
		}
		t.setSummaryContent(StringUtils.substring(t.getContent(), 0, 1000));
		return super.create(t);
	}

	/**
	 * 검색 조건을 생성한다.
	 * @param searchForm
	 * @return
	 */
	public Specification<Comment> generateSpecification(CommentSearchForm searchForm) {
		String key = searchForm.getSearchKey();
		String value = searchForm.getSearchValue();
		boolean isShowAll = searchForm.isShowAll();
		Long groupId = searchForm.getGroupId();
		int level = 0;
		Specification<Comment> spec = Specification.where((root, query, cb) -> {
			final Collection<Predicate> predicates = new ArrayList<>();
			if (!isShowAll) {
				predicates.add(cb.equal(root.get("isDeleted"), false));
			}
			predicates.add(cb.equal(root.get("groupId"), groupId));
			predicates.add(cb.equal(root.get("level"), level));
			if (!StringUtils.isEmpty(value)) {
				predicates.add(cb.like(root.get(key), "%" + value + "%"));
			}
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		});
		return spec;
	}

	@Override
	@Transactional
	public Comment modify(Comment t) {
		t.setSummaryContent(StringUtils.substring(t.getContent(), 0, 1000));
		return super.modify(t);
	}

	@Override
	@Transactional
	public Comment modify(Comment t, String currentUserId) {
		t.setSummaryContent(StringUtils.substring(t.getContent(), 0, 1000));
		return super.modify(t, currentUserId);
	}

	@Override
	@Autowired
	public void setRepository(CommentRepository repository) {
		super.setRepository(repository);
	}

}
