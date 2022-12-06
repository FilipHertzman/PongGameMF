package com.example.ponggamemf

import android.graphics.RectF
import kotlin.random.Random

class Ball(screenX : Int, screenY : Int) {

    val rect : RectF
    private var ballXSpeed : Float = 0.toFloat()
    private var ballYSpeed : Float = 0.toFloat()
    private val ballWidth : Float
    private val ballHeight : Float
    var screenYr = screenY

    init {
        ballWidth = (screenX / 80).toFloat()
        ballHeight = ballWidth

        ballXSpeed = (screenY / 3).toFloat()
        ballXSpeed = ballYSpeed

        rect = RectF()
    }

    fun update(fps: Long) {
        rect.left = rect.left + ballXSpeed / fps
        rect.top = rect.top + ballWidth
        rect.right = rect.left + ballWidth
        rect.bottom = rect.top - ballHeight
    }

    fun reverseYSpeed() {
        ballYSpeed = - ballYSpeed
    }

    fun reverseXSpeed() {
        ballXSpeed = -ballXSpeed
    }

    fun setRandomXSpeed() {
        val generator = Random()
        val answer = generator.nextInt(2)

        if(answer == 0) {
            reverseXSpeed()
        }

    }

    fun increaseSpeed() {
        ballXSpeed += ballXSpeed / 40
        ballYSpeed += ballYSpeed / 40
    }

    fun resetSpeed() {
        ballYSpeed = (screenYr / 3).toFloat()
        ballYSpeed = ballYSpeed
    }

    fun clearObstacleY(y : Float) {
        rect.bottom = y
        rect.top = y - ballHeight

    }

    fun clearObstacleX(x : Float) {
        rect.left = x
        rect.right = x + ballWidth
    }

    fun reset(x : Int, y : Int) {
        rect.left = (x / 2).toFloat()
        rect.top = (y / 2).toFloat()
        rect.right = (x / 2) + ballWidth
        rect.bottom = (y.toFloat() / 2) - ballHeight
    }




}