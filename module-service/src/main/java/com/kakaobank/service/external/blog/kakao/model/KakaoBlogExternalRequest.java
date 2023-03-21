package com.kakaobank.service.external.blog.kakao.model;

import com.kakaobank.service.web.blog.model.BlogRequest;

import lombok.Getter;

@Getter
public class KakaoBlogExternalRequest {
	
	private String query;
	private String sort;
	private int page;
	private int size;
	
	public KakaoBlogExternalRequest(BlogRequest request) {
		this.query = request.getQuery();
		this.sort = request.getSort().getKakaoKey();
		this.page = request.getPage();
		this.size = request.getSize();
	}
}
