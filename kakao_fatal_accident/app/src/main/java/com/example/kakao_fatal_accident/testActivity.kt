package com.example.kakao_fatal_accident

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kakao_fatal_accident.databinding.ActivityTestBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


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
        }

        if (isPermitted()) {
            startMap()
            getNowGPS()
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERM_FLAG)
        }

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
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.566535, 126.97796919), 4, true);

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
        println("                  "+latitude)
        println("                  "+longitude)

        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(latitude, longitude), 2, true);
        val marker = MapPOIItem()
        val mappoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)

        marker.itemName = ("내 위치")
        marker.tag = 0
        marker.mapPoint = mappoint
        marker.markerType = (MapPOIItem.MarkerType.BluePin)
        mapView.addPOIItem(marker)
        
    }

    fun getKoreanAddress()  {


    }
}
