package com.megazone.devops.comment.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author sudden(sch0718@naver.com)
 */
@RestControllerAdvice
public class RestExceptionController {

	private static final Logger LOG = LoggerFactory.getLogger(RestExceptionController.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Exception> handleUnAuthorized(HttpServletRequest request, Exception e) {
		if (LOG.isWarnEnabled()) {
			LOG.warn("Request: " + request.getRequestURL() + ", Message: " + e.getMessage(), e);
		}
		return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
