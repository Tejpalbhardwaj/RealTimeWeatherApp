package com.example.realtimeweatherapp


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realtimeweatherapp.Model.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

   private val getweatherapi = RetrofitInstance.weatherapi

    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city : String){
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = getweatherapi.getWeather(Constant.apikey,city)
                if (response.isSuccessful){
                    response.body()?.let {
                        _weatherResult.value= NetworkResponse.Success(it)
                    }
                }else{
                    _weatherResult.value = NetworkResponse.Failure("Error in fetching data")
                }
            }
            catch (e:Exception){
              _weatherResult.value = NetworkResponse.Failure("failed to load data")
            }

            }

    }

}