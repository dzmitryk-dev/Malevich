package malunki

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

internal data class Malunek(
    val title: String = "",
    val impl: @Composable (Modifier) -> Unit,
)

internal interface MalunkiProvider {
    fun getMalunki(): List<Malunek> = listOf(
        Malunek(
            title = "First",
            impl = ::First
        ),
        Malunek(
            title = "Rotating Line",
            impl = ::RotatingLine
        ),
        Malunek(
            title = "Rotating Line 2",
            impl = ::RotatingLine2
        ),
        Malunek(
            title = "Mandelbrot",
            impl = ::Mandelbrot
        ),
        Malunek(
            title = "White Noise",
            impl = ::WhiteNoise
        ),
    )
}

internal fun MalunkiProvider() = object : MalunkiProvider { }
