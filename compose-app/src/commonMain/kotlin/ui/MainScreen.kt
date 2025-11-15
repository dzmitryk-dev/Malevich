package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
    onMalunekClicked: (Malunek) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Malevich") }
            )
        },
        content = { innerPadding ->
            Content(
                modifier = Modifier.fillMaxSize(),
                innerPadding = innerPadding,
                malunki = malunki,
                onMalunekClicked = onMalunekClicked
            )
        }
    )
}

@Composable
private fun Content(
    modifier: Modifier,
    innerPadding: PaddingValues,
    malunki: List<Malunek>,
    onMalunekClicked: (Malunek) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 240.dp),
        modifier = modifier.padding(innerPadding).background(color = Color.Red)
    ) {
        items(malunki) { malunek -> GridCell(malunek, onMalunekClicked) }
    }
}

@Composable
private fun GridCell(malunek: Malunek, onMalunekClicked: (Malunek) -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(240.dp)
            .background(color = Color.Yellow)
            .clickable { onMalunekClicked(malunek) }
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
