package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame.model

import dk.kea.androidgame.martin.myfirstgameengine.breakoutGame.world.World

class Paddle {
    companion object {
        const val WIDTH = 56f
        const val HEIGHT = 11f
    }

    var x = 160 - WIDTH / 2
    var y = World.MAX_Y - 30
}