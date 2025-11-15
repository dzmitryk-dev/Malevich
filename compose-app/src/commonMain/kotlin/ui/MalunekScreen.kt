package ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import malevich.compose_app.generated.resources.Res
import malevich.compose_app.generated.resources.ic_back
import malunki.Malunek
import malunki.RotatingLine
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MalunekScreen(
    modifier: Modifier = Modifier,
    malunek: Malunek,
    onBackClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(malunek.title) },
                navigationIcon = {
                    Button(onClick = onBackClick) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Content(
                modifier = Modifier.fillMaxSize(),
                innerPadding = innerPadding,
                malunek = malunek,
                onBackClick = onBackClick
            )
        }
    )
}

@Composable
private fun Content(
    modifier: Modifier,
    innerPadding: PaddingValues,
    malunek: Malunek,
    onBackClick: () -> Unit = {},
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
