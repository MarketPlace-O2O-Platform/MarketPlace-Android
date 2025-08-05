import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.viewmodel.MyViewModel

@Composable
fun MyPage2(
    myViewModel: MyViewModel = MyViewModel()
) {

}

@Preview(showBackground = true)
@Composable
fun MyPage2Preview() {
    MarketPlaceTheme() {
        MyPage2()
    }
}