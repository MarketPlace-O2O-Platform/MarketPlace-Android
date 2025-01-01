package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .padding(horizontal = PAGE_HORIZONTAL_PADDING)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color(0xff121212),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Spacer(
                modifier = Modifier
                    .background(Color(0xffff0000))
                    .width(1.dp)
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.width(10.dp))

            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Normal),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            ) { innerTextField ->
                if (searchText.text.isEmpty()) {
                    Text(
                        text = "찾으시는 이용권을 검색해 보세요.",
                        style = TextStyle(color = Color(0xffB0B0B0)),
                    )
                }
                innerTextField()
            }
        }
    }
}