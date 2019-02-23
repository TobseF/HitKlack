package de.tfr.game


class Menu : Controller.ControlListener {

    enum class Game { BoxGame, BlockGame }

    var level: Int = 0

    fun levelUp() = level++

    fun levelDown() {
        if (level > 0) {
            level--
        }
    }

    override fun controlEvent(control: Controller.Control) {
    }

}
