package dev.kichan.marketplace.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.coupon.IssuedCouponRes
import dev.kichan.marketplace.model.repository.CouponRepository
import dev.kichan.marketplace.model.repository.PayBackCouponMemberRepository
import kotlinx.coroutines.launch

data class MyViewModelState(
    val couponList: List<IssuedCouponRes> = listOf()
)

class MyViewModel: ViewModel() {
    val paybackCouponRepo = PayBackCouponMemberRepository()
    val couponRepo = CouponRepository()

    var state by mutableStateOf(MyViewModelState())

    init {
        getPaybackCoupon()
    }

    fun getPaybackCoupon() {
        viewModelScope.launch {
            val res = paybackCouponRepo.getMemberPayBackCoupon(type = "ISSUED")

            if(!res.isSuccessful) {
                //todo: 예외처리
                return@launch
            }

            val resList = res.body()!!.response.couponResDtos
            state = state.copy(couponList = state.couponList + resList)
        }
    }
}