import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import malunki.First
import malunki.MalunkiProvider
import ui.MainScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Malevich for Desktop") {
        MainScreen(
            modifier = Modifier.fillMaxSize(),
            malunki = MalunkiProvider().getMalunki()
        )
    }
}
