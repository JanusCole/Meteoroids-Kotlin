package game

import GamePanel
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import kotlin.random.Random
import GameHeight
import GameWidth
import score

class AlienShip : GameObject () {

    init {
        location = GameObjectLocation(Random.nextInt(250, GameWidth - 5),
                                      Random.nextInt(-100, -50))
        velocity = GameObjectVelocity(0,1)
        size = GameObjectSize(10,10)
        try {
            gameObjectImage  = ImageIO.read(File("alien.png"))
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun updateLocation(window : GamePanel) {
        super.updateLocation(window)

        if (location.yLocation > GameHeight - 5) {
            velocity.yVelocity = -1
        }

        if (location.yLocation < -10) {
            velocity.yVelocity = 1
            location.xLocation = Random.nextInt(250, GameWidth - 5)
        }
    }

    override fun checkForCollision (gameObject : GameObject) : MutableList <GameObject> {
        var newGameObjects : MutableList<GameObject> = mutableListOf()
        if (isColliding(gameObject)) {
            if (gameObject is Laser) {
                gameObject.destroy = true
                newGameObjects.add(Explosion(getXLocation(), getYLocation()))
                location = GameObjectLocation(Random.nextInt(250, GameWidth - 5),
                    Random.nextInt(-100, -50))
                score++
            }
        }

        if (Random.nextInt(1, 30000) == 5) {
            newGameObjects.add(Laser(getXLocation() - 50, getYLocation(), -1))
            newGameObjects.add(Laser(getXLocation() - 70, getYLocation(), -1))
            newGameObjects.add(Laser(getXLocation() - 90, getYLocation(), -1))
        }

        return newGameObjects
    }
}
