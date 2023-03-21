package com.kakaobank.service.web.blog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kakaobank.service.web.blog.service.BlogCacheService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {
	
	private final BlogCacheService service;
	
	@GetMapping
	public String main(Model model) {
		model.addAttribute("topKeywords", service.getTop10SearchKeywords());
		return "blog/index.html";
	}
	
	
}
