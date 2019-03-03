package game

import GamePanel
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import GameHeight
import GameWidth
import score

class Laser (val xStartLocation : Int, val yStartLocation : Int, val xVelocity : Int) : GameObject () {

    init {
        location = GameObjectLocation(xStartLocation,yStartLocation)
        velocity = GameObjectVelocity(xVelocity,0)
        size = GameObjectSize(14,3)
        try {
            if (xVelocity > 0) {
                gameObjectImage = ImageIO.read(File("playerLaser.png"))
            } else {
                gameObjectImage = ImageIO.read(File("alienLaser.png"))
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    override fun updateLocation(window : GamePanel) {
        super.updateLocation(window)

        if ((getXLocation() < 0) || (getXLocation() > GameWidth)) {
            this.destroy = true
        }
    }
}
