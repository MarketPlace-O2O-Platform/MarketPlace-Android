package dev.kichan.marketplace.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.data.MemberLoginRes
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    member: MemberLoginRes,
    onLogout: () -> Unit = {},
    onCuration: () -> Unit,
    onCallCenter: () -> Unit,
) {
    var isShowLogoutMemu by remember { mutableStateOf(false) }

    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile",
                tint = Color(0xFFDADADA),
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFF9F9F9), shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "${member.studentId}님",
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PretendardFamily
            )

            IconButton(onClick = { isShowLogoutMemu = !isShowLogoutMemu }) {
                Icon(
                    painter = painterResource(id = R.drawable.down),
                    contentDescription = "드롭다운",
                    tint = Color(0xFF838A94),
                    modifier = Modifier.size(24.dp)
                )
            }

            DropdownMenu(
                expanded = isShowLogoutMemu,
                onDismissRequest = { isShowLogoutMemu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("로그아웃") },
                    onClick = {
                        onLogout()
                    }
                )
            }
        }

        TwoButtonGroup(
            labels = listOf(
                "큐레이션" to onCuration,
                "고객센터" to onCallCenter,
            ),
            round = 16.dp
        )
    }
}