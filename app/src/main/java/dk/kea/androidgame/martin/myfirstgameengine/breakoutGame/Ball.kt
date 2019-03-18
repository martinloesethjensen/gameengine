package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame

class Ball {
    var x = 160f
    var y = 240f
    var velocityX = 150f
    var velocityY = -150f

    companion object {
        const val WIDTH = 15f
        const val HEIGHT = 15f
    }
}