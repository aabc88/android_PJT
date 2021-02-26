package com.example.game

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.Display
import android.view.View

var score: Int = 0

class Game_View(context: Context) : View(context) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()

        paint.color = Color.BLACK
        paint.textSize = 50f
        canvas!!.drawText("Score : $score", 50f, 50f, paint)
        invalidate()

/*        val drawable:Drawable = resources.getDrawable(R.drawable.iron_attack)
        val dwidth = drawable.intrinsicWidth
        val dheight = drawable.intrinsicHeight

        drawable.setBounds(0, 0,(drawable.intrinsicWidth)/2,(drawable.intrinsicHeight)/2)
        drawable.draw(canvas)
        drawable.setHotspot((w - dwidth/2).toFloat(), (h- dheight/2).toFloat())*/

        val bitmap : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.iron_attack)
        val re_size = Bitmap.createScaledBitmap(bitmap, bitmap.width/2, bitmap.height/2, true)
        canvas.drawBitmap(re_size, (w/2 - bitmap.width/2).toFloat(), (h/2 - bitmap.height/2).toFloat(), null)
        //canvas.translate((bitmap.width/2).toFloat(), (bitmap.height/2).toFloat())

    }
}