package io.github.dzkoirn

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import malunki.First
import malunki.Mandelbrot
import ui.ListSelector
import ui.Malunek

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val (width, height) = with(displayMetrics) { widthPixels to heightPixels }

        setContent {
            val malunek = remember { mutableStateOf(Malunek.FIRST) }

            ListSelector(
                modifier = Modifier,
                selected = malunek,
                textSelectListener = { m -> clickable { malunek.value = m } }
            )
            //First(width, height)
//            Mandelbrot(width, height)
        }
    }
}