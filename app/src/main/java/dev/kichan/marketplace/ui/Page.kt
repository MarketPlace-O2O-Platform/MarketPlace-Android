package dev.kichan.marketplace.ui

enum class Page(
    val pageName : String
) {
    Main(pageName = "메인"),
    LocalApiTestPage(pageName = "Test1"),
    GoogleMapTestPage(pageName = "Test2"),

    Home(pageName = "홈"),
    Like(pageName = "공감"),
    Map(pageName = "지도"),
    My(pageName = "마이페이지"),
    Coupon(pageName = "쿠폰"),
    Login(pageName = "로그인"),
    EventDetail(pageName = "이벤트 정보"),

    PopularEvent(pageName = "이벤트"),
}