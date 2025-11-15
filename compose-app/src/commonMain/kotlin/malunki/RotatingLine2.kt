package malunki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
import kotlin.math.min
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun RotatingLine2(modifier: Modifier = Modifier) {
    var canvasSize = IntSize.Zero
    val lines = remember { mutableStateListOf<Pair<Offset, Offset>>() }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .onSizeChanged { canvasSize = it }
    ) {
        val width = this.size.width
        val height = this.size.height

        val center = Offset(width / 2, height / 2)

        val alphaStep = 1.0f / lines.size
        lines.reversed().forEachIndexed { index, (start, end) ->
            val alpha = 1.0f - index * alphaStep
            val color = Color.Companion.White.copy(alpha = alpha)
            drawLine(color = color, start = start, end = end)
            drawCircle(color = color, radius = 2.0f, center = center)
            drawCircle(color = color, radius = 2.0f, center = start)
            drawCircle(color = color, radius = 2.0f, center = end)
        }
    }

    LaunchedEffect(canvasSize) {
        val rotatePipeline = with(canvasSize) {
            listOf(
                Matrix().apply { translate(-(width / 2).toFloat(), -(height / 2).toFloat(), 0f) },
                Matrix().apply { rotateZ(1.0f) },
                Matrix().apply { translate((width / 2).toFloat(), (height / 2).toFloat(), 0f) }
            )
        }

        lines.add(
            with(canvasSize) {
                val center = Offset(width.toFloat() / 2, height.toFloat() / 2)
                val radius = min(width, height) * 0.8 / 2
                Pair(
                    center - Offset(radius.toFloat(), 0.0f),
                    center + Offset(radius.toFloat(), 0.0f)
                )
            }
        )

        while (true) {
            lines.takeIf { it.size > 360 }?.removeFirst()
            lines.add(
                rotatePipeline.fold(lines.last()) { (start, end), matrix ->
                    Pair(
                        matrix.map(start),
                        matrix.map(end)
                    )
                }
            )
            delay(150.0.milliseconds)
        }
    }
}
