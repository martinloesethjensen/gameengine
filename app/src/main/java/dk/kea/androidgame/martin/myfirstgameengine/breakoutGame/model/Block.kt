package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame.model

data class Block(var x: Float, var y: Float, var type: Int) {
    companion object {
        const val WIDTH = 40f
        const val HEIGHT = 18f
    }
}