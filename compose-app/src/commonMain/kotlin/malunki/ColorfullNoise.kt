//package malunki
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.*
//import androidx.compose.ui.text.ExperimentalTextApi
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.drawText
//import androidx.compose.ui.text.rememberTextMeasurer
//import kotlinx.coroutines.delay
//import kotlin.random.Random
//import kotlin.time.DurationUnit
//import kotlin.time.toDuration
//
//@OptIn(ExperimentalTextApi::class)
//@Composable
//fun ColorfullNoise(width: Int, height: Int) {
//    val debugInfo = remember { mutableStateOf(Pair(0L, 0)) }
//    val textMeasurer = rememberTextMeasurer()
//
//    val imageBitmap = remember { mutableStateOf(ImageBitmap(width, height, ImageBitmapConfig.Rgb565)) }
//
//    Canvas(modifier = Modifier.fillMaxSize()) {
//        drawImage(imageBitmap.value)
//        drawText(
//            textMeasurer = textMeasurer,
//            text = "Image generation time = ${debugInfo.value.first} ms for ${debugInfo.value.second} dots",
//            style = TextStyle.Default.copy(background = Color.Black, color = Color.White)
//        )
//    }
//
//    LaunchedEffect(Unit) {
//        val random = Random(System.currentTimeMillis())
//        val maxSize = width * height
//
//        val paint = Paint().apply {
//            color = Color.Black
//            strokeWidth = 1.0f
//            alpha = 1.0f
//            isAntiAlias = false
//            style = PaintingStyle.Fill
//        }
//
//        while (true) {
//            val startPoint = System.nanoTime()
//            val size = random.nextInt(maxSize)
//            Canvas(imageBitmap.value).also { canvas ->
//                canvas.drawPoints(
//                    pointMode = PointMode.Points,
//                    points = List(size) {
//                        Offset(
//                            x = random.nextInt(until = width).toFloat(),
//                            y = random.nextInt(until = height).toFloat()
//                        )
//                    },
//                    paint = paint.apply {
//                        color = Color(
//                            red = random.nextInt(until = 0xFF),
//                            green = random.nextInt(until = 0xFF),
//                            blue = random.nextInt(until = 0xFF),
//                            alpha = 0xFF
//                        )
//                    }
//                )
//            }
//            debugInfo.value = Pair(
//                (System.nanoTime() - startPoint).toDuration(DurationUnit.NANOSECONDS).toLong(DurationUnit.MILLISECONDS),
//                size
//            )
//            delay(250.toDuration(DurationUnit.MILLISECONDS))
//        }
//    }
//}