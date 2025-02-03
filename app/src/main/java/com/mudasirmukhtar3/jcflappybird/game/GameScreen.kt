package com.mudasirmukhtar3.jcflappybird.game

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.media.MediaPlayer
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.mudasirmukhtar3.jcflappybird.R

class GameScreen(context: Context) :
    SurfaceView(context), SurfaceHolder.Callback, GameManagerCallback {
    var thread: MainThread
    private var gameState = GameState.INITIAL
    private var bird: Bird? = null
    private var background: Background? = null
    private val dm: DisplayMetrics
    private var obstacleManager: ObstacleManager? = null
    private var gameOver: GameOver? = null
    private var gameMessage: GameMessage? = null
    private var scoreSprite: Score? = null
    private var score = 0
    private var birdPosition: Rect? = null
    private var obstaclePositions = HashMap<Obstacle, List<Rect>>()
    private var mpPoint: MediaPlayer? = null
    private var mpSwoosh: MediaPlayer? = null
    private var mpDie: MediaPlayer? = null
    private var mpHit: MediaPlayer? = null
    private var mpWing: MediaPlayer? = null

    init {
        initSounds()
        holder.addCallback(this)
        thread = MainThread(holder, this)
        dm = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(dm)

        initGame()
    }

    private fun initGame() {
        score = 0
        birdPosition = Rect()
        obstaclePositions = HashMap()
        bird = Bird(resources, dm.heightPixels, this)
        background = Background(resources, dm.heightPixels)
        obstacleManager = ObstacleManager(resources, dm.heightPixels, dm.widthPixels, this)
        gameOver = GameOver(resources, dm.heightPixels, dm.widthPixels)
        gameMessage = GameMessage(resources, dm.heightPixels, dm.widthPixels)
        scoreSprite = Score(resources, dm.heightPixels, dm.widthPixels)
    }
    private fun initSounds() {
        mpPoint = MediaPlayer.create(context, R.raw.point)
        mpSwoosh = MediaPlayer.create(context, R.raw.swoosh)
        mpDie = MediaPlayer.create(context, R.raw.die)
        mpHit = MediaPlayer.create(context, R.raw.hit)
        mpWing = MediaPlayer.create(context, R.raw.wing)
    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        thread.setRunning(true)
        thread.start()
    }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            retry = false
        }
    }
    fun update() {
        when (gameState) {
            GameState.PLAYING -> {
                bird!!.update()
                obstacleManager!!.update()
            }

            GameState.GAME_OVER -> bird!!.update()
            GameState.INITIAL -> {
                Log.e("gammeee","initiall")
            }
        }
    }
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (canvas != null) {
            canvas.drawRGB(150, 255, 255)
            background!!.draw(canvas)
            when (gameState) {
                GameState.PLAYING -> {
                    bird!!.draw(canvas)
                    obstacleManager!!.draw(canvas)
                    scoreSprite!!.draw(canvas)
                    calculateCollision()
                }
                GameState.INITIAL -> {
                    bird!!.draw(canvas)
                    gameMessage!!.draw(canvas)
                }
                GameState.GAME_OVER -> {
                    bird!!.draw(canvas)
                    obstacleManager!!.draw(canvas)
                    gameOver!!.draw(canvas)
                    scoreSprite!!.draw(canvas)
                }
            }
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (gameState) {
            GameState.PLAYING -> {
                bird!!.onTouchEvent()
                mpWing!!.start()
            }

            GameState.INITIAL -> {
                bird!!.onTouchEvent()
                mpWing!!.start()
                gameState = GameState.PLAYING
                mpSwoosh!!.start()
            }

            GameState.GAME_OVER -> {
                initGame()
                gameState = GameState.INITIAL
            }
        }
        return super.onTouchEvent(event)
    }

    override fun updatePosition(birdPosition: Rect?) {
        this.birdPosition = birdPosition
    }


    override fun updatePosition(obstacle: Obstacle?, positions: ArrayList<Rect>?) {
        if (obstaclePositions.containsKey(obstacle)) {
            obstaclePositions.remove(obstacle)
        }
        obstaclePositions[obstacle!!] = positions!!
    }

    override fun removeObstacle(obstacle: Obstacle?) {
        obstaclePositions.remove(obstacle)
        score++
        scoreSprite!!.updateScore(score)
        mpPoint!!.start()
    }



    fun calculateCollision() {
        var collision = false
        if (birdPosition!!.bottom > dm.heightPixels) {
            collision = true
        } else {
            for (obstacle in obstaclePositions.keys) {
                val bottomRectangle = obstaclePositions[obstacle]!![0]
                val topRectangle = obstaclePositions[obstacle]!![1]
                if (birdPosition!!.right > bottomRectangle.left && birdPosition!!.left < bottomRectangle.right && birdPosition!!.bottom > bottomRectangle.top) {
                    collision = true
                } else if (birdPosition!!.right > topRectangle.left && birdPosition!!.left < topRectangle.right && birdPosition!!.top < topRectangle.bottom) {
                    collision = true
                }
            }
        }

        if (collision) {
            gameState = GameState.GAME_OVER
            bird!!.collision()
            scoreSprite!!.collision(context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE))
            mpHit!!.start()
            mpHit!!.setOnCompletionListener { mpDie!!.start() }
        }
    }

    companion object {
        private const val APP_NAME = "FlappyBirdClone"
    }
}
