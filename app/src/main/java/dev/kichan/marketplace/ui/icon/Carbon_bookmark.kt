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

public val Carbon_bookmark: ImageVector
	get() {
		if (_Carbon_bookmark != null) {
			return _Carbon_bookmark!!
		}
		_Carbon_bookmark = ImageVector.Builder(
            name = "Carbon_bookmark",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
			path(
    			fill = SolidColor(Color(0xFFFFFFFF)),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.NonZero
			) {
				moveTo(18f, 3f)
				verticalLineTo(20.0625f)
				lineTo(12.675f, 17.37f)
				lineTo(12f, 17.0325f)
				lineTo(11.325f, 17.37f)
				lineTo(6f, 20.0625f)
				verticalLineTo(3f)
				horizontalLineTo(18f)
				close()
				moveTo(18f, 1.5f)
				horizontalLineTo(6f)
				curveTo(5.60220f, 1.50f, 5.22060f, 1.6580f, 4.93930f, 1.93930f)
				curveTo(4.6580f, 2.22060f, 4.50f, 2.60220f, 4.50f, 30f)
				verticalLineTo(22.5f)
				lineTo(12f, 18.75f)
				lineTo(19.5f, 22.5f)
				verticalLineTo(3f)
				curveTo(19.50f, 2.60220f, 19.3420f, 2.22060f, 19.06070f, 1.93930f)
				curveTo(18.77940f, 1.6580f, 18.39780f, 1.50f, 180f, 1.50f)
				close()
			}
		}.build()
		return _Carbon_bookmark!!
	}

private var _Carbon_bookmark: ImageVector? = null
