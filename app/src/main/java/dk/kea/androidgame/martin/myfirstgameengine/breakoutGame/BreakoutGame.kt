package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame

import dk.kea.androidgame.martin.myfirstgameengine.core.GameEngine
import dk.kea.androidgame.martin.myfirstgameengine.core.Screen

class BreakoutGame : GameEngine() {
    override fun createStartScreen(): Screen {
        return MainMenuScreen(this)
    }
}