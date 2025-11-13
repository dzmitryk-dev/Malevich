package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import malunki.Malunek
import malunki.MalunkiProvider
import malunki.RotatingLine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    malunki: List<Malunek> = emptyList(),
) {
    // This is the main screen of the application.
    // You can add your UI components here.
    // For example, you can call other composable functions to build your UI.
    // Example: RotatingLine(), Matrix(), etc.
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
                malunki = malunki
            )
        }
    )
}

@Composable
private fun Content(
    modifier: Modifier,
    innerPadding: PaddingValues,
    malunki: List<Malunek>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 240.dp),
        modifier = modifier.padding(innerPadding).background(color = Color.Red)
    ) {
        items(malunki) { malunek -> GridCell(malunek) }
    }
}

@Composable
private fun GridCell(malunek: Malunek) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(240.dp)
            .background(color = Color.Yellow)
    ) {
        // Draw the preview using Malunek's compose function
        malunek.impl(Modifier.weight(weight = 1.0f, fill = true).background(color = Color.Green))
        // Display the title of the Malunek
        Text(
            text = malunek.title
        )
    }
}

@Preview
@Composable
private fun PreviewMainScreen() {
    MainScreen(
        modifier = Modifier.fillMaxSize(),
        malunki = MalunkiProvider().getMalunki(),
    )
}

@Preview
@Composable
private fun PreviewGridCell() {
    GridCell(
        malunek = Malunek(
            title = "RotatingLine",
            impl = ::RotatingLine
        )
    )
}
