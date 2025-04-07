package dev.kichan.marketplace.ui.component.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.marketplace.model.data.market.Market
import dev.kichan.marketplace.ui.component.atoms.SkeletonItem
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun MarketListLoadingItem(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(
                top = 20.dp,
                bottom = 20.dp,
                start = 20.dp,
                end = 14.dp
            )
            .fillMaxWidth()
    ) {
        SkeletonItem(
            modifier = Modifier
                .width(110.dp)
                .height(110.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .height(110.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                SkeletonItem(
                    modifier = Modifier
                        .width(100.dp)
                        .height(20.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                SkeletonItem(
                    modifier = Modifier
                        .width(160.dp)
                        .height(20.dp)
                )
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                SkeletonItem(
                    Modifier
                        .width(50.dp)
                        .height(20.dp)
                )

                SkeletonItem(
                    Modifier.size(28.dp).clip(CircleShape)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MarketListLoadingItemPreview() {
    MarketPlaceTheme {
        MarketListLoadingItem()
    }
}