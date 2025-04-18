package com.example.realtimeweatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.realtimeweatherapp.Model.WeatherModel

@Composable
fun WeatherPage(viewModel: WeatherViewModel) {

    var city by remember { mutableStateOf("") }

    val WeatherView = viewModel.weatherResult.observeAsState()

    Column {

        Text(text = "")
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "MY CITY WEATHER" , fontSize = 25.sp , textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp), fontWeight = FontWeight.Bold , color = Color.Red)


        Row(
            modifier = Modifier.fillMaxWidth().padding(26.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = city,
                onValueChange = { city = it },
                label = { Text(text = "Enter City") })

            IconButton(onClick = {
                viewModel.getData(city)
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            }
        }

        when (val finalBoss = WeatherView.value) {
            is NetworkResponse.Failure -> Text(text = finalBoss.message)
            NetworkResponse.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(22.dp).align(Alignment.CenterHorizontally)
                )
            }

            is NetworkResponse.Success -> DetailScreen(data = finalBoss.data)
            null -> {}
        }
    }
}

@Composable
fun DetailScreen(data: WeatherModel) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                modifier = Modifier.size(25.dp),
                imageVector = Icons.Default.LocationOn,
                contentDescription = ""
            )
            Text(text = "${data.location.name} ,", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Text(text = data.location.country, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "${data.current.temp_c} °C",
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            textAlign = TextAlign.Center
        )
        AsyncImage(
            modifier = Modifier.size(100.dp),
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = ""
        )
        Text(text = "${data.current.condition.text} Weather ", textAlign = TextAlign.Center)

        Card(modifier = Modifier.padding(18.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    BoxRope("Humidity", "${data.current.humidity} % ")
                    BoxRope("Wind Blow", "${data.current.wind_kph} ")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    BoxRope("Region", "${data.location.region} ")
                    BoxRope("Condition", "${data.current.cloud} ")
                }

            }
        }
    }
}

@Composable
fun BoxRope(key: String, value: String) {
    Column(modifier = Modifier.padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = key, fontWeight = FontWeight.Bold)
        Text(text = "${value}")
    }
}