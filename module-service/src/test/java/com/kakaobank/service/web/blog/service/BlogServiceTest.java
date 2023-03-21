package com.kakaobank.service.web.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import com.kakaobank.service.database.repository.BlogSearchKeywordRepository;
import com.kakaobank.service.web.blog.task.BlogSearchKeywordTask;
import com.kakaobank.service.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.kakaobank.service.common.exception.KakaobankException;
import com.kakaobank.service.external.blog.BlogMediator;
import com.kakaobank.service.external.blog.HABlogMediator;
import com.kakaobank.service.external.blog.kakao.KakaoBlogExternalClient;
import com.kakaobank.service.external.blog.kakao.KakaoBlogMediator;
import com.kakaobank.service.external.blog.kakao.model.KakaoBlogExternalRequest;
import com.kakaobank.service.external.blog.kakao.model.KakaoBlogExternalResponse;
import com.kakaobank.service.external.blog.naver.NaverBlogExternalClient;
import com.kakaobank.service.external.blog.naver.NaverBlogMediator;
import com.kakaobank.service.external.blog.naver.model.NaverBlogExternalRequest;
import com.kakaobank.service.external.blog.naver.model.NaverBlogExternalResponse;
import com.kakaobank.service.type.SortType;
import com.kakaobank.service.type.StatusType;
import com.kakaobank.service.web.blog.model.BlogRequest;
import com.kakaobank.service.web.blog.model.BlogResponse;

import feign.FeignException;
import feign.Request;
import feign.Response;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
	
	private static final String KAKAO_RESPONSE = "{\"documents\":[{\"blogname\":\"하늘하늘의 공간사랑\",\"contents\":\"나들이는 매월 정기적인 행사가 되었습니다. 2023년 공주 사이버기자단 활동을 하고 있으니 나들이 겸 취재 겸 다니는 공주입니다. 공주 맛집 - 촌 정선 곤드레\\u003cb\\u003e밥\\u003c/b\\u003e 오늘은 공산성 앞 백미고을 먹자골목에 있는 공주맛집이야기를 전합니다. 공주시 음식문화의 거리에 있는 촌정선곤드레\\u003cb\\u003e밥\\u003c/b\\u003e 식당으로 들어갑니다. 한 번 방문...\",\"datetime\":\"2023-03-15T23:19:53.000+09:00\",\"thumbnail\":\"https://search4.kakaocdn.net/argon/130x130_85_c/5h9YHAeoRO7\",\"title\":\"공주 맛집 -- 촌 정선곤드레\\u003cb\\u003e밥\\u003c/b\\u003e\",\"url\":\"http://csmsjy.tistory.com/7093626\"},{\"blogname\":\"반갑다 세상아\",\"contents\":\"카무트 먹는 방법에 관하여 알려드리려고 합니다. 저도 생소한 카무트 어떻게 먹어야 효과적으로 먹을수 있는지 또 카무트 \\u003cb\\u003e밥\\u003c/b\\u003e짓기에 관하여 공부해 보았어요. 이번 제가 공부한것을 같이 나누어 드리려고 합니다. 그럼 포스팅 시작합니다. 목차 카무트란 벼과 밀속에 속하는 곡물입니다. 밀 중에서도 고대 이집트에서...\",\"datetime\":\"2023-03-13T17:30:06.000+09:00\",\"thumbnail\":\"https://search3.kakaocdn.net/argon/130x130_85_c/KbF88zDCY9y\",\"title\":\"카무트 먹는 방법 \\u0026amp; \\u003cb\\u003e밥\\u003c/b\\u003e짓기 방법\",\"url\":\"http://1helloworld1.everythingstory.co.kr/149\"},{\"blogname\":\"만물정보\",\"contents\":\"토요일은 \\u003cb\\u003e밥\\u003c/b\\u003e이 좋아 천안 디저트 오마카세 위치 토밥즈 안녕하세요. 3월 18일 토요일은 \\u003cb\\u003e밥\\u003c/b\\u003e이 좋아에서는 디저트 오마카세 방송되었다고 합니다. 디저트 오마카세 방문 후기를 알아보도록 하겠습니다. 디저트 오마카세 방문 후기 아래 위치 정보를 확인할 수 있습니다. 1. 디저트 오마카세 방문 후기 리뷰 토요일은 \\u003cb\\u003e밥\\u003c/b\\u003e이...\",\"datetime\":\"2023-03-18T07:48:45.000+09:00\",\"thumbnail\":\"https://search3.kakaocdn.net/argon/130x130_85_c/69iWvuiL00I\",\"title\":\"토요일은 \\u003cb\\u003e밥\\u003c/b\\u003e이 좋아 천안 디저트 오마카세 위치 토밥즈\",\"url\":\"http://all5.allcar321.com/123\"}],\"meta\":{\"is_end\":false,\"pageable_count\":800,\"total_count\":43216123}}";
	private static final String NAVER_RESPONSE = "{ \"lastBuildDate\":\"Mon, 20 Mar 2023 01:14:17 +0900\", \"total\":46985566, \"start\":1, \"display\":3, \"items\":[ { \"title\":\"[집밥<\\/b>일기] 삼시세끼 집밥<\\/b> 메뉴 추천 - 3월 밥<\\/b>상 1탄:)\", \"link\":\"https:\\/\\/blog.naver.com\\/jisie\\/223047874875\", \"description\":\"조촐하게 차린 혼밥<\\/b> 점심상- 반찬_반숙달걀장, 콩나물잡채, 취나물, 두부김치 [반숙 달걀장 레피시]... 냉장고에서 이틀간 숙성된 카레를 데우고 이번엔 밥<\\/b> 대신 난을 구웠다. 카레를 끓일 때 인도 향신료와... \", \"bloggername\":\"초록섬 프로젝트-\", \"bloggerlink\":\"blog.naver.com\\/jisie\", \"postdate\":\"20230317\" }, { \"title\":\"아른거린 제주 전복밥<\\/b>\", \"link\":\"https:\\/\\/blog.naver.com\\/ssobangz\\/223026932458\", \"description\":\"얼마전 가족들과 여행을 갔다가 든든하게 배를 채우고 싶어서 제주 전복밥<\\/b> 식당에 들렀어요. 전문점 답게 신선한 식재료만 사용하는 곳이라 공유해보려고 한답니다. 이번에 찾아간 중문고등어쌉밥<\\/b>은... \", \"bloggername\":\"쏘방이, 그 행복만으로도..\", \"bloggerlink\":\"blog.naver.com\\/ssobangz\", \"postdate\":\"20230225\" }, { \"title\":\"밥<\\/b>이보약 강아지사료샘플 추천해요\", \"link\":\"https:\\/\\/blog.naver.com\\/honey126\\/223028034431\", \"description\":\"요즘 슈슈가 밥<\\/b>을 잘 안 먹어서 걱정이 이만저만이 아니에요. 그래서 고민이 정말 많았는데 다른 친구들에게 강아지사료 관련해서 물어봤더니 밥<\\/b>이보약 강아지사료샘플 신청해서 먹여보라고 알려주더라고요.... \", \"bloggername\":\"비숑프리제 슈슈컴퍼니\", \"bloggerlink\":\"blog.naver.com\\/honey126\", \"postdate\":\"20230226\" } ] }"; 
	
	@Mock
	BlogMediator blogMediator;

	@Mock
	BlogSearchKeywordTask searchKeywordTask;
	
	@Mock
	BlogSearchKeywordRepository blogSearchKeywordRepository;
	
	@Mock
    BlogCacheService blogCacheService;
	
	@Spy
	BlogMediator haBlogMediator = new HABlogMediator();
	
	@Spy
	BlogMediator kakaoBlogMediator = new KakaoBlogMediator(null);
	
	@Spy
	BlogMediator naverBlogMediator = new NaverBlogMediator(null);
	
	@Mock
	KakaoBlogExternalClient kakaoClient;
	
	@Mock
	NaverBlogExternalClient naverClient;
	
	@InjectMocks
    BlogService blogService;
	
	
	@BeforeEach
	public void init() {
		ReflectionTestUtils.setField(haBlogMediator, "kakaoBlogMediator", kakaoBlogMediator);
		ReflectionTestUtils.setField(haBlogMediator, "naverBlogMediator", naverBlogMediator);
		ReflectionTestUtils.setField(kakaoBlogMediator, "kakaoClient", kakaoClient);
		ReflectionTestUtils.setField(naverBlogMediator, "naverClient", naverClient);
	}
	
	@Test
	@DisplayName("HABlogMediator 이용 시 카카오 블로그 결과 Return")
	public void getBlogs__GivenHABlogMediator__When__ThenReturnKakaoBlogs() {
		// given
		BlogRequest request = createRequest();
		KakaoBlogExternalResponse expectedKakaoResponse = createKakaoBlogResponse();
		BlogResponse expectedApiResponse = BlogResponse.fromKakao(request, expectedKakaoResponse);
		String expectedResponseString = JsonUtil.objectToJson(expectedApiResponse);
		when(blogCacheService.getBlogMediator()).thenReturn(haBlogMediator);
		when(kakaoClient.getBlogs(any(KakaoBlogExternalRequest.class))).thenReturn(expectedKakaoResponse);
		
		// when
		BlogResponse actualResponse = blogService.getBlogs(request);
		String actualResponseString = JsonUtil.objectToJson(actualResponse);
		
		// then
		verify(kakaoBlogMediator, times(1)).getBlogs(any(BlogRequest.class));
		verify(naverBlogMediator, never()).getBlogs(any(BlogRequest.class));
		assertEquals(expectedResponseString, actualResponseString);
	}
	

	@Test
	@DisplayName("HABlogMediator 이용 중 카카오 연결 실패 시 네이버 블로그 Return")
	public void getBlogs__GivenHABlogMediator__WhenKakaoApiFail_ThenReturnNaverBlogs() {
		// given
		BlogRequest request = createRequest();
		NaverBlogExternalResponse expectedNaverResponse = createNaverBlogResponse();
		BlogResponse expectedApiResponse = BlogResponse.fromNaver(request, expectedNaverResponse);
		String expectedResponseString = JsonUtil.objectToJson(expectedApiResponse);
		when(blogCacheService.getBlogMediator()).thenReturn(haBlogMediator);
		when(kakaoClient.getBlogs(any(KakaoBlogExternalRequest.class))).thenThrow(createFeignException(404));
		when(naverClient.getBlogs(any(NaverBlogExternalRequest.class))).thenReturn(expectedNaverResponse);
		
		// when
		BlogResponse actualResponse = blogService.getBlogs(request);
		String actualResponseString = JsonUtil.objectToJson(actualResponse);
		
		// then
		verify(kakaoBlogMediator, times(1)).getBlogs(any(BlogRequest.class));
		verify(naverBlogMediator, times(1)).getBlogs(any(BlogRequest.class));
		assertEquals(expectedResponseString, actualResponseString);
	}
	
	@Test
	@DisplayName("HABlogMediator 이용 중 카카오 연결 실패, 네이버 연결 실패 시 Exception 처리")
	public void getBlogs__GivenHABlogMediator__WhenKakaoApiAndNaverApiFail_ThenException() {
		// given
		when(blogCacheService.getBlogMediator()).thenReturn(haBlogMediator);
		when(kakaoClient.getBlogs(any(KakaoBlogExternalRequest.class))).thenThrow(createFeignException(500));
		when(naverClient.getBlogs(any(NaverBlogExternalRequest.class))).thenThrow(createFeignException(500));
		
		try {
			// when
			blogService.getBlogs(createRequest());
		} catch(Exception ex) {
			// then
			verify(kakaoBlogMediator, times(1)).getBlogs(any(BlogRequest.class));
			verify(naverBlogMediator, times(1)).getBlogs(any(BlogRequest.class));
			assert ex instanceof KakaobankException;
			KakaobankException kakaoException = (KakaobankException)ex;
			assertEquals(StatusType.BLOG_API_ERROR, kakaoException.getStatusType());
		}
	}
	
	@Test
	@DisplayName("KakaoBlogMediator 이용 시 카카오 블로그 Return")
	public void getBlogs__GivenKakaoBlogMediator__When_ThenReturnKakaoBlogs() {
		// given
		BlogRequest request = createRequest();
		KakaoBlogExternalResponse expectedKakaoResponse = createKakaoBlogResponse();
		BlogResponse expectedApiResponse = BlogResponse.fromKakao(request, expectedKakaoResponse);
		String expectedResponseString = JsonUtil.objectToJson(expectedApiResponse);
		when(blogCacheService.getBlogMediator()).thenReturn(kakaoBlogMediator);
		when(kakaoClient.getBlogs(any(KakaoBlogExternalRequest.class))).thenReturn(expectedKakaoResponse);
		
		// when
		BlogResponse actualResponse = blogService.getBlogs(request);
		String actualResponseString = JsonUtil.objectToJson(actualResponse);
		
		// then
		verify(kakaoBlogMediator, times(1)).getBlogs(any(BlogRequest.class));
		assertEquals(expectedResponseString, actualResponseString);
	}
	
	@Test
	@DisplayName("KakaoBlogMediator 이용 중 카카오 연결 실패 시 Exception 처리")
	public void getBlogs__GivenKakaoBlogMediator__WhenKakaoApiFail_ThenException() {
		// given
		when(blogCacheService.getBlogMediator()).thenReturn(kakaoBlogMediator);
		when(kakaoClient.getBlogs(any(KakaoBlogExternalRequest.class))).thenThrow(createFeignException(500));
		
		try {
			// when
			blogService.getBlogs(createRequest());
		} catch(Exception ex) {
			// then
			verify(kakaoBlogMediator, times(1)).getBlogs(any(BlogRequest.class));
			verify(naverBlogMediator, never()).getBlogs(any(BlogRequest.class));
			assert ex instanceof FeignException;
		}
	}
	
	@Test
	@DisplayName("NaverBlogMediator 이용 시 네이버 블로그 Return")
	public void getBlogs__GivenNaverBlogMediator__When_ThenReturnNaverBlogs() {
		// given
		BlogRequest request = createRequest();
		NaverBlogExternalResponse expectedNaverResponse = createNaverBlogResponse();
		BlogResponse expectedApiResponse = BlogResponse.fromNaver(request, expectedNaverResponse);
		String expectedResponseString = JsonUtil.objectToJson(expectedApiResponse);
		when(blogCacheService.getBlogMediator()).thenReturn(naverBlogMediator);
		when(naverClient.getBlogs(any(NaverBlogExternalRequest.class))).thenReturn(expectedNaverResponse);
		
		// when
		BlogResponse actualResponse = blogService.getBlogs(request);
		String actualResponseString = JsonUtil.objectToJson(actualResponse);
		
		// then
		verify(naverBlogMediator, times(1)).getBlogs(any(BlogRequest.class));
		assertEquals(expectedResponseString, actualResponseString);
	}
	
	@Test
	@DisplayName("NaverBlogMediator 이용 중 네이버 연결 실패 시 Exception 처리")
	public void getBlogs__GivenNaverBlogMediator__WhenNaverApiFail_ThenException() {
		// given
		when(blogCacheService.getBlogMediator()).thenReturn(naverBlogMediator);
		when(naverClient.getBlogs(any(NaverBlogExternalRequest.class))).thenThrow(createFeignException(500));
		
		try {
			// when
			blogService.getBlogs(createRequest());
		} catch(Exception ex) {
			// then
			verify(kakaoBlogMediator, never()).getBlogs(any(BlogRequest.class));
			verify(naverBlogMediator, times(1)).getBlogs(any(BlogRequest.class));
			assert ex instanceof FeignException;
		}
	}
	

	private BlogRequest createRequest() {
		BlogRequest request = new BlogRequest();
		request.setQuery("스프링");
		request.setSort(SortType.ACCURACY);
		request.setPage(1);
		request.setSize(10);
		request.setIsSearch(true);
		return request;
	}
	
	private KakaoBlogExternalResponse createKakaoBlogResponse() {
		return JsonUtil.jsonToObject(KAKAO_RESPONSE, KakaoBlogExternalResponse.class);
	}
	
	private NaverBlogExternalResponse createNaverBlogResponse() {
		return JsonUtil.jsonToObject(NAVER_RESPONSE, NaverBlogExternalResponse.class);
	}
	
	private FeignException createFeignException(int statusCode) {
		Request request = Request.create(Request.HttpMethod.POST, "/api/blog", Collections.emptyMap(), null, null, null);
		Response response = Response.builder()
				.status(statusCode)
				.reason("some_message")
				.request(request)
				.body(new byte[] {})
				.build();
        
		return FeignException.errorStatus("apiError", response);
	}
}
