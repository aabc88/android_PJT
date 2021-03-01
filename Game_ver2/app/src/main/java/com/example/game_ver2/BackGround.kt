package com.example.game_ver2

import android.R
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory


class Background internal constructor(screenX: Int, screenY: Int, res: Resources?, img_id: Int) {
    var x = 0
    var y = 0
    var background: Bitmap

    init {
        background = BitmapFactory.decodeResource(res, img_id)
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false)
    }
}