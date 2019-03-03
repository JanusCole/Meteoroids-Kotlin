
import kotlin.random.*
import kotlin.math.absoluteValue
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.File
import javax.imageio.ImageIO

data class BigMeteor (val image : Char, var velocity : Int) {

    private var xLocation : Int = Random.nextInt(250, 1500)
    private var yLocation : Int = Random.nextInt(5, 750)
    lateinit var meteor: BufferedImage
    lateinit var explosion: BufferedImage
    init {
        try {
            meteor = ImageIO.read(File("bigMeteor.png"))
            explosion = ImageIO.read(File("explosion.png"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

    }
    fun update(window : GamePanel) {
        mvwprintw(window, yLocation, xLocation, meteor)
        xLocation -= velocity

        if (xLocation < 1) {
            resetLocation()
        }

        if (xLocation > 1200) {
            velocity = 1
        }
    }

    fun resetLocation() {
        xLocation = Random.nextInt(1500, 1700)
    }

    fun getXLocation() : Int {
        return xLocation
    }


    fun getYLocation() : Int {
        return yLocation
    }

    fun changeDirection() {
        velocity *= -1
    }

}

data class LittleMeteor (var xLocation : Int, var yLocation : Int, val xVelocity : Int, var yVelocity : Int) {
    lateinit var meteor: BufferedImage
    init {
        try {
            meteor = ImageIO.read(File("littleMeteor.png"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

    }
    fun update(window : GamePanel, asteroids : MutableList<BigMeteor>, littleMeteors : MutableList<LittleMeteor>) {
        mvwprintw(window, yLocation, xLocation, meteor)
        xLocation -= xVelocity
        yLocation += yVelocity

        if ((xLocation < 1) || (yLocation <=1)) {
            littleMeteors.remove(this)
        }

        littleMeteors.forEach {

            if ((it.yLocation == this.yLocation) && (it.xLocation  == xLocation)) {
                yVelocity *= -1
                it.yVelocity *= -1
            }

        }

        asteroids.forEach {

            if ((it.getYLocation() == this.yLocation) && (it.getXLocation()  == xLocation)) {
                yVelocity *= -1
            }

        }
    }

/*    fun getXLocation() : Int {
        return xLocation
    }


    fun getYLocation() : Int {
        return yLocation
    }*/
}


data class Player (val image : Char, val velocity : Int) {

    var y : Int = 200
    var x : Int = 3
    lateinit var spaceShip: BufferedImage
    init {
        try {
            spaceShip = ImageIO.read(File("player.png"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

    }

    fun update(window : GamePanel, asteroids : MutableList<BigMeteor>, littleMeteors : MutableList<LittleMeteor>) : Boolean {
        mvwprintw(window, y, x, spaceShip)

        littleMeteors.forEach {

            if ((it.yLocation == this.y) && ((it.yLocation - x).absoluteValue < 2)) {
                return false;
            }

        }

        asteroids.forEach {

            if ((it.getYLocation() == this.y) && (it.getXLocation() <= x)) {
                return false;
            }

        }

        return true;

    }

    fun moveUp() {
        y -= 10

        if (y <= 1) {y = 1}
    }

    fun moveDown() {
        y += 10

        if (y >= 790) {y = 790}
    }

}

data class Laser (var yLocation : Int) {

    var xLocation : Int = 75
    lateinit var laser: BufferedImage
    lateinit var explosion: BufferedImage
    init {
        try {
            laser = ImageIO.read(File("playerLaser.png"))
            explosion = ImageIO.read(File("explosion.png"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

    }

    fun update(window : GamePanel, lasers : MutableList <Laser>, asteroids : MutableList<BigMeteor>, littleMeteors : MutableList<LittleMeteor>, alien : Alien) : Int {
        mvwprintw(window, yLocation, xLocation, laser)
        xLocation += 2
        var newScore : Int = 0

        littleMeteors.forEach {

            if ((it.yLocation == this.yLocation) && ((it.xLocation - xLocation).absoluteValue < 2)) {
//                littleMeteors.remove(it)
                newScore++
                lasers.remove(this)
            }

        }

        asteroids.forEach {

            if (((it.getYLocation() - yLocation).absoluteValue < 50) && ((it.getXLocation() - xLocation).absoluteValue < 50)) {
                it.resetLocation()
                newScore++
                lasers.remove(this)
                littleMeteors.add(LittleMeteor(xLocation, yLocation, 2, -1))
                littleMeteors.add(LittleMeteor(xLocation, yLocation, 2, 1))
            }

        }

        if (((alien.yLocation - yLocation).absoluteValue < 50) && ((alien.xLocation - xLocation).absoluteValue < 50)) {
            alien.reset()
            newScore += 20
            lasers.remove(this)
        }

        if (xLocation > 1400) {
            lasers.remove(this)
        }

        return newScore
    }

}


class Alien () {
    lateinit var alien: BufferedImage
    init {
        try {
            alien = ImageIO.read(File("alien.png"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

    }
    var xLocation : Int = Random.nextInt(500, 1100)
    var yLocation : Int = Random.nextInt(-100, -50)

    var velocity : Int = 1

    fun reset() {
        yLocation = Random.nextInt(-200, -100)
    }

    fun update (window : GamePanel, asteroids : MutableList<BigMeteor>, AlienLasers : MutableList <AlienLaser>) {

        mvwprintw(window, yLocation, xLocation, alien)
        yLocation += velocity

        asteroids.forEach {

            if (((it.getYLocation() == this.yLocation) || (it.getYLocation() == this.yLocation - 1)) && ((it.getXLocation() - xLocation).absoluteValue < 2)) {
                it.changeDirection()
            }

        }

        if (Random.nextInt(1, 100) == 5) {
            AlienLasers.add(AlienLaser(xLocation, yLocation))
            AlienLasers.add(AlienLaser(xLocation - 2, yLocation))
            AlienLasers.add(AlienLaser(xLocation - 4, yLocation))
        }

        if (yLocation > 600) {velocity = -1}
        if (yLocation < -100) {
            velocity = 1
            xLocation = Random.nextInt(500, 1100)}

    }

}

data class AlienLaser (var xLocation : Int, var yLocation : Int) {
    lateinit var laser: BufferedImage
    init {
        try {
            laser = ImageIO.read(File("alienLaser.png"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

    }
    fun update(window : GamePanel, AlienLasers : MutableList <AlienLaser>, player : Player) : Boolean {
        mvwprintw(window, yLocation, xLocation, laser)
        xLocation -= 2

        if ((player.y == this.yLocation) && ((player.x - xLocation).absoluteValue < 2)) {
            return true
        }
        if (xLocation < 1) {
            AlienLasers.remove(this)
        }

        return false
    }

}

fun mvwprintw (window: GamePanel, yLocation : Int, xLocation : Int, image : BufferedImage) {
    window.addImage(GameImage(image, yLocation, xLocation))
}
