package com.kakaobank.service.common.model;

import com.kakaobank.service.type.StatusType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseHeader {
	
	private int status;
	private String message;
	
	
	public BaseHeader(StatusType statusType) {
		this.status = statusType.getStatus();
		this.message = statusType.getMessage();
	}
}
