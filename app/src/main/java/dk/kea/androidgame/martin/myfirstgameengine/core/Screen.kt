package dk.kea.androidgame.martin.myfirstgameengine.core

abstract class Screen protected constructor(protected val gameEngine: GameEngine) {

    abstract fun update(deltaTime: Float)

    abstract fun pause()

    abstract fun resume()
}
