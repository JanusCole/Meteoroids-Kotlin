package game

import GamePanel
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import GameHeight
import GameWidth
import score

class LittleMeteor (val xStartLocation : Int, val yStartLocation : Int, val yVelocity : Int) : GameObject () {

    init {
        location = GameObjectLocation(xStartLocation,yStartLocation)
        velocity = GameObjectVelocity(-3,yVelocity)
        size = GameObjectSize(5,5)
        try {
            gameObjectImage  = ImageIO.read(File("littleMeteor.png"))
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun updateLocation(window : GamePanel) {
        super.updateLocation(window)

        if (getXLocation() < 0) {
            this.destroy = true
        }
    }

    fun changeDirection() {
        velocity.yVelocity *= -1
    }

    override fun checkForCollision (gameObject : GameObject) : MutableList <GameObject> {
        var newGameObjects : MutableList<GameObject> = mutableListOf()
        if (isColliding(gameObject)) {
            if (gameObject is Laser) {
                gameObject.destroy = true
                this.destroy = true
                newGameObjects.add(Explosion(getXLocation(), getYLocation()))
                score++
            }

            if (gameObject is AlienShip) {
                this.destroy = true
                newGameObjects.add(Explosion(getXLocation(), getYLocation()))
            }

            if ((gameObject is Meteor) || (gameObject is LittleMeteor)) {
                changeDirection()
            }
        }

        return newGameObjects
    }

}
