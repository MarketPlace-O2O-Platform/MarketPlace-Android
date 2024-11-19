enum class LargeCategory(
    val nameKo: String,
    val backendLable: String,
) {
    All("전체", "ALL"),
    Food("음식", "FOOD"),
    Dessert("디저트", "DESSERT"),
    Sports("스포츠", "SPORT"),
    Beauty("미용", "BEAUTY"),
    Hospital("병원", "HOSPITAL"),
    Education("교육", "EDUCATION"),
    Rest("기타", "ETC");

    companion object {
        fun findByNameKo(key: String): LargeCategory =
            runCatching { valueOf(key) }.getOrElse { Rest }
    }
}