import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.AppNavigation

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Malevich for Desktop") {
        AppNavigation()
    }
}
