package com.example.game_ver2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Beam (res: Resources){
    var x:Int = 0
    var y:Int = 0
    var width: Int
    var height: Int
    var beam: Bitmap

    init {
        beam = BitmapFactory.decodeResource(res, R.drawable.iron_beam)
        // 빔 크기 정의
        beam = Bitmap.createScaledBitmap(beam, beam.width/8, beam.height/4, false)
        width = beam.width
        height = beam.height
    }
    val collisionShape: Rect
        get() = Rect(x, y, x + width, y + height)

}