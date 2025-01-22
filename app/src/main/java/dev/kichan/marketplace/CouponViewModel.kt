package dev.kichan.marketplace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kichan.marketplace.model.data.ClosingCouponRes
import dev.kichan.marketplace.model.data.CouponMemberRes
import dev.kichan.marketplace.model.data.LatestCoupon
import dev.kichan.marketplace.model.data.Member.MemberCoupon
import dev.kichan.marketplace.model.data.coupon.LatestCouponRes
import dev.kichan.marketplace.model.repository.CouponRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponMember
import kotlinx.coroutines.launch

class CouponViewModel : ViewModel() {
    private val repository = CouponRepositoryImpl()

    val coupons = MutableLiveData<List<MemberCoupon>>()
    fun getCoupons(marketId: Long, couponId: Long? = null, size: Int? = null) {
        viewModelScope.launch {
            val res = repository.getCoupons(marketId, couponId, size)

            if(res.isSuccessful) {
                coupons.value = res.body()!!.response.couponResDtos
            }
        }
    }

    val latestCoupon = MutableLiveData<List<LatestCoupon>?>()
    fun getLatestCoupon() {
        viewModelScope.launch {
            val res = repository.getLatestTopCoupon(null, null, null)
            if(res.isSuccessful) {
                latestCoupon.value = res.body()!!.response.couponResDtos
            }
        }
    }

    val closingCoupon = MutableLiveData<List<ClosingCouponRes>>()
    fun getClosingCoupon() {
        viewModelScope.launch {
            val res = repository.getClosingCoupon(null);
            if(res.isSuccessful) {
                closingCoupon.value = res.body()!!.response
            }
        }
    }
}