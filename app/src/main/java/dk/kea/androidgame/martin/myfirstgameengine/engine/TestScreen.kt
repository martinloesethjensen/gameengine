package dk.kea.androidgame.martin.myfirstgameengine.engine

import android.graphics.Bitmap
import android.graphics.Color
import dk.kea.androidgame.martin.myfirstgameengine.engine.core.GameEngine
import dk.kea.androidgame.martin.myfirstgameengine.engine.core.Screen
import dk.kea.androidgame.martin.myfirstgameengine.engine.sound.Music
import dk.kea.androidgame.martin.myfirstgameengine.engine.sound.Sound

class TestScreen(gameEngine: GameEngine) : Screen(gameEngine) {
    private var x = 0f

    private var y = 100
    private val bitmap: Bitmap = gameEngine.loadBitmap("engine/bob.png")
    private val sound: Sound = gameEngine.loadSound("engine/blocksplosion.wav")
    private val backgroundMusic: Music = gameEngine.loadMusic("engine/music.ogg")
    private var isPlaying = false
    init {
        isPlaying = true
    }

    override fun update(deltaTime: Float) {

        println("Delta time: $deltaTime")
//                Log.d("GameLoop", "FPS: " + gameEngine.getFramePerSecond());
        gameEngine.clearFrameBuffer(Color.GREEN)

        x += 250 * deltaTime
        if (x > 320 + bitmap.width) {
            x = (0 - bitmap.width).toFloat()
        }

        if (gameEngine.isTouchDown(0)) {
            x = gameEngine.getTouchX(0).toFloat()
            y = gameEngine.getTouchY(0)
            sound.play(SOUND)

            isPlaying = if (backgroundMusic.isPlaying) {
                backgroundMusic.pause()
                false
            } else {
                backgroundMusic.play()
                true
            }
        }

        var xPoint = gameEngine.accelerometer[0]
        var yPoint = -1 * gameEngine.accelerometer[1]
        xPoint = (gameEngine.frameBufferWidth / 2).toFloat() - xPoint / 10 * gameEngine.frameBufferWidth / 2
        yPoint = (gameEngine.frameBufferHeight / 2).toFloat() - yPoint / 10 * gameEngine.frameBufferHeight / 2

        gameEngine.clearFrameBuffer(Color.GREEN)
        gameEngine.drawBitmap(bitmap, x - 64, (y - 64).toFloat())
        gameEngine.drawBitmap(bitmap, (xPoint.toInt() - 64).toFloat(), (yPoint.toInt() - 64).toFloat())
        //gameEngine.drawBitmap(bitmap, 200, 300, 64, 64, 64, 64);
    }

    override fun pause() {}

    override fun resume() {}

    override fun dispose() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private const val SOUND = 1f
    }
}
