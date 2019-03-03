package game

import GamePanel

import gameOver

class GameActions () {

    var gameObjects : MutableList <GameObject> = mutableListOf()
    val player = Player()
    val alien = AlienShip()

    init {
        for (i in 1..20) {
            gameObjects.add(Meteor())
        }
        gameObjects.add(player)
        gameObjects.add(alien)
    }

    fun updateGame(window : GamePanel) {
        gameObjects.forEach {
            it.updateLocation(window)
        }

        var newGameObjects : MutableList <GameObject> = mutableListOf()

        gameObjects.forEach {
            val collidingObject = it
            gameObjects.forEach {
                newGameObjects.addAll(it.checkForCollision(collidingObject))
            }
        }

        gameObjects.addAll(newGameObjects)

        gameObjects.retainAll {it.destroy == false}
    }

    fun processInput (inputChar : Char) {
        when (inputChar) {
            'x' -> gameOver = true
            'n' -> player.moveUp()
            'm' -> player.moveDown()
            'a' -> gameObjects.add(Laser(player.getXLocation() + 75, player.getYLocation() + 15, 1))
        }
    }

}