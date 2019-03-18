package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame


class World {
    companion object {

        const val MIN_X = 0f
        const val MAX_X = 319f
        const val MIN_Y = 36f
        const val MAX_Y = 479f
    }

    var ball: Ball = Ball()

    var paddle: Paddle = Paddle()
    var blocks: ArrayList<Block> = arrayListOf()

    init {
        generateBlocks()
    }

    fun update(deltaTime: Float, accelerometerX: Float, touchDown: Boolean, touchX: Int) {
        ball.x += ball.velocityX * deltaTime
        ball.y += ball.velocityY * deltaTime
        if (ball.x < MIN_X) { // if it reaches the end of the world --> bounce from the wall
            ball.velocityX = -ball.velocityX
            ball.x = MIN_X
        }
        if (ball.x > MAX_X - Ball.WIDTH) {
            ball.velocityX = -ball.velocityX
            ball.x = MAX_X - Ball.WIDTH
        }
        if (ball.y < MIN_Y) {
            ball.velocityY = -ball.velocityY
            ball.y = MIN_Y
        }
//        if (ball.y > MAX_Y - Ball.HEIGHT) {
//            GameScreen.State.GAME_OVER
//            ball.velocityY = -ball.velocityY
//            ball.y = MAX_Y - Ball.HEIGHT
//        }

        paddle.x = paddle.x - accelerometerX * 75 * deltaTime
        // Move paddle based on touch, only for testing in emulator! TODO remove after testing
        if (touchDown) {
            paddle.x = touchX - Paddle.HEIGHT / 2
        }
        // check collision
        checkCollisionBallAndPaddle()
        // make sure the paddle stops at the edge
        if (paddle.x < MIN_X) paddle.x = MIN_X
        if (paddle.x + Paddle.WIDTH > MAX_X) paddle.x = MAX_X - Paddle.WIDTH


    } // end of update method

    private fun checkCollisionBallAndPaddle() {
        if (ball.y > paddle.y) return
        if ((ball.x >= paddle.x) && (ball.x + Ball.WIDTH < paddle.x + Paddle.WIDTH) && (ball.y + Ball.HEIGHT > paddle.y)) {
            ball.velocityY *= -1

        }
    }

    //    private fun generateBlocks() {
//        blocks.clear()
//        var y_coo = 60
//        for ((type, y) in (60 until (20 + 8 * Block.HEIGHT).toInt()).withIndex() ) {
//            for (x in 20 until (320 - Block.WIDTH).toInt()) {
//                blocks.add(Block(x.toFloat(), y.toFloat(), type))
//            }
//            y_coo = (y + Block.HEIGHT).toInt()
//        }
//    }
    private fun generateBlocks() {
        blocks.clear()
        var y = 60
        var type = 0
        while (y < 60 + 8 * (Block.HEIGHT + 4)) {
            var x = 20
            while (x < 320 - Block.WIDTH) {
                blocks.add(Block(x.toFloat(), y.toFloat(), type))
                x = x + Block.WIDTH.toInt() + 4
            }
            y = y + Block.HEIGHT.toInt()
            type++
        }
    }
}
