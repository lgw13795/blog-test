package com.kakaobank.service.web.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.kakaobank.service.database.entity.EntityBlogConfig;
import com.kakaobank.service.database.repository.BlogConfigRepository;
import com.kakaobank.service.database.repository.BlogSearchKeywordRepository;
import com.kakaobank.service.common.exception.KakaobankException;
import com.kakaobank.service.external.blog.BlogMediator;
import com.kakaobank.service.type.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kakaobank.service.web.blog.model.BlogSearchKeyword;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogCacheService {
	
	private static final String MODULE_NAME = "Blog";
	private static final String HA_SERVICE_CODE = "0";
	private static final String KAKAO_SERVICE_CODE = "1";
	private static final String NAVER_SERVICE_CODE = "2";
	
	@Autowired
	private BlogMediator haBlogMediator;
	
	@Autowired
	private BlogMediator kakaoBlogMediator;
	
	@Autowired
	private BlogMediator naverBlogMediator;
	
	private final BlogSearchKeywordRepository blogSearchKeywordRepository;
	
	private final BlogConfigRepository blogConfigRepository;

	@PostConstruct
	public void init() {
		if(blogConfigRepository.findById(MODULE_NAME).isEmpty()) {
			blogConfigRepository.save(new EntityBlogConfig(MODULE_NAME, HA_SERVICE_CODE));
		}
	}
	
	/**
	 * DB 설정 상 블로그 검색방법 조회 (테스트를 위해 1초 캐싱 - 실 운용시에는 캐싱시간이 달라져야함)
	 * @return
	 */
	@Cacheable("getBlogMediator")
	public BlogMediator getBlogMediator() {
		EntityBlogConfig entityConfig = blogConfigRepository.findById(MODULE_NAME).orElseThrow(() -> new KakaobankException(StatusType.RESOURCE_NOT_FOUND));
		
		// 설정 값에 따라 적절한 서비스 return
		switch(entityConfig.getData()) {
			case HA_SERVICE_CODE: return haBlogMediator;
			case KAKAO_SERVICE_CODE: return kakaoBlogMediator;
			case NAVER_SERVICE_CODE: return naverBlogMediator;
		}
		
		throw new KakaobankException(StatusType.RESOURCE_NOT_FOUND);
	}

	/**
	 * 검색어-순위 상위 10위 조회 (테스트를 위해 1초 캐싱 - 실 운용시에는 캐싱시간은 달라져야함)
	 * 
	 * @return
	 */
	@Cacheable("getTop10SearchKeywords")
	public List<BlogSearchKeyword> getTop10SearchKeywords() {
		return blogSearchKeywordRepository.findTop10ByOrderByCountDesc()
				.stream()
				.map(BlogSearchKeyword::new)
				.collect(Collectors.toList());
	}

}
