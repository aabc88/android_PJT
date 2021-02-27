package com.example.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class Game(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    //캐릭터 정의
    val ch1 = BitmapFactory.decodeResource(resources, R.drawable.iron_attack)

    //캐릭터 비율조절
    val ch1_resize = Bitmap.createScaledBitmap(ch1, ch1.width / 2, ch1.height / 2, true)

    //현재 점수
    var score: Int = 0
    val display_size = Paint()

    //초기 y축
    var now_y: Int = (h * 4 / 5 - ch1_resize.height / 2)

    //터치이동
    var touch_listen: Int = 0

    //쓰레드
    private var game_thread: Thread = Thread()


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (game_thread == null) {
            game_thread = GameThread()
            game_thread.start()
        }
    }

    override fun onDetachedFromWindow() {
        //game_thread.run = false
        super.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        display_size.setColor(Color.parseColor("#ff0000"))
        display_size.textSize = h / 16.toFloat()

        canvas!!.drawBitmap(
            ch1_resize, (w * 4 / 5 - ch1_resize.width / 2).toFloat(),
            now_y.toFloat(), null)
        canvas!!.drawText("Score : " + score, 0f, h / 16.toFloat(), display_size)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val startY = event!!.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                var move_y = event!!.rawY.toInt()
                if (move_y <= (h - ch1_resize.height)) now_y = move_y.toInt()
                invalidate()
            }
        }

        return true
    }

    class GameThread : Thread() {
        var run: Boolean = true

        override fun run() {
            super.run()
            while (run)
                try {
                    println("1")
                    sleep(100)
                } catch (e: Exception) {

                }
        }
    }
}

