package ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import malunki.Malunek
import malunki.RotatingLine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MalunekScreen(
    modifier: Modifier = Modifier,
    malunek: Malunek,
) {
    Scaffold(
        modifier = modifier,
        topBar = { /* Add your top bar here if needed */ },
        content = { innerPadding ->
            // Content of the main screen goes here
            // You can call other composable functions to build your UI
            // For example: RotatingLine(), Matrix(), etc.
            // Example: RotatingLine(innerPadding = innerPadding)
            Content(
                modifier = Modifier.fillMaxSize(),
                innerPadding = innerPadding,
                malunek = malunek
            )
        }
    )
}

@Composable
private fun Content(
    modifier: Modifier,
    innerPadding: PaddingValues,
    malunek: Malunek,
) {
    malunek.impl(modifier.padding(innerPadding))
}

@Preview
@Composable
private fun PreviewMalunekScreen() {
    MalunekScreen(
        modifier = Modifier.fillMaxSize(),
        malunek = Malunek(
            title = "RotatingLine",
            impl = ::RotatingLine
        ),
    )
}

@Preview
@Composable
private fun PreviewContent() {
    Content(
        modifier = Modifier.fillMaxSize(),
        innerPadding = PaddingValues(0.dp),
        malunek = Malunek(
            title = "RotatingLine",
            impl = ::RotatingLine
        )
    )
}