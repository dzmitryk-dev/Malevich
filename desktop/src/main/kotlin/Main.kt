import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import malunki.*
import mu.KotlinLogging
import kotlin.math.roundToInt

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Malevich for Desktop") {
        val (width, height) = with(window.size) { width to height }
        val logger = KotlinLogging.logger("Malevich App")
        logger.debug { "Windows size is: width=$width, height=$height " }
        println("Width = $width, height = $height")
        Box(modifier = Modifier.fillMaxSize()) {
//            First()
//            RotatingLine()
            RotatingLine2()
        }


//        Mandelbrot(width, height)
//        MandelbrotChatGpt(width, height)
//        MandelbrotChatGptOptimized(width, height)
//        WhiteNoise(width, height)
//        WhiteNoiseV2(width, height)
//        ColorfullNoise(width, height)
//        Matrix(width, height)
    }
}
