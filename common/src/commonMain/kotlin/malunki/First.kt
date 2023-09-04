package malunki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun First() {
    val random = Random(System.currentTimeMillis())
    val iterations = remember { random.nextInt(10..100_000) }
    val iterationsCounter = remember { mutableStateOf(0) }

    val lines = remember { mutableListOf<Pair<Offset, Offset>>() }

    Column(Modifier.fillMaxSize()) {
        Text("Iterations: ${iterationsCounter.value} from $iterations")
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        if (lines.isEmpty()) {
            Pair(
                Offset(
                    random.nextDouble(0.0, width.toDouble()).toFloat(),
                    random.nextDouble(0.0, height.toDouble()).toFloat()
                ),
                Offset(
                    random.nextDouble(0.0, width.toDouble()).toFloat(),
                    random.nextDouble(0.0, height.toDouble()).toFloat()
                )
            )
        } else {
            Pair(
                lines.last().second,
                Offset(
                    random.nextDouble(until = width.toDouble()).toFloat(),
                    random.nextDouble(until = height.toDouble()).toFloat()
                )
            )
        }.let { coordinates -> lines.add(coordinates) }

        lines.forEach { (start, end) ->
            drawLine(
                color = Color.Black,
                start = start,
                end = end
            )
        }
    }

    LaunchedEffect(Unit) {
        repeat(iterations) {
            iterationsCounter.value = it
            delay(1000L)
        }
    }
}