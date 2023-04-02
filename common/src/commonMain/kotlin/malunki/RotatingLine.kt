package malunki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import kotlinx.coroutines.delay
import kotlin.math.min
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun RotatingLine(width: Int, height: Int) {

    val center = Offset(width.toFloat() / 2, height.toFloat() / 2)
    val radius = min(width, height) * 0.8 / 2

    val corners = mutableStateOf(
        Pair(
            center - Offset(radius.toFloat(), 0.0f),
            center + Offset(radius.toFloat(), 0.0f)
        )
    )

    Canvas(modifier = Modifier.fillMaxSize().background(color = Color.Black)) {
        val (start, end) = corners.value
        this.drawLine(color = Color.White, start = start, end = end)
        this.drawCircle(color = Color.White, radius = 2.0f, center = center)
        this.drawCircle(color = Color.White, radius = 2.0f, center = start)
        this.drawCircle(color = Color.White, radius = 2.0f, center = end)
    }

    LaunchedEffect(Unit) {
        val rotatePipeline = listOf(
            Matrix().apply { translate(-(width/2).toFloat(), -(height/2).toFloat(), 0f) },
            Matrix().apply { rotateZ(1.0f) },
            Matrix().apply { translate((width/2).toFloat(), (height/2).toFloat(), 0f) }
        )
        while (true) {
            corners.value = rotatePipeline.fold(corners.value) { (start, end), matrix ->
                Pair(
                    matrix.map(start),
                    matrix.map(end)
                )
            }
            delay(500.0.milliseconds)
        }
    }
}

@Composable
fun RotatingLine2(width: Int, height: Int) {

    val center = Offset(width.toFloat() / 2, height.toFloat() / 2)
    val radius = min(width, height) * 0.8 / 2

    val lines = mutableStateListOf(
        Pair(
            center,
            center + Offset(0.0f, -radius.toFloat())
        )
    )

    Canvas(modifier = Modifier.fillMaxSize().background(color = Color.Black)) {
        val alphaStep = 1.0f / lines.size
        lines.reversed().forEachIndexed { index, (start, end) ->
            val alpha = 1.0f - index * alphaStep
            val color = Color.White.copy(alpha = alpha)
            drawLine(color = color, start = start, end = end)
            drawCircle(color = color, radius = 2.0f, center = center)
            drawCircle(color = color, radius = 2.0f, center = start)
            drawCircle(color = color, radius = 2.0f, center = end)
        }
    }

    LaunchedEffect(Unit) {
        val rotatePipeline = listOf(
            Matrix().apply { translate(-(width/2).toFloat(), -(height/2).toFloat(), 0f) },
            Matrix().apply { rotateZ(1.0f) },
            Matrix().apply { translate((width/2).toFloat(), (height/2).toFloat(), 0f) }
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