package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame

import dk.kea.androidgame.martin.myfirstgameengine.core.GameEngine
import dk.kea.androidgame.martin.myfirstgameengine.core.Screen

class BreakoutGame : GameEngine() {
    override fun createStartScreen(): Screen {
        music = this.loadMusic("music.ogg")
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