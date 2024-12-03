package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.image

import com.google.gson.annotations.SerializedName

data class ImageRes(
    @SerializedName("imageId") val id: Long,
    val sequence: Int,
    val name: String
)