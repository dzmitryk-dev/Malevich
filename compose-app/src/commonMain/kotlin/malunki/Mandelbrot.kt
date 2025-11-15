package malunki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private sealed class ImageState {
    object Loading : ImageState()
    data class Ready(val pixelData: Array<IntArray>) : ImageState()
}

@Composable
fun Mandelbrot(modifier: Modifier = Modifier) {
    var cachedWidth by remember { mutableStateOf(0) }
    var cachedHeight by remember { mutableStateOf(0) }

    val imageState by produceState<ImageState>(initialValue = ImageState.Loading, cachedWidth, cachedHeight) {
        if (cachedWidth > 0 && cachedHeight > 0) {
            value = ImageState.Ready(
                pixelData = computeMandelbrot(cachedWidth, cachedHeight)
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .onSizeChanged { intSize ->
                cachedWidth = intSize.width
                cachedHeight = intSize.height
            }
    ) {
        when (imageState) {
            is ImageState.Loading -> {
                // Show loading indicator
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Blue,
                    strokeWidth = 4.dp
                )
            }

            is ImageState.Ready -> {
                val pixelData = (imageState as ImageState.Ready).pixelData

                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawMandelbrotData(pixelData)
                }
            }
        }
    }
}

private suspend fun computeMandelbrot(width: Int, height: Int): Array<IntArray> {
    return withContext(Dispatchers.Default) {
        val data = Array(height) { IntArray(width) }

        val xscaleFactor = 3.5f / width.toFloat()
        val yscaleFactor = 2.5f / height.toFloat()

        for (py in 0 until height) {
            for (px in 0 until width) {
                val iteration = calculateIteration(px, py, xscaleFactor, yscaleFactor)
                data[py][px] = iteration
            }
        }

        data
    }
}

private fun DrawScope.drawMandelbrotData(pixelData: Array<IntArray>) {
    val height = pixelData.size
    val width = if (height > 0) pixelData[0].size else 0

    for (py in 0 until height) {
        for (px in 0 until width) {
            val iteration = pixelData[py][px]
            val color = colorFromIteration(iteration)
            drawRect(
                color = color,
                topLeft = Offset(px.toFloat(), py.toFloat()),
                size = Size(1.0f, 1.0f)
            )
        }
    }
}

private fun calculateIteration(px: Int, py: Int, xscaleFactor: Float, yscaleFactor: Float): Int {
    val x0 = (xscaleFactor * px - 2.5).toFloat()
    val y0 = (yscaleFactor * py - 1.25).toFloat()
    var x = 0.0f
    var y = 0.0f
    var iteration = 0
    val maxIteration = 256

    while (iteration < maxIteration) {
        val xx = x * x
        val yy = y * y
        if (xx + yy > 4.0) break

        val xtemp = xx - yy + x0
        y = 2 * x * y + y0
        x = xtemp
        iteration++
    }

    return iteration
}

private fun colorFromIteration(iteration: Int): Color {
    if (iteration == 256) {
        return Color.Black // Points in set are black
    }

    // Create vibrant colors based on escape time
    val normalized = iteration / 256.0f

    // Use multiple color bands for more vibrant colors
    val hue = (iteration * 360 / 30) % 360 // More variation
    val saturation = 0.9f
    val brightness = 0.3f + (normalized * 0.7f) // Brighter colors

    // HSV to RGB conversion
    val h = hue / 60.0f
    val c = brightness * saturation
    val x = c * (1 - kotlin.math.abs((h % 2) - 1))
    val m = brightness - c

    val (r, g, b) = when {
        h < 1 -> Triple(c, x, 0f)
        h < 2 -> Triple(x, c, 0f)
        h < 3 -> Triple(0f, c, x)
        h < 4 -> Triple(0f, x, c)
        h < 5 -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }

    return Color(
        red = (r + m).coerceIn(0f, 1f),
        green = (g + m).coerceIn(0f, 1f),
        blue = (b + m).coerceIn(0f, 1f),
        alpha = 1.0f
    )
}
