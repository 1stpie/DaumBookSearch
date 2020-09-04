# DaumBookSearch
Android application to search for books with Daum API

[APK 링크 Github]
[APK 링크 Google Drive]


## 목차

- [요구사항 정리](#요구사항-정리)
- [개발 환경](#개발-환경)
- [프로젝트 구성](#프로젝트-구성)
    - [1. Directory](#1-Directory)
    - [2. Architecture Design Pattern and Paging](#2-Architecture-Design-Pattern-and-Paging)
    - [3. Dependency Injection](#3-Dependency-Injection)
    - [4. CI](#4-CI)
- [화면 구성](#화면-구성)
    - [1. 스플래시 화면](#1-스플래시-화면)
    - [2. 검색 화면](#2-검색-화면)
    - [3. 상세 화면](#3-상세-화면)
- [작업 계획](#작업-계획)
- [License](#license)
    
    
## 요구사항 정리

  * Kotlin을 이용하여 개발
  * 카카오 도서 검색 API를 이용하여 도서 검색
    * https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-book
    * size param : 50
  * 스크롤 시 연속 Paging 기능 제공
  * 검색 리스트 결과(메인화면) 및 상세화면으로 구성
  * 메인 리스트와 상세화면은 Fragment로 구성
  * 메인 리스트에서 특정 Item 클릭 시 상세화면으로 이동
  

## 개발 환경

  * 기본 환경
    * OS : Mac OS X
    * IDE : Android Studio 4.0.1
    * JVM target : Java-1.8
    * Kotlin 1.3.72
  
  * AndroidX 라이브러리
    * Core 1.3.1
    * Coroutine 1.3.8
    * MultiDex 2.0.1
    * Lifecycle 1.3.8
    * Room 2.3.0-alpha01
    * Paging 3.0.0-alpha05

  * 기타 라이브러리
    * Dagger 2.27       // Dependency Injection Tool
    * Retrofit 2.8.1    // REST API Tool
    * OkHttp 4.7.0      // HTTP Client Tool
    * Glide 4.11.0      // Image Loading Tool
    * Timber 4.7.1      // Logging Tool
    * Logger 2.2.0      // Logging Tool
    
  * 이미지 출처
    * 앱 아이콘 : 자체 제작
    * 기본 아이콘 : MATERIAL DESIGN
    * 카카오 캐릭터 : 카카오 프렌즈 월페이퍼
    
    
## 프로젝트 구성

### 1. Directory

```
/com/meuus90
│
├── base             ---------> # base package
│   ├── arch/util    ---------> # architecture util source
│   ├── common/util  ---------> # common util source
│   ├── constant     ---------> # constant source
│   └── view         ---------> # custom view source
│
└── daumbooksearch   ---------> # project package
    ├── di           ---------> # dependency injection
    ├── model
    │   ├── data/source
    │   │   ├── api     ------> # remote server api
    │   │   ├── local   ------> # local room dao
    │   │   └── repository      # repository source
    │   ├── paging   ---------> # paging source
    │   └── schema   ---------> # schema collection
    ├── viewmodel    ---------> # viewmodel source
    ├── view         ---------> # view source
    └── DaumBookSearch.kt  ---> # application
```

### 2. Architecture Design Pattern and Paging

  * 아키텍쳐 디자인 패턴은 MVVM 패턴을 적용하였다.
    * View는 MainActivity, SplashFragment, BookListFragment, BookDetailFragment로 구성하였다.
        * View component들은 필요 시 ViewModel을 Inject 하여 필요한 데이터를 observe 한다. Injection 내용은 하단 참조. [Dependency Injection](#Dependency-Injection)
        * View component 간 화면 이동은 하단 참조. [화면 구성](#화면-구성)
    * ViewModel은 SplashViewModel, BooksViewModel로 구성하였다.
        * 각 ViewModel은 View의 lifecycle을 고려하여 필요한 비즈니스 로직을 처리하고 데이터를 저장하거나 변경된 내용을 알리는 역할을 한다.
        * SplashViewModel은 BooksRepository를 Inject하여 cache 초기화 기능을 제공하며, SplashFragment 생성 시 로컬 캐시 데이터를 초기화한다.
        * BooksViewModel은 BooksRepository를 Inject하여 paging 관련 처리를 중계한다.
    * Model은 BooksRepository와 LocalDB, RemoteAPI로 구성하였다.
        * BooksRepository는 Pager를 이용하여 Remote DataSource에서 필요한 데이터를 Room에 저장하고 Config 파라미터에 따라 PagingData를 메모리에 캐싱하여 관찰자에게 알린다.
      


### 3. Dependency Injection



### 4. Continuous Integration

  * Github Actions Workflow를 이용해 테스트 자동화를 등록하였다. [Github Actions](https://github.com/meuus90/DaumBookSearch/actions)
  * 주요 기능
    * develop branch에서 commit push 완료시 실행
    * JDK i.8 셋업
    * Kotlin linter 체크
    * Android linter 체크
    * Test code Unit test 실시



## 화면 구성

### 1. 스플래시 화면


### 2. 검색 화면


### 3. 상세 화면


## 작업 계획
- [x] 프로젝트 세팅
- [x] 스키마 디자인
- [x] Model 세팅
    - [x] Remote Repository 세팅
    - [x] Local Room 세팅
    - [x] Paging Data 세팅
- [x] ViewModel 세팅
- [x] Unit Test 테스트코드 작성
- [x] UI 디자인
- [x] API 에러 타입 별 대응
- [x] 애니메이션 등 UX 설정
- [x] 디바이스 퍼포먼스 체크
- [x] UI 테스트 및 기타 버그 픽스
- [x] Release


## License

Completely free (MIT)! See [LICENSE.md](LICENSE.md) for more.
