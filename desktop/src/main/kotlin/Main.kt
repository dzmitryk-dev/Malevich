import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import malunki.First

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Compose for Desktop") {
        val (width, height) = with(window.size) { width to height }
        First(width, height)
    }
}
