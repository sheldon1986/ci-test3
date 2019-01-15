package com.megazone.devops.comment.service;

import com.megazone.devops.comment.entity.Comment;
import com.megazone.devops.comment.entity.CommentSearchForm;
import com.megazone.devops.common.service.BaseCrudService;

/**
 * @author sudden(sch0718@naver.com)
 */
public interface CommentService extends BaseCrudService<Comment, CommentSearchForm, Long> {

}
