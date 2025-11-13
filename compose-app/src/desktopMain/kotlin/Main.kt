import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.AppNavigation

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Malevich for Desktop") {
        AppNavigation()
    }
}
