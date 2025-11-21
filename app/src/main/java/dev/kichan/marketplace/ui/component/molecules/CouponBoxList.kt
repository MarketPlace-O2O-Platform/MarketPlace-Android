package dev.kichan.marketplace.ui.component.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.CouponBox
import dev.kichan.marketplace.ui.component.atoms.EmptyMessage
import dev.kichan.marketplace.ui.component.atoms.MoreViewTitle
import dev.kichan.marketplace.ui.component.atoms.SkeletonItem
import dev.kichan.marketplace.ui.data.CouponBoxProps

@Composable
fun CouponBoxList(
    modifier: Modifier = Modifier,
    navController: NavController,
    isLoading: Boolean,
    title: String,
    couponList: List<CouponBoxProps>,
    onMoreClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        MoreViewTitle(
            title = title
        ) {
            onMoreClick()
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (couponList.isEmpty()) {
            EmptyMessage()
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 7.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING)
            ) {
                items(couponList) {
                    CouponBox(
                        modifier = Modifier
                            .clickable {
                                navController.navigate("${Page.EventDetail.name}/${it.marketId}")
                            }
                            .fillParentMaxSize(0.8f)
                            .aspectRatio(1f / 1),
                        couponBoxProps = it,
                    )
                }
            }
        }
    }
}