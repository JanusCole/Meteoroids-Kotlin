package game

import GamePanel
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import GameHeight
import GameWidth
import score
import gameOver

class Player : GameObject () {

    init {
        location = GameObjectLocation(3,GameHeight / 2)
        velocity = GameObjectVelocity(0,0)
        size = GameObjectSize(6,6)
        try {
            gameObjectImage  = ImageIO.read(File("player.png"))
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun updateLocation(window : GamePanel) {
        super.updateLocation(window)

        val direction = if (velocity.yVelocity > 0) -1 else 1
        velocity.yVelocity = if (velocity.yVelocity != 0) velocity.yVelocity + direction else 0

        if (location.yLocation < 1) {
            location.yLocation = 1
        }

        if (location.yLocation > GameHeight - 1) {
            location.yLocation = GameHeight - 1
        }
   }

    fun moveUp() {
        velocity.yVelocity = -5
    }

    fun moveDown() {
        velocity.yVelocity = 5
    }

    override fun checkForCollision (gameObject : GameObject) : MutableList <GameObject> {

        if (isColliding(gameObject)) {
            if (gameObject.velocity.xVelocity < 0) {
                gameOver = true
            }
        }

        return mutableListOf()
    }
}
