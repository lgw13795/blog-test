package com.kakaobank.service.web.blog.model;

import java.time.LocalDate;

import com.kakaobank.service.common.consts.DateConst;
import com.kakaobank.service.external.blog.kakao.model.KakaoBlogExternalResponse;
import com.kakaobank.service.external.blog.naver.model.NaverBlogExternalResponse;

import com.kakaobank.service.util.DateUtil;
import lombok.Getter;

@Getter
public class Blog {
	private String blogname;
	private String contents;
	private LocalDate datetime;
	private String thumbnail;
	private String title;
	private String url;
	
	public Blog(KakaoBlogExternalResponse.KakaoBlog blog) {
		this.blogname = blog.getBlogname();
		this.contents = blog.getContents();
		this.datetime = DateUtil.toLocalDateTime(blog.getDatetime(), DateConst.YYYYMMDDHHMMSSZZZZZ).toLocalDate();
		this.thumbnail = blog.getThumbnail();
		this.title = blog.getTitle();
		this.url = blog.getUrl();
	}
	
	public Blog(NaverBlogExternalResponse.NaverBlog blog) {
		this.blogname = blog.getBloggername();
		this.contents = blog.getDescription();
		this.datetime = DateUtil.toLocalDate(blog.getPostdate(), DateConst.YYYYMMDD);
		this.title = blog.getTitle();
		this.url = blog.getLink();
	}
}
