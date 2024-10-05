enum class LargeCategory(
    val nameKo: String,
) {
    Food("음식"),
    Bar("주점"),
    Cafe("카페"),
    Beauty("뷰티"),
    Game("놀이"),
    Rest("기타"),
    Class("클래스"), ;

    companion object {
        fun getCategory(key: String): LargeCategory = when (key) {
            "Food" -> Food
            "Bar" -> Bar
            "Cafe" -> Cafe
            "Beauty" -> Beauty
            "Game" -> Game
            else -> Rest
        }
    }
}