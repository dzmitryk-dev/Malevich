package io.github.dzkoirn

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import malunki.First
import malunki.Mandelbrot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val (width, height) = with(displayMetrics) { widthPixels to heightPixels }

        setContent {
            //First(width, height)
            Mandelbrot(width, height)
        }
    }
}