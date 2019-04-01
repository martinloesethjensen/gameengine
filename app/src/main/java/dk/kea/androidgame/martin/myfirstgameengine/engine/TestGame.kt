package dk.kea.androidgame.martin.myfirstgameengine.engine

import dk.kea.androidgame.martin.myfirstgameengine.engine.core.GameEngine
import dk.kea.androidgame.martin.myfirstgameengine.engine.core.Screen

class TestGame : GameEngine() {
    override fun createStartScreen(): Screen {
        return TestScreen(this)
    }
}
