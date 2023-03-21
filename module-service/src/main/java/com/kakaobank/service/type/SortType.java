package com.kakaobank.service.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortType {
	ACCURACY("accuracy", "sim"),
	DATE("recency", "date")
	;
	
	private String kakaoKey;
	private String naverKey;
	
}
