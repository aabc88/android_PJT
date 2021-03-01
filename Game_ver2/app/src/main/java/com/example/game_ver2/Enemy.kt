package com.example.game_ver2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Enemy(res: Resources) {
    var was_shot: Boolean = true
    var x: Int = 0
    var y: Int
    var width: Int
    var height: Int
    var enemy1: Bitmap
    var enemy2: Bitmap
    var enemy3: Bitmap
    var enemy4: Bitmap
    var speed:Int = 1

    var enemy_counter = 1

    init {
        enemy1 = BitmapFactory.decodeResource(res, R.drawable.enemy_1)
        enemy2 = BitmapFactory.decodeResource(res, R.drawable.enemy_2)
        enemy3 = BitmapFactory.decodeResource(res, R.drawable.enemy_3)
        enemy4 = BitmapFactory.decodeResource(res, R.drawable.enemy_4)

        width = enemy1.width/2
        height = enemy1.height/2

        enemy1 = Bitmap.createScaledBitmap(enemy1, width, height, false)
        enemy2 = Bitmap.createScaledBitmap(enemy2, width, height, false)
        enemy3 = Bitmap.createScaledBitmap(enemy3, width, height, false)
        enemy4 = Bitmap.createScaledBitmap(enemy4, width, height, false)

        y = -height

    }

    val enemy: Bitmap
        get() {
            if (enemy_counter == 1) {
                enemy_counter++
                return enemy1
            }
            if (enemy_counter == 2) {
                enemy_counter++
                return enemy2
            }
            if (enemy_counter == 3) {
                enemy_counter++
                return enemy3
            }
            enemy_counter = 1
            return enemy4
        }
    val collisionShape: Rect
        get() = Rect(x, y, (x + width), y + height)

}