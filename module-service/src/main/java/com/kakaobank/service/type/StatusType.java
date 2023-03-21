package com.kakaobank.service.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusType {
	SUCCESS(0, "SUCCESS"),
	RESOURCE_NOT_FOUND(1, "RESOURCE NOT FOUND"),
	// 1000 ~ 2000 external api code
	KAKAO_API_ERROR(1101, "KAKAO API ERROR"),
	NAVER_API_ERROR(1201, "KAKAO API ERROR"),
	// 2000 ~  service error 
	BLOG_API_ERROR(2101, "BLOG API ERROR"),
	JSON_PARSE_ERROR(2102, "JSON PARSE ERROR"),
	ERROR(-1, "ERROR"), 
	;
	
	private int status;
	private String message;
}
