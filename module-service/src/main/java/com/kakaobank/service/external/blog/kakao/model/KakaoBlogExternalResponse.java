package com.kakaobank.service.external.blog.kakao.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class KakaoBlogExternalResponse {
	
	private Meta meta;
	
	private List<KakaoBlog> documents;
	
	@Getter
	public static class Meta {
		@JsonProperty("is_end")
		private boolean isEnd;
		
		@JsonProperty("pageable_count")
		private long pageCount;
		
		@JsonProperty("total_count")
		private long totalCount;
	}
	
	@Getter
	public static class KakaoBlog {
		private String blogname;
		private String contents;
		private String datetime;
		private String thumbnail;
		private String title;
		private String url;
	}
}
