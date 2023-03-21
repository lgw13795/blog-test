package com.kakaobank.service.external.blog.naver;

import com.kakaobank.service.external.blog.BlogMediator;
import com.kakaobank.service.external.blog.naver.model.NaverBlogExternalRequest;
import com.kakaobank.service.external.blog.naver.model.NaverBlogExternalResponse;
import org.springframework.stereotype.Service;

import com.kakaobank.service.web.blog.model.BlogRequest;
import com.kakaobank.service.web.blog.model.BlogResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NaverBlogMediator implements BlogMediator {
	
	
	private final NaverBlogExternalClient naverClient;
	
	/**
	 * 네이버 블로그 조회
	 * @param request
	 * @return
	 */
	@Override
	public BlogResponse getBlogs(BlogRequest request) {
		NaverBlogExternalResponse response = naverClient.getBlogs(new NaverBlogExternalRequest(request));
		return BlogResponse.fromNaver(request, response);
	}

}
