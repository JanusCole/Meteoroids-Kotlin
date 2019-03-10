
import game.GameActions
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame
import javax.swing.JLabel
import java.awt.Font
import java.lang.Thread.sleep

var inputChar : Char = ' '

public val GameHeight = 800
val GameWidth = 1200

var gameOver : Boolean = false
var score : Int = 0

fun main (args : Array<String>) {
    var meteroids = Meteoroids()
    meteroids.play()
}

class Meteoroids : KeyListener {

    fun play() {

        var gameActions = GameActions()

        var gamePanel : GamePanel = GamePanel()
        var jFrame: JFrame = JFrame("Meteroids")

        var score : Int = 0
        jFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        jFrame.setSize(1200, 800)
        jFrame.setLocationRelativeTo(null)

        jFrame.addKeyListener(this)
        jFrame.add(gamePanel)
        jFrame.isVisible = true

        while (!gameOver) {

            gamePanel.resetImages()

            gameActions.processInput(inputChar)
            inputChar = ' '
            gameActions.updateGame(gamePanel)

            gamePanel.revalidate();
            gamePanel.repaint()

            sleep(5)

        }

        sleep(5000)
        jFrame.dispose()

    }
    override fun keyPressed(e: KeyEvent?) {
        inputChar = e!!.keyChar
    }

    override fun keyReleased(e: KeyEvent?) {
    }

    override fun keyTyped(e: KeyEvent?) {
    }

}
