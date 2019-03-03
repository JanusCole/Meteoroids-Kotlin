
import java.awt.Graphics
import java.awt.LayoutManager
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JPanel

class GamePanel : JPanel {

    var gameImages : MutableList<GameImage> = mutableListOf()
    lateinit var stars: BufferedImage

    init {
        try {
            stars = ImageIO.read(File("stars.jpg"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

    }

    constructor(layout: LayoutManager?, isDoubleBuffered: Boolean) : super(layout, isDoubleBuffered)
    constructor(layout: LayoutManager?) : super(layout)
    constructor(isDoubleBuffered: Boolean) : super(isDoubleBuffered)
    constructor() : super()

    fun resetImages() {
            gameImages.clear()
    }

    fun addImage (image: GameImage) {
            gameImages.add(image)
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponents(g)
            g!!.drawImage(stars, 0,0, this)
            gameImages.forEach {
                g!!.drawImage(it.image, it.xLocation,it.yLocation, this)
            }

    }
}

data class GameImage (val image : BufferedImage, val yLocation : Int, val xLocation : Int)
data class Explosion (val image : BufferedImage, val yLocation : Int, val xLocation : Int, var count : Int)
