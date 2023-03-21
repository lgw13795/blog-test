package com.kakaobank.service.common.exception;

import com.kakaobank.service.type.StatusType;

import lombok.Getter;
@Getter
public class KakaobankException extends RuntimeException {
	
	private StatusType statusType;
	private int code;
	private String exceptionMessage;
	
	
	public KakaobankException(StatusType statusType) {
		this.statusType = statusType;
		this.code = statusType.getStatus();
		this.exceptionMessage = statusType.getMessage();
	}
	
}
