package com.kakaobank.service.external.blog.naver.model;

import com.kakaobank.service.web.blog.model.BlogRequest;

import lombok.Getter;

@Getter
public class NaverBlogExternalRequest {
	
	private String query;
	private String sort;
	private int start;
	private int display;
	
	public NaverBlogExternalRequest(BlogRequest request) {
		this.query = request.getQuery();
		this.sort = request.getSort().getNaverKey();
		this.start = (request.getPage() - 1) * request.getSize() + 1;
		this.display = request.getSize();
	}
}
