package com.kakaobank.service.common.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfiguration {
	
	public static final String BLOG_SEARCH_TASK_EXECUTOR = "blogSearchthreadPoolTaskExecutor"; 
	
	@Bean(name = BLOG_SEARCH_TASK_EXECUTOR)
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(30);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setThreadNamePrefix("Blog-Search-Executor-");
        return taskExecutor;
    }
}
