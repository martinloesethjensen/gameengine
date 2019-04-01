package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame

import android.graphics.Bitmap
import dk.kea.androidgame.martin.myfirstgameengine.engine.core.GameEngine
import dk.kea.androidgame.martin.myfirstgameengine.engine.core.Screen

class MainMenuScreen(override val gameEngine: GameEngine) : Screen(gameEngine) {
    private var mainMenu: Bitmap = gameEngine.loadBitmap("breakout/mainmenu.png")
    private var insertCoin: Bitmap = gameEngine.loadBitmap("breakout/insertcoin.png")
    var passedTime: Float = 0f
    var startTime: Long = System.nanoTime()

    override fun update(deltaTime: Float) {
        if (gameEngine.isTouchDown(0) && (passedTime) > 0.5f) {
            gameEngine.setScreen(GameScreen(gameEngine))
            return
        }
        gameEngine.drawBitmap(mainMenu, 0f, 0f)
        passedTime += deltaTime
        if ((passedTime - passedTime.toInt()) > 0.75f) {
            gameEngine.drawBitmap(insertCoin, (160 - (insertCoin.width / 2)).toFloat(), 320f)
        }
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {

    }
}