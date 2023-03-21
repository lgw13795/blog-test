package com.kakaobank.service.web.blog.model;

import java.util.List;
import java.util.stream.Collectors;

import com.kakaobank.service.external.blog.kakao.model.KakaoBlogExternalResponse;
import com.kakaobank.service.external.blog.naver.model.NaverBlogExternalResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlogResponse {
	
	private long totalPage;
	
	private long totalCount;
	
	private int size;
	
	private List<Blog> blogs;
	
	
	public static BlogResponse fromKakao(BlogRequest request, KakaoBlogExternalResponse response) {
		BlogResponse blogResponse = new BlogResponse();
		blogResponse.totalPage = response.getMeta().getPageCount();
		blogResponse.totalCount = response.getMeta().getTotalCount();
		blogResponse.size = request.getSize();
		blogResponse.blogs = response.getDocuments().stream()
				.map(Blog::new)
				.collect(Collectors.toList());
		return blogResponse;
	}
	
	public static BlogResponse fromNaver(BlogRequest request, NaverBlogExternalResponse response) {
		BlogResponse blogResponse = new BlogResponse();
		blogResponse.totalPage = Double.valueOf(Math.ceil(response.getTotal() /  request.getSize())).longValue();
		blogResponse.totalCount = response.getTotal();
		blogResponse.size = request.getSize();
		blogResponse.blogs = response.getItems().stream()
				.map(Blog::new)
				.collect(Collectors.toList());
		
		return blogResponse;
	}
}