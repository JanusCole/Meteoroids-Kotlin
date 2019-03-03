package game

import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import kotlin.random.Random
import GamePanel

import GameHeight
import GameWidth
import score



class Meteor : GameObject () {

    init {
        location = GameObjectLocation(Random.nextInt(GameWidth, (GameWidth * 2)),
                                      Random.nextInt(5, GameHeight - 5))
        velocity = GameObjectVelocity(-1,0)
        size = GameObjectSize(32,32)
        try {
            gameObjectImage  = ImageIO.read(File("bigMeteor.png"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun updateLocation(window : GamePanel) {
        super.updateLocation(window)

        if (location.xLocation < 0) {
            resetLocation()
        }

        if (location.xLocation > GameWidth) {
            velocity.xVelocity = -1
        }
    }

    fun resetLocation() {
        location.xLocation = Random.nextInt(GameWidth + 5, (GameWidth * 2))
    }

    override fun checkForCollision (gameObject : GameObject) : MutableList <GameObject> {
        var newGameObjects : MutableList<GameObject> = mutableListOf()

        if (isColliding(gameObject)) {
            if (gameObject is Laser) {
                newGameObjects.add(LittleMeteor(getXLocation() - 40, getYLocation() - 40, -1))
                newGameObjects.add(LittleMeteor(getXLocation() - 40, getYLocation() + 40, 1))
                newGameObjects.add(Explosion(getXLocation(), getYLocation()))
                newGameObjects.add(Meteor())
                gameObject.destroy = true
                this.destroy = true
                score++
            }

            if (gameObject is AlienShip) {
                newGameObjects.add(Explosion(getXLocation(), getYLocation()))
                newGameObjects.add(Meteor())
                this.destroy=true
            }
        }

        return newGameObjects
    }

}
