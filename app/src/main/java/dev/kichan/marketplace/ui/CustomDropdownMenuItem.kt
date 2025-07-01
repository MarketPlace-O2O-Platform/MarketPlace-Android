package dev.kichan.marketplace.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun CustomDropdownMenuItem(
    text: String,
    isLastItem: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        if (!isLastItem) {
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomDropdownMenuItemPreview() {
    MarketPlaceTheme {
        CustomDropdownMenuItem(
            text = "인천대학교",
            isLastItem = true,
            onClick = {}
        )
    }
}

