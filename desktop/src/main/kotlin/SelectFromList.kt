import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import malunki.*
import mu.KotlinLogging
import ui.ListSelector
import ui.Malunek

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Malevich for Desktop") {
        val (width, height) = with(window.size) { width to height }
        val logger = KotlinLogging.logger("Malevich App")
        logger.debug { "Windows size is: width=$width, height=$height " }

        Row(modifier = Modifier) {
            val areaWidth = remember { mutableStateOf(width) }
            val malunek = remember { mutableStateOf(Malunek.FIRST) }
            ListSelector(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    areaWidth.value = width - coordinates.size.width
                },
                initial = Malunek.FIRST,
                onItemSelected = { m -> malunek.value = m }
            )
            when(malunek.value) {
                Malunek.FIRST -> First(areaWidth.value, height)
                Malunek.MANDELBROT -> Mandelbrot(areaWidth.value, height)
                Malunek.MANDELBROT_CHAT_GPT -> MandelbrotChatGpt(areaWidth.value, height)
                Malunek.ROTATING_LINE_1 -> RotatingLine(areaWidth.value, height)
                Malunek.ROTATING_LINE_2 -> RotatingLine2(areaWidth.value, height)
                Malunek.MANDELBROT_CHAT_GPT_OPTIMIZED -> MandelbrotChatGptOptimized(areaWidth.value, height)
                Malunek.WHITE_NOISE -> WhiteNoise(areaWidth.value, height)
                Malunek.WHITE_NOISE_V2 -> WhiteNoiseV2(areaWidth.value, height)
            }
        }

    }
}