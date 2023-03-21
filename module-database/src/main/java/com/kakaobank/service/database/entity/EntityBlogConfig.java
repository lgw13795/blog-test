package com.kakaobank.service.database.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_blog_config")
public class EntityBlogConfig {
	
	@Id
	private String moduleName;
	
	private String data;
	
}
