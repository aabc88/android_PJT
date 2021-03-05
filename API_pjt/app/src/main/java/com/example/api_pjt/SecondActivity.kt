package com.example.api_pjt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.api_pjt.databinding.ActivitySecondBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.math.roundToInt


class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!is_login) finish()
        coroutine()
    }

    fun coroutine() {
        //코루틴 매인은 동기 백그라운드는 비동기
        CoroutineScope(Dispatchers.Main).launch {

            val html = CoroutineScope(Dispatchers.Default).async {
                get_html()
            }.await()
            //gson 객체
            var gson = GsonBuilder().create()
            //json 전체값 받아오기
            val json_object = JSONObject(html)
            //json main받아오기
            val json_parse_main = json_object.getJSONObject("main")
            //json weather[] 배열 받아오기
            val json_weather = json_object.getJSONArray("weather").get(0).toString()
            var weather = gson.fromJson(json_weather, WeatherVO::class.java)
            //할당
            val region = json_object.getString("name")
            var temp = json_parse_main.getString("temp")
            //온도계산
            temp = (((temp.toDouble() - 273.15)).roundToInt()).toString()
            //받은 값을 액티비티에 할당
            binding.txtRegion.text = region
            binding.txtTemp.text = temp + "˚"
            binding.txtWeather.text = (weather.main)
            //이미지 할당
            get_weather(weather.main)
        }
    }

    fun get_html(): String {
        //1. 클라이언트 만들기
        val client = OkHttpClient.Builder().build()
        //2. 요청
        val req = Request.Builder()
            .url("http://api.openweathermap.org/data/2.5/weather?q=Incheon&appid=008e934459f37c348b6b07fe01fc9852")
            .build()
        //3. 응답
        client.newCall(req).execute().use { response ->
            return if (response.body != null) {
                response.body!!.string()
            } else "body null"
        }
    }

    fun get_html_str() {

    }

    fun get_weather(weather: String) {
        if (weather == "Clouds")
            binding.imgWeather.setImageResource(R.drawable.weather_cloud_only)
        if (weather == "Rain")
            binding.imgWeather.setImageResource(R.drawable.weather_rain_only)
    }
}