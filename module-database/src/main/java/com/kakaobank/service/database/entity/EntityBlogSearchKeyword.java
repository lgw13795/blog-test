package com.kakaobank.service.database.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_blog_search_keyword", indexes = @Index(name = "idx_count", columnList = "count"))
public class EntityBlogSearchKeyword {
	
	@Id
	private String keyword;
	
	private long count;
}
