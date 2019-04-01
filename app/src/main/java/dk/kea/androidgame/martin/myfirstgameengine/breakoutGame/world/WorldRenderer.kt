package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame.world

import android.graphics.Bitmap
import dk.kea.androidgame.martin.myfirstgameengine.breakoutGame.model.Block
import dk.kea.androidgame.martin.myfirstgameengine.engine.core.GameEngine


class WorldRenderer(var gameEngine: GameEngine, var world: World) {
    var ballImage: Bitmap = gameEngine.loadBitmap("breakout/ball.png")
    var paddleImage: Bitmap = gameEngine.loadBitmap("breakout/paddle.png")
    var block: Block? = null // do I need this?
    var blockImage: Bitmap = gameEngine.loadBitmap("breakout/blocks.png")
    fun render() {
        gameEngine.drawBitmap(ballImage, world.ball.x, world.ball.y)
        gameEngine.drawBitmap(paddleImage, world.paddle.x, world.paddle.y)
        for (block: Block in world.blocks) {
            this.block = block // maybe remove
            gameEngine.drawBitmap(blockImage, block.x.toInt(), block.y.toInt(),
                    0, (block.type * Block.HEIGHT).toInt(),
                    Block.WIDTH.toInt(), Block.HEIGHT.toInt())
        }
    }
}