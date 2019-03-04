package de.tfr.game.renderer


import com.soywiz.korge.view.Graphics
import com.soywiz.korma.geom.vector.circle
import com.soywiz.korma.geom.vector.rect
import de.tfr.game.lib.actor.Point
import de.tfr.game.model.*
import de.tfr.game.ui.BLACK
import de.tfr.game.ui.GRAY_DARK
import de.tfr.game.ui.GREEN_LIGHT
import de.tfr.game.ui.GREEN_LIGHT2
import de.tfr.game.util.extensions.startFill


class GameFieldRenderer(point: Point) : Point by point {

    private val gap = 6
    private val blockWith = 18.0
    private val radius = 8f
    private lateinit var renderer: Graphics

    fun render(field: GameField, renderer: Graphics) {
        this.renderer = renderer
        renderBackground(field, renderer)
        renderer.startFill(GREEN_LIGHT2)
        renderer.circle(x, y, radius)

        field.forEach(this::renderRing)
    }

    private fun renderBackground(field: GameField, renderer: Graphics) {
        renderer.startFill(GREEN_LIGHT)
        val radius = getFieldSize(field)
        renderer.rect(x - radius, y - radius, radius * 2, radius * 2)
        renderer.endFill()
    }

    fun getFieldSize(field: GameField): Double = (blockWith / 2.0) + field.size * (gap + blockWith)

    private fun renderRing(ring: Ring) {
        ring.forEach { renderBlock(it, it.stone) }
    }

    fun renderStone(stone: Stone) {
        renderBlock(stone.block, stone)
    }

    private fun renderBlock(block: Block, stone: Stone?) {
        val distance = gap + blockWith + (block.row * (gap + blockWith))
        when (block.orientation) {
            Orientation.Left -> renderBlock(block, stone, x - distance, y)
            Orientation.Right -> renderBlock(block, stone, x + distance, y)
            Orientation.Down -> renderBlock(block, stone, x, y + distance)
            Orientation.Up -> renderBlock(block, stone, x, y - distance)
        }
    }

    private fun renderBlock(block: Block, stone: Stone?, x: Double, y: Double) {
        val length = ((block.row) * (blockWith * 2)) + ((2 * gap) * (block.row + 1))
        val side = length / 2
        val width = blockWith / 2
        when {
            stone == null -> renderer.startFill(GREEN_LIGHT2)
            stone.state == Stone.State.Active -> renderer.startFill(BLACK)
            stone.state == Stone.State.Set -> renderer.startFill(GRAY_DARK)
        }

        when (block.orientation) {
            Orientation.Left -> renderer.rect(x - width, y - side, blockWith, length)
            Orientation.Right -> renderer.rect(x - width, y - side, blockWith, length)
            Orientation.Down -> renderer.rect(x - side, y - width, length, blockWith)
            Orientation.Up -> renderer.rect(x - side, y - width, length, blockWith)
        }

        when (block.orientation) {
            Orientation.Left -> {
                renderer.triangleLeftUp(blockWith, x - width, y + side)
                renderer.triangleLeftDown(blockWith, x - width, y - side)
            }
            Orientation.Right -> {
                renderer.triangleRightUp(blockWith, x - width, y + side)
                renderer.triangleRightDown(blockWith, x - width, y - side)
            }
            Orientation.Down -> {
                renderer.triangleDownLeft(blockWith, x - side, y - width)
                renderer.triangleDownRight(blockWith, x + side, y - width)
            }
            Orientation.Up -> {
                renderer.triangleUpLeft(blockWith, x - side, y - width)
                renderer.triangleUpRight(blockWith, x + side, y - width)
            }

        }
        renderer.endFill()
    }

}