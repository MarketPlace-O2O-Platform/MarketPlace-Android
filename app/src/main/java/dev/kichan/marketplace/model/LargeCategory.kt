enum class LargeCategory(
    val nameKo: String,
) {
    All("전체보기"),
    Food("음식"),
    Bar("주점"),
    Cafe("카페"),
    Beauty("뷰티"),
    Game("놀이"),
    Class("클래스"),
    Rest("기타");

    companion object {
        fun getCategory(key: String): LargeCategory = when (key) {
            "All" -> All
            "Food" -> Food
            "Bar" -> Bar
            "Cafe" -> Cafe
            "Beauty" -> Beauty
            "Game" -> Game
            "Class" -> Class
            else -> Rest
        }
    }
}