package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
    onMalunekClick: (Malunek) -> Unit = {},
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
                onMalunekClick = onMalunekClick
            )
        }
    )
}

@Composable
private fun Content(
    innerPadding: PaddingValues,
    malunki: List<Malunek>,
    modifier: Modifier = Modifier,
    onMalunekClick: (Malunek) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 240.dp),
        modifier = modifier.padding(innerPadding).background(color = Color.Red)
    ) {
        items(malunki) { malunek -> GridCell(malunek, onMalunekClick) }
    }
}

@Composable
private fun GridCell(malunek: Malunek, onMalunekClick: (Malunek) -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(240.dp)
            .background(color = Color.Yellow)
            .clickable { onMalunekClick(malunek) }
    ) {
        // Draw the preview using Malunek's compose function
        malunek.impl(Modifier.fillMaxSize().background(color = Color.Green))
        // Display the title of the Malunek
        Text(
            text = malunek.title
        )
    }
}

@Preview
@Composable
private fun PreviewMainScreenPreview() {
    MainScreen(
        modifier = Modifier.fillMaxSize(),
        malunki = MalunkiProvider().getMalunki(),
    )
}

@Preview
@Composable
private fun PreviewGridCellPreview() {
    GridCell(
        malunek = Malunek(
            title = "RotatingLine",
            impl = ::RotatingLine
        )
    )
}
