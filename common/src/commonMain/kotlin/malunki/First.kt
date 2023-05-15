package malunki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun First(width: Int, height: Int) {
    val random = Random(System.currentTimeMillis())
    val iterations = random.nextInt(10..100_000)
    val iterationsCounter = remember { mutableStateOf(0) }
    val lines = remember { mutableStateListOf<Pair<Offset, Offset>>() }

    Column(Modifier.fillMaxSize()) {
        Text("Iterations: ${iterationsCounter.value} from $iterations")
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        lines.forEach {(start, end) ->
            drawLine(
                color = Color.Black,
                start = start,
                end = end
            )
        }
    }

    LaunchedEffect(Unit) {
        repeat(iterations) {
            if (lines.isEmpty()) {
                Pair(
                    Offset(
                        random.nextInt(0, width).toFloat(),
                        random.nextInt(0, height).toFloat()
                    ),
                    Offset(
                        random.nextInt(0, width).toFloat(),
                        random.nextInt(0, height).toFloat()
                    )
                )
            } else {
                Pair(
                    lines.last().second,
                    Offset(
                        random.nextInt(0, width).toFloat(),
                        random.nextInt(0, height).toFloat()
                    )
                )
            }.let { coordinates ->  lines.add(coordinates) }

            iterationsCounter.value = it
            delay(100L)
        }
    }
}