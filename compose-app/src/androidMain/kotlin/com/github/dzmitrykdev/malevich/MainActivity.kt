package com.github.dzmitrykdev.malevich

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import malunki.MalunkiProvider
import ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                malunki = MalunkiProvider().getMalunki()
            )
        }
    }
}
