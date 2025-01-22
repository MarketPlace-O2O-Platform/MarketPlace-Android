package dev.kichan.marketplace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kichan.marketplace.model.data.CouponMemberRes
import dev.kichan.marketplace.model.repository.CouponRepositoryImpl
import kotlinx.coroutines.launch

class CouponViewModel : ViewModel() {
    private val repository = CouponRepositoryImpl()

    val coupons = MutableLiveData<List<CouponMemberRes>>()
    fun getCoupons(marketId: Long, couponId: Long? = null, size: Int? = null) {
        viewModelScope.launch {
            val res = repository.getCoupons(marketId, couponId, size)

            if(res.isSuccessful) {
                coupons.value = res.body()!!.response.couponResDtos
            }
        }
    }

    fun getLatestCoupon() {
        viewModelScope.launch {
            repository.getLatestTopCoupon(null, null, null)
        }
    }

    fun getClosingCoupon() {
        viewModelScope.launch {
            repository.getLatestTopCoupon(null, null, null)
        }
    }
}