package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.component.atoms.CouponListItemProps

data class PopularCouponRes(
    //todo: 필드 입력 다 안함
    @SerializedName("couponId") val id: Long,
    @SerializedName("couponName") val name: String,
    @SerializedName("couponDescription") val description: String,
    val marketId: Long,
    val marketName: String,
    val address: String,
    val thumbnail: String,
    val isAvailable: Boolean,
    val isMemberIssued: Boolean,
    val issuedCount: Int,
) {
    fun toCouponListItemProps(): CouponListItemProps = CouponListItemProps(
        id = this.id,
        name = this.name,
        marketName = this.marketName,
        imageUrl = NetworkModule.getImage(this.thumbnail),
        address = this.address,
        isAvailable = this.isAvailable,
        isMemberIssued = this.isMemberIssued,
        marketId = this.marketId
    )
}