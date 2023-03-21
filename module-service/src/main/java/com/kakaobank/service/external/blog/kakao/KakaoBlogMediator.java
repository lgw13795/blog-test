package com.kakaobank.service.external.blog.kakao;

import com.kakaobank.service.external.blog.BlogMediator;
import com.kakaobank.service.external.blog.kakao.model.KakaoBlogExternalRequest;
import com.kakaobank.service.external.blog.kakao.model.KakaoBlogExternalResponse;
import org.springframework.stereotype.Service;

import com.kakaobank.service.web.blog.model.BlogRequest;
import com.kakaobank.service.web.blog.model.BlogResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoBlogMediator implements BlogMediator {
	
	
	private final KakaoBlogExternalClient kakaoClient;
	
	/**
	 * 카카오API 블로그 조회
	 * @param request
	 * @return
	 */
	@Override
	public BlogResponse getBlogs(BlogRequest request) {
		KakaoBlogExternalResponse response = kakaoClient.getBlogs(new KakaoBlogExternalRequest(request));
		return BlogResponse.fromKakao(request, response);
	}


}
