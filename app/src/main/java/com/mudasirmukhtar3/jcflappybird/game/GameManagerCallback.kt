package com.mudasirmukhtar3.jcflappybird.game

import android.graphics.Rect
interface GameManagerCallback {
    fun updatePosition(birdPosition: Rect?)
    fun updatePosition(obstacle: Obstacle?, positions: ArrayList<Rect>?)
    fun removeObstacle(obstacle: Obstacle?)
}
