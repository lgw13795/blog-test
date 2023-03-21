package com.kakaobank.service.web.blog;

import com.kakaobank.service.common.model.BaseResponse;
import com.kakaobank.service.web.blog.model.BlogSearchKeyword;
import com.kakaobank.service.web.blog.service.BlogCacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaobank.service.web.blog.model.BlogRequest;
import com.kakaobank.service.web.blog.model.BlogResponse;
import com.kakaobank.service.web.blog.service.BlogService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/v1/blog")
@RequiredArgsConstructor
public class BlogApiController {
	
	private final BlogService service;

	private final BlogCacheService cacheService;
	
	/**
	 * 블로그 검색 (페이지네이션)
	 * @param request
	 * @return
	 */
	@GetMapping
	public BaseResponse<BlogResponse> getBlogs(BlogRequest request) {
		return new BaseResponse<>(service.getBlogs(request));
	}

	@GetMapping("/top10-keywords")
	public BaseResponse<List<BlogSearchKeyword>> getTop10Keywords() {
		return new BaseResponse<>(cacheService.getTop10SearchKeywords());
	}
}
