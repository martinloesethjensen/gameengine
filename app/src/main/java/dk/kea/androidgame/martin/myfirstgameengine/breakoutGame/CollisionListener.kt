package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame

interface CollisionListener {
    fun collisionWall(): Unit {

    }

    fun collisionPaddle(): Unit {

    }

    fun collisionBlocks(): Unit {

    }
}