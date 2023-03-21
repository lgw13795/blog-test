package com.kakaobank.service.database.repository;

import java.util.List;
import java.util.Set;

import com.kakaobank.service.database.entity.EntityBlogSearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface BlogSearchKeywordRepository extends JpaRepository<EntityBlogSearchKeyword, String> {

	List<EntityBlogSearchKeyword> findTop10ByOrderByCountDesc();
	
	@Query("SELECT K.keyword FROM EntityBlogSearchKeyword K WHERE K.keyword IN :keywords")
	Set<String> findKeywordsByKeywordIn(@Param("keywords") Set<String> keywords);
	
	@Modifying(flushAutomatically = true)
	@Query("UPDATE EntityBlogSearchKeyword K SET K.count = K.count + 1 WHERE K.keyword IN :keywords")
	void addCountByKeywordsIn(@Param("keywords") Set<String> keywords);

}
