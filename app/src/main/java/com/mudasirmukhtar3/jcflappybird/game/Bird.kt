package com.mudasirmukhtar3.jcflappybird.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import com.mudasirmukhtar3.jcflappybird.R

class Bird(
    resources: Resources,
    private val screenHeight: Int,
    private val callback: GameManagerCallback
) :
    Interactions {
    private val bird_down: Bitmap
    private val bird_up: Bitmap
    private val birdX = resources.getDimension(R.dimen.bird_x).toInt()
    private var birdY: Int
    private val birdWidth: Int
    private val birdHeight: Int
    private val gravity: Float
    private var currentFallingSpeed = 0f
    private val flappyBoost: Float
    private var collision = false
    init {
        birdY = screenHeight / 2
        birdWidth = resources.getDimension(R.dimen.bird_width).toInt()
        birdHeight = resources.getDimension(R.dimen.bird_height).toInt()
        gravity = resources.getDimension(R.dimen.gravity)
        flappyBoost = resources.getDimension(R.dimen.flappy_boost)

        val birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird_down)
        bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false)
        val birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird_up)
        bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false)
    }
    override fun draw(canvas: Canvas?) {
        if (currentFallingSpeed < 0) {
            canvas!!.drawBitmap(bird_up, birdX.toFloat(), birdY.toFloat(), null)
        } else {
            canvas!!.drawBitmap(bird_down, birdX.toFloat(), birdY.toFloat(), null)
        }
    }
    override fun update() {
        if (collision) {
            if (birdY + bird_down.height < screenHeight) {
                birdY = (birdY + currentFallingSpeed).toInt()
                currentFallingSpeed += gravity
            }
        } else {
            birdY = (birdY + currentFallingSpeed).toInt()
            currentFallingSpeed += gravity
            val birdPosition = Rect(birdX, birdY, birdX + birdWidth, birdY + birdHeight)
            callback.updatePosition(birdPosition)
        }
    }

    fun onTouchEvent() {
        if (!collision) {
            currentFallingSpeed = flappyBoost
        }
    }

    fun collision() {
        collision = true
    }
}
