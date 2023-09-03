import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import malunki.*
import mu.KotlinLogging

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Malevich for Desktop") {
        val bounds = window.bounds
        val h = bounds.height
        val w = bounds.width
        val (width, height) = with(window.size) { width to height }
        val logger = KotlinLogging.logger("Malevich App")
        logger.debug { "Windows size is: width=$width, height=$height " }
        assert(width > 0) { "width should be > 0" }
        assert(height > 0) { "height should be > 0" }
        First(width, height)
//        RotatingLine(width, height)
//        RotatingLine2(width, height)
//        Mandelbrot(width, height)
//        MandelbrotChatGpt(width, height)
//        MandelbrotChatGptOptimized(width, height)
//        WhiteNoise(width, height)
//        WhiteNoiseV2(width, height)
//        ColorfullNoise(width, height)
//        Matrix(width, height)
    }
}
