package com.kakaobank.service.web.blog.service;

import com.kakaobank.service.web.blog.task.BlogSearchKeywordTask;
import org.springframework.stereotype.Service;

import com.kakaobank.service.web.blog.model.BlogRequest;
import com.kakaobank.service.web.blog.model.BlogResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
	
	private final BlogCacheService blogCacheService;

	private final BlogSearchKeywordTask searchKeywordTask;

	/**
	 * 블로그 조회
	 * 
	 * @param request
	 * @return
	 */
	public BlogResponse getBlogs(BlogRequest request) {
		BlogResponse response = blogCacheService.getBlogMediator().getBlogs(request);
		if(request.getIsSearch()) searchKeywordTask.addCount(request.getQuery());
		return response;
	}

}
