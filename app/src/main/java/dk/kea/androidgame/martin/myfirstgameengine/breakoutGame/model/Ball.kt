package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame.model

class Ball {
    var x = 160f
    var y = 240f
    var velocityX = randomDirection()
    var velocityY = randomDirection()

    companion object {
        const val WIDTH = 15f
        const val HEIGHT = 15f
        const val INITIAL_SPEED = 200f
    }

    private fun randomDirection(): Float {
        return if (Math.random() < 0.5) -INITIAL_SPEED else INITIAL_SPEED
    }
}