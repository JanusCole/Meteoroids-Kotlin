package game

import java.awt.image.BufferedImage
import kotlin.math.absoluteValue
import GamePanel
import GameImage
import GameHeight
import GameWidth
import score

open class GameObject {

    lateinit var location : GameObjectLocation
    lateinit var velocity : GameObjectVelocity
    lateinit var size : GameObjectSize
    lateinit var gameObjectImage: BufferedImage

    var destroy : Boolean = false

    open fun updateLocation(window : GamePanel) {
        location.xLocation += velocity.xVelocity
        location.yLocation += velocity.yVelocity
        mvwprintw(window, getYLocation(), getXLocation(), gameObjectImage)
    }

    fun getXLocation() : Int {
        return location.xLocation
    }


    fun getYLocation() : Int {
        return location.yLocation
    }

    fun isColliding (gameObject : GameObject) : Boolean {

        if (gameObject === this) {
            return false
        }

        if ((getXLocation() - gameObject.getXLocation()).absoluteValue <= (gameObject.size.xSize + this.size.xSize) &&
            (getYLocation() - gameObject.getYLocation()).absoluteValue <= (gameObject.size.ySize + this.size.ySize)) {
            return true
        }

        return false
    }

    open fun checkForCollision (gameObject : GameObject) : MutableList <GameObject> {
        return mutableListOf()
    }

    fun mvwprintw (window: GamePanel, yLocation : Int, xLocation : Int, image : BufferedImage) {
        window.addImage(GameImage(image, yLocation, xLocation))
    }
}

data class GameObjectLocation (var xLocation : Int, var yLocation : Int)
data class GameObjectVelocity (var xVelocity : Int, var yVelocity : Int)
data class GameObjectSize (val xSize : Int = 1, val ySize : Int = 1)

