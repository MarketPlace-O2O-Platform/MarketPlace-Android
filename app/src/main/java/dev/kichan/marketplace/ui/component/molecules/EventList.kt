package dev.kichan.marketplace.ui.component.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kichan.marketplace.ui.data.Event
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.EmptyMessage
import dev.kichan.marketplace.ui.component.atoms.EventBox
import dev.kichan.marketplace.ui.component.atoms.MoreViewTitle

@Composable
fun EventList(
    modifier: Modifier = Modifier,
    navController: NavController,
    title: String,
    couponList: List<Event>, //todo: Event -> Coupon으로 변경
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

        if(couponList.isEmpty()) {
            EmptyMessage()
        }
        else {
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 7.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING)
            ) {
                items(couponList) {
                    EventBox(
                        modifier = Modifier
                            .clickable {
                                navController.navigate("${Page.EventDetail.name}/${it.id}")
                            }
                            .fillParentMaxSize(0.8f)
                            .aspectRatio(1f / 1),
                        event = it
                    )
                }
            }
        }
    }
}