package com.example.game_ver2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.SurfaceView
import java.util.*
import kotlin.collections.ArrayList

class GameView(activity: Game_Activity, screenX: Int, screenY: Int) : SurfaceView(activity), Runnable {
    lateinit var thread: Thread
    private var is_playing: Boolean?? = null
    private var is_game_over: Boolean = false

    var screenX: Int
    var screenY: Int
    var screenRatioX: Float?? = null
    var screenRatioY: Float?? = null
    var paint: Paint
    var background1: Background
    var background2: Background
    var flight: Flight
    private var beams: MutableList<Beam>
    private val enemys: Array<Enemy?>
    var random: Random?? = null
    var score: Int = 0
    var prefs : SharedPreferences
    val activity : Game_Activity



    init {
        this.activity = activity

        this.screenX = screenX
        this.screenY = screenY
        screenRatioX = display_x.toFloat()
        screenRatioY = display_h.toFloat()

        background1 = Background(screenX, screenY, resources, R.drawable.iron_background)
        background2 = Background(screenX, screenY, resources, R.drawable.iron_background)

        flight = Flight(screenY, screenX, resources, R.drawable.iron_fly0, this)

        background2!!.x = screenX

        beams = ArrayList<Beam>()
        paint = Paint()

        enemys = arrayOfNulls<Enemy>(16)

        for (i in 0..15) {
            val enemy = Enemy(resources)
            enemys[i] = enemy
        }

        random = Random()
        paint.textSize = (display_h / 16).toFloat()

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE)
    }

    override fun run() {
        while (is_playing == true) {

            update()
            draw()
            sleep()
        }
    }

    fun update() {

        background1!!.x -= 10 //* screenRatioX!!.toInt()
        background2!!.x -= 10 //* screenRatioX!!.toInt()

        if (background1!!.x + background1!!.background.width < 0) {
            background1!!.x = screenX
        }
        if (background2!!.x + background2!!.background.width < 0) {
            background2!!.x = screenX
        }

        //if (flight.isGoingUp) flight.y -= 30 else flight.y += 30

        if (flight.y < 0) flight.y = 0
        if (flight.y > screenY - flight.height) flight.y = screenY - flight.height

        var trash: MutableList<Beam> = java.util.ArrayList<Beam>()

        for (beam in beams) {
            if (beam.x > screenX) trash.add(beam)

            beam.x += 50

            for (enemy in enemys) {
                if (Rect.intersects(enemy!!.collisionShape, beam.collisionShape)) {
                    score++
                    enemy.x = -1000
                    beam.x = screenX + 500
                    enemy.was_shot = true
                }
            }
        }
        for (beam in trash)
            beams.remove(beam)

        for (enemy in enemys) {
            enemy!!.x -= enemy!!.speed

            if (enemy.x + enemy.width < 0) {

                if (!enemy.was_shot) {
                    is_game_over = true
                    return
                }

                var bound = 10 //수정
                enemy.speed = random!!.nextInt(bound)

                //if (enemy.speed < 10) enemy.speed = 10

                enemy.x = screenX
                enemy.y = random!!.nextInt(screenY - enemy.height)

                enemy.was_shot = false
            }
            if (Rect.intersects(enemy.collisionShape, flight.collisionShape))
            {
                is_game_over = true
                return
            }
        }



    }

    fun draw() {

        if (holder.surface.isValid) {
            var canvas: Canvas = holder.lockCanvas()
            canvas.drawBitmap(
                background1!!.background,
                (background1!!.x).toFloat(),
                (background1!!.y).toFloat(),
                paint
            )
            canvas.drawBitmap(
                background2!!.background,
                (background2!!.x).toFloat(),
                (background2!!.y).toFloat(),
                paint
            )


            canvas.drawText("Score : " +score, 100f, 100f , paint)

            if (is_game_over) {
                is_playing = false
                canvas.drawBitmap(flight.get_dead, (screenX/16).toFloat(), (flight.y).toFloat(), paint)
                holder.unlockCanvasAndPost(canvas)
                save_high_score()
                wait_before_exit()

                return
            }
            canvas.drawBitmap(
                flight.flight_motion,
                (screenX/16).toFloat(),
                (flight.y).toFloat(),
                null
            )
            for (enemy in enemys)
                canvas.drawBitmap(enemy!!.enemy, (enemy!!.x).toFloat(), (enemy!!.y).toFloat(), paint)
            for (beam in beams)
                canvas.drawBitmap(beam.beam, (beam.x).toFloat(), (beam.y).toFloat(), paint)

            holder.unlockCanvasAndPost(canvas)
        }

    }

    private fun wait_before_exit() {
        Thread.sleep(1000)
        activity.startActivity(Intent(activity, MainActivity ::class.java))
        activity.finish()
    }

    private fun save_high_score() {
        if (prefs.getInt("highscore", 0) < score) {
            val editor = prefs.edit()
            editor.putInt("highscore", score)
            editor.apply()
        }
    }

    private fun sleep() {
        try {
            Thread.sleep(18)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    fun resume() {

        is_playing = true
        thread = Thread(this)
        thread!!.start()
    }

    fun pause() {
        try {
            is_playing = false
            thread!!.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            /*MotionEvent.ACTION_DOWN -> {
                if (event.getX() < screenX / 2) {
                    flight.isGoingUp = true
                }
            }*/
            MotionEvent.ACTION_MOVE -> {
                //flight.isGoingUp = false
                if (event.getX() < screenX / 2) {
                    var move_y = event!!.rawY.toInt()
                    if (move_y <= (screenY - flight.height)) flight.y = move_y
                }
            }

            MotionEvent.ACTION_UP -> {
                if (event.getX() > screenX/2) {
                    flight.shoot++
                }
            }
        }

        return true
    }

    fun newBeam() {
        val beam = Beam(resources)
        beam.x = flight.x
        beam.y =  flight.y + flight.height/4
        beams.add(beam)
    }
}