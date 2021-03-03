package com.example.api_pjt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.api_pjt.databinding.ActivitySecondBinding
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.lang.StringBuilder


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
            val json_object = JSONObject(html)
            val json_parse = json_object.getJSONObject("main")
            val region = json_object.getString("name")
            var temp = json_parse.getString("temp")
            temp = (temp.toDouble() - 273.15).toString()

            //temp = (temp.toDouble()-275.15).toString()
            binding.txtRegion.text = region
            binding.txtTemp.text = temp
            println(region)
            println(temp)
            binding.txtHtml.text = html
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
}