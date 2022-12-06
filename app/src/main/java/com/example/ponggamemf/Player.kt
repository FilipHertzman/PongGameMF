package com.example.ponggamemf

import android.graphics.RectF

class Player(private val screenSizeX: Int, private val screenSizeY: Int, onTop: Boolean) {

    val rect: RectF

    private val playerBarLength: Float = (screenSizeX / 6).toFloat()
    private val playerBarHeight: Float = (screenSizeY / 50).toFloat()

    private var playerBarLeftX: Float = ((screenSizeX / 2) - (playerBarLength / 2))
    private val playerBarTopY: Float = (screenSizeY - 20).toFloat()

    private val playerBarSpeed: Float = screenSizeX.toFloat() * 2

    val STOPPED = 0
    val LEFT = 1
    val RIGHT = 2

    private var playerBarMoving = STOPPED

    init {
        rect = if (onTop) {
            RectF(
                playerBarLeftX, 35f,
                playerBarLeftX + playerBarLength,
                35f + playerBarHeight
            )
        } else {
            RectF(
                playerBarLeftX,
                playerBarTopY - 55f,
                playerBarLeftX + playerBarLength,
                playerBarTopY + playerBarHeight - 55f
            )
        }
    }

    fun playerBarMovementState(state: Int) {
        playerBarMoving = state
    }

    fun playerBarUpdate(fps: Long) {
        if (playerBarMoving == LEFT) {
            playerBarLeftX = playerBarLeftX - playerBarSpeed / fps
        }
        if (playerBarMoving == RIGHT) {
            playerBarLeftX = playerBarLeftX + playerBarSpeed / fps
        }

        if (rect.left < 0) {
            playerBarLeftX = 0f
        }
        if (rect.right > screenSizeX) {
            playerBarLeftX = screenSizeX - (rect.right - rect.left)
        }

        rect.left = playerBarLeftX
        rect.right = playerBarLeftX + playerBarLength

    }
}

