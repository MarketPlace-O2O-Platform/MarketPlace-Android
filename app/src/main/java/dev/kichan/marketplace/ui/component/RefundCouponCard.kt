package dev.kichan.marketplace.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

//https://chatgpt.com/c/68bd9b5a-da74-8322-8bd1-3c9afc14d6eb

@Composable
fun RefundCouponCard(
    storeName: String = "하노이키친 인천대점",
    discountTitle: String = "결제 금액 10% 환급",
    imageUrl: String = "https://placehold.co/600x600",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    isUsable: Boolean = true
) {
    val density = LocalDensity.current
//    val CouponShape = couponShape(density)

    Surface(
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.White,
            ),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color(0xFFE0E0E0))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                /* 헤더 ----------------------------------------------------------------- */
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = NetworkModule.getImageModel(LocalContext.current, imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            storeName,
                            fontFamily = PretendardFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xff727272),
                        )
                        Text(
                            discountTitle,
                            fontFamily = PretendardFamily,
                            fontSize = 24.sp,
                            fontWeight = FontWeight(500),
                        )
                    }
                }

                /* 버튼 ----------------------------------------------------------------- */
                Spacer(Modifier.height(16.dp))
                CustomButton(
                    text = if (isUsable) "환급하러 가기" else "사용 불가",
                    modifier = Modifier.fillMaxWidth(),
                    isDisable = !isUsable,
                    onClick = onClick
                )

                /* 절취선 ---------------------------------------------------------------- */
                Spacer(Modifier.height(21.dp))
                CouponDivider()      // 아래에 정의
                Spacer(Modifier.height(9.dp))

                /* 하단 메모 ------------------------------------------------------------- */
                Text(
                    "주류 제외",
                    color = Color.Gray,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(400),
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

fun Dp.toPx(density: Density): Float {
    return with(density) { this@toPx.toPx() }
}

@Composable
fun couponShape(
    density: Density,
    cornerR: Dp = 8.dp,   // 사각형 모서리
    holeR: Dp = 6.dp    // 타공 반지름
) = GenericShape { size, _ ->
    val c = cornerR.toPx(density)
    val h = holeR.toPx(density)
    val w = size.width
    val hgt = size.height
    val midY = hgt / 2

    // ┌────────── 위쪽 ──────────┐
    moveTo(c, 0f)
    lineTo(w - c, 0f)
    arcTo(Rect(w - 2 * c, 0f, w, 2 * c), -90f, 90f, false)

    // ┤ 오른쪽 상단 → 타공
    lineTo(w, midY - h)
    arcTo(Rect(w - h, midY - h, w + h, midY + h), 0f, 180f, false)

    // ┤ 오른쪽 하단
    lineTo(w, hgt - c)
    arcTo(Rect(w - 2 * c, hgt - 2 * c, w, hgt), 0f, 90f, false)

    // └────────── 아래쪽 ─────────
    lineTo(c, hgt)
    arcTo(Rect(0f, hgt - 2 * c, 2 * c, hgt), 90f, 90f, false)

    // ┤ 왼쪽 하단 → 타공
    lineTo(0f, midY + h)
    arcTo(Rect(-h, midY - h, h, midY + h), 180f, 180f, false)

    // ┤ 왼쪽 상단
    lineTo(0f, c)
    arcTo(Rect(0f, 0f, 2 * c, 2 * c), 180f, 90f, false)
    close()
}

/* 절취선 + 옆 타공 */
@Composable
fun CouponDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(18.dp)
    ) {
        val y = size.height / 2
        val dash = 5.dp.toPx()
        val gap = dash
        val stroke = 1.dp.toPx()
        var x = 0f
        while (x < size.width) {
            drawLine(
                color = Color(0xFFCCCCCC),
                start = Offset(x, y),
                end = Offset(x + dash, y),
                strokeWidth = stroke
            )
            x += dash + gap
        }
//        // 좌-우 타공
//        val r = 4.dp.toPx()
//        drawCircle(Color.Black, r, center = Offset(0f, y))
//        drawCircle(Color.Black, r, center = Offset(size.width, y))
    }
}

@Preview
@Composable
private fun RefunCouponCardPreview() {
    MarketPlaceTheme {
        RefundCouponCard()
    }
}

@Composable
fun RefundCouponCardSkeleton(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // 헤더
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE0E0E0))
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE0E0E0))
                    )
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .width(180.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE0E0E0))
                    )
                }
            }

            // 버튼
            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFE0E0E0))
            )

            // 절취선 위치
            Spacer(Modifier.height(21.dp))
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            Spacer(Modifier.height(9.dp))

            // 하단 메모
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFE0E0E0))
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}
