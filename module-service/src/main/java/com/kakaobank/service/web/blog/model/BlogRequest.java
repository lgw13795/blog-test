package com.kakaobank.service.web.blog.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kakaobank.service.type.SortType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BlogRequest {
	
	@NotBlank
	private String query;
	
	@NotNull
	private SortType sort = SortType.ACCURACY;
	
	private int page = 1;
	
	private int size = 30;

	private Boolean isSearch = false;
}
