## 1. API 명세서
    1. 블로그 목록 검색 (페이지네이션)
        GET http://localhost:8080/v1/blog?query={}&page={}&size={}&sort={}&isSearch={}
        
        
        >> Request: 
        {
            query: string               // 검색어
            page: int                   // 페이지
            size: int                   // 사이즈
            sort: string                // 정렬방식 (정확도: ACCURACY, 날짜: DATE)
            isSearch: bool              // 페이징 최초 검색 여부 (키워드 저장 여부 결정)
        }
        
        >> Response:
        {
            header: {
                status: int             // 결과 정상여부 (0: 정상처리)
                message: string         // 결과 메세지
            },
            body: {
                totalPage: int          // 총 페이지
                totalCount: long        // 총 블로그 개수
                size: int               // 페이징 요청 사이즈
                blogs: [{
                    blogname: string    // 블로그 명
                    title: string       // 컨텐츠 명
                    contents: string    // 컨텐츠
                    datetime: string    // 생성일 (포맷: yyyy-MM-dd)
                    thumbnail: string   // 썸네일 (네이버 미제공)
                    url: string         // 링크
                }]
            }
        }

    ------------------------------------------------------------------------------

    2. 상위10위 키워드 조회
        GET http://localhost:8080/v1/blog/top10-keywords

        >> Request: 없음

        >> Response: 
        {
            header: {
                status: int             // 결과 정상여부 (0: 정상처리)
                message: string         // 결과 메세지
            },
            body: [{
                keyword: string         // 키워드
                count: long             // 검색 횟수
            }]
        }


## 2. 추가 기능
1. 형태소 분석기로 의미없는 문자 (형용사, 부사 등) 필터링하여 명사만 추출해 키워드 저장
2. BlogMediator를 구현한 HABlogMediator, KakaoBlogMediator, NaverBlogMediator를 DB 설정값(tb_blog_config)에 따라 선택하여 조회방식 결정 (확장성이 보장되고 리얼에서 재배포 없이 장애 실시간 대응 가능)
    1. HABlogMediator = 카카오 API 장애 시 네이버 API 조회
    2. KakaoBlogMediator = 카카오 API 조회 (실패 시 예외처리)
    3. NaverBlogMediator = 네이버 API 조회 (실패 시 예외처리)
3. 10위 까지의 검색어를 매번 조회하면 장애를 유발할 수 있기 때문에 캐시를 이용해서 저장 (다중화 시스템에서는 각자의 인스턴스마다 캐시가 설정되므로 단독서버에만 권장되고 다중서버의 경우는 레디스 같은 인메모리 저장소를 이용하는게 유리)
4. 블로그 조회 시 검색어를 DB에 저장할 때 코드 블로킹이 발생하므로 비동기 처리로써 저장하도록 함 (다만 업데이트 쿼리는 문제가 없지만 생성이 동시에 요청된다면 JPA에서 정합성 장애를 유발할 가능성이 있어서 ON DUPLICATE KEY 쿼리를 사용하거나 동기화 로직 필요)

## 3. 외부 라이브러리

| **사용목적**      | **오픈소스**                                                |
|---------------|---------------------------------------------------------|
| Restful 지원    | org.springframework.boot:spring-boot-starter-web        |
| 타임리프 지원       | org.springframework.boot:spring-boot-starter-thymeleaf  |
| 파라미터 유효성 확인   | org.springframework.boot:spring-boot-starter-validation |
| 캐시 지원         | org.springframework.boot:spring-boot-starter-cache   |
| 외부 API 호출     | org.springframework.cloud:spring-cloud-starter-openfeign   |
| 캐시 지원         | org.ehcache:ehcache:3.10.0                                       |
| 캐시 지원         | javax.cache:cache-api:1.1.1                                       |
| H2 인메모리 DB    | com.h2database:h2                                       |
| 형태소 분석기       | com.github.shin285:KOMORAN:3.3.4   |
| 롬복 객체 함수 자동완성 | org.projectlombok:lombok                                |

## 4. 멀티모듈
1. module-service
    1. 웹 서비스 제공

2. module-database
    1. ORM, Repository 관리

## 5. 실행방법
java -jar ${project}/module-service.jar

블로그 검색창: [http://localhost:8080/blog](http://localhost:8080/blog)

H2 콘솔: [http://localhost:8080/h2-console](http://localhost:8080/h2-console) 
    



