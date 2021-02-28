package com.example.game

import android.content.Context
import android.graphics.*
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class Game(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    //캐릭터 정의
    val ch1 = BitmapFactory.decodeResource(resources, R.drawable.iron_attack1)

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
    private var game_thread: GameThread? = null

    var count = true
    var re_road:String = "REROAD"
    var missile_count = 10 // 미사일 갯수
    var missileNum = IntArray(10) // 미사일 번호 저장
    var missile_y = IntArray(10) // 미사일 y위치
    var missile_x = IntArray(10) // 미사일 x위치

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (game_thread == null) {
            game_thread = GameThread()
            game_thread
        }
    }

    override fun onDetachedFromWindow() {
        //game_thread.run = false
        super.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var handler: move_handler = move_handler()
        // 캐릭터 이미지 등록
        val main = arrayOfNulls<Bitmap>(2)
        for (i in 0..1) {
            main[i] = BitmapFactory.decodeResource(resources, R.drawable.iron_attack1 + i)
            main[i] = Bitmap.createScaledBitmap(main[i]!!, ch1.width / 2, ch1.height / 2, true)
        }
        // 이미지 변경
        if (count == true) {
            var n = 0
            canvas!!.drawBitmap(
                main[n]!!, (w * 4 / 5 - ch1_resize.width / 2).toFloat(),
                now_y.toFloat(), null
            )
            count = !count
        } else {
            var n = 1
            canvas!!.drawBitmap(
                main[n]!!, (w * 4 / 5 - ch1_resize.width / 2).toFloat(),
                now_y.toFloat(), null
            )
            count = !count
        }
        // 미사일 이미지 등록
        val missile = arrayOfNulls<Bitmap>(10)
        //missile_count = 0
        for (i in 0..9) {
            // 미사일 이미지 등록
            missile[i] = BitmapFactory.decodeResource(resources, R.drawable.attack_beam)
            missile[i] = Bitmap.createScaledBitmap(missile[i]!!, w / 16, h / 16, true)
            //
            //if (missileNum[i] == 0) missile_count++
            missile_count = 10//

            if (missileNum[i] == 1) {
                canvas.drawBitmap(missile[i]!!, missile_x[i].toFloat(), missile_y[i].toFloat(), null)
                missile_x[i] -= 50
                missile_count--
            }
            if (missile_x[i] <= 0) {
                missileNum[i] = 0
                //score += 1

            }
            if (missile_count != 10) re_road = "REROAD" else re_road = ""

            /*if (missileNum[i] == 1) {
                canvas.drawBitmap(
                    missile[i]!!,
                    missile_now_x.toFloat(),
                    missile_y[i].toFloat(),
                    null
                )
                missile_now_x -= 60
                missile_count--
            }
            if (missile_y[i] <= 0) missileNum[i] = 0*/
        }

        display_size.setColor(Color.parseColor("#ff0000"))
        display_size.textSize = h / 16.toFloat()

        //canvas!!.drawBitmap(ch1_resize, (w * 4 / 5 - ch1_resize.width / 2).toFloat(),now_y.toFloat(), null)

        //점수표시
        canvas!!.drawText(re_road, 500f, h/16.toFloat(), display_size)
        canvas!!.drawText("Score : " + score, 50f, h / 16.toFloat(), display_size)
        handler.sendEmptyMessageDelayed(0, 50)


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.getX() < w / 2) {
                    for (i in 0..9) {

                        if (missileNum[i] == 0) {
                            missile_x[i] = (w * 4 / 5 - ch1_resize.width / 2) - 30
                            missile_y[i] = now_y
                            missileNum[i] = 1
                            if (missile_count != 0) missile_count -= 1
                            break
                        }
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.getX() > w / 2) {
                    var move_y = event!!.rawY.toInt()
                    if (move_y <= (h - ch1_resize.height)) now_y = move_y.toInt()
                    game_thread
                }
            }
        }

        return true
    }

    inner class move_handler : android.os.Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            postInvalidate()
        }
    }


    internal inner class GameThread : Thread() {
        var run = true

        override fun run() {
            super.run()
            while (run)
                try {
                    postInvalidate()
                    sleep(100)
                } catch (e: Exception) {

                }
        }
    }
}

