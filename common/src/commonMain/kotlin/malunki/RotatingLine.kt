package malunki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun RotatingLine(width: Int, height: Int) {

    val corners = mutableStateOf(Pair<Offset, Offset>(Offset.Zero, Offset(width.toFloat(), height.toFloat())))

    Canvas(modifier = Modifier.fillMaxSize()) {
        val (start, end) = corners.value
        this.drawLine(
            color = Color.Black,
            start = start,
            end = end
        )
    }

    LaunchedEffect(Unit) {
        val matrix = Matrix()
        while (true) {
            delay(1.0.seconds)
            matrix.translate(-(width/2).toFloat(), -(height/2).toFloat(), 0f)
            corners.value = corners.value.let { (start, end) ->
                Pair(
                    matrix.map(start),
                    matrix.map(end)
                )
            }
            matrix.reset()
            matrix.rotateX(10.0f)
            corners.value = corners.value.let { (start, end) ->
                Pair(
                    matrix.map(start),
                    matrix.map(end)
                )
            }
            matrix.reset()
            matrix.translate((width/2).toFloat(), (height/2).toFloat(), 0f)
            corners.value = corners.value.let { (start, end) ->
                Pair(
                    matrix.map(start),
                    matrix.map(end)
                )
            }
            matrix.reset()
            delay(1.0.seconds)
        }
    }
}