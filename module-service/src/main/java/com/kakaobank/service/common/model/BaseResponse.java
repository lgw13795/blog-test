package com.kakaobank.service.common.model;

import com.kakaobank.service.type.StatusType;

import lombok.Getter;

@Getter
public class BaseResponse<T> {
	
	private BaseHeader header;
	private T body;
	
	private static BaseHeader SUCCESS_HEADEER = new BaseHeader(StatusType.SUCCESS);
	public static BaseResponse<Void> SUCCESS = new BaseResponse<>();
	
	public BaseResponse() {
		this.header = SUCCESS_HEADEER;
	}
	
	public BaseResponse(T body) {
		this.header = SUCCESS_HEADEER;
		this.body = body;
	}
	
	public BaseResponse(StatusType statusType) {
		this.header = new BaseHeader(statusType);
	}

}
