package malunki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun First(modifier: Modifier = Modifier) {
    val random = Random(Clock.System.now().toEpochMilliseconds())
    val iterations = remember { random.nextInt(10..100_000) }
    val iterationsCounter = remember { mutableStateOf(0) }

    val lines = remember { mutableListOf<Pair<Offset, Offset>>() }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = modifier.fillMaxSize().background(color = Color.Black)) {
            val width = size.width
            val height = size.height

            if (width > 0.0f && height > 0.0f) {
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
                        color = Color.White,
                        start = start,
                        end = end
                    )
                }
            }
        }
        Text("Iterations: ${iterationsCounter.value} from $iterations")
    }

    LaunchedEffect(Unit) {
        repeat(iterations) {
            iterationsCounter.value = it
            delay(1000L)
        }
    }
}