package com.mudasirmukhtar3.jcflappybird.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.mudasirmukhtar3.jcflappybird.R

class GameOver(resources: Resources?, private val screenHeight: Int, private val screenWidth: Int) :
    Interactions {
    private val gameOver: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.gameover)

    override fun draw(canvas: Canvas?) {
        canvas!!.drawBitmap(
            gameOver,
            (screenWidth / 2 - gameOver.width / 2).toFloat(),
            (screenHeight / 4).toFloat(),
            null
        )
    }
    override fun update() {
    }
}