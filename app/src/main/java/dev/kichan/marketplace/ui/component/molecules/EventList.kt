package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kichan.marketplace.model.data.event.Event
import dev.kichan.marketplace.model.data.market.Market
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.EventBox
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.MoreViewTitle

@Composable
fun EventList(
    modifier: Modifier = Modifier,
    navController: NavController,
    title: String,
    eventList: List<Market>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        MoreViewTitle(
            modifier = Modifier.padding(horizontal = PAGE_HORIZONTAL_PADDING),
            title = title
        ) {
            navController.navigate("${Page.EventList.name}/${title}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if(eventList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("이벤트가 없습니다.", modifier = Modifier.padding(vertical = 16.dp))
            }
        }
        else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING)
            ) {
                items(eventList) {
                    EventBox(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Page.EventDetail.name)
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