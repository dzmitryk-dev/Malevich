package screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.onClick
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import malunki.*
import malunki.MandelbrotChatGpt

private enum class Screen {
    LIST, FIRST, MANDELBROT, MANDELBROT_CHAT_GPT, ROTATING_LINE_1, ROTATING_LINE_2,
}

@Composable
fun SelectFromList(width: Int, height: Int) {
    val screen = remember { mutableStateOf(Screen.LIST) }

    val onBackPressed = { screen.value = Screen.LIST }

    when(screen.value) {
        Screen.LIST -> { List(screen) }
        Screen.FIRST -> ToolbarWrapper(content = { First(width, height) }, onBackPressed = onBackPressed)
        Screen.MANDELBROT -> ToolbarWrapper(content =  { Mandelbrot(width, height) }, onBackPressed = onBackPressed)
        Screen.MANDELBROT_CHAT_GPT -> ToolbarWrapper(content = { MandelbrotChatGpt(width, height) }, onBackPressed = onBackPressed)
        Screen.ROTATING_LINE_1 -> ToolbarWrapper(content = { RotatingLine(width, height) }, onBackPressed = onBackPressed)
        Screen.ROTATING_LINE_2 -> ToolbarWrapper(content = { RotatingLine2(width, height) }, onBackPressed = onBackPressed)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun List(screen: MutableState<Screen>) {
    Column(modifier = Modifier) {
        Text(
            modifier = Modifier.onClick(onClick = { screen.value = Screen.FIRST }),
            text = "First"
        )
        Text(
            modifier = Modifier.onClick(onClick = { screen.value = Screen.MANDELBROT }),
            text = "Mandelbrot"
        )
        Text(
            modifier = Modifier.onClick(onClick = {screen.value = Screen.MANDELBROT_CHAT_GPT }),
            text = "Mandelbrot animation"
        )
        Text(
            modifier = Modifier.onClick(onClick = { screen.value = Screen.ROTATING_LINE_1 }),
            text = "Rotating Line"
        )
        Text(
            modifier = Modifier.onClick(onClick = { screen.value = Screen.ROTATING_LINE_2 }),
            text = "Rotating Line 2"
        )
    }
}

@Composable
private fun ToolbarWrapper(
    content: @Composable () -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar { TopBar(onBackPressed) } },
        content = { content() }
    )
}

@Composable
internal expect fun TopBar(onBackPressed: () -> Unit)