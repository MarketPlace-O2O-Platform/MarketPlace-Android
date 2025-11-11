package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun SearchEmptyUI() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical=12.dp)
            .padding(start = 9.dp, end=16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.left_black),
                contentDescription = "Back",
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(1.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
//            SearchBars()
        }
        Divider(color = Color(0xFF303030), thickness = 1.dp)
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "검색 결과가 없어요.\n찾으시는 매장이 없으신가요?",
            fontSize = 14.sp,
            lineHeight = 21.sp,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight(500),
            color = Color(0xFF5E5E5E),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "매장 요청하기를 해보세요!",
            fontSize = 15.sp,
            lineHeight = 22.5.sp,
            fontWeight = FontWeight(600),
            color = Color(0xFF303030),
            fontFamily = PretendardFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.searchempty),
                contentDescription = "Search Empty",
                modifier = Modifier
                    .width(302.dp)
                    .height(185.dp)
            )
        }
        Spacer(modifier = Modifier.height(31.dp))
        CustomButton(
            text = "요청하기",
            onClick = { println("매장 요청하기 버튼 클릭됨!") },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 68.dp) ,
            textColor = Color.White,
            isDisable = false
        )

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSearchEmptyUI() {
    SearchEmptyUI()
}
