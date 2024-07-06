package com.mudasirmukhtar3.jcflappybird.game

import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.mudasirmukhtar3.jcflappybird.R

class Score(resources: Resources?, private val screenHeight: Int, private val screenWidth: Int) :
    Interactions {
    private val zero: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.zero)
    private val one: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.one)
    private val two: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.two)
    private val three: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.three)
    private val four: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.four)
    private val five: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.five)
    private val six: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.six)
    private val seven: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.seven)
    private val eight: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.eight)
    private val nine: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.nine)
    private val bmpScore: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.score)
    private val bmpBest: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.best)
    private val map = HashMap<Int, Bitmap>()
    private var score = 0
    private var topScore = 0
    private var collision = false


    init {
        map[0] = zero
        map[1] = one
        map[2] = two
        map[3] = three
        map[4] = four
        map[5] = five
        map[6] = six
        map[7] = seven
        map[8] = eight
        map[9] = nine
    }


    private fun convertToBitmaps(amount: Int): ArrayList<Bitmap?> {
        var amount = amount
        val digits: ArrayList<Any?> = ArrayList<Any?>()
        if (amount == 0) {
            digits.add(zero)
        }
        while (amount > 0) {
            val lastDigit = amount % 10
            amount /= 10
            digits.add(map[lastDigit])
        }
        val finalDigits = ArrayList<Bitmap?>()
        for (i in digits.indices.reversed()) {
            finalDigits.add(digits[i] as Bitmap?)
        }
        return finalDigits
    }

    override fun draw(canvas: Canvas?) {
        if (!collision) {
            val digits = convertToBitmaps(score)
            for (i in digits.indices) {
                val x = screenWidth / 2 - digits.size * zero.width / 2 + zero.width * i
                canvas!!.drawBitmap(digits[i]!!, x.toFloat(), (screenHeight / 4).toFloat(), null)
            }
        } else {
            val currentDigits = convertToBitmaps(score)
            val topDigits = convertToBitmaps(topScore)

            canvas!!.drawBitmap(
                bmpScore,
                (screenWidth / 4 - bmpScore.width / 2).toFloat(),
                (3 * screenHeight / 4 - zero.height - bmpScore.height).toFloat(),
                null
            )
            for (i in currentDigits.indices) {
                val x = screenWidth / 4 - currentDigits.size * zero.width + zero.width * i
                canvas.drawBitmap(
                    currentDigits[i]!!,
                    x.toFloat(),
                    (3 * screenHeight / 4).toFloat(),
                    null
                )
            }

            canvas.drawBitmap(
                bmpBest,
                (3 * screenWidth / 4 - bmpBest.width / 2).toFloat(),
                (3 * screenHeight / 4 - zero.height - bmpBest.height).toFloat(),
                null
            )
            for (i in topDigits.indices) {
                val x = 3 * screenWidth / 4 - topDigits.size * zero.width + zero.width * i
                canvas.drawBitmap(
                    topDigits[i]!!,
                    x.toFloat(),
                    (3 * screenHeight / 4).toFloat(),
                    null
                )
            }
        }
    }

    override fun update() {
    }

    fun updateScore(score: Int) {
        this.score = score
    }

    fun collision(prefs: SharedPreferences) {
        collision = true
        topScore = prefs.getInt(SCORE_PREF, 0)
        if (topScore < score) {
            prefs.edit().putInt(SCORE_PREF, score).apply()
            topScore = score
        }
    }

    companion object {
        private const val SCORE_PREF = "Score pref"
    }
}
