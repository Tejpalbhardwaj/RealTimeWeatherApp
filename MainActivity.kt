package com.example.realtimeweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.realtimeweatherapp.ui.theme.RealTimeWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherviewModel  = ViewModelProvider(this)[ WeatherViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            RealTimeWeatherAppTheme {
                Column(modifier = Modifier.fillMaxSize().background(Color.Cyan)) {
                    WeatherPage(weatherviewModel)
                }
            }
        }
    }
}

