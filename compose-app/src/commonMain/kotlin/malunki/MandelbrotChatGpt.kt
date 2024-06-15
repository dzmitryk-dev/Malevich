//package malunki
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.*
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.text.*
//import kotlinx.coroutines.delay
//import java.time.Duration
//import kotlin.math.sqrt
//import kotlin.random.Random
//
///**
// * Experiment: Realization of zooming animation for Mandelbrot set implemented with help of CahtGpt
// */
//@OptIn(ExperimentalTextApi::class)
//@Composable
//fun MandelbrotChatGpt(width: Int, height: Int) {
//    val debugDraw = false
//    var imageBitmap by remember { mutableStateOf(ImageBitmap(width, height)) }
//    var frame by remember { mutableStateOf(Pair(Offset.Zero, Offset(x = width.toFloat(), y = height.toFloat()))) }
//    var center by remember { mutableStateOf(Offset(x = width.toFloat()/2, y = height.toFloat()/2)) }
//    var duration by remember { mutableStateOf(0L) }
//    var textMeasurer = rememberTextMeasurer()
//
//    Canvas(modifier = Modifier.fillMaxSize()) {
//        drawImage(imageBitmap)
//        if (debugDraw) {
//            drawRect(
//                Color.Green,
//                topLeft = frame.first,
//                size = Size(width = frame.second.x - frame.first.x, height = frame.second.y - frame.first.y),
//                style = Stroke(1.0f)
//            )
//        }
//        drawCircle(Color.Red, radius = 1.0f, center = center)
//        drawText(textMeasurer = textMeasurer, text = "Image generation time = $duration ms", style = TextStyle.Default.copy(background = Color.Black, color = Color.White))
//    }
//
//    LaunchedEffect(Unit) {
//        var xMin = -2.0
//        var xMax = 1.0
//        var yMin = -1.5
//        var yMax = 1.5
//
//        imageBitmap = fromPixelMap(width, height,
//            generateMandelbrotImage(width, height, xMin, xMax, yMin, yMax)
//        )
//        delay(100L)
//
//        val (xCenter, yCenter) = findBoundaryOnLine(
//            maxIterations = 1000,
//            xmin = xMin,
//            xmax = xMax,
//            ymin = yMin,
//            ymax = yMax
//        )
//        val sizeFactor = 0.9 //0.1 + 0.8 * Math.random()
//
//        val numIterations = 500
//
//        // генерируем указанное количество изображений
//        repeat(numIterations) {
//            center = Offset(
//                x = ((xCenter - xMin) * width / (xMax - xMin)).toFloat(),
//                y = ((yCenter - yMin) * height / (yMax - yMin)).toFloat()
//            )
//            delay(100L)
//
//            val xRange = xMax - xMin
//            val yRange = yMax - yMin
//            val xSize = xRange * sizeFactor
//            val ySize = yRange * sizeFactor
//
//            val newxMin = xCenter - xSize / 2
//            val newxMax = xCenter + xSize / 2
//            val newyMin = yCenter - ySize / 2
//            val newyMax = yCenter + ySize / 2
//
//            val xScale = width.toDouble() / xRange
//            val yScale = height.toDouble() / yRange
//
//            frame = Pair(
//                Offset(
//                    x = ((newxMin - xMin) * xScale).toFloat(),
//                    y = ((yMax - newyMax) * yScale).toFloat()
//                ),
//                Offset(
//                    x = ((newxMax - xMin) * xScale).toFloat(),
//                    y = ((yMax - newyMin) * yScale).toFloat()
//                )
//            )
//            delay(10L)
//
//            xMin = newxMin
//            xMax = newxMax
//            yMin = newyMin
//            yMax = newyMax
//
//            val start = System.nanoTime()
//            imageBitmap = fromPixelMap(width, height,
//                generateMandelbrotImage(width, height, xMin, xMax, yMin, yMax))
//            frame = Pair(Offset.Zero, Offset(x = width.toFloat(), y = height.toFloat()))
//            duration = Duration.ofNanos(System.nanoTime() - start).toMillis()
//            delay(10L)
//        }
//    }
//}
//
//private fun generateMandelbrotImage(
//    width: Int,
//    height: Int,
//    xMin: Double,
//    xMax: Double,
//    yMin: Double,
//    yMax: Double
//): Array<List<Offset>> {
//    val maxIterations = 1000 // максимальное количество итераций
//    val image = Array(0x100) { mutableListOf<Offset>() } // создаем пустой массив списков
//
//    for (i in 0 until width) {
//        for (j in 0 until height) {
//            val x0 = xMin + i * (xMax - xMin) / width // вычисляем реальную координату x
//            val y0 = yMin + j * (yMax - yMin) / height // вычисляем реальную координату y
//
//            var x = 0.0
//            var y = 0.0
//            var iteration = 0
//
//            while (x * x + y * y <= 4 && iteration < maxIterations) {
//                val xTemp = x * x - y * y + x0 // вычисляем новое значение x
//                y = 2 * x * y + y0 // вычисляем новое значение y
//                x = xTemp
//                iteration++
//            }
//
//            val gray = (255 * iteration / maxIterations) // вычисляем оттенок серого
//            image[gray].add(Offset(i.toFloat(), j.toFloat())) // добавляем координаты точки в список
//        }
//    }
//
//    return image.map { it.toList() }.toTypedArray()
//}
//
//private fun fromPixelMap(width: Int, height: Int, colorMapping: Array<List<Offset>>): ImageBitmap {
//    return ImageBitmap(width, height).also { imageBitmap ->
//        Canvas(imageBitmap).also { canvas ->
//            val paint = Paint().apply {
//                color = Color.Black
//                strokeWidth = 1.0f
//                alpha = 1.0f
//                isAntiAlias = false
//            }
//            colorMapping.forEachIndexed { index, points ->
//                paint.alpha = 1.0f / 0xFF * index
//                canvas.drawPoints(pointMode = PointMode.Points, points, paint)
//            }
//        }
//    }
//}
//
//private fun findBoundaryOnLine(
//    maxIterations: Int = 1000,
//    threshold: Double = 0.000001,
//    xmin: Double = -2.0,
//    xmax: Double = 1.0,
//    ymin: Double = -1.5,
//    ymax: Double = 1.5
//): Pair<Double, Double> {
//    fun isMandelbrotPointOnBoundary(
//        x: Double,
//        y: Double,
//        maxIterations: Int
//    ): Boolean {
//        var real = 0.0
//        var imag = 0.0
//        for (i in 0 until maxIterations) {
//            val tempReal = real * real - imag * imag + x
//            val tempImag = 2 * real * imag + y
//            real = tempReal
//            imag = tempImag
//            if (real * real + imag * imag >= 4) {
//                return false // точка не находится на границе множества Мандельброта
//            }
//        }
//        return true // точка находится на границе множества
//    }
//
//    // Генерируем две точки на плоскости
//    var (x1, y1) = if (Random.nextBoolean()) {
//        if (Random.nextBoolean()) {
//            xmin
//        } else {
//            xmax
//        } to Random.nextDouble(ymin, ymax)
//    } else {
//        Random.nextDouble(xmin, xmax) to if (Random.nextBoolean()) {
//            ymin
//        } else {
//            ymax
//        }
//    }
//
//    var x2: Double = 0.0
//    var y2: Double = 0.0
//
//    var iteration = 0
//    do {
//        // Строим отрезок между двумя точками
//        val dx = x2 - x1
//        val dy = y2 - y1
//
//        // Вычисляем координаты точки на отрезке с заданным параметром t
//        val x = x1 + dx / 2
//        val y = y1 + dy / 2
//
//        val isOnBoundary = isMandelbrotPointOnBoundary(x, y, maxIterations)
//        // Изменяем границы поиска
//        if (isOnBoundary) {
//            x2 = x
//            y2 = y
//        } else {
//            x1 = x
//            y1 = y
//        }
//        iteration++
//    } while (sqrt(dx*dx + dy*dy) > threshold || iteration < maxIterations)
//    return Pair(x1 + (x2 - x1) / 2, y1 + (y2 - y1) /2)
//}
//
//
//
//
//
