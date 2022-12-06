package com.example.ponggamemf

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.RectF
import android.view.SurfaceView


import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
hrhrh
//hejkio


@SuppressLint("ViewConstructor")
class PongView (context: Context, var ScreenX : Int, var ScreenY : Int) : SurfaceView(context), Runnable {



    var gameThread : Thread? = null

    private var mOurHolder : SurfaceHolder = holder


    @Volatile
    var playing : Boolean = false
    private var paused = true

    var mFPS : Long = 0

    var topPlayerBar : Player = Player(ScreenX, ScreenY, true )
    var bottomPlayerBar : Player = Player(ScreenX, ScreenY, false)

    var pongBall : Ball = Ball(ScreenX, ScreenY)

    var score = 0
    var topScore = 0
    var topLives = 3
    var botLives = 3

    // sounds here

    private fun restart() {
        pongBall.reset(ScreenX, ScreenY)

        if (topLives == 0 || botLives == 0) {
            if(score > topScore) topScore = score
            score = 0
            topLives = 3
            botLives = 3
            pongBall.resetSpeed()
        }
    }

    override fun run() {
        while (playing) {

            val startFrameTime = System.currentTimeMillis()

            if(!paused)

                draw()
        }
    }

    fun update() {

        bottomPlayerBar.playerBarUpdate(mFPS)
        topPlayerBar.playerBarUpdate(mFPS)
        pongBall.update(mFPS)

        if (RectF.intersects(bottomPlayerBar.rect, pongBall.rect)) {
            pongBall.setRandomXSpeed()
            pongBall.reverseYSpeed()
            pongBall.clearObstacleY(bottomPlayerBar.rect.top - 2)

            score ++
            pongBall.increaseSpeed()

        }

        if (pongBall.rect.bottom > ScreenY) {
            pongBall.reverseYSpeed()
            pongBall.clearObstacleY((ScreenY - 2).toFloat())

            botLives--
            if (botLives == 0)
                paused = true

            pongBall.setRandomXSpeed()
            restart()
        }

        if (pongBall.rect.left < 0) {
            pongBall.reverseXSpeed()
            pongBall.clearObstacleX(2f)

        }

        if (pongBall.rect.right > ScreenX) {
            pongBall.reverseXSpeed()
            pongBall.clearObstacleX((ScreenX - 22).toFloat())

        }

    }

    private fun draw() {

        if (mOurHolder.surface.isValid) {
            val canvas = mOurHolder.lockCanvas()
            val styleMovingObjects = Paint()

            canvas.drawColor(Color.BLUE)
            canvas.drawRect(bottomPlayerBar.rect, styleMovingObjects)
            canvas.drawRect(topPlayerBar.rect, styleMovingObjects)
            canvas.drawOval(pongBall.rect, styleMovingObjects)

            val styleScore = Paint()
            styleScore.setColor(Color.WHITE)
            styleScore.textSize

            mOurHolder.unlockCanvasAndPost(canvas)


        }
    }

    fun pause() {
        playing = false
        try {
            gameThread!!.join()
        } catch (e : InterruptedException) {
            Log.e("Error:", "joining thread")
        }
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread!!.start()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        paused = false
        for (i in 0 until event.pointerCount) {
            if (event.getY(i) > height / 2) {
                if (event.getX(i) > ScreenX / 2) {
                    bottomPlayerBar.playerBarMovementState(bottomPlayerBar.RIGHT)
                } else {
                    bottomPlayerBar.playerBarMovementState(bottomPlayerBar.LEFT)
                }

                when (event.actionMasked) {
                    MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_UP -> {
                        bottomPlayerBar.playerBarMovementState(bottomPlayerBar.STOPPED)
                    }
                }

            } else {
                if (event.getX(i) > ScreenX / 2) {
                    topPlayerBar.playerBarMovementState(topPlayerBar.RIGHT)
                } else {
                    topPlayerBar.playerBarMovementState(topPlayerBar.STOPPED)
                }

                when (event.actionMasked) {
                    MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_UP -> {
                        topPlayerBar.playerBarMovementState(topPlayerBar.STOPPED)
                    }
                }
            }

        }

        return true
    }
}





