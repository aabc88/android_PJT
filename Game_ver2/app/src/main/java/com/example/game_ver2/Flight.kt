package com.example.game_ver2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Flight internal constructor(
    screenY: Int,
    screenX: Int,
    res: Resources?,
    img_id: Int,
    gameView: GameView
) {
    var x: Int = 0
    var y: Int = 0
    var width: Int = 0
    var height: Int = 0
    var isGoingUp: Boolean = false
    var wing_counter: Int = 0
    var shoot: Int = 0
    var shoot_counter: Int = 1
    var gameView: GameView = gameView

    lateinit var flight1: Bitmap
    lateinit var flight2: Bitmap
    lateinit var shoot1: Bitmap
    lateinit var shoot2: Bitmap
    lateinit var shoot3: Bitmap
    lateinit var dead: Bitmap

    init {
        // 이미지 할당
        flight1 = BitmapFactory.decodeResource(res, R.drawable.iron_fly0)
        flight2 = BitmapFactory.decodeResource(res, R.drawable.iron_fly1)
        shoot1 = BitmapFactory.decodeResource(res, R.drawable.iron_attack0)
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.iron_attack1)
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.iron_attack2)
        dead = BitmapFactory.decodeResource(res, R.drawable.iron_dead)

        width = flight1.width
        height = flight1.height




        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false)
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false)


        y = screenY / 2
        x = screenX / 5
    }

    val flight_motion: Bitmap
        get() {
            if (shoot != 0) {
                if (shoot_counter == 1) {
                    shoot_counter++
                    return shoot1
                }
                if (shoot_counter == 2) {
                    shoot_counter++
                    return shoot2
                }
                shoot_counter = 1
                shoot--
                gameView.newBeam()
                return shoot3
            }

            if (wing_counter == 0) {
                wing_counter++
                return flight1
            }
            wing_counter--
            return flight2

        }
    val collisionShape: Rect
        get() = Rect(x, y, x + width, y + height)

    val get_dead: Bitmap
        get() {
            return dead
        }

}