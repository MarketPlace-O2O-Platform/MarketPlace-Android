# 쿠러미 — 인천대 전용 제휴 혜택 앱

<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.webp" width="120" alt="쿠러미 앱 아이콘" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-Android-7F52FF?logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Jetpack_Compose-UI-4285F4?logo=jetpackcompose&logoColor=white" />
  <img src="https://img.shields.io/badge/Retrofit-Network-48B983?logo=square&logoColor=white" />
  <img src="https://img.shields.io/badge/Firebase-FCM_·_Analytics_·_RemoteConfig-FFCA28?logo=firebase&logoColor=black" />
  <img src="https://img.shields.io/badge/Version-2.0.7-blue" />
</p>

<p align="center">
  <a href="https://play.google.com/store/apps/details?id=dev.kichan.marketplace&hl=ko">
    <img src="https://img.shields.io/badge/Google_Play-다운로드-34A853?logo=googleplay&logoColor=white" />
  </a>
  <a href="https://apps.apple.com/kr/app/%EC%BF%A0%EB%9F%AC%EB%AF%B8/id6748341038">
    <img src="https://img.shields.io/badge/App_Store-다운로드-0D96F6?logo=appstore&logoColor=white" />
  </a>
</p>

## 프로젝트 소개

인천대학교 학생이 캠퍼스 주변 제휴 매장의 쿠폰 혜택을 한 곳에서 찾고 사용할 수 있는 네이티브 Android 앱입니다.
일반 할인 쿠폰뿐만 아니라 영수증을 제출하면 차액을 환급받는 **환급 쿠폰**, 아직 제휴되지 않은 매장을 응원하는 **공감 매장** 기능을 제공합니다.

팀으로 시작된 프로젝트를 인수인계 받아 리팩토링, 기능 확장, QA 대응을 단독으로 진행하며 **v2.0.0 ~ v2.0.7** 릴리즈를 담당했습니다.

## 주요 기능

- **쿠폰** — 카테고리·인기·마감임박 순 조회, 발급 및 사용 처리
- **환급 쿠폰** — 영수증 제출 → 환급 신청, 계좌번호 임시 저장 및 자동 삭제
- **매장 검색** — 키워드 검색, 최근 검색어 관리, 인기 혜택 노출
- **지도** — 카카오맵 기반 커스텀 마커·그룹화, 카테고리 필터, 바텀 시트
- **공감 매장** — 미제휴 매장에 공감, 목표 공감 수 달성 시 제휴 요청으로 노출
- **알림** — FCM 푸시, 알림 목록 및 전체 읽음 처리
- **서버 점검 공지** — Firebase Remote Config 기반 동적 공지 노출

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM, 2-Layer (Data + UI) |
| Network | Retrofit2, OkHttp3, Gson |
| Image | Coil |
| Map | 카카오맵 SDK, Google Maps Compose |
| Auth | Kakao SDK, JWT (SharedPreferences) |
| Push | Firebase Cloud Messaging |
| Analytics | Firebase Analytics |
| Remote Config | Firebase Remote Config |
| Local Storage | SharedPreferences, DataStore |
| Build | Gradle KTS, Secrets Gradle Plugin |

## 아키텍처

도메인 계층 없는 MVVM 2계층 구조로 오버엔지니어링을 배제했습니다.

```
dev.kichan.marketplace/
├── common/          (확장함수, 상수, 헬퍼)
├── model/           (Repository, DTO, Retrofit Service, NetworkModule)
├── ui/              (Jetpack Compose — Atomic Design)
│   ├── atom/
│   ├── molecule/
│   ├── organism/
│   ├── template/
│   └── page/
└── viewmodel/       (ViewModel, UiState)
```

인증 토큰은 `TokenManager` (Object 싱글톤, SharedPreferences 동기 읽기)로 관리하고, `AuthInterceptor`가 모든 요청에 자동 주입합니다.

## 개발 과정 & 문제 해결

> Implemented by [@junhee8649](https://github.com/junhee8649) (v2.0.0 ~ )

### 지도 기능 전면 개선

초기 구현은 카카오맵 기본 마커를 단순 나열하는 수준이었습니다. 아래 순서로 단계적으로 개선했습니다.

1. 좌표 조회 방식을 직접 파싱에서 **카카오 키워드 검색 API**로 전환하고, 실패 시 주소 검색으로 폴백하는 2단계 구조를 적용 — 정확도 개선
2. 위·경도를 소수점 2자리로 반올림한 격자 기준 **마커 그룹화**(약 1km 단위) — 같은 위치 마커 밀집 문제 해결
3. 선택한 마커에 흰색 테두리 강조 및 **커스텀 마커 디자인** 적용
4. 바텀 시트에 `NestedScrollConnection` 연결 — 시트 안에서의 스크롤과 시트 드래그 충돌 해소
5. 화면 중앙·모서리 좌표를 **역지오코딩**해 행정구역(시·도·구)을 도출하고, 이를 기준으로 매장을 조회·병합

### Firebase Remote Config 기반 서버 점검 공지 시스템

초기에는 점검 일정을 코드에 하드코딩하여 점검일이 바뀔 때마다 앱 배포가 필요했습니다. Firebase Remote Config를 도입해 점검 메시지와 표시 여부를 서버에서 제어하도록 변경했으며, 이후 롤아웃·운영 공지에도 동일한 구조를 재사용할 수 있게 됐습니다.

### 환급 쿠폰 UX 개선

환급 신청 시 매번 계좌번호를 입력해야 하는 불편함을 해소하기 위해 **서버 API 기반 계좌번호 저장** 기능을 구현했습니다. 앱 진입 시 `getMember()`로 저장된 계좌를 자동으로 불러오고, "이번만 사용" 체크박스 미체크 시 영수증 제출 성공 후 서버에서 계좌 정보를 자동 삭제합니다. 또한 쿠폰 목록을 API 재호출 없이 즉시 UI에 반영하도록 상태 관리를 개선했습니다.

### 환급 쿠폰 발급·API 연동 안정화

매장 상세 화면에서 환급 쿠폰이 깨지거나 발급되지 않던 문제를 데이터 연동 관점에서 해결했습니다.

- **로딩 타이밍 NPE 방지**: `marketData`가 준비되기 전에 쿠폰을 변환하다 `NullPointerException`이 발생하던 구조를, 매장 정보·쿠폰을 `async`로 병렬 호출하되 매장 정보 준비 후 변환하도록 재정렬하고 상태 업데이트를 1회로 합쳐 불필요한 recompose를 제거
- **서버 응답 파싱 에러 방지**: 서버가 일부 매장 정보 필드를 내려주지 않아 파싱이 실패하던 문제를, `CouponRes`의 해당 필드를 nullable로 변경하고 누락된 값은 매장 상세 정보에서 보강해 채우도록 수정
- **재발급 지원**: 이미 발급받은 환급 쿠폰이 단순 발급 실패로 처리돼 다시 표시되지 않던 필터링 로직 수정
- **중복 API 호출 제거**: 매장 상세 진입 시 `checkFavoriteStatus()`가 중복 호출되던 것을 정리(5회 → 4회)
- **발급 실패 피드백**: 쿠폰 다운로드 실패 시 토스트 메시지로 사용자에게 명확히 안내

### 네비게이션 탭 상태 버그 일괄 수정

QA 과정에서 여러 탭 이동 버그가 보고됐습니다.

- **쿠폰 발급 후 마이 탭 미인식**: `Page.My`로 `navigate()` 시 route를 `Page.My.name`(문자열)로 전달하던 버그를 `Page.My` 객체 기반 라우팅으로 수정
- **마이페이지에서 홈 탭 이동 불가**: `Page.My`가 중첩 NavGraph 외부에 선언되어 `popUpTo("Home")`이 동작하지 않던 구조를 NavGraph 내부로 이동해 해결
- **홈 탭 재클릭 시 스택 롤백**: 홈 탭 클릭을 `navigate()`가 아닌 `popBackStack(Page.Home, inclusive = false)`로 교체하고, 다른 탭은 `saveState` / `restoreState` / `launchSingleTop` 조합으로 정리

### Material 2 → Material 3 마이그레이션

인수인계 시점의 코드베이스는 Material 2와 3가 혼재되어 있어 주요 컴포넌트의 스타일이 일관되지 않았습니다. Material 3 공식 문서를 기준으로 `Scaffold backgroundColor → containerColor`, `scaffoldState → SnackbarHostState`, `Divider → HorizontalDivider` 등 핵심 컴포넌트를 전환했습니다. 이와 별개로 `Scaffold` 람다에서 `PaddingValues`를 하위 컴포넌트에 전달하지 않아 콘텐츠가 상태바를 침범하던 문제도 같은 시기에 수정했습니다.

### Android 15 · 16 호환성 대응

- Android 15: `ArrayDeque.removeLast()` 미지원으로 발생한 크래시를 `removeAt(lastIndex)`로 수정
- Android 16: `kakao-sdk-all`이 대형 화면(폴더블 · 태블릿) 레이아웃 오류를 일으켜 필요한 모듈만 선택적으로 의존하는 `v2-common`으로 교체

### 코드 품질 개선

미사용 import와 dead code를 제거하고 네이밍·미사용 변수 등 lint 경고를 해소했으며, 중복된 `NotificationService` 로직을 통합해 유지보수성을 높였습니다.

## 버전 히스토리 (v2 — Junhee-lee)

| 버전 | 주요 변경 |
|------|-----------|
| 2.0.7 | Android 16 대형 화면 호환성 수정, AD_ID 권한 추가 |
| 2.0.6 | 마이페이지 스켈레톤 반복 버그, 하단바 탭 이동 버그 수정 |
| 2.0.5 | 권한 무한 루프 크래시, 로그인 UI 흰색 배경 누락 수정 |
| 2.0.4 | Firebase Remote Config 서버 점검 공지 시스템 구현 |
| 2.0.3 | 계좌번호 임시 저장·자동 삭제, 환급 쿠폰 UX 개선 |
| 2.0.2 | 로그인·로그아웃 플로우 개선, UI 전반 정리 |
| 2.0.1 | Firebase Analytics 연동, Material 3 주요 컴포넌트 전환 |
| 2.0.0 | 지도 커스텀 마커·그룹화, 공감 페이지 캐싱, 검색 기능 강화 |

