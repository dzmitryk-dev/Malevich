package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ListSelector(
    modifier: Modifier,
    selected: State<Malunek>,
    textSelectListener: Modifier.(Malunek) -> Modifier,
) {
    Column(modifier = modifier) {
        Malunek.values().forEach { malunek ->
            val isSelected = selected.value == malunek
            Text(
                modifier = modifier
                    .background(color = if (isSelected) {
                        Color.Black
                    } else {
                        Color.White
                    }).textSelectListener(malunek),
                text = malunek.title,
                color = if (isSelected) {
                    Color.White
                } else {
                    Color.Black
                }
            )
        }
    }
}

