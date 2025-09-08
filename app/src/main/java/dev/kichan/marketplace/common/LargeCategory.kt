package dev.kichan.marketplace.common

import androidx.annotation.DrawableRes
import dev.kichan.marketplace.R

enum class LargeCategory(
    val nameKo: String,
    val backendLabel: String?,
    @DrawableRes val icon : Int,
) {
    All(
        "전체",
        null,
        R.drawable.ic_category_all
    ),
    Food(
        "푸드",
        "FOOD",
        R.drawable.ic_category_food
    ),
    Dessert(
        "디저트",
        "DESSERT",
        R.drawable.ic_category_dessert
    ),
    Sports(
        "스포츠",
        "SPORT",
        R.drawable.ic_category_sports
    ),
    Beauty(
        "뷰티&헤어",
        "BEAUTY",
        R.drawable.ic_category_beauty
    ),
    Hospital(
        "메디컬",
        "HOSPITAL",
        R.drawable.ic_category_medical
    ),
    Education(
        "에듀",
        "EDUCATION",
        R.drawable.ic_category_education
    ),
    Rest(
        "더 다양한",
        "ETC",
        R.drawable.ic_category_etc
    );

    companion object {
        fun findByNameKo(key: String): LargeCategory =
            runCatching { valueOf(key) }.getOrElse { Rest }
    }
}