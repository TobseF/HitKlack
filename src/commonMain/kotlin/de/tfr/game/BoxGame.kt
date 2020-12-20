package de.tfr.game

import com.soywiz.klogger.Logger
import com.soywiz.korge.view.Container
import de.tfr.game.Controller.Control
import de.tfr.game.lib.engine.Loadable
import de.tfr.game.model.Block
import de.tfr.game.model.GameField
import de.tfr.game.model.Orientation
import de.tfr.game.model.Ring
import de.tfr.game.util.Timer


class BoxGame(private val field: GameField, startOrientation: Orientation = Orientation.Left) :
    Controller.ControlListener, Loadable {

    private val log = Logger<BoxGame>()

    private var cursor: Block
    private var activeRing: Ring? = null
    private val timer: Timer
    private val fallingSpeed = 0.3
    private val firstPause = 0.7
    private val sounds = SoundMachine()

    /**
     * Only for testing
     */
    var nextDirection: Orientation? = null

    init {
        cursor = field.firstRing()[startOrientation]
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

    override fun controlEvent(control: Control) {
        log.debug { "Input: $control" }
        when (control) {
            Control.Left, Control.Right, Control.Top, Control.Bottom -> {
                val currentOrientation = cursor.orientation.toControl()
                if (currentOrientation == control) {
                    setBlock()
                } else {
                    log.debug { "Ignored wrong input. Cursor($currentOrientation) - Button($control)" }
                }
            }
            Control.Action -> {
                move()
            }
            Control.Esc -> reset()
            Control.Pause -> timer.togglePause()
        }
    }

    private fun Orientation.toControl(): Control = when (this) {
        Orientation.Left -> Control.Left
        Orientation.Right -> Control.Right
        Orientation.Up -> Control.Top
        Orientation.Down -> Control.Bottom
    }

    fun update(deltaTime: Double) {
        timer.update(deltaTime)
    }

    private fun reset() {
        field.reset()
        timer.reset()
        activeRing = null
        respawnBlock()
    }

    fun respawnBlock(orientation: Orientation) {
        log.debug { "respawnBlock: $orientation" }
        cursor = field.firstRing()[orientation]
        cursor.active = true
        timer.reset()
        timer.actionTime = firstPause
    }

    fun respawnBlock() = respawnBlock(randomFreeOrientation())

    private fun randomFreeOrientation() = nextDirection ?: (activeRing?.randomFreeSide() ?: Orientation.random())

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
        } else {
            log.debug { "Ignore set, because cursor is empty" }
        }
    }

    private infix fun Block.isOutsideOf(ring: Ring?) = ring?.index != this.row

    private fun setBlock(block: Block) {
        block.active = false
        block.setFull()
        log.debug { "setBlock: $block\n$this" }
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
        log.debug { "resetCursor: $cursor" }
        cursor.reset()
        if (!field.firstRing().isEmpty()) {
            log.warn { this.toString() }
        }
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
        return "Cursor: $cursor\nActive: $activeRing\n$field\n"
    }
}
