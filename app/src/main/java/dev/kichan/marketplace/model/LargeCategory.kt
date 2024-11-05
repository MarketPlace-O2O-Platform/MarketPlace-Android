enum class LargeCategory(
    val nameKo: String,
) {
    All("전체"),
    Food("음식"),
    Dessert("디저트"),
    Sports("스포츠"),
    Beauty("미용"),
    Hospital("병원"),
    Education("교육"),
    Rest("기타");

    companion object {
        fun findByNameKo(key: String): LargeCategory =
            runCatching { valueOf(key) }.getOrElse { Rest }
    }
}