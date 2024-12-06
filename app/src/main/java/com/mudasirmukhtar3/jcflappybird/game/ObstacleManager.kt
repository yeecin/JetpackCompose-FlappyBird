package com.mudasirmukhtar3.jcflappybird.game

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Rect
import com.mudasirmukhtar3.jcflappybird.R


class ObstacleManager(
    private val resources: Resources,
    private val screenHeight: Int,
    private val screenWidth: Int,
    callback: GameManagerCallback
) :
    ObstacleCallback {
    private val interval = resources.getDimension(R.dimen.obstacle_interval).toInt()
    private val obstacles: ArrayList<Obstacle> = ArrayList<Obstacle>()
    private var progress = 0
    private val speed = resources.getDimension(R.dimen.obstacle_speed).toInt()



    private val callback: GameManagerCallback = callback

    init {
        obstacles.add(Obstacle(resources, screenHeight, screenWidth, this))
    }

    fun update() {
        progress += speed
        if (progress > interval) {
            progress = 0
            obstacles.add(Obstacle(resources, screenHeight, screenWidth, this))
        }
        val duplicate: MutableList<Obstacle> = ArrayList<Obstacle>()
        duplicate.addAll(obstacles)
        for (obstacle in duplicate) {
            obstacle.update()
        }
    }

    fun draw(canvas: Canvas?) {
        for (obstacle in obstacles) {
            obstacle.draw(canvas)
        }
    }

    override fun obstacleOffScreen(obstacle: Obstacle?) {
        obstacles.remove(obstacle)
        callback.removeObstacle(obstacle)
    }

    override fun updatePosition(obstacle: Obstacle?, positions: ArrayList<Rect>) {
        callback.updatePosition(obstacle, positions)
    }
}
