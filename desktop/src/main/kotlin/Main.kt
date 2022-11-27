import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.random.nextInt

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Compose for Desktop") {
        val random = Random(System.currentTimeMillis())
        val iterations = random.nextInt(10..100_000)
        val iterationsCounter = remember { mutableStateOf(0) }
        val lines = remember { mutableStateListOf<Pair<Offset, Offset>>() }

        MaterialTheme {
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
        }

        LaunchedEffect(Unit) {
            repeat(iterations) {
                if (lines.isEmpty()) {
                    Pair(
                        Offset(
                            random.nextInt(0, window.size.width).toFloat(),
                            random.nextInt(0, window.size.height).toFloat()
                        ),
                        Offset(
                            random.nextInt(0, window.size.width).toFloat(),
                            random.nextInt(0, window.size.height).toFloat()
                        )
                    )
                } else {
                    Pair(
                        lines.last().second,
                        Offset(
                            random.nextInt(0, window.size.width).toFloat(),
                            random.nextInt(0, window.size.height).toFloat()
                        )
                    )
                }.let { coordinates ->  lines.add(coordinates) }

                iterationsCounter.value = it
                delay(100L)
            }

        }
//        val count = remember { mutableStateOf(0) }
//        MaterialTheme {
//            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
//                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
//                    onClick = {
//                        count.value++
//                    }) {
//                    Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
//                }
//                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
//                    onClick = {
//                        count.value = 0
//                    }) {
//                    Text("Reset")
//                }
//            }
//        }
    }
}
