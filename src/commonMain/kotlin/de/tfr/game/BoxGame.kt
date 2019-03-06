package de.tfr.game

import com.soywiz.korge.view.Container
import de.tfr.game.Controller.Control
import de.tfr.game.lib.engine.Loadable
import de.tfr.game.model.Block
import de.tfr.game.model.GameField
import de.tfr.game.model.Orientation
import de.tfr.game.model.Ring
import de.tfr.game.util.Timer
import debug


class BoxGame(val field: GameField) : Controller.ControlListener, Loadable {

    private var cursor: Block
    private var activeRing: Ring? = null
    private val timer: Timer
    private val fallingSpeed = 0.3
    private val firstPause = 0.7
    private val sounds = SoundMachine()

    init {
        cursor = field[field.size - 1][Orientation.Left]
        cursor.active = true
        timer = Timer(firstPause, this::doStep)
    }

    override suspend fun create(container: Container) {
        sounds.create(container)
    }

    private fun doStep() {
        timer.actionTime = fallingSpeed
        move()
    }

    fun move() {
        move(cursor)
    }

    override fun controlEvent(control: Controller.Control) {
        when (control) {
            Control.Left, Control.Right, Control.Top, Control.Bottom -> {
                if (cursor.orientation.toControl() == control) {
                    setBlock()
                }
            }
            Control.Action -> {
                if (debug) {
                    move()
                }
            }
            Control.Esc -> reset()
            Control.Pause -> timer.togglePause()
        }
    }

    private fun Orientation.toControl(): Controller.Control = when (this) {
        Orientation.Left -> Control.Left
        Orientation.Right -> Control.Right
        Orientation.Up -> Control.Top
        Orientation.Down -> Control.Bottom
    }

    fun update(deltaTime: Double) {
        if (!debug) {
            timer.update(deltaTime)
        }
    }

    private fun reset() {
        field.reset()
        timer.reset()
        activeRing = null
        respawnBlock()
    }

    fun respawnBlock(orientation: Orientation) {
        cursor = field[field.size - 1][orientation]
        cursor.active = true
        timer.reset()
        timer.actionTime = firstPause
    }

    fun respawnBlock() = respawnBlock(randomFreeOrientation())

    private fun randomFreeOrientation() = activeRing?.randomFreeSide() ?: Orientation.random()

    private fun move(block: Block) {
        if (block.isInLastRow()) {
            misstep()
            respawnBlock()
        } else {
            cursor.active = false
            val next = field[cursor.row - 1][cursor.orientation]
            next.active = true
            cursor = next
        }
    }

    private fun Block.isInLastRow() = this.row == 0

    fun setBlock() {
        if (cursor.isEmpty()) {
            setBlock(cursor)
        }
    }

    private infix fun Block.isOutsideOf(ring: Ring?) = ring?.index != this.row

    private fun setBlock(block: Block) {
        block.active = false
        block.setFull()
        if (activeRing != null) {
            if (activeRing!!.isFull()) {
                sounds.playCircleOK()
                activeRing = null
            } else if (cursor isOutsideOf activeRing) {
                misstep()
            } else {
                sounds.playLineOK()
            }
        } else {
            sounds.playLineOK()
            activeRing = field[cursor.row]
        }
        respawnBlock()
    }

    private fun misstep() {
        sounds.playLineMissed()
        resetRing()
        cursor.reset()
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

    override fun toString(): String {
        return "Active: $cursor\n$field\n"
    }
}
