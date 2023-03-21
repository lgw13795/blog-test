package com.kakaobank.service.web.blog.task;

import com.kakaobank.service.database.entity.EntityBlogSearchKeyword;
import com.kakaobank.service.database.repository.BlogSearchKeywordRepository;
import com.kakaobank.service.common.config.AsyncConfiguration;
import com.kakaobank.service.util.AnalyzeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogSearchKeywordTask {
	
	private final BlogSearchKeywordRepository repository;
	
	/**
	 * 비동기처리: 검색어 카운트 + 1
	 * @param query
	 */
	@Async(AsyncConfiguration.BLOG_SEARCH_TASK_EXECUTOR)
	@Transactional
	public void addCount(String query) {
		// 형태소 분석 (명사 추출)
		Set<String> keywords = AnalyzeUtil.getNowns(query);
		
		// DB에 저장된 키워드 조회
		Set<String> registeredKeywords = repository.findKeywordsByKeywordIn(keywords);
		
		// 카운트 + 1
		if(registeredKeywords.size() > 0) {
			repository.addCountByKeywordsIn(registeredKeywords);
			keywords.removeAll(registeredKeywords);
		}
		
		// 신규 등록
		if(keywords.size() > 0) {
			List<EntityBlogSearchKeyword> newKeywords = keywords.stream().map(keyword -> new EntityBlogSearchKeyword(keyword, 1)).collect(Collectors.toList());
			repository.saveAll(newKeywords);
		}
	}
}
