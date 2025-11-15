package malunki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import kotlinx.coroutines.delay
import kotlin.math.min
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun RotatingLine(modifier: Modifier) {
    val angel = remember { mutableStateOf(0.0f) }

    Canvas(modifier = modifier.fillMaxSize().background(color = Color.Black)) {
        val width = this.size.width
        val height = this.size.height

        val center = Offset(width / 2, height / 2)
        val radius = min(width, height) * 0.8 / 2

        val (start, end) = listOf(
            Matrix().apply { translate(-(width / 2), -(height / 2), 0f) },
            Matrix().apply { rotateZ(angel.value) },
            Matrix().apply { translate((width / 2), (height / 2), 0f) }
        ).fold(
            Pair(
                center - Offset(radius.toFloat(), 0.0f),
                center + Offset(radius.toFloat(), 0.0f)
            )
        ) { (start, end), matrix ->
            Pair(
                matrix.map(start),
                matrix.map(end)
            )
        }

        this.drawLine(color = Color.White, start = start, end = end)
        this.drawCircle(color = Color.White, radius = 2.0f, center = center)
        this.drawCircle(color = Color.White, radius = 2.0f, center = start)
        this.drawCircle(color = Color.White, radius = 2.0f, center = end)
    }

    LaunchedEffect(angel.value) {
        delay(500.0.milliseconds)
        angel.value += 1
    }
}
