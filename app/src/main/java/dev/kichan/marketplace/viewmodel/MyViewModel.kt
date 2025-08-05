package dev.kichan.marketplace.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.coupon.IssuedCouponRes
import dev.kichan.marketplace.model.repository.CouponMemberRepositoryImpl
import dev.kichan.marketplace.model.repository.PayBackCouponMemberRepository
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.memberCoupon.MemberCoupon
import kotlinx.coroutines.launch

data class MyViewModelState(
    val paybackCouponList: List<IssuedCouponRes> = listOf(),
    val giftCouponList: List<MemberCoupon> = listOf()
)

class MyViewModel: ViewModel() {
    val paybackCouponRepo = PayBackCouponMemberRepository()
    val couponRepo = CouponMemberRepositoryImpl()

    var state by mutableStateOf(MyViewModelState())

    init {
//        getPaybackCoupon()
//        getGiftCoupon()
    }

    fun getGiftCoupon() {
        viewModelScope.launch {
            val res = couponRepo.getValidMemberCoupons(1)

            if(!res.isSuccessful) {
                //todo: 예외처리
                return@launch
            }

            val resList = res.body()!!.response
            state = state.copy(giftCouponList = resList)
        }
    }

    fun getPaybackCoupon() {
        viewModelScope.launch {
            val res = paybackCouponRepo.getMemberPayBackCoupon(type = "ISSUED")

            if(!res.isSuccessful) {
                //todo: 예외처리
                return@launch
            }

            val resList = res.body()!!.response.couponResDtos
            state = state.copy(paybackCouponList = resList)
        }
    }
}