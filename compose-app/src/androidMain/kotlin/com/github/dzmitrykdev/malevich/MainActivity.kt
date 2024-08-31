package com.github.dzmitrykdev.malevich

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import malunki.RotatingLine

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RotatingLine()
        }
    }
}
