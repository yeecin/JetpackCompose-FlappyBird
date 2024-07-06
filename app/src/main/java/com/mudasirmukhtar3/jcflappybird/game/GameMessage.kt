package com.mudasirmukhtar3.jcflappybird.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.mudasirmukhtar3.jcflappybird.R

class GameMessage(
    resources: Resources?,
    private val screenHeight: Int,
    private val screenWidth: Int
) :
    Interactions {
    private val message: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.message)

    override fun draw(canvas: Canvas?) {
        canvas!!.drawBitmap(
            message,
            (screenWidth / 2 - message.width / 2).toFloat(),
            (screenHeight / 4).toFloat(),
            null
        )
    }

    override fun update() {
    }
}
