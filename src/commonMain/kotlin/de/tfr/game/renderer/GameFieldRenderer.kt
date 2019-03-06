package de.tfr.game.renderer


import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Graphics
import com.soywiz.korge.view.graphics
import com.soywiz.korma.geom.vector.circle
import com.soywiz.korma.geom.vector.rect
import de.tfr.game.lib.actor.Point
import de.tfr.game.lib.engine.Loadable
import de.tfr.game.model.Block
import de.tfr.game.model.GameField
import de.tfr.game.model.Orientation
import de.tfr.game.model.Ring
import de.tfr.game.ui.BLACK
import de.tfr.game.ui.GREEN_LIGHT
import de.tfr.game.ui.GREEN_LIGHT2
import de.tfr.game.util.extensions.drawFill
import de.tfr.game.util.extensions.startFill


class GameFieldRenderer(point: Point, val field: GameField) : Point by point, Loadable {

    private val gap = 6
    private val blockWith = 18.0
    private val radius = 8f
    private fun thisX() = x
    private fun thisY() = y

    override suspend fun create(container: Container) {
        createBackground(container)
        createFieldCache(container)
    }

    private fun createFieldCache(container: Container) {

        for (ring in field) {
            for (block in ring) {
                val ringI = ring.index
                val blockI = block.orientation
                val field = GameField(field.size)
                field.setActive()
                val cacheImage = container.graphics()
                val newBlock = field[ringI][blockI]
                newBlock.state = Block.State.Full
                newBlock.active = false
                cacheImage.renderField(field)
                cacheImage.visible = false
                block.image = cacheImage
            }
        }
    }

    private fun createBackground(container: Container) {
        val background = container.graphics()
        renderBackground(field.size, background)
        background.drawFill(GREEN_LIGHT2) {
            it.circle(x, y, radius)
        }
        val emptyField = GameField(field.size)
        background.renderField(emptyField)
    }

    fun Graphics.renderField(gameField: GameField) {
        gameField.forEach { this.renderRing(it) }
    }

    private fun renderBackground(fieldSize: Int, renderer: Graphics) {
        renderer.startFill(GREEN_LIGHT)
        val radius = getFieldSize(fieldSize)
        renderer.rect(x - radius, y - radius, radius * 2, radius * 2)
        renderer.endFill()
    }

    fun getFieldSize(fieldSize: Int): Double = (blockWith / 2.0) + fieldSize * (gap + blockWith)

    private fun Graphics.renderRing(ring: Ring) {
        ring.forEach { renderBlock(it) }
    }

    private fun Graphics.renderBlock(block: Block) {
        val distance = gap + blockWith + (block.row * (gap + blockWith))
        when (block.orientation) {
            Orientation.Left -> renderBlock(block, thisX() - distance, thisY())
            Orientation.Right -> renderBlock(block, thisX() + distance, thisY())
            Orientation.Down -> renderBlock(block, thisX(), thisY() + distance)
            Orientation.Up -> renderBlock(block, thisX(), thisY() - distance)
        }
    }

    private fun Graphics.renderBlock(block: Block, x: Double, y: Double) {
        val length = ((block.row) * (blockWith * 2)) + ((2 * gap) * (block.row + 1))
        val side = length / 2
        val width = blockWith / 2
        if (block.active) {
            return
        }
        when (block.state) {
            Block.State.Empty -> this.startFill(GREEN_LIGHT2)
            Block.State.Full -> this.startFill(BLACK)
        }

        when (block.orientation) {
            Orientation.Left -> rect(x - width, y - side, blockWith, length)
            Orientation.Right -> rect(x - width, y - side, blockWith, length)
            Orientation.Down -> rect(x - side, y - width, length, blockWith)
            Orientation.Up -> rect(x - side, y - width, length, blockWith)
        }

        when (block.orientation) {
            Orientation.Left -> {
                triangleLeftUp(blockWith, x - width, y + side)
                triangleLeftDown(blockWith, x - width, y - side)
            }
            Orientation.Right -> {
                triangleRightUp(blockWith, x - width, y + side)
                triangleRightDown(blockWith, x - width, y - side)
            }
            Orientation.Down -> {
                triangleDownLeft(blockWith, x - side, y - width)
                triangleDownRight(blockWith, x + side, y - width)
            }
            Orientation.Up -> {
                triangleUpLeft(blockWith, x - side, y - width)
                triangleUpRight(blockWith, x + side, y - width)
            }

        }
        endFill()
    }

}