package com.kakaobank.service.external.blog;

import com.kakaobank.service.type.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaobank.service.common.exception.KakaobankException;
import com.kakaobank.service.web.blog.model.BlogRequest;
import com.kakaobank.service.web.blog.model.BlogResponse;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Service("haBlogMediator")
@Slf4j
public class HABlogMediator implements BlogMediator {
	
	@Autowired
	private BlogMediator kakaoBlogMediator;
	
	@Autowired
	private BlogMediator naverBlogMediator;
	
	/**
	 * 블로그 조회
	 * 	1. 카카오API 블로그 조회
	 * 	2. 조회 실패 시 네이버API 블로그 조회
	 * @param request
	 * @return
	 */
	@Override
	public BlogResponse getBlogs(BlogRequest request) {
		try {
			return kakaoBlogMediator.getBlogs(request);
		} catch(FeignException e) {
			log.warn("kakao blog api network fail {}", e.getMessage());
		}
		
		try {
			return naverBlogMediator.getBlogs(request);
		} catch(FeignException e) {
			log.warn("naver blog api network fail {}", e.getMessage());
			throw new KakaobankException(StatusType.BLOG_API_ERROR);
		}
	}
}
