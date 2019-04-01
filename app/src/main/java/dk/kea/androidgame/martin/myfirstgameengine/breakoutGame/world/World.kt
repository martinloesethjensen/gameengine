package dk.kea.androidgame.martin.myfirstgameengine.breakoutGame.world

import dk.kea.androidgame.martin.myfirstgameengine.breakoutGame.CollisionListener
import dk.kea.androidgame.martin.myfirstgameengine.breakoutGame.model.Ball
import dk.kea.androidgame.martin.myfirstgameengine.breakoutGame.model.Block
import dk.kea.androidgame.martin.myfirstgameengine.breakoutGame.model.Paddle


class World(private var collisionListener: CollisionListener) {
    companion object {
        const val MIN_X = 0f
        const val MAX_X = 319f
        const val MIN_Y = 36f
        const val MAX_Y = 479f
    }

    var ball: Ball = Ball()

    var paddle: Paddle = Paddle()
    var blocks: ArrayList<Block> = arrayListOf()

    var points = 0
    var level = 1
    var hits = 0
    var lives = 3
    var lostLife = false
    var gameOver = false

    init {
        generateBlocks()
    }

    fun update(deltaTime: Float, accelerometerX: Float, touchDown: Boolean, touchX: Int) {
        ball.x += ball.velocityX * deltaTime
        ball.y += ball.velocityY * deltaTime

        paddle.x = ball.x - (Paddle.WIDTH / 2) // todo: Remove when all testing is done. This is only used for testing.

        if (ball.x < MIN_X) { // if it reaches the end of the world --> bounce from the wall
            ball.velocityX = -ball.velocityX
            ball.x = MIN_X
            collisionListener.collisionWall()
        }
        if (ball.x > MAX_X - Ball.WIDTH) {
            ball.velocityX = -ball.velocityX
            ball.x = MAX_X - Ball.WIDTH
            collisionListener.collisionWall()
        }
        if (ball.y < MIN_Y) {
            ball.velocityY = -ball.velocityY
            ball.y = MIN_Y
            collisionListener.collisionWall()
        }/*
        if (ball.y > MAX_Y ) {
            GameScreen.State.GAME_OVER
            ball.velocityY = -ball.velocityY
            ball.y = MAX_Y - Ball.HEIGHT
        }*/

        if (ball.y > MAX_Y) {
            lives--
            print(lives)
            lostLife = true
            ball.y = paddle.y - Ball.HEIGHT - 5
            ball.x = paddle.x + Paddle.WIDTH / 2
            ball.velocityY = -Ball.INITIAL_SPEED
            if (lives == 0) gameOver = true
            return
        }

        paddle.x = paddle.x - accelerometerX * 75 * deltaTime
        // Move paddle based on touch, only for testing in emulator! TODO remove after testing
        if (touchDown) {
            paddle.x = touchX - Paddle.HEIGHT / 2
        }

        // make sure the paddle stops at the edge
        if (paddle.x < MIN_X) paddle.x = MIN_X
        if (paddle.x + Paddle.WIDTH > MAX_X) paddle.x = MAX_X - Paddle.WIDTH

        // check collision
        checkCollisionBallAndPaddle(deltaTime = deltaTime)
        collideBallAndBlocks(deltaTime = deltaTime)

        if (blocks.size == 0) {
            level++
            generateBlocks()
            ball.x = 160f
            ball.y = 320 - 40f
            ball.velocityY = -Ball.INITIAL_SPEED * 1.3f
            ball.velocityX = Ball.INITIAL_SPEED * 1.3f
        }
    } // end of update method

    private fun checkCollisionBallAndPaddle(deltaTime: Float) {
        //if (ball.y > paddle.y) return
        if (((ball.x + Ball.WIDTH) >= paddle.x) && (ball.x < (paddle.x + Paddle.WIDTH)) && ((ball.y + Ball.HEIGHT) > paddle.y)) {
            ball.y = ball.y - ball.velocityY * deltaTime * 1.01f
            ball.velocityY *= -1

            collisionListener.collisionPaddle()

            // increment the hits to increase the level
            hits++
            print(hits)
            if (hits == 5) {
                print(hits)
                hits = 0
                if (level > 1) {
                    ball.velocityY = ball.velocityY * (1 + level / 100)
                    ball.velocityX = ball.velocityX * (1 + level / 100)
                    advanceBlocks()
                }
            }
        }
    }

    private fun collideBallAndBlocks(deltaTime: Float) {
        var i = 0
        lateinit var block: Block
        while (i < blocks.size) {
            block = blocks[i]
            if (collideRectangles(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x, block.y, Block.WIDTH, Block.HEIGHT)) {
                blocks.removeAt(i)
                val oldVelocityX = ball.velocityX
                val oldVelocityY = ball.velocityY
                reflectBall(ball, block)
                // back out the ball with 1% to avoid multiple interactions
                ball.x = ball.x - oldVelocityX * deltaTime * 1.01f
                ball.y = ball.y - oldVelocityY * deltaTime * 1.01f
                points += 10 - block.type
                collisionListener.collisionBlocks()
                break // no need to check collision with other block when it hit this block
            }
            i++
        }
    }

    private fun reflectBall(ball: Ball, block: Block) {
        // check if ball hits the top left corner of the block
        if (collideRectangles(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x, block.y, 1f, 1f)) {
            if (ball.velocityX > 0) ball.velocityX = -ball.velocityX
            if (ball.velocityY > 0) ball.velocityY = -ball.velocityY
            return
        }
        // check if ball hits the top right corner of the block
        if (collideRectangles(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, (block.x + Block.WIDTH), block.y, 1f, 1f)) {
            if (ball.velocityX < 0) ball.velocityX = -ball.velocityX
            if (ball.velocityY > 0) ball.velocityY = -ball.velocityY
            return
        }
        // check if ball hits the bottom left corner of the block
        if (collideRectangles(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x, (block.y + Block.HEIGHT), 1f, 1f)) {
            if (ball.velocityX > 0) ball.velocityX = -ball.velocityX
            if (ball.velocityY < 0) ball.velocityY = -ball.velocityY
            return
        }
        // check if ball hits the bottom right corner of the block
        if (collideRectangles(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, (block.x + Block.WIDTH), (block.y + Block.HEIGHT), 1f, 1f)) {
            if (ball.velocityX < 0) ball.velocityX = -ball.velocityX
            if (ball.velocityY < 0) ball.velocityY = -ball.velocityY
            return
        }
        // check the top edge of the block
        if (collideRectangles(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x, block.y, Block.WIDTH, 1f)) {
            if (ball.velocityY > 0) ball.velocityY = -ball.velocityY
            return
        }
        // check the bottom edge of the block
        if (collideRectangles(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x, (block.y + Block.HEIGHT), Block.WIDTH, 1f)) {
            if (ball.velocityY < 0) ball.velocityY = -ball.velocityY
            return
        }
        // check the left edge of the block
        if (collideRectangles(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x, block.y, 1f, Block.HEIGHT)) {
            if (ball.velocityX > 0) ball.velocityY = -ball.velocityY
            return
        }
        // check the right edge of the block
        if (collideRectangles(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, (block.x + Block.WIDTH), block.y, 1f, Block.HEIGHT)) {
            if (ball.velocityX < 0) ball.velocityY = -ball.velocityY
            return
        }
    }

    private fun collideRectangles(x: Float, y: Float, width: Float, height: Float, x2: Float, y2: Float, width2: Float, height2: Float): Boolean {
        if (x < (x2 + width2) && (x + width) > x2 && y < (y2 + height2) && (y + height) > y2) {
            return true
        }
        return false
    }

    private fun generateBlocks() {
        blocks.clear()
        var y = 60
        var type = 0
        while (y < 60 + 8 * (Block.HEIGHT + 4)) {
            var x = 30
            while (x < 320 - Block.WIDTH) {
                blocks.add(Block(x.toFloat(), y.toFloat(), type))
                x += Block.WIDTH.toInt() + 4
            }
            y += Block.HEIGHT.toInt() + 4
            type++
        }
    }

    private fun advanceBlocks() {
        var block: Block
        for (i in 0 until blocks.size) {
            block = blocks[i]
            block.y = block.y + 10
        }
    }
}
