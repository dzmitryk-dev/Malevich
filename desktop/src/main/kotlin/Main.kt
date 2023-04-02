import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import malunki.First
import malunki.RotatingLine
import malunki.RotatingLine2
import mu.KotlinLogging

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Compose for Desktop") {
        val (width, height) = with(window.size) { width to height }
        val logger = KotlinLogging.logger("Malevich App")
        logger.debug { "Windows size is: width=$width, height=$height " }
//        First(width, height)
        RotatingLine2(width, height)
    }
}
