package com.kakaobank.service.web.blog.model;

import com.kakaobank.service.database.entity.EntityBlogSearchKeyword;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class BlogSearchKeyword {
	
	private String keyword;
	private long count;
	public BlogSearchKeyword(EntityBlogSearchKeyword response) {
		this.keyword = response.getKeyword();
		this.count = response.getCount();
		
	}
}
