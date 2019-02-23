package de.tfr.game.renderer


import com.soywiz.korge.view.Graphics
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.IRectangle
import com.soywiz.korma.geom.shape.Shape2d
import com.soywiz.korma.geom.vector.circle
import com.soywiz.korma.geom.vector.rect
import de.tfr.game.lib.actor.Point
import de.tfr.game.lib.actor.Point2D
import de.tfr.game.model.*
import de.tfr.game.ui.BLACK
import de.tfr.game.ui.GRAY_DARK
import de.tfr.game.ui.GREEN_LIGHT
import de.tfr.game.ui.GREEN_LIGHT2
import de.tfr.game.util.extensions.startFill
import de.tfr.game.util.extensions.triangle


class GameFieldRenderer(point: Point) : Point by point {

    private val gap = 6
    private val blockWith = 18f
    private val radius = 8f
    private lateinit var renderer: Graphics

    fun start() {}

    fun render(field: GameField, renderer: Graphics) {
        this.renderer = renderer
        renderBackground(field, renderer)
        renderer.startFill(GREEN_LIGHT2)
        renderer.circle(x, y, radius)
        renderer.endFill()

        field.forEach(this::renderRing)
    }

    private fun renderBackground(field: GameField, renderer: Graphics) {
        renderer.startFill(GREEN_LIGHT)
        val radius = getFieldSize(field)
        renderer.rect(x - radius, y - radius, radius * 2, radius * 2)
        renderer.endFill()
    }

    fun getFieldSize(field: GameField): Double = (blockWith / 2.0) + field.size * (gap + blockWith)

    fun end() {
        renderer.endFill()
    }

    private fun renderRing(ring: Ring) {
        ring.forEach { renderBlock(it, it.stone) }
    }

    fun IRectangle.center(): Point {
        return Point2D((x + (width / 2.0)), (y + (height / 2.0)))
    }

    fun renderTouchArea(touchAreas: List<Shape2d.Rectangle>) {
        renderer.startFill(Colors.NAVY)

        touchAreas.forEach {
            val center = it.center()
            renderer.circle(center.x, center.y, it.width / 2)
        }
    }

    fun renderStone(stone: Stone) {
        renderBlock(stone.block, stone)
    }

    private fun renderBlock(block: Block, stone: Stone?) {
        val distance = gap + blockWith + (block.row * (gap + blockWith))
        when (block.orientation) {
            Orientation.Left -> renderBlock(block, stone, x - distance, y)
            Orientation.Right -> renderBlock(block, stone, x + distance, y)
            Orientation.Up -> renderBlock(block, stone, x, y + distance)
            Orientation.Down -> renderBlock(block, stone, x, y - distance)
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
            Orientation.Up -> renderer.rect(x - side, y - width, length, blockWith)
            Orientation.Down -> renderer.rect(x - side, y - width, length, blockWith)
        }

        fun renderTriangleLeftUp(x: Double, y: Double) = renderer.triangle(x, y, x, y + blockWith, x + blockWith, y)
        fun renderTriangleLeftDown(x: Double, y: Double) = renderer.triangle(x, y, x, y - blockWith, x + blockWith, y)
        fun renderTriangleRightUp(x: Double, y: Double) = renderer.triangle(x,
                y,
                x + blockWith,
                y + blockWith,
                x + blockWith,
                y)

        fun renderTriangleRightDown(x: Double, y: Double) = renderer.triangle(x,
                y,
                x + blockWith,
                y - blockWith,
                x + blockWith,
                y)

        fun renderTriangleUpLeft(x: Double, y: Double) = renderer.triangle(x,
                y,
                x,
                y + blockWith,
                x - blockWith,
                y + blockWith)

        fun renderTriangleUpRight(x: Double, y: Double) = renderer.triangle(x,
                y,
                x,
                y + blockWith,
                x + blockWith,
                y + blockWith)

        fun renderTriangleDownLeft(x: Double, y: Double) = renderer.triangle(x, y, x, y + blockWith, x - blockWith, y)
        fun renderTriangleDownRight(x: Double, y: Double) = renderer.triangle(x, y, x, y + blockWith, x + blockWith, y)

        when (block.orientation) {
            Orientation.Left -> {
                renderTriangleLeftUp(x - width, y + side)
                renderTriangleLeftDown(x - width, y - side)
            }
            Orientation.Right -> {
                renderTriangleRightUp(x - width, y + side)
                renderTriangleRightDown(x - width, y - side)
            }
            Orientation.Up -> {
                renderTriangleUpLeft(x - side, y - width)
                renderTriangleUpRight(x + side, y - width)
            }
            Orientation.Down -> {
                renderTriangleDownLeft(x - side, y - width)
                renderTriangleDownRight(x + side, y - width)
            }

        }
        renderer.endFill()
    }

}