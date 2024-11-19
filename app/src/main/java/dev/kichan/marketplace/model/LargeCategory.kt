import androidx.annotation.DrawableRes
import dev.kichan.marketplace.R

enum class LargeCategory(
    val nameKo: String,
    @DrawableRes val icon : Int,
) {
    All(
        "전체",
        R.drawable.ic_category_all
    ),
    Food(
        "음식",
        R.drawable.ic_category_food
    ),
    Dessert(
        "디저트",
        R.drawable.ic_category_dessert
    ),
    Sports(
        "스포츠",
        R.drawable.ic_category_sports
    ),
    Beauty(
        "미용",
        R.drawable.ic_category_beauty
    ),
    Hospital(
        "병원",
        R.drawable.ic_category_medical
    ),
    Education(
        "교육",
        R.drawable.ic_category_education
    ),
    Rest(
        "기타",
        R.drawable.ic_category_etc
    );

    companion object {
        fun findByNameKo(key: String): LargeCategory =
            runCatching { valueOf(key) }.getOrElse { Rest }
    }
}