/*
* Converted using https://composables.com/svgtocompose
*/

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Bookmark: ImageVector
	get() {
		if (_Bookmark != null) {
			return _Bookmark!!
		}
		_Bookmark = ImageVector.Builder(
            name = "Bookmark",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
			path(
    			fill = SolidColor(Color(0xFF000000)),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.NonZero
			) {
				moveTo(17.6875f, 2.88934f)
				horizontalLineTo(6.3125f)
				curveTo(6.07090f, 2.88930f, 5.8750f, 3.08840f, 5.8750f, 3.3340f)
				verticalLineTo(20.6764f)
				lineTo(12f, 18.1354f)
				lineTo(18.125f, 20.6764f)
				verticalLineTo(3.33402f)
				curveTo(18.1250f, 3.08840f, 17.92910f, 2.88930f, 17.68750f, 2.88930f)
				close()
			}
			path(
    			fill = SolidColor(Color(0xFF000000)),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.EvenOdd
			) {
				moveTo(5f, 3.33403f)
				curveTo(50f, 2.59730f, 5.58760f, 20f, 6.31250f, 20f)
				horizontalLineTo(17.6875f)
				curveTo(18.41240f, 20f, 190f, 2.59730f, 190f, 3.3340f)
				verticalLineTo(22f)
				lineTo(12f, 19.096f)
				lineTo(5f, 22f)
				verticalLineTo(3.33403f)
				close()
				moveTo(6.75f, 3.77871f)
				verticalLineTo(19.3528f)
				lineTo(12f, 17.1748f)
				lineTo(17.25f, 19.3528f)
				verticalLineTo(3.77871f)
				horizontalLineTo(6.75f)
				close()
			}
		}.build()
		return _Bookmark!!
	}

private var _Bookmark: ImageVector? = null
