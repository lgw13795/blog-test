package com.kakaobank.service.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;

public class AnalyzeUtil {
	
	/**
	 * 형태소분석기, 명사 추출
	 * @param keyword
	 * @return
	 */
	public static Set<String> getNowns(String keyword) {
		Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);

        KomoranResult analyzeResultList = komoran.analyze(keyword);

        List<String> keywords = analyzeResultList.getNouns();
        
        return new HashSet<>(keywords);
	}
}
