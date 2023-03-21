package com.kakaobank.service.external.blog.kakao;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class KakaoExternalInterceptor {
	
	
	private String authKey;
	
	public KakaoExternalInterceptor(@Value("${external.kakao.auth-key}") String authKey) {
		this.authKey = "KakaoAK " + authKey;
	}
	
	
	@Bean
    public RequestInterceptor kakaoRequestInterceptor() {
        return requestTemplate -> requestTemplate.header("Authorization", Collections.singletonList(this.authKey));
    }

}
