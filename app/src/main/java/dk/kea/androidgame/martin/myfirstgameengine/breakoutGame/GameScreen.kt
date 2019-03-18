package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame

import android.graphics.Bitmap
import dk.kea.androidgame.martin.myfirstgameengine.core.GameEngine
import dk.kea.androidgame.martin.myfirstgameengine.core.Screen

class GameScreen(gameEngine: GameEngine) : Screen(gameEngine) {
    enum class State {
        PAUSED,
        RUNNING,
        GAME_OVER
    }

    var background: Bitmap = gameEngine.loadBitmap("background.png")
    var resume: Bitmap = gameEngine.loadBitmap("resume.png")
    var gameOver: Bitmap = gameEngine.loadBitmap("gameover.png")
    var state: State = State.RUNNING
    var world: World = World()
    var worldRenderer: WorldRenderer = WorldRenderer(gameEngine, world)

    override fun update(deltaTime: Float) {
        if (state === State.PAUSED && gameEngine.isTouchDown(0)) { // if paused
            state = State.RUNNING
        }
        if (state === State.GAME_OVER && gameEngine.isTouchDown(0)) { // if game over
            gameEngine.setScreen(MainMenuScreen(gameEngine))
            return
        }
        if (state === State.RUNNING && gameEngine.getTouchY(0) < 35 && gameEngine.getTouchX(0) > 320 - 35) {
            state = State.PAUSED
            return
        }

        gameEngine.drawBitmap(background, 0f, 0f)

        if (state === State.RUNNING) world.update(deltaTime, gameEngine.accelerometer[0], gameEngine.isTouchDown(0), gameEngine.getTouchX(0))
        worldRenderer.render()
        if (world.ball.y > World.MAX_Y - Ball.HEIGHT) {
            state = State.GAME_OVER
        }
        if (state === State.PAUSED) {
            gameEngine.drawBitmap(resume, (160 - resume.width / 2).toFloat(), (240 - resume.height / 2).toFloat())
        }
        if (state === State.GAME_OVER) {
            gameEngine.drawBitmap(gameOver, (160 - gameOver.width / 2).toFloat(), (240 - gameOver.height / 2).toFloat())
        }
    }

    override fun pause() {
        if (state === State.PAUSED) state = State.PAUSED
    }

    override fun resume() {

    }

    override fun dispose() {

    }

}
