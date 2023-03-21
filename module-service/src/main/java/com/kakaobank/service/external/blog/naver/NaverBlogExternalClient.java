package com.kakaobank.service.external.blog.naver;

import com.kakaobank.service.external.blog.naver.model.NaverBlogExternalRequest;
import com.kakaobank.service.external.blog.naver.model.NaverBlogExternalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="NaverExternalClient", url="${external.naver.host}", configuration = NaverExternalInterceptor.class)
public interface NaverBlogExternalClient {
	
	@GetMapping("/v1/search/blog.json")
	public NaverBlogExternalResponse getBlogs(@SpringQueryMap NaverBlogExternalRequest request);
	
	
}
