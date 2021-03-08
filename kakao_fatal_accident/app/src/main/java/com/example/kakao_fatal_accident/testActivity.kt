package com.example.kakao_fatal_accident

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kakao_fatal_accident.databinding.ActivityTestBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.math.roundToInt


class testActivity : AppCompatActivity() {
    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val PERM_FLAG = 99
    private lateinit var mapView: MapView

    lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGps.setOnClickListener {
            getNowGPS()
            markWeather()
        }

        if (isPermitted()) {
            startMap()
            getNowGPS()
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERM_FLAG)
        }

        markWeather()
    }


    fun isPermitted(): Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    perm
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    fun startMap() {
        mapView = MapView(this)
        val map = findViewById<ViewGroup>(R.id.map_view)
        map.addView(mapView)
        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                37.566535,
                126.97796919
            ), 4, true
        );

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERM_FLAG -> {
                var check = false
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        check = false
                        break
                    }
                }
                if (check) startMap() else {
                    Toast.makeText(this, "권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }


            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getNowGPS() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        val longitude = location!!.longitude
        val latitude = location!!.latitude
        println("                  " + latitude)
        println("                  " + longitude)

        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(latitude, longitude),
            2,
            true
        );

        val temp  = getMyTempJson(latitude, longitude)

        /*val marker = MapPOIItem()
        val mappoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        println("             my gps_temp : "+temp)
        mapView.removeAllPOIItems()
        marker.itemName = ("내 위치\n"+temp+"℃")
        marker.tag = 0
        marker.mapPoint = mappoint
        marker.markerType = (MapPOIItem.MarkerType.BluePin)
        mapView.addPOIItem(marker)*/

        //var now_Addres = getKoreanAddress(latitude, longitude)
        //println("            현재 주소는 " + now_Addres)

    }

    fun getMyTempJson(lat : Double, lon: Double): String {

        var resTemp : String = null.toString()
        CoroutineScope(Dispatchers.Main).launch {
            val html = CoroutineScope(Dispatchers.Default).async {
                getMyTemp(lat, lon)
            } .await()
            //gson 객체
            var gson = GsonBuilder().create()
            //json 전체값 받기
            val json_obj = JSONObject(html)
            println(html)
            //json main 받고 온도 계산하기
            val json_main = json_obj.getJSONObject("main")
            var temp = json_main.getString("temp")
            temp = (((temp.toDouble() - 273.15)).roundToInt()).toString()
            resTemp = temp
            println("***************"+resTemp)

            val marker = MapPOIItem()
            val mappoint = MapPoint.mapPointWithGeoCoord(lat, lon)

            mapView.removeAllPOIItems()
            marker.itemName = ("내 위치\n"+temp+"℃")
            marker.tag = 0
            marker.mapPoint = mappoint
            marker.markerType = (MapPOIItem.MarkerType.BluePin)
            mapView.addPOIItem(marker)
        }

        return resTemp
    }

    fun getMyTemp(lat : Double, lon : Double) : String {
        val client = OkHttpClient.Builder().build()
        //2. 요청
        val req = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=008e934459f37c348b6b07fe01fc9852")
            .build()
        //3. 응답
        client.newCall(req).execute().use { response ->
            return if (response.body != null) {
                response.body!!.string()
            } else "body null"
        }
    }

    fun getKoreanAddress(latitude: Double, longitude: Double): String {
        //지오코더 위도 경도를 주소로 변환
        val geocoder = Geocoder(this, Locale.getDefault())

        val addresses: List<Address>

        addresses = try {
            geocoder.getFromLocation(
                latitude,
                longitude,
                1
            )
        } catch (ioException: IOException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show()
            return "지오코더 서비스 사용불가"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"
        }

        if (addresses == null || addresses.size == 0) {
            //Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show()
            return "주소 미발견"
        }
        val address: Address = addresses[0]
        return address.getAddressLine(0).toString() + "\n"
    }

    fun markWeather() {
        val array = arrayOf(
            "Seoul", "Suwon", "Incheon", "Anyang", "Seongnam", "Osan", "Wonju",
            "Yangyang", "Sokcho", "Gangneung", "Chungju", "Cheonan", "Seosan", "Daejeon", "Jeonju", "Iksan",
            "Mokpo", "Haenam", "Gwangju", "Yeosu", "Beolgyo", "Gimcheon", "Daegu", "Waegwan", "Andong",
            "Pohang", "Uiseong", "Busan", "Ulsan", "Miryang", "Masan", "Changwon", "Jinju", "Jeju", "Anseong",
            "Ansan-si", "Andong", "Gaigeturi", "Anyang-si", "Jamwon-dong", "Janggol", "Changp’o", "Changsu",
            "Changwon", "Jinan-gun", "Chinch'ŏn", "Chinhae", "Chinju", "Jeollabuk-do", "Jeollanam-do",  "Ch’ŏngnim",
            "Cheongsong gun", "Chuncheon", "Chungcheongbuk-do", "Chungcheongnam-do", "Jungpyong",
            "Hamyang", "Hayang", "Hongch’ŏn", "Hwaseong-si", "Icheon-si", "Imsil", "Inje", "Yanggu", "Ganghwa-gun",
            "Gangneung", "Gangwon-do", "Gapyeong", "Gimcheon", "Kimhae", "Kimje", "Gimpo-si", "Koch'ang",
            "Koesan", "Kyosai", "Gongju", "Goyang-si",  "Gumi", "Gunpo", "Guri-si", "Gwacheon", "Masan", "Muan", "Paju",
            "Naju", "Nonsan", "Bucheon-si"


            )

        for (i in 0..array.size-1) {
            CoroutineScope(Dispatchers.Main).launch {
                val html = CoroutineScope(Dispatchers.Default).async {
                    get_html(array[i])
                } .await()
                //gson 객체
                var gson = GsonBuilder().create()
                //json 전체값 받기
                val json_obj = JSONObject(html)
                //json main 받고 온도 계산하기
                val json_main = json_obj.getJSONObject("main")
                var temp = json_main.getString("temp")

                temp = (((temp.toDouble() - 273.15)).roundToInt()).toString()
                //json weather[]배열 받기
                val json_weather = json_obj.getJSONArray("weather").get(0).toString()
                println("%%%%%%%%%%%%%%%%%%%%%%%%%%"+json_weather)
                val weather = gson.fromJson(json_weather, WeatherVO::class.java)
                println("%%%%%%%%%%%%%%%%%%%%%%%%%%"+weather.main)
                //json에서 coord받고 위도 경도 받아오기
                val coord = json_obj.getJSONObject("coord")
                val lon = coord.getDouble("lon")
                val lat = coord.getDouble("lat")


                val marker = MapPOIItem()
                val mappoint = MapPoint.mapPointWithGeoCoord(lat, lon)
                var now_Addres = getKoreanAddress(lat, lon)
                val splitArray = now_Addres.split(" ", " ")

                marker.itemName = (array[i]+"\n"+temp+"℃")
                marker.tag = 0
                marker.mapPoint = mappoint
                marker.markerType = (MapPOIItem.MarkerType.RedPin)
                mapView.addPOIItem(marker)
            }
        }
    }

    fun get_html(dosi : String): String {
        //1. 클라이언트 만들기
        val client = OkHttpClient.Builder().build()
        //2. 요청
        val req = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?q="+dosi+"&appid=008e934459f37c348b6b07fe01fc9852")
            .build()
        //3. 응답
        client.newCall(req).execute().use { response ->
            return if (response.body != null) {
                response.body!!.string()
            } else "body null"
        }
    }


}
