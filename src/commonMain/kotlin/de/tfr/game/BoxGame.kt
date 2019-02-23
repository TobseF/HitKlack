package de.tfr.game

import de.tfr.game.model.GameField
import de.tfr.game.model.Orientation
import de.tfr.game.model.Ring
import de.tfr.game.model.Stone
import de.tfr.game.model.Stone.State
import de.tfr.game.util.Timer


class BoxGame(val field: GameField) : Controller.ControlListener {

    private var active: Stone
    private var activeRing: Ring? = null
    private val timer: Timer
    private val fallingSpeed = 0.3
    private val firstPause = 0.7
    private val sounds = SoundMachine()

    init {
        active = Stone(field[field.size - 1][Orientation.Left])
        timer = Timer(firstPause, this::doStep)
    }

    private fun doStep() {
        timer.actionTime = fallingSpeed
        move(active)
    }

    override fun controlEvent(control: Controller.Control) {
        when (control) {
            active.block.orientation.toControl() -> setStone()
            Controller.Control.Action -> setStone(active)
            Controller.Control.Esc -> reset()
            Controller.Control.Pause -> timer.togglePause()
        }
    }

    fun Orientation.toControl(): Controller.Control = when (this) {
        Orientation.Left -> Controller.Control.Left
        Orientation.Right -> Controller.Control.Right
        Orientation.Up -> Controller.Control.Top
        Orientation.Down -> Controller.Control.Bottom
    }


    fun getStones() = listOf(active)

    fun update(deltaTime: Double) {
        timer.update(deltaTime)
    }

    private fun reset() {
        field.reset()
        timer.reset()
        activeRing = null
        respawnStone()
    }

    private fun respawnStone() {
        val field = field[field.size - 1][randomFreeOrientation()]
        active = Stone(field)
        timer.reset()
        timer.actionTime = firstPause
    }

    private fun randomFreeOrientation() = activeRing?.randomFreeSide() ?: Orientation.random()


    private fun move(stone: Stone) {
        if (stone.isInLastRow()) {
            misstep()
        } else {
            val next = field[active.block.row - 1][active.block.orientation]
            active.block = next
        }
    }

    private fun Stone.isInLastRow() = this.block.row == 0

    private fun setStone() {
        if (active.block.isEmpty()) {
            setStone(active)
        }
    }

    private infix fun Stone.isOutsideOf(ring: Ring?) = ring?.index != this.block.row

    private fun setStone(stone: Stone) {
        active.block.stone = active
        stone.freeze()
        if (activeRing != null) {
            if (activeRing!!.isFull()) {
                sounds.playCircleOK()
                activeRing = null
            } else if (active isOutsideOf activeRing) {
                misstep()
            } else {
                sounds.playLineOK()
            }
        } else {
            sounds.playLineOK()
            activeRing = field[active.block.row]
        }
        respawnStone()
    }

    private fun misstep() {
        sounds.playLineMissed()
        resetRing()
        if (active.state == State.Set) {
            active.block.reset()
        }
        respawnStone()
    }

    private fun resetRing() {
        activeRing?.reset()
        activeRing = null
        resetLastFullRing()
    }

    private fun firstFull() = field.find(Ring::isFull)

    private fun resetLastFullRing() {
        firstFull()?.reset()
    }

}
