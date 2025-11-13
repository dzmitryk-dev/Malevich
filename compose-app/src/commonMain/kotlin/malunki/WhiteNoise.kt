@file:OptIn(ExperimentalTime::class)

package malunki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun WhiteNoise(modifier: Modifier = Modifier) {
    var canvasWidth by remember { mutableStateOf(0) }
    var canvasHeight by remember { mutableStateOf(0) }
    
    val randomOffsets by produceState(
        initialValue = emptyList(),
        canvasWidth,
        canvasHeight
    ) {
        if (canvasWidth > 0 && canvasHeight > 0) {
            while (true) {
                value = generateRandomOffsets(canvasWidth, canvasHeight)
                delay(500) // Update every 500ms
            }
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .onSizeChanged { intSize ->
                canvasWidth = intSize.width
                canvasHeight = intSize.height
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw random points
            randomOffsets.forEach { offset ->
                drawCircle(
                    color = Color.Black,
                    radius = 1.0f,
                    center = offset
                )
            }
        }
    }
}

private suspend fun generateRandomOffsets(width: Int, height: Int): List<Offset> {
    return withContext(Dispatchers.Default) {
        val random = Random(Clock.System.now().toEpochMilliseconds())
        val numPoints = random.nextInt(100, 5000)
        
        List(numPoints) {
            Offset(
                x = random.nextInt(width).toFloat(),
                y = random.nextInt(height).toFloat()
            )
        }
    }
}
