package com.kakaobank.service.external.blog.naver.model;

import java.util.List;

import lombok.Getter;

@Getter
public class NaverBlogExternalResponse {
	
	private String lastBuildDate;
	private long total;
	private int start;
	private int display;
	private List<NaverBlog> items;
	
	
	@Getter
	public static class NaverBlog {
		private String title;
		private String link;
		private String description;
		private String bloggername;
		private String bloggerlink;
		private String postdate;
	}
}
