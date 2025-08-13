package dev.kichan.marketplace.ui

enum class Page(
    val pageName : String
) {
    Main(pageName = "메인"),
    LocalApiTestPage(pageName = "Test1"),
    GoogleMapTestPage(pageName = "Test2"),

    Splash(pageName = "스플래시"),

    Home(pageName = "홈"),
    Like(pageName = "공감"),
    Map(pageName = "지도"),
    My(pageName = "마이페이지"),
    My2(pageName = "마이2"),

    Search(pageName = "검색"),
    Coupon(pageName = "쿠폰"),
    Login(pageName = "로그인"),
    EventDetail(pageName = "이벤트 정보"),
    CouponHam(pageName = "쿠폰함페이지"),

    MarketListPage(pageName = "매장 목록"),
    CouponListPage(pageName = "쿠폰 목록"),
    ReceptUploadPage(pageName = "영수증 등록")
}