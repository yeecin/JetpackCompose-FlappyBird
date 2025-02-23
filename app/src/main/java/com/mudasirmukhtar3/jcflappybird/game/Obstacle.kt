package com.mudasirmukhtar3.jcflappybird.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.mudasirmukhtar3.jcflappybird.R
import java.util.Random

class Obstacle(
    resources: Resources,
    private val screenHeight: Int,
    private val screenWidth: Int,
    callback: ObstacleCallback
) :
    Interactions {
    private val height: Int
    private val width = resources.getDimension(R.dimen.obstacle_width).toInt()
    private val separation = resources.getDimension(R.dimen.obstacle_separation).toInt()
    private var xPosition = screenWidth
    private val speed = resources.getDimension(R.dimen.obstacle_speed).toInt()
    private val headHeight = resources.getDimension(R.dimen.head_height).toInt()
    private val headExtraWidth = resources.getDimension(R.dimen.head_extra_width).toInt()
    private val obstacleMinPosition =
        resources.getDimension(R.dimen.obstacle_min_position).toInt()
    private val image: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.pipes)
    private val callback: ObstacleCallback = callback

    init {
        val random = Random(System.currentTimeMillis())
        height =
            random.nextInt(screenHeight - 2 * obstacleMinPosition - separation) + obstacleMinPosition
    }


    override fun draw(canvas: Canvas?) {
        val bottomPipe = Rect(
            xPosition + headExtraWidth, screenHeight - height, xPosition + width + headExtraWidth,
            screenHeight
        )
        val bottomHead = Rect(
            xPosition, screenHeight - height - headHeight,
            xPosition + width + 2 * headExtraWidth, screenHeight - height
        )
        val topPipe = Rect(
            xPosition + headExtraWidth, 0, xPosition + headExtraWidth + width,
            screenHeight - height - separation - 2 * headHeight
        )
        val topHead = Rect(
            xPosition,
            screenHeight - height - separation - 2 * headHeight,
            xPosition + width + 2 * headExtraWidth, screenHeight - height - separation - headHeight
        )

        val paint = Paint()
        canvas!!.drawBitmap(image, null, bottomPipe, paint)
        canvas.drawBitmap(image, null, bottomHead, paint)
        canvas.drawBitmap(image, null, topPipe, paint)
        canvas.drawBitmap(image, null, topHead, paint)

    }

    override fun update() {
        xPosition -= speed
        if (xPosition <= 0 - width - 2 * headExtraWidth) {
            callback.obstacleOffScreen(this)
        } else {
            val positions = ArrayList<Rect>()
            val bottomPosition = Rect(
                xPosition, screenHeight - height - headHeight,
                xPosition + width + 2 * headExtraWidth,
                screenHeight
            )
            val topPosition = Rect(
                xPosition,
                0,
                xPosition + width + 2 * headExtraWidth,
                screenHeight - height - headHeight - separation
            )

            positions.add(bottomPosition)
            positions.add(topPosition)
            callback.updatePosition(this, positions)
        }
    }
}
