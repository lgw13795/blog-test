package com.kakaobank.service.external.blog.naver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class NaverExternalInterceptor {
	
	
	@Value("${external.naver.client-id}")
	private String clientId;
	
	@Value("${external.naver.client-secret}")
	private String clientSecret;
	
	
	@Bean
    public RequestInterceptor naverRequestInterceptor() {
        return requestTemplate -> {
        	requestTemplate.header("X-Naver-Client-Id", clientId);
        	requestTemplate.header("X-Naver-Client-Secret", clientSecret);
        };
    }

}
