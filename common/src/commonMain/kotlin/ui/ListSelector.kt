package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.onClick
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListSelector(
    modifier: Modifier,
    initial: Malunek,
    onItemSelected: (Malunek) -> Unit,
) {
    val selected = remember { mutableStateOf(initial) }

    Column(modifier = modifier) {
        Malunek.values().forEach { malunek ->
            Text(
                modifier = Modifier.onClick(onClick = {
                    selected.value = malunek
                    onItemSelected(malunek)
                }),
                text = malunek.title,
            )
        }
    }
}

