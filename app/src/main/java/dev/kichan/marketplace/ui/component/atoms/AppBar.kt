package dev.kichan.marketplace.ui.component.atoms

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.component.molecules.SearchBar
import dev.kichan.marketplace.ui.theme.Gray_9
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

val appBarModifier = Modifier
    .fillMaxWidth()
    .padding(top = 32.dp, bottom = 12.dp, start = 20.dp, end = 20.dp)

val titleStyle = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = PretendardFamily,
    color = Gray_9
)

@Composable
fun LogoAppBar(@DrawableRes logo: Int, vararg icons: Pair<ImageVector, () -> Unit>) {
    Row(
        modifier = appBarModifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(logo),
            contentDescription = null,
            modifier = Modifier.width(56.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (icon in icons) {
                IconButton(onClick = { icon.second }) {
                    Icon(
                        imageVector = icon.first,
                        contentDescription = null,
                        tint = Color(0xff121212)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeAppBar(@DrawableRes logo: Int, onSearch : (String) -> Unit, vararg icons: Pair<ImageVector, () -> Unit>) {
    Row(
        modifier = appBarModifier,
        horizontalArrangement = Arrangement.spacedBy(11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(logo),
            contentDescription = null,
            modifier = Modifier.width(56.dp)
        )

        SearchBar(modifier = Modifier.weight(1f), onSearch = onSearch)

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (icon in icons) {
                IconButton(onClick = { icon.second }) {
                    Icon(
                        imageVector = icon.first,
                        contentDescription = null,
                        tint = Color(0xff121212)
                    )
                }
            }
        }
    }
}

@Composable
fun NavAppBar(title: String, onBack: () -> Unit) {
    Box(
        modifier = appBarModifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onBack() },
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = null,
            tint = Color(0xff545454),
        )
        Text(text = title, style = titleStyle)
    }
}

@Preview(showBackground = true)
@Composable
fun NavAppBarPreview() {
    MarketPlaceTheme {
        NavAppBar("제에에에목", {})
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeAppBarPreview() {
    MarketPlaceTheme {
        HomeAppBar(R.drawable.logo, {})
    }
}
