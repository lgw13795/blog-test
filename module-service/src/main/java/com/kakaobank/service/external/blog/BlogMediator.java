package com.kakaobank.service.external.blog;

import com.kakaobank.service.web.blog.model.BlogRequest;
import com.kakaobank.service.web.blog.model.BlogResponse;


public interface BlogMediator {
	

	
	/**
	 * 블로그 조회
	 * 	1. 카카오API 블로그 조회
	 * 	2. 조회 실패 시 네이버API 블로그 조회
	 * @param request
	 * @return
	 */
	BlogResponse getBlogs(BlogRequest request);

}
