package dk.kea.androidgame.martin.myfirstgameengine

import dk.kea.androidgame.martin.myfirstgameengine.core.GameEngine
import dk.kea.androidgame.martin.myfirstgameengine.core.Screen

class TestGame : GameEngine() {
    override fun createStartScreen(): Screen {
        return TestScreen(this)
    }
}
