package com.kakaobank.service.external.blog.kakao;

import com.kakaobank.service.external.blog.kakao.model.KakaoBlogExternalRequest;
import com.kakaobank.service.external.blog.kakao.model.KakaoBlogExternalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="KakaoExternalClient", url="${external.kakao.host}", configuration = KakaoExternalInterceptor.class)
public interface KakaoBlogExternalClient {
	
	@GetMapping("/v2/search/blog")
	public KakaoBlogExternalResponse getBlogs(@SpringQueryMap KakaoBlogExternalRequest request);
}
