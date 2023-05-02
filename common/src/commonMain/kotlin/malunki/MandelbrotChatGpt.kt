package malunki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlinx.coroutines.delay

@Composable
fun MandelbrotChatGpt(width: Int, height: Int) {
    var imageBitmap by remember { mutableStateOf(ImageBitmap(width, height)) }
    var frame by remember { mutableStateOf(Pair(Offset.Zero, Offset(x = width.toFloat(), y = height.toFloat()))) }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawImage(imageBitmap)
        drawRect(
            Color.Green,
            topLeft = frame.first,
            size = Size(width = frame.second.x - frame.first.x, height = frame.second.y - frame.first.y),
            style = Stroke(1.0f)
        )
    }

    LaunchedEffect(Unit) {
        var xMin = -2.0
        var xMax = 1.0
        var yMin = -1.5
        var yMax = 1.5

        imageBitmap = fromPixelMap(width, height,
            generateMandelbrotImage(width, height, xMin, xMax, yMin, yMax)
        )
        delay(1000L)

        val numImages = 100

        // генерируем указанное количество изображений
        repeat(numImages) {
            // выбираем случайную область на границе множества Мандельброта
            val xRange = xMax - xMin
            val yRange = yMax - yMin
            val xCenter = xMin + xRange * Math.random()
            val yCenter = yMin + yRange * Math.random()
            val sizeFactor = 0.1 + 0.4 * Math.random()
            val xSize = xRange * sizeFactor
            val ySize = yRange * sizeFactor

            val newxMin = xCenter - xSize / 2
            val newxMax = xCenter + xSize / 2
            val newyMin = yCenter - ySize / 2
            val newyMax = yCenter + ySize / 2

            val xScale = width.toDouble() / xRange
            val yScale = height.toDouble() / yRange

            frame = Pair(
                Offset(
                    x = ((newxMin - xMin) * xScale).toFloat(),
                    y = ((yMax - newyMax) * yScale).toFloat()
                ),
                Offset(
                    x = ((newxMax - xMin) * xScale).toFloat(),
                    y = ((yMax - newyMin) * yScale).toFloat()
                )
            )
            delay(1000L)

            xMin = newxMin
            xMax = newxMax
            yMin = newyMin
            yMax = newyMax

            imageBitmap = fromPixelMap(width, height,
                generateMandelbrotImage(width, height, xMin, xMax, yMin, yMax))
            frame = Pair(Offset.Zero, Offset(x = width.toFloat(), y = height.toFloat()))
            delay(1000L)
        }
    }
}

private fun generateMandelbrotImage(
    width: Int,
    height: Int,
    xMin: Double,
    xMax: Double,
    yMin: Double,
    yMax: Double
): Array<List<Offset>> {
    val maxIterations = 1000 // максимальное количество итераций
    val image = Array(0x100) { mutableListOf<Offset>() } // создаем пустой массив списков

    for (i in 0 until width) {
        for (j in 0 until height) {
            val x0 = xMin + i * (xMax - xMin) / width // вычисляем реальную координату x
            val y0 = yMin + j * (yMax - yMin) / height // вычисляем реальную координату y

            var x = 0.0
            var y = 0.0
            var iteration = 0

            while (x * x + y * y <= 4 && iteration < maxIterations) {
                val xTemp = x * x - y * y + x0 // вычисляем новое значение x
                y = 2 * x * y + y0 // вычисляем новое значение y
                x = xTemp
                iteration++
            }

            val gray = (255 * iteration / maxIterations) // вычисляем оттенок серого
            image[gray].add(Offset(i.toFloat(), j.toFloat())) // добавляем координаты точки в список
        }
    }

    return image.map { it.toList() }.toTypedArray()
}

private fun fromPixelMap(width: Int, height: Int, colorMapping: Array<List<Offset>>): ImageBitmap {
    return ImageBitmap(width, height).also { imageBitmap ->
        Canvas(imageBitmap).also { canvas ->
            val paint = Paint().apply {
                color = Color.Black
                strokeWidth = 1.0f
                alpha = 1.0f
                isAntiAlias = false
            }
            colorMapping.forEachIndexed { index, points ->
                paint.alpha = 1.0f / 0xFF * index
                canvas.drawPoints(pointMode = PointMode.Points, points, paint)
            }
        }
    }
}

