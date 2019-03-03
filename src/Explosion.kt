package game

import GamePanel
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class Explosion (val xStartLocation : Int, val yStartLocation : Int) : GameObject () {

    var lifetime : Int = 15

    init {
        location = GameObjectLocation(xStartLocation,yStartLocation)
        velocity = GameObjectVelocity(0,0)
        size = GameObjectSize(1,0)
        try {
            gameObjectImage  = ImageIO.read(File("explosion.png"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun updateLocation(window : GamePanel) {
        super.updateLocation(window)
        lifetime--
        if (lifetime <= 0) {
            this.destroy = true
        }
    }
}
