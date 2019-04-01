package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame

import dk.kea.androidgame.martin.myfirstgameengine.engine.core.GameEngine
import dk.kea.androidgame.martin.myfirstgameengine.engine.core.Screen

class BreakoutGame : GameEngine() {
    override fun createStartScreen(): Screen {
        music = this.loadMusic("breakout/music.ogg")
        return MainMenuScreen(this)
    }

    override fun onPause() {
        super.onPause()
        music.pause()
    }

    override fun onResume() {
        super.onResume()
        music.play()
    }
}