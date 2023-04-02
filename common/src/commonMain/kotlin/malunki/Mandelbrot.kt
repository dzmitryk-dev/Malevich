package malunki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*


@Composable
fun Mandelbrot(width: Int, height: Int) {

    var imageBitmap by remember { mutableStateOf(ImageBitmap(width, height)) }

    Box(modifier = Modifier.fillMaxSize()) {
        // CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawImage(imageBitmap)
        }
    }

    LaunchedEffect(Unit) {
        imageBitmap = calculateMaldebrotNaive(width, height)
    }
}

/**
 * Naive implementation of Mandelrot fractal
 */
private fun calculateMaldebrotNaive(width: Int, height: Int): ImageBitmap {
    /**
     * Pseudo-code from wikipedia: https://en.wikipedia.org/wiki/Mandelbrot_set
     *
     * for each pixel (Px, Py) on the screen do
     *     x0 := scaled x coordinate of pixel (scaled to lie in the Mandelbrot X scale (-2.00, 0.47))
     *     y0 := scaled y coordinate of pixel (scaled to lie in the Mandelbrot Y scale (-1.12, 1.12))
     *     x := 0.0
     *     y := 0.0
     *     iteration := 0
     *     max_iteration := 1000
     *     while (x*x + y*y ≤ 2*2 AND iteration < max_iteration) do
     *         xtemp := x*x - y*y + x0
     *         y := 2*x*y + y0
     *         x := xtemp
     *         iteration := iteration + 1
     *
     *     color := palette[iteration]
     *     plot(Px, Py, color)
     */
    val colorMapping = Array(0x100) { mutableListOf<Offset>() }
    val xscaleFactor = 2.5f / width.toFloat()
    val yscaleFactor = 2.5f / height.toFloat()

    for (px in 0..width) {
        for (py in 0..height) {
            val color = calculateColor(px, py, xscaleFactor, yscaleFactor)
            colorMapping[color].add(Offset(px.toFloat(), py.toFloat()))
        }
    }

    return ImageBitmap(width, height).also { imageBitmap ->
        Canvas(imageBitmap).also { canvas ->
            val paint = Paint().apply {
                color = Color.Black
                strokeWidth = 1.0f
                alpha = 1.0f
                isAntiAlias = false
            }
            colorMapping.forEachIndexed { index, points ->
                paint.alpha = 1.0f/0xFF * index
                canvas.drawPoints(pointMode = PointMode.Points, points, paint)
            }
        }
    }
}

private fun calculateColor(px: Int, py: Int, xscaleFactor: Float, yscaleFactor: Float): Int {
    val x0 = (xscaleFactor * px + -2.0).toFloat()
    val y0 = (yscaleFactor * py + -1.2).toFloat()
    var x = 0.0f
    var y = 0.0f
    var iteration = 0
    val maxIteration = 255
    while (x * x + y * y <= 2 * 2 && iteration < maxIteration) {
        val xtemp = x * x - y * y + x0
        y = 2 * x * y + y0
        x = xtemp
        iteration++
    }
    return iteration
}
