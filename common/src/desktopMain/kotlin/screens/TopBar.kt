package screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
internal actual fun TopBar(onBackPressed: () -> Unit) {
    Button(onClick = onBackPressed, content = { Text("<--") })
}