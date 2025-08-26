package dev.kichan.marketplace.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.CouponReq
import dev.kichan.marketplace.model.data.MarketReq
import dev.kichan.marketplace.model.data.MemberAccountReq
import dev.kichan.marketplace.model.data.MemberLoginReq
import dev.kichan.marketplace.model.repository.CouponRepository
import dev.kichan.marketplace.model.repository.CouponRepositoryImpl
import dev.kichan.marketplace.model.repository.MemberRepository
import dev.kichan.marketplace.model.repository.MemberRepositoryImpl
import dev.kichan.marketplace.model.repository.OwnerMarketRepository
import dev.kichan.marketplace.model.repository.OwnerMarketRepositoryImpl
import dev.kichan.marketplace.model.service.CouponService
import dev.kichan.marketplace.model.service.MemberService
import dev.kichan.marketplace.model.service.OwnerMarketService
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun ApiTestPage() {
    val memberRepository: MemberRepository = MemberRepositoryImpl(NetworkModule.getService(MemberService::class.java))
    val ownerMarketRepository: OwnerMarketRepository = OwnerMarketRepositoryImpl(NetworkModule.getService(OwnerMarketService::class.java))
    val couponRepository: CouponRepository = CouponRepositoryImpl(NetworkModule.getService(CouponService::class.java))

    Scaffold { _ ->
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val loginReq = MemberLoginReq(studentId = "202010000", password = "password")
                        val loginRes = memberRepository.loginMember(loginReq)
                        if (loginRes.isSuccessful) {
                            Log.d("ApiTest", "Login Success: ${loginRes.body()?.response}")
                            NetworkModule.updateToken(loginRes.body()?.response)
                            val memberRes = memberRepository.getMember()
                            if (memberRes.isSuccessful) {
                                Log.d("ApiTest", "Get Member Success: ${memberRes.body()?.response}")
                            } else {
                                Log.e("ApiTest", "Get Member Failed: ${memberRes.errorBody()?.string()}")
                            }
                        } else {
                            Log.e("ApiTest", "Login Failed: ${loginRes.errorBody()?.string()}")
                        }
                    } catch (e: Exception) {
                        Log.e("ApiTest", "Login Exception: ${e.message}")
                    }
                }
            }) {
                Text("Test Member Login & Get Member")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val marketReq = MarketReq(
                            marketName = "테스트 매장",
                            description = "테스트 매장 설명",
                            operationHours = "09:00-18:00",
                            closedDays = "주말",
                            phoneNumber = "010-1234-5678",
                            address = "인천대학교",
                            major = "컴퓨터공학"
                        )
                        val createMarketRes = ownerMarketRepository.createMarket(marketReq, emptyList())
                        if (createMarketRes.isSuccessful) {
                            Log.d("ApiTest", "Create Market Success: ${createMarketRes.body()?.response}")
                        } else {
                            Log.e("ApiTest", "Create Market Failed: ${createMarketRes.errorBody()?.string()}")
                        }
                    } catch (e: Exception) {
                        Log.e("ApiTest", "Create Market Exception: ${e.message}")
                    }
                }
            }) {
                Text("Test Create Market")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val couponReq = CouponReq(
                            couponName = "테스트 쿠폰",
                            description = "테스트 쿠폰 설명",
                            deadLine = LocalDateTime.now().plusDays(7).toString(),
                            stock = 100
                        )
                        // Assuming marketId 1 for testing
                        val createCouponRes = couponRepository.createCoupon(1L, couponReq)
                        if (createCouponRes.isSuccessful) {
                            Log.d("ApiTest", "Create Coupon Success: ${createCouponRes.body()?.response}")
                        } else {
                            Log.e("ApiTest", "Create Coupon Failed: ${createCouponRes.errorBody()?.string()}")
                        }
                    } catch (e: Exception) {
                        Log.e("ApiTest", "Create Coupon Exception: ${e.message}")
                    }
                }
            }) {
                Text("Test Create Coupon")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val popularCouponsRes = couponRepository.getPopularCoupon(pageSize = 10)
                        if (popularCouponsRes.isSuccessful) {
                            Log.d("ApiTest", "Get Popular Coupons Success: ${popularCouponsRes.body()?.response}")
                        } else {
                            Log.e("ApiTest", "Get Popular Coupons Failed: ${popularCouponsRes.errorBody()?.string()}")
                        }
                    } catch (e: Exception) {
                        Log.e("ApiTest", "Get Popular Coupons Exception: ${e.message}")
                    }
                }
            }) {
                Text("Test Get Popular Coupons")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val accountReq = MemberAccountReq("신한은행", "110-123-456789")
                        val permitAccountRes = memberRepository.permitAccount(accountReq)
                        if (permitAccountRes.isSuccessful) {
                            Log.d("ApiTest", "Permit Account Success: ${permitAccountRes.body()?.response}")
                        } else {
                            Log.e("ApiTest", "Permit Account Failed: ${permitAccountRes.errorBody()?.string()}")
                        }
                    } catch (e: Exception) {
                        Log.e("ApiTest", "Permit Account Exception: ${e.message}")
                    }
                }
            }) {
                Text("Test Permit Account")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApiTestPagePreview() {
    MarketPlaceTheme {
        ApiTestPage()
    }
}

