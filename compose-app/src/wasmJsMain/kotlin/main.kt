import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import malunki.First
import malunki.RotatingLine2

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        Box(modifier = Modifier.fillMaxSize()) {
            First()
//            RotatingLine()
//            RotatingLine2()
        }
    }
}