package dev.kichan.marketplace.ui.data

data class CouponBoxProps(
    val id: String,
    val title: String,
    val subTitle : String,
    val url : String,
    val marketId: Long,
    val onDownloadClick: () -> Unit,
)