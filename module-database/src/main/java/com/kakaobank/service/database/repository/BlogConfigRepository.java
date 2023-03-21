package com.kakaobank.service.database.repository;

import com.kakaobank.service.database.entity.EntityBlogConfig;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogConfigRepository extends JpaRepository<EntityBlogConfig, String> {

}
