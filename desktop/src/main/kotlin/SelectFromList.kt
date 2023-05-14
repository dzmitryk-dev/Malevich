import androidx.compose.foundation.layout.Column
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import mu.KotlinLogging
import screens.SelectFromList

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Malevich for Desktop") {
        val (width, height) = with(window.size) { width to height }
        val logger = KotlinLogging.logger("Malevich App")
        logger.debug { "Windows size is: width=$width, height=$height " }
        SelectFromList(width, height)
    }
}