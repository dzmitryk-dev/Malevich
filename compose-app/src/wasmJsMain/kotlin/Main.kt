import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import malunki.First
import malunki.MalunkiProvider
import ui.MainScreen

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        MainScreen(
            modifier = Modifier.fillMaxSize(),
            malunki = MalunkiProvider().getMalunki()
        )
    }
}
